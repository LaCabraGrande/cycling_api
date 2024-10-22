package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.WheelDAO;
import dat.dtos.WheelDTO;
import dat.entities.Wheel;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.Map;

public class WheelController implements IController<IController> {

    private final WheelDAO wheelDAO;

    public WheelController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.wheelDAO = WheelDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(wheelDAO.getAll());
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
            WheelDTO wheelDTO = wheelDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(wheelDTO, WheelDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid wheel ID format.",
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
            WheelDTO wheelDTO = ctx.bodyAsClass(WheelDTO.class);
            if (wheelDTO == null) {
                throw new HttpResponseException(400, "Invalid wheel data provided.");
            }
            WheelDTO newWheel = wheelDAO.add(wheelDTO);
            ctx.status(201).json(newWheel);
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
            WheelDTO wheelDTO = wheelDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(wheelDTO, WheelDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid wheel ID format.",
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
            Wheel deletedWheel = wheelDAO.delete(id).toEntity();

            ctx.status(200).json(deletedWheel);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid wheel ID format.",
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
        return wheelDAO.validatePrimaryKey(integer);
    }

    public WheelDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(WheelDTO.class)
                .check(p -> p.getType() != null && !p.getType().isEmpty(), "Wheel type must be set")
                .check(p -> p.getBrand() != null && !p.getBrand().isEmpty(), "Wheel brand must be set")
                .check(p -> p.getMaterial() != null && !p.getMaterial().isEmpty(),  "Wheel material must be set")
                .check(p -> p.getWeight() != 0, "Weight must be set")
                .check(p -> p.getSize() != 0, "Size must be set")
                .get();
    }
}
