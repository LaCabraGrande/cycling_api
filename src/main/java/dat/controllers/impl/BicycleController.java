package dat.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.*;
import dat.dtos.*;
import dat.entities.Bicycle;
import dat.utils.JsonUtil;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dat.config.Populate.populateDatabase;

public class BicycleController implements IController<BicycleDTO> {

    private final BicycleDAO bicycleDAO;
    private final FrameDAO frameDAO;
    private final GearDAO gearDAO;
    private final WheelDAO wheelDAO;
    private final SaddleDAO saddleDAO;

    private static final Logger logger = LoggerFactory.getLogger(BicycleController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public BicycleController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.bicycleDAO = BicycleDAO.getInstance(emf);
        this.frameDAO = FrameDAO.getInstance(emf);
        this.gearDAO = GearDAO.getInstance(emf);
        this.wheelDAO = WheelDAO.getInstance(emf);
        this.saddleDAO = SaddleDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            List<BicycleDTO> bicyclesDTOS = bicycleDAO.getAll();
            ctx.json(bicyclesDTOS);
        } catch (Exception e) {
            logger.error("Unknown error occurred", e);
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

//    public void getFilterCounts(Context ctx) {
//        FilterCountDTO filterCountDTO = bicycleDAO.getFilterCounts();
//        ctx.json(filterCountDTO);
//    }

    // Modtag filtrene via Context
    public void getFilteredCounts(Context ctx) {
        try {
            // Hent query-parametre fra Context
            List<String> gearSeries = ctx.queryParams("gearSeries");
            List<String> saddleBrand = ctx.queryParams("saddleBrand");
            List<String> wheelBrand = ctx.queryParams("wheelBrand");
            List<String> bicycleBrand = ctx.queryParams("bicycleBrand");
            List<String> bicycleType = ctx.queryParams("bicycleType");
            List<String> wheelType = ctx.queryParams("wheelType");
            List<String> priceInterval = ctx.queryParams("priceInterval");

            // Konverter de modtagne filtre til en Map
            Map<String, List<String>> filters = Map.of(
                    "gearSeries", gearSeries,
                    "saddleBrand", saddleBrand,
                    "wheelBrand", wheelBrand,
                    "bicycleBrand", bicycleBrand,
                    "bicycleType", bicycleType,
                    "wheelType", wheelType,
                    "priceInterval", priceInterval
            );

            boolean noFiltersActive = filters.values().stream().allMatch(List::isEmpty);

            if (noFiltersActive) {
                logger.info("📦 Ingen aktive filtre – sender initial_filtercounts.json");
                String json = JsonUtil.loadStaticJson("static/initial_filtercounts.json");
                ctx.contentType("application/json").result(json);
                return;
            }


            // Brug DAO til at hente filtrerede tællinger
            FilterCountDTO filterCountDTO = bicycleDAO.getFilteredCounts(filters);

            // Returner resultatet som JSON
            ctx.json(filterCountDTO);
        } catch (Exception e) {
            // Håndter eventuelle fejl
            ctx.status(500).result("Error processing request: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BicycleDTO bicycleDTO = bicycleDAO.getById(id);
            if (bicycleDTO != null) {
                ctx.json(bicycleDTO);  // Sends the bicycleDTO as JSON
            } else {
                throw new NotFoundResponse("Bicycle ID not found");  // Exception is thrown
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID, wrong format",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
        catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void getBicyclesCreatedByUser(Context ctx) {
        try {
            String username = ctx.pathParam("username");
            List<BicycleDTO> bicyclesDTOS = bicycleDAO.getBicyclesCreatedByUser(username);
            ctx.json(bicyclesDTOS);
        } catch (Exception e) {
            logger.error("Unknown error occurred", e);
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void filterByComponents(Context ctx) {
        int saddleId = 0;
        int frameId = 0;
        int wheelId = 0;
        int gearId = 0;

        try {
            if (ctx.queryParam("saddleId") != null) {
                saddleId = Integer.parseInt(ctx.queryParam("saddleId"));
            }
            if (ctx.queryParam("frameId") != null) {
                frameId = Integer.parseInt(ctx.queryParam("frameId"));
            }
            if (ctx.queryParam("wheelId") != null) {
                wheelId = Integer.parseInt(ctx.queryParam("wheelId"));
            }
            if (ctx.queryParam("gearId") != null) {
                gearId = Integer.parseInt(ctx.queryParam("gearId"));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid query parameter. Parameters must be integers.");
            return;
        }

        try {
            List<BicycleDTO> filteredBicycles = bicycleDAO.filterByComponents(saddleId, frameId, wheelId, gearId);
            ctx.json(filteredBicycles);
        } catch (RuntimeException e) {
            ctx.status(500).result("An error occurred while filtering bicycles: " + e.getMessage());
        }
    }

    public void getFilteredBicycles(Context ctx) {
        try {
            // Hent query-parametre fra Context
            List<String> gearSeries = ctx.queryParams("gearSeries");
            List<String> saddleBrand = ctx.queryParams("saddleBrand");
            List<String> wheelBrand = ctx.queryParams("wheelBrand");
            List<String> bicycleBrand = ctx.queryParams("bicycleBrand");
            List<String> bicycleType = ctx.queryParams("bicycleType");
            List<String> wheelType = ctx.queryParams("wheelType");
            List<String> priceInterval = ctx.queryParams("priceInterval");


            int minPrice = 0;
            String minPriceParam = ctx.queryParam("minPrice");
            if (minPriceParam != null && !minPriceParam.isEmpty()) {
                minPrice = Integer.parseInt(minPriceParam);
            }

            int maxPrice = 50000;
            String maxPriceParam = ctx.queryParam("maxPrice");
            if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
                maxPrice = Integer.parseInt(maxPriceParam);
            }

            // Byg filter-mappe
            Map<String, List<String>> filters = new HashMap<>();
            if (!gearSeries.isEmpty()) {
                filters.put("gearSeries", gearSeries);
            }
            if (!saddleBrand.isEmpty()) {
                filters.put("saddleBrand", saddleBrand);
            }
            if (!wheelBrand.isEmpty()) {
                filters.put("wheelBrand", wheelBrand);
            }
            if (!bicycleBrand.isEmpty()) {
                filters.put("bicycleBrand", bicycleBrand);
            }
            if (!bicycleType.isEmpty()) {
                filters.put("bicycleType", bicycleType);
            }
            if (!wheelType.isEmpty()) {
                filters.put("wheelType", wheelType);
            }
            if (!priceInterval.isEmpty()) {
                filters.put("priceInterval", priceInterval);
            }

            if (filters.isEmpty()) {
                String filename = "static/initial_bicycles.json";
                logger.info("📦 Ingen aktive filtre - sender initial_bicycles.json");
                //String json = JsonUtil.loadStaticJson(filename);
                String json = JsonUtil.loadStaticJson("static/initial_bicycles.json");
                ctx.contentType("application/json").result(json);
                return;
            }

            // Kald DAO for at hente filtrerede cykler
            List<BicycleDTO> bicyclesDTOS = bicycleDAO.getBicyclesByFilters(filters, minPrice, maxPrice);

            // Returner resultat som JSON
            ctx.json(bicyclesDTOS);
        } catch (Exception e) {
            // Returner fejlmeddelelse
            ctx.status(500).json(Map.of("error", "Error fetching bicycles: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            BicycleDTO bicycleDTO = ctx.bodyAsClass(BicycleDTO.class);
            BicycleDTO savedResellerDTO = bicycleDAO.add(bicycleDTO);
            ctx.status(201).json(savedResellerDTO);
        } catch (Exception e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle data",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void createdByUser(Context ctx) {
        try {
            BicycleDTO bicycleDTO = ctx.bodyAsClass(BicycleDTO.class);
            BicycleDTO savedResellerDTO = bicycleDAO.add(bicycleDTO);
            ctx.status(201).json(savedResellerDTO);
        } catch (Exception e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle data",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void createWithComponents(Context ctx) {
        try {
            BicycleCompleteDTO bicycleCompleteDTO = ctx.bodyAsClass(BicycleCompleteDTO.class);

            int frameId = bicycleCompleteDTO.getFrameId();
            int gearId = bicycleCompleteDTO.getGearId();
            int wheelId = bicycleCompleteDTO.getWheelId();
            int saddleId = bicycleCompleteDTO.getSaddleId();

            // Hent entiteter fra DAO
            FrameDTO frameDTO = frameDAO.getById(frameId);
            GearDTO gearDTO = gearDAO.getById(gearId);
            WheelDTO wheelDTO = wheelDAO.getById(wheelId);
            SaddleDTO saddleDTO = saddleDAO.getById(saddleId);

            // Opret BicycleDTO
            BicycleDTO bicycleDTO = new BicycleDTO();
            bicycleDTO.setBrand(bicycleCompleteDTO.getBrand());
            bicycleDTO.setModel(bicycleCompleteDTO.getModel());
            bicycleDTO.setSize(bicycleCompleteDTO.getSize());
            bicycleDTO.setPrice(bicycleCompleteDTO.getPrice());
            bicycleDTO.setWeight(bicycleCompleteDTO.getWeight());
            bicycleDTO.setLink(bicycleCompleteDTO.getLink());
            bicycleDTO.setUsername(bicycleCompleteDTO.getUsername());
            bicycleDTO.setFrame(frameDTO);
            bicycleDTO.setGear(gearDTO);
            bicycleDTO.setWheel(wheelDTO);
            bicycleDTO.setSaddle(saddleDTO);

            // Send til DAO
            BicycleDTO savedBicycle = bicycleDAO.createWithComponents(bicycleDTO);

            // Returner response
            ctx.status(201).json(savedBicycle);
        } catch (Exception e) {
            ctx.status(500); // Internal Server Error
            ctx.result("Error creating bicycle with components: " + e.getMessage());
        }
    }


    public void addFrameToBicycle(Context ctx) {
        try {
            int bicycleId = Integer.parseInt(ctx.pathParam("bicycleId"));
            int frameId = Integer.parseInt(ctx.pathParam("frameId"));
            Bicycle bicycle = bicycleDAO.addFrameToBicycle(bicycleId, frameId);
            ctx.json(bicycle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle or frame ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Log fejlen
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add frame to bicycle: " + e.getMessage(), // Inkluder undtagelsesbeskeden
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void addGearToBicycle(Context ctx) {
        try {
            int bicycleId = Integer.parseInt(ctx.pathParam("bicycleId"));
            int gearId = Integer.parseInt(ctx.pathParam("gearId"));
            Bicycle bicycle = bicycleDAO.addGearToBicycle(bicycleId, gearId);
            ctx.json(bicycle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle or gear ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add Gear to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void addSaddleToBicycle(Context ctx) {
        try {
            int bicycleId = Integer.parseInt(ctx.pathParam("bicycleId"));
            int saddleId = Integer.parseInt(ctx.pathParam("saddleId"));
            Bicycle bicycle = bicycleDAO.addSaddleToBicycle(bicycleId, saddleId);
            ctx.json(bicycle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle or saddle ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add saddle to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void addWheelToBicycle(Context ctx) {
        try {
            int bicycleId = Integer.parseInt(ctx.pathParam("bicycleId"));
            int wheelId = Integer.parseInt(ctx.pathParam("wheelId"));
            Bicycle bicycle = bicycleDAO.addWheelToBicycle(bicycleId, wheelId);
            ctx.json(bicycle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle or wheel ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add wheel to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void addAllComponentsToBicycle(Context ctx) {
        try {
            int bicycleId = Integer.parseInt(ctx.pathParam("bicycleId"));
            int frameId = Integer.parseInt(ctx.pathParam("frameId"));
            int gearId = Integer.parseInt(ctx.pathParam("gearId"));
            int saddleId = Integer.parseInt(ctx.pathParam("saddleId"));
            int wheelId = Integer.parseInt(ctx.pathParam("wheelId"));
            Bicycle bicycle = bicycleDAO.addAllComponentsToBicycle(bicycleId, frameId, gearId, saddleId, wheelId);
            ctx.json(bicycle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle or component ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add components to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BicycleDTO bicycleDTO = ctx.bodyAsClass(BicycleDTO.class);
            BicycleDTO updatedBicycleDTO = bicycleDAO.update(id, bicycleDTO);
            if (updatedBicycleDTO != null) {
                ctx.json(updatedBicycleDTO);
            } else {
                throw new NotFoundResponse("Bicycle not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void updateAllDetails(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BicycleCompleteDTO bicycleCompleteDTO = ctx.bodyAsClass(BicycleCompleteDTO.class);

            int frameId = bicycleCompleteDTO.getFrameId();
            int gearId = bicycleCompleteDTO.getGearId();
            int wheelId = bicycleCompleteDTO.getWheelId();
            int saddleId = bicycleCompleteDTO.getSaddleId();

            // Henter entiteter fra DAO
            FrameDTO frameDTO = frameDAO.getById(frameId);
            GearDTO gearDTO = gearDAO.getById(gearId);
            WheelDTO wheelDTO = wheelDAO.getById(wheelId);
            SaddleDTO saddleDTO = saddleDAO.getById(saddleId);

            // Opretter BicycleDTO
            BicycleDTO bicycleDTO = new BicycleDTO();
            bicycleDTO.setBrand(bicycleCompleteDTO.getBrand());
            bicycleDTO.setModel(bicycleCompleteDTO.getModel());
            bicycleDTO.setSize(bicycleCompleteDTO.getSize());
            bicycleDTO.setPrice(bicycleCompleteDTO.getPrice());
            bicycleDTO.setWeight(bicycleCompleteDTO.getWeight());
            bicycleDTO.setLink(bicycleCompleteDTO.getLink());
            bicycleDTO.setFrame(frameDTO);
            bicycleDTO.setGear(gearDTO);
            bicycleDTO.setWheel(wheelDTO);
            bicycleDTO.setSaddle(saddleDTO);

            // Opdaterer BicycleDTO'en med alle detaljer
            BicycleDTO updatedBicycleDTO = bicycleDAO.updateAllDetails(id, bicycleDTO);

            if (updatedBicycleDTO != null) {
                ctx.json(updatedBicycleDTO);
            } else {
                throw new NotFoundResponse("Bicycle not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BicycleDTO bicycle = bicycleDAO.delete(id);
            if (bicycle != null) {
                String jsonResponse = String.format("{\"Message\": \"Bicycle deleted\", \"bicycle\": %s}",
                        OBJECT_MAPPER.writeValueAsString(bicycle));
                ctx.status(200).json(jsonResponse);
            } else {
                throw new NotFoundResponse("Bicycle not found");
            }

        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        return bicycleDAO.validatePrimaryKey(integer);
    }

    // Tænker at vi måske skal flytte den her metode, da det ikke er en del af CRUD
    public void populate(Context ctx) {
        try {
            if(!bicycleDAO.getAll().isEmpty()) {
                ctx.status(400).json(Map.of(
                        "status", 400,
                        "message", "The Bicycle Database is already populated",
                        "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ));
            } else {
                populateDatabase();
                ctx.res().setStatus(200);
                ctx.json("{ \"Message\": \"The Bicycle Database has been populated\" }");
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }
}
