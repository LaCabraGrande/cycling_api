package dat.routes;

import dat.controllers.impl.FrameController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class FrameRoute {

    private final FrameController frameController = new FrameController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", frameController::getAll);
            get("/{id}", frameController::getById);
            post("/", frameController::create, Role.USER);
            put("/{id}", frameController::update, Role.USER);
            delete("/{id}", frameController::delete, Role.USER);
        };
    }
}
