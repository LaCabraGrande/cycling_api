package dat.routes;

import dat.controllers.impl.SaddleController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class SaddleRoute {

    private final SaddleController saddleController = new SaddleController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", saddleController::getAll, Role.ANYONE);
            get("/{id}", saddleController::getById, Role.ANYONE);
            post("/",saddleController::create, Role.USER);
            put("/{id}", saddleController::update, Role.USER);
            delete("/{id}",saddleController::delete, Role.USER);
        };
    }
}
