package dat.routes;

import dat.controllers.impl.GearController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class GearRoute {

    private final GearController gearController = new GearController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", gearController::getAll);
            get("/{id}", gearController::getById);
            post("/", gearController::create);
            put("/{id}",  gearController::update);
            delete("/{id}",  gearController::delete);
        };
    }
}
