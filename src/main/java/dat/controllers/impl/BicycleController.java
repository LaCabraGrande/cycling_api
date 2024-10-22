package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.controllers.IController;
import dat.daos.impl.BicycleDAO;
import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
                    "message", "Invalid reseller data",
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
                        "message", "Reseller not found",
                        "timestamp", LocalDateTime.now()
                ));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid reseller ID",
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
                    "message", "Invalid reseller or plant ID",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Log fejlen
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", "Failed to add plant to reseller: " + e.getMessage(), // Inkluder undtagelsesbeskeden
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
                throw new NotFoundResponse("Reseller not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid reseller ID",
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
                throw new NotFoundResponse("Reseller not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid reseller ID",
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

    public void populate(Context ctx) {
        Populate populate = new Populate();
        populate.populateDatabase();
        ctx.res().setStatus(200);
        ctx.json("{ \"message\": \"Database has been populated\" }");
    }
}
