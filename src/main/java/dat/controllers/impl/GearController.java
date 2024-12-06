package dat.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.GearDAO;
import dat.dtos.GearDTO;
import dat.entities.Frame;
import dat.entities.Gear;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class GearController implements IController<IController> {

    private final GearDAO gearDAO;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public GearController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.gearDAO = GearDAO.getInstance(emf);
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(gearDAO.getAll());
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
            GearDTO gearDTO = gearDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(gearDTO, GearDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid gear ID format.",
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
            GearDTO gearDTO = ctx.bodyAsClass(GearDTO.class);
            if (gearDTO == null) {
                throw new HttpResponseException(400, "Invalid gear data provided.");
            }
            GearDTO newGear = gearDAO.add(gearDTO);
            ctx.status(201).json(newGear);
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
            GearDTO gearDTO = gearDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(gearDTO, GearDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid gear ID format.",
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
            Gear gear = gearDAO.delete(id).toEntity();
            if (gear != null) {
                String jsonResponse = String.format("{\"Message\": \"Gear deleted\", \"gear\": %s}",
                        OBJECT_MAPPER.writeValueAsString(gear));
                ctx.status(200).json(jsonResponse);
            } else {
                throw new NotFoundResponse("Gear not found");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid gear ID format.",
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
        return gearDAO.validatePrimaryKey(integer);
    }

    public GearDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(GearDTO.class)
                .check(p -> p.getType() != null && !p.getType().isEmpty(), "Gear type must be set")
                .check(p -> p.getBrand() != null && !p.getBrand().isEmpty(), "Gear name must be set")
                .check(p -> p.getMaterial() != null && !p.getMaterial().isEmpty(),  "Gear material must be set")
                .check(p -> p.getWeight() != 0, "Gear weight must be set")
                .get();
    }
}
