package dat.controllers.impl;

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
import java.util.List;
import java.util.Map;

import static dat.config.Populate.populateDatabase;

public class BicycleController implements IController<BicycleDTO> {

    private final BicycleDAO bicycleDAO;
    private static final Logger logger = LoggerFactory.getLogger(BicycleController.class);

    public BicycleController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.bicycleDAO = BicycleDAO.getInstance(emf);
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            BicycleDTO bicycleDTO = bicycleDAO.getById(id);
            if (bicycleDTO != null) {
                ctx.json(bicycleDTO);
            } else {
                ctx.status(404).json(Map.of(
                        "status", 404,
                        "message", "Bicycle not found",
                        "timestamp", LocalDateTime.now()
                ));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
            ));
        }
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
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Log fejlen
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add frame to bicycle: " + e.getMessage(), // Inkluder undtagelsesbeskeden
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add Gear to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add saddle to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add wheel to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add components to bicycle: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
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
                    "timestamp", LocalDateTime.now()
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Bicycle bicycle = bicycleDAO.delete(id);
            if (bicycle != null) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("Bicycle not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid bicycle ID",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Throwable e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error",
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    // Tænker at vi måske skal flytte den her metode, da det ikke er en del af CRUD
    public void populate(Context ctx) {
        populateDatabase();
        ctx.res().setStatus(200);
        ctx.json("{ \"Message\": \"The Bicycle Database has been populated\" }");
    }
}
