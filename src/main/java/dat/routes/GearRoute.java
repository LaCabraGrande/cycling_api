package dat.routes;

import dat.controllers.impl.GearController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class GearRoute {

    private final GearController gearController = new GearController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", gearController::getAll, Role.ANYONE);
            get("/{id}", gearController::getById, Role.ANYONE);
            get("createdbyuser/{username}", gearController::getByUser, Role.ANYONE);
            post("/", gearController::create, Role.USER);
            put("/{id}",  gearController::update, Role.USER);
            delete("/{id}",  gearController::delete, Role.USER);
        };
    }
}
