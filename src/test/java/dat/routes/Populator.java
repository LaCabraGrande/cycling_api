package dat.routes;

import dat.daos.impl.*;
import dat.dtos.*;
import dat.entities.*;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {

    private BicycleDAO bicycleDAO;
    private GearDAO gearDAO;
    private WheelDAO wheelDAO;
    private SaddleDAO saddleDAO;
    private FrameDAO frameDAO;

    private EntityManagerFactory emf;

    public Populator(BicycleDAO bicycleDAO, FrameDAO frameDAO, GearDAO gearDAO, WheelDAO wheelDAO, SaddleDAO saddleDAO, EntityManagerFactory emf) {
        this.bicycleDAO = bicycleDAO;
        this.gearDAO = gearDAO;
        this.wheelDAO = wheelDAO;
        this.saddleDAO = saddleDAO;
        this.frameDAO = frameDAO;
        this.emf = emf;
    }

    public List<BicycleDTO> populate3bicycles() {
        // Opretter cykler uden komponenter
        Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", 56, 3500, 8.7, "High-performance road bike", "admin");
        Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX", 54, 4000, 6.8, "Lightweight racing bike", "admin");
        Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", 58, 5000, 7.6, "Aero road bike", "admin");

        System.out.println("Oprettet cykel 1: " + bicycle1);
        System.out.println("Oprettet cykel 2: " + bicycle2);
        System.out.println("Oprettet cykel 3: " + bicycle3);

        // Opretter komponenter og gemmer dem i databasen
        Gear gear1 = createGear("Shimano", "Ultegra R8100", "Shimano Ultegra","Aluminium", "Electronic", "Disc", 1422, "admin");
        Gear gear2 = createGear("SRAM", "Red", "SRAM Red","Aluminium", "Electronic", "Disc", 1622, "admin");
        Gear gear3 = createGear("Shimano", "Dura-Ace R9200", "Shimano Dura-Ace", "Carbon", "Mechanical", "Disc", 1600, "admin");

        System.out.println("Oprettet gear 1: " + gear1);
        System.out.println("Oprettet gear 2: " + gear2);
        System.out.println("Oprettet gear 3: " + gear3);

        Wheel wheel1 = createWheel("Zipp", "Carbon", "Disc", "404 Firecrest", 1400, 25, "admin");
        Wheel wheel2 = createWheel("Mavic", "Aluminium", "Rim", "Ksyrium Pro", 1300, 23, "admin");
        Wheel wheel3 = createWheel("DT Swiss", "Carbon", "Disc", "PRC 1400", 1600, 28, "admin");

        Saddle saddle1 = createSaddle("Fizik", "Carbon", "Arione", 200, 140, "admin");
        Saddle saddle2 = createSaddle("Brooks", "Leather", "Classic", 350, 160, "admin");
        Saddle saddle3 = createSaddle("Selle Italia", "Leather", "Flite", 300, 155, "admin");

        Frame frame1 = createFrame("Canyon", "Aeroad CF SLX", "Carbon", "Disc", 1200, 56, "admin");
        Frame frame2 = createFrame("Olmo", "Olmo s1", "Aluminium", "Rim", 1600, 54, "admin");
        Frame frame3 = createFrame("Pinarello", "Dogma 12", "Carbon", "Disc", 1200, 58, "admin");

        // Gemmer komponenterne i databasen og knytter dem til cykler
        GearDTO g1 = gearDAO.add(new GearDTO(gear1));
        GearDTO g2 = gearDAO.add(new GearDTO(gear2));
        GearDTO g3 = gearDAO.add(new GearDTO(gear3));

        WheelDTO w1 = wheelDAO.add(new WheelDTO(wheel1));
        WheelDTO w2 = wheelDAO.add(new WheelDTO(wheel2));
        WheelDTO w3 = wheelDAO.add(new WheelDTO(wheel3));

        SaddleDTO s1 = saddleDAO.add(new SaddleDTO(saddle1));
        SaddleDTO s2 = saddleDAO.add(new SaddleDTO(saddle2));
        SaddleDTO s3 = saddleDAO.add(new SaddleDTO(saddle3));

        FrameDTO f1 = frameDAO.add(new FrameDTO(frame1));
        FrameDTO f2 = frameDAO.add(new FrameDTO(frame2));
        FrameDTO f3 = frameDAO.add(new FrameDTO(frame3));


        // Knyt komponenterne til cyklerne
        bicycle1 = bicycleDAO.addGearToBicycle(bicycle1.getId(), Math.toIntExact(g1.getId()));
        bicycle2 = bicycleDAO.addGearToBicycle(bicycle2.getId(), Math.toIntExact(g2.getId()));
        bicycle3 = bicycleDAO.addGearToBicycle(bicycle3.getId(), Math.toIntExact(g3.getId()));

        bicycle1 = bicycleDAO.addWheelToBicycle(bicycle1.getId(), Math.toIntExact(w1.getId()));
        bicycle2 = bicycleDAO.addWheelToBicycle(bicycle2.getId(), Math.toIntExact(w2.getId()));
        bicycle3 = bicycleDAO.addWheelToBicycle(bicycle3.getId(), Math.toIntExact(w3.getId()));

        bicycle1 = bicycleDAO.addSaddleToBicycle(bicycle1.getId(), Math.toIntExact(s1.getId()));
        bicycle2 = bicycleDAO.addSaddleToBicycle(bicycle2.getId(), Math.toIntExact(s2.getId()));
        bicycle3 = bicycleDAO.addSaddleToBicycle(bicycle3.getId(), Math.toIntExact(s3.getId()));

        bicycle1 = bicycleDAO.addFrameToBicycle(bicycle1.getId(), Math.toIntExact(f1.getId()));
        bicycle2 = bicycleDAO.addFrameToBicycle(bicycle2.getId(), Math.toIntExact(f2.getId()));
        bicycle3 = bicycleDAO.addFrameToBicycle(bicycle3.getId(), Math.toIntExact(f3.getId()));

        // Opretter DTO'er og returnerer dem
        BicycleDTO b1 = new BicycleDTO(bicycle1);
        BicycleDTO b2 = new BicycleDTO(bicycle2);
        BicycleDTO b3 = new BicycleDTO(bicycle3);

        System.out.println("Oprettet cykel 1 DTO: " + b1);
        System.out.println("Oprettet cykel 2 DTO: " + b2);
        System.out.println("Oprettet cykel 3 DTO: " + b3);

        return new ArrayList<>(List.of(b1, b2, b3));
    }

    private Gear createGear(String brand, String model, String series, String material, String type, String brakes, int weight, String username) {
        Gear gear = new Gear();
        gear.setBrand(brand);
        gear.setModel(model);
        gear.setSeries(series);
        gear.setMaterial(material);
        gear.setType(type);
        gear.setBrakes(brakes);
        gear.setWeight(weight);
        gear.setUsername(username);
        return gear;
    }

    private Wheel createWheel(String brand, String material, String type, String model, int weight, int size, String username) {
        Wheel wheel = new Wheel();
        wheel.setBrand(brand);
        wheel.setMaterial(material);
        wheel.setType(type);
        wheel.setModel(model);
        wheel.setWeight(weight);
        wheel.setSize(size);
        wheel.setUsername(username);
        return wheel;
    }

    private Saddle createSaddle(String brand, String material, String model, int weight, int width, String username) {
        Saddle saddle = new Saddle();
        saddle.setBrand(brand);
        saddle.setMaterial(material);
        saddle.setModel(model);
        saddle.setWeight(weight);
        saddle.setWidth(width);
        saddle.setUsername(username);
        return saddle;
    }

    private Frame createFrame(String brand, String model, String material, String type, int weight, int size, String username) {
        Frame frame = new Frame();
        frame.setBrand(brand);
        frame.setModel(model);
        frame.setMaterial(material);
        frame.setType(type);
        frame.setWeight(weight);
        frame.setSize(size);
        frame.setUsername(username);
        return frame;
    }
}
