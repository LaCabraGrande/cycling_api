package dat.routes;

import dat.controllers.impl.WheelController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class WheelRoute {

    private final WheelController wheelController = new WheelController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", wheelController::getAll);
            get("/{id}", wheelController::getById);
            get("/type/{type}", wheelController::getByType);
            post("/", wheelController::create);
            put("/{id}", wheelController::update);
            delete("/{id}", wheelController::delete);
        };
    }
}
