package dat.routes;

import dat.controllers.impl.WheelController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class WheelRoute {

    private final WheelController wheelController = new WheelController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", wheelController::getAll, Role.ANYONE);
            get("/{id}", wheelController::getById, Role.ANYONE);
            post("/", wheelController::create, Role.USER);
            put("/{id}", wheelController::update, Role.USER);
            delete("/{id}", wheelController::delete, Role.USER);
        };
    }
}
