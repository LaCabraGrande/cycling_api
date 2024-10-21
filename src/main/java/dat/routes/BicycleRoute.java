package dat.routes;

import dat.controllers.impl.BicycleController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;


public class BicycleRoute {

    private final BicycleController bicycleController = new BicycleController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", bicycleController::getAll);
            get("/{id}", bicycleController::getById);
            post("/", bicycleController::create);
            post("/{bicycleId}/frame/{frameId}", bicycleController::addFrameToBicycle);
            post("/{bicycleId}/gear/{gearId}", bicycleController::addGearToBicycle);
            post("/{bicycleId}/wheel/{wheelId}", bicycleController::addWheelToBicycle);
            post("/{bicycleId}/saddle/{saddleId}", bicycleController::addSaddleToBicycle);
            put("/{id}", bicycleController::update);
            delete("/{id}", bicycleController::delete);
        };
    }
}
