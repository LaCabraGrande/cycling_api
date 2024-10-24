package dat.routes;

import dat.daos.impl.BicycleDAO;
import dat.dtos.BicycleDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {

    private BicycleDAO bicycleDAO;
    private EntityManagerFactory emf;

    public Populator(BicycleDAO bicycleDAO, EntityManagerFactory emf) {
        this.bicycleDAO = bicycleDAO;
        this.emf = emf;
    }

    public List<BicycleDTO> populate3bicyles() {

        BicycleDTO b1, b2, b3;

        // Populate data and test objects
        b1 = new BicycleDTO("Bicycle1", "test", 1, 1, 1, "test god");
        b2 = new BicycleDTO("Bicycle2", "test", 1, 1, 1, "test god");
        b3 = new BicycleDTO("Bicycle3", "test", 1, 1, 1, "test god");

        b1 = bicycleDAO.add(b1);
        b2 = bicycleDAO.add(b2);
        b3 = bicycleDAO.add(b3);
        return new ArrayList<>(List.of(b1, b2, b3));

    }
}
