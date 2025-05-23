package dat.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.FrameDAO;
import dat.dtos.FrameDTO;
import dat.entities.Bicycle;
import dat.entities.Frame;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FrameController implements IController<IController> {

    private final FrameDAO frameDAO;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public FrameController() {
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
                    "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
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
                    "message", "Invalid frame ID format.",
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

    public void getByUser(Context ctx) {
        try {
            String username = ctx.pathParam("username");
            ctx.json(frameDAO.getByUser(username));
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
            FrameDTO frameDTO = ctx.bodyAsClass(FrameDTO.class);
            if (frameDTO == null) {
                throw new HttpResponseException(400, "Invalid frame data provided.");
            }
            FrameDTO newFrame = frameDAO.add(frameDTO);
            ctx.status(201).json(newFrame);
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
            FrameDTO frameDTO = frameDAO.update(id, validateEntity(ctx) );
            ctx.res().setStatus(200);
            ctx.json(frameDTO, FrameDTO.class);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid frame ID format.",
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
                Frame frame = frameDAO.delete(id).toEntity();
                if (frame != null) {
                    String jsonResponse = String.format("{\"Message\": \"Frame deleted\", \"frame\": %s}",
                            OBJECT_MAPPER.writeValueAsString(frame));
                    ctx.status(200).json(jsonResponse);
                } else {
                    throw new NotFoundResponse("Frame not found");
                }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "status", 400,
                    "message", "Invalid frame ID format.",
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
        return frameDAO.validatePrimaryKey(integer);
    }

    public FrameDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(FrameDTO.class)
                .check(p -> p.getType() != null && !p.getType().isEmpty(), "Frame type must be set")
                .check(p -> p.getBrand() != null && !p.getBrand().isEmpty(), "Frame brand must be set")
                .check(p -> p.getMaterial() != null && !p.getMaterial().isEmpty(),  "Frame material must be set")
                .check(p -> p.getWeight() != 0, "Frame weight must be set")
                .check(p -> p.getSize() != 0, "Frame size must be set")
                .get();
    }
}
