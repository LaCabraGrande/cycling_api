package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.FrameDAO;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PlantControllerDB implements IController<PlantDTO> {

    private final FrameDAO plantDAO;

    public PlantControllerDB() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.plantDAO = FrameDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(plantDAO.getAll());
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    @Override
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlantDTO plantDTO = plantDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(plantDTO, PlantDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid plant ID format.",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    @Override
    public void getByType(Context ctx) {
        try {
            String type = ctx.pathParam("type");
            List<PlantDTO> plantDTOS = plantDAO.getAll().stream()
                    .filter(p -> p.getPlantType().equalsIgnoreCase(type))
                    .toList();

            if (plantDTOS.isEmpty()) {
                throw new NotFoundResponse("No plants found for type: " + type);
            }

            ctx.status(200).json(plantDTOS, PlantDTO.class);
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    public void create(Context ctx) {
        try {
            PlantDTO plantDTO = ctx.bodyAsClass(PlantDTO.class);
            if (plantDTO == null) {
                throw new HttpResponseException(400, "Invalid plant data provided.");
            }
            PlantDTO newPlant = plantDAO.add(plantDTO);
            ctx.status(201).json(newPlant);
        } catch (HttpResponseException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlantDTO plantDTO = plantDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(plantDTO, PlantDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid plant ID format.",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Plant deletedPlant = plantDAO.delete(id);
            if (deletedPlant == null) {
                throw new NotFoundResponse("Plant with id " + id + " not found.");
            }
            ctx.status(200).json(deletedPlant);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid plant ID format.",
                    "timestamp", LocalDateTime.now()
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }



    public boolean validatePrimaryKey(Integer integer) {
        return plantDAO.validatePrimaryKey(integer);
    }

    public PlantDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(PlantDTO.class)
                .check(p -> p.getPlantType() != null && !p.getPlantType().isEmpty(), "Plant type must be set")
                .check(p -> p.getPlantName() != null && !p.getPlantName().isEmpty(), "Plant name must be set")
                .check(p -> p.getPlantHeight() != 0, "MaxHeight must be set")
                .check(p -> p.getPlantPrice() != 0, "Price must be set")
                .get();
    }
}
