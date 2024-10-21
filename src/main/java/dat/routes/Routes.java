package dat.routes;

import io.javalin.apibuilder.EndpointGroup;


import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final FrameRoute frameRoute = new FrameRoute();
    private final GearRoute gearRoute = new GearRoute();
    private final WheelRoute wheelRoute = new WheelRoute();
    private final SaddleRoute saddleRoute = new SaddleRoute();
    private final BicycleRoute bicycleRoute = new BicycleRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/bicycles", bicycleRoute.getRoutes());
            path("/frames", frameRoute.getRoutes());
            path("/gears", gearRoute.getRoutes());
            path("/wheels", wheelRoute.getRoutes());
            path("/saddles", saddleRoute.getRoutes());
        };
    }
}
