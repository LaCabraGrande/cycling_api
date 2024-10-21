package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.FrameDAO;
import dat.dtos.FrameDTO;
import dat.entities.Frame;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.Map;

public class GearController implements IController<IController> {

    private final FrameDAO frameDAO;

    public GearController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.frameDAO = FrameDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(frameDAO.getAll());
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
            FrameDTO frameDTO = frameDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(frameDTO, FrameDTO.class);
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

    public void create(Context ctx) {
        try {
            FrameDTO frameDTO = ctx.bodyAsClass(FrameDTO.class);
            if (frameDTO == null) {
                throw new HttpResponseException(400, "Invalid plant data provided.");
            }
            FrameDTO newPlant = frameDAO.add(frameDTO);
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
            FrameDTO frameDTO = frameDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(frameDTO, FrameDTO.class);
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
            Frame deletedFrame = frameDAO.delete(id).toEntity();

            ctx.status(200).json(deletedFrame);
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
        return frameDAO.validatePrimaryKey(integer);
    }

    public FrameDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(FrameDTO.class)
                .check(p -> p.getType() != null && !p.getType().isEmpty(), "Plant type must be set")
                .check(p -> p.getBrand() != null && !p.getBrand().isEmpty(), "Plant name must be set")
                .check(p -> p.getMaterial() != null && !p.getMaterial().isEmpty(),  "MaxHeight must be set")
                .check(p -> p.getWeight() != 0, "Price must be set")
                .get();
    }
}
