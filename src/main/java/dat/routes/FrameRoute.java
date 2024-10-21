package dat.routes;

import dat.controllers.impl.FrameController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class FrameRoute {

    private final FrameController frameController = new FrameController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", frameController::getAll);
            get("/{id}", frameController::getById);
            get("/type/{type}", frameController::getByType);
            post("/", frameController::create);
            put("/{id}", frameController::update);
            delete("/{id}", frameController::delete);
        };
    }
}
