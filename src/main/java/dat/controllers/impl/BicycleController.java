package dat.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.BicycleDAO;
import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static dat.config.Populate.populateDatabase;

public class BicycleController implements IController<BicycleDTO> {

    private final BicycleDAO bicycleDAO;
    private static final Logger logger = LoggerFactory.getLogger(BicycleController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public BicycleController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.bicycleDAO = BicycleDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            List<BicycleDTO> bicycleDTOS = bicycleDAO.getAll();
            ctx.json(bicycleDTOS);
        } catch (Exception e) {
            logger.error("Unknown error occurred", e);
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
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

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Bicycle bicycle = bicycleDAO.delete(id);
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
