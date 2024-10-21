package dat.routes;

import dat.controllers.impl.SaddleController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class SaddleRoute {

    private final SaddleController saddleController = new SaddleController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", saddleController::getAll);
            get("/{id}", saddleController::getById);
            post("/",saddleController::create);
            put("/{id}", saddleController::update);
            delete("/{id}",saddleController::delete);
        };
    }
}
