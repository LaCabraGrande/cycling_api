package dat.routes;

import dat.controllers.impl.BicycleController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;


public class BicycleRoute {

    private final BicycleController bicycleController = new BicycleController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/populate", bicycleController::populate, Role.ANYONE);
            get("/", bicycleController::getAll, Role.ANYONE);
            get("/{id}", bicycleController::getById, Role.ANYONE);
            post("/", bicycleController::create, Role.USER);
            post("/{bicycleId}/frame/{frameId}", bicycleController::addFrameToBicycle, Role.USER);
            post("/{bicycleId}/gear/{gearId}", bicycleController::addGearToBicycle, Role.USER);
            post("/{bicycleId}/wheel/{wheelId}", bicycleController::addWheelToBicycle, Role.USER);
            post("/{bicycleId}/saddle/{saddleId}", bicycleController::addSaddleToBicycle, Role.USER);
            post("/{bicycleId}/frame/{frameId}/gear/{gearId}/wheel/{wheelId}/saddle/{saddleId}", bicycleController::addAllComponentsToBicycle, Role.USER);
            put("/{id}", bicycleController::update, Role.USER);
            delete("/{id}", bicycleController::delete, Role.USER);
        };
    }
}
