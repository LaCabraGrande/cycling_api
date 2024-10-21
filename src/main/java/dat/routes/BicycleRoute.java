package dat.routes;

import dat.controllers.impl.BicycleController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;


public class BicycleRoute {

    private final BicycleController bicycleController = new BicycleController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", bicycleController::getAll);
            get("/{id}", bicycleController::getById);
            post("/", bicycleController::create, Role.USER);
            post("/{bicycleId}/frame/{frameId}", bicycleController::addFrameToBicycle, Role.USER);
            //post("/{bicycleId}/gear/{gearId}", bicycleController::addGearToBicycle);
            //post("/{bicycleId}/wheel/{wheelId}", bicycleController::addWheelToBicycle);
            //post("/{bicycleId}/saddle/{saddleId}", bicycleController::addSaddleToBicycle);
            put("/{id}", bicycleController::update, Role.USER);
            delete("/{id}", bicycleController::delete, Role.USER);
        };
    }
}
