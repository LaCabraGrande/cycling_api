package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.SaddleDAO;
import dat.dtos.SaddleDTO;
import dat.entities.Saddle;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SaddleController implements IController<IController> {

    private final SaddleDAO saddleDAO;

    public SaddleController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.saddleDAO = SaddleDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(saddleDAO.getAll());
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    @Override
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SaddleDTO saddleDTO = saddleDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(saddleDTO, SaddleDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid saddle ID format.",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void create(Context ctx) {
        try {
            SaddleDTO saddleDTO = ctx.bodyAsClass(SaddleDTO.class);
            if (saddleDTO == null) {
                throw new HttpResponseException(400, "Invalid saddle data provided.");
            }
            SaddleDTO newSaddle = saddleDAO.add(saddleDTO);
            ctx.status(201).json(newSaddle);
        } catch (HttpResponseException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SaddleDTO saddleDTO = saddleDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(saddleDTO, SaddleDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid saddle ID format.",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Saddle deletedSaddle = saddleDAO.delete(id).toEntity();

            ctx.status(200).json(deletedSaddle);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid saddle ID format.",
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (NotFoundResponse e) {
            ctx.status(404).json(Map.of(
                    "status", 404,
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "status", 500,
                    "message", "Internal server error: " + e.getMessage(),
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        }
    }


    public boolean validatePrimaryKey(Integer integer) {
        return saddleDAO.validatePrimaryKey(integer);
    }

    public SaddleDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(SaddleDTO.class)
                .check(p -> p.getBrand() != null && !p.getBrand().isEmpty(), "Saddle brand must be set")
                .check(p -> p.getModel() != null && !p.getModel().isEmpty(), "Saddle model must be set")
                .check(p -> p.getMaterial() != null && !p.getMaterial().isEmpty(),  "Saddle material must be set")
                .check(p -> p.getWeight() != 0, "Saddle weight must be set")
                .check(p -> p.getWidth() != 0, "Saddle width must be set")
                .get();
    }
}
