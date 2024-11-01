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
        // Opretter og gemmer cykler
        Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", 56, 3500, 8.7, "High-performance road bike");
        Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX", 54, 4000, 6.8, "Lightweight racing bike");
        Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", 58, 5000, 7.6, "Aero road bike");

        BicycleDTO b1 = bicycleDAO.add(new BicycleDTO(bicycle1));
        BicycleDTO b2 = bicycleDAO.add(new BicycleDTO(bicycle2));
        BicycleDTO b3 = bicycleDAO.add(new BicycleDTO(bicycle3));

        // Opretter gear
        Gear gear1 = createGear("Shimano", "Ultegra", "Aluminium", "Electronic", 1422);
        Gear gear2 = createGear("SRAM", "Red", "Aluminium", "Electronic", 1622);
        Gear gear3 = createGear("Shimano", "Dura-Ace", "Carbon", "Mechanical", 1600);

        GearDTO g1 = gearDAO.add(new GearDTO(gear1));
        GearDTO g2 = gearDAO.add(new GearDTO(gear2));
        GearDTO g3 = gearDAO.add(new GearDTO(gear3));

        // Knyt gear til cykler
        bicycle1 = bicycleDAO.addGearToBicycle(b1.getId(), Math.toIntExact(g1.getId()));
        bicycle2 = bicycleDAO.addGearToBicycle(b2.getId(), Math.toIntExact(g2.getId()));
        bicycle3 = bicycleDAO.addGearToBicycle(b3.getId(), Math.toIntExact(g3.getId()));

        // Opretter hjul
        Wheel wheel1 = createWheel("Zipp", "Carbon", "Disc", "404 Firecrest", 1400, 25);
        Wheel wheel2 = createWheel("Mavic", "Aluminium", "Rim", "Ksyrium Pro", 1300, 23);
        Wheel wheel3 = createWheel("DT Swiss", "Carbon", "Disc", "PRC 1400", 1600, 28);

        WheelDTO w1 = wheelDAO.add(new WheelDTO(wheel1));
        WheelDTO w2 = wheelDAO.add(new WheelDTO(wheel2));
        WheelDTO w3 = wheelDAO.add(new WheelDTO(wheel3));

        // Knyt hjul til cykler
        bicycle1 = bicycleDAO.addWheelToBicycle(b1.getId(), Math.toIntExact(w1.getId()));
        bicycle2 = bicycleDAO.addWheelToBicycle(b2.getId(), Math.toIntExact(w2.getId()));
        bicycle3 = bicycleDAO.addWheelToBicycle(b3.getId(), Math.toIntExact(w3.getId()));

        // Opretter sadler
        Saddle saddle1 = createSaddle("Fizik", "Carbon", "Arione", 200, 140);
        Saddle saddle2 = createSaddle("Brooks", "Leather", "Classic", 350, 160);
        Saddle saddle3 = createSaddle("Selle Italia", "Leather", "Flite", 300, 155);

        SaddleDTO s1 = saddleDAO.add(new SaddleDTO(saddle1));
        SaddleDTO s2 = saddleDAO.add(new SaddleDTO(saddle2));
        SaddleDTO s3 = saddleDAO.add(new SaddleDTO(saddle3));

        // Knyt sadler til cykler
        bicycle1 = bicycleDAO.addSaddleToBicycle(b1.getId(), Math.toIntExact(s1.getId()));
        bicycle2 = bicycleDAO.addSaddleToBicycle(b2.getId(), Math.toIntExact(s2.getId()));
        bicycle3 = bicycleDAO.addSaddleToBicycle(b3.getId(), Math.toIntExact(s3.getId()));

        // Opretter rammer
        Frame frame1 = createFrame("Canyon", "Carbon", "Disc", 1200, 56);
        Frame frame2 = createFrame("Olmo", "Aluminium", "Rim", 1600, 54);
        Frame frame3 = createFrame("Pinarello", "Carbon", "Disc", 1200, 58);

        FrameDTO f1 = frameDAO.add(new FrameDTO(frame1));
        FrameDTO f2 = frameDAO.add(new FrameDTO(frame2));
        FrameDTO f3 = frameDAO.add(new FrameDTO(frame3));

        // Knyt rammer til cykler
        bicycle1 = bicycleDAO.addFrameToBicycle(b1.getId(), Math.toIntExact(f1.getId()));
        bicycle2 = bicycleDAO.addFrameToBicycle(b2.getId(), Math.toIntExact(f2.getId()));
        bicycle3 = bicycleDAO.addFrameToBicycle(b3.getId(), Math.toIntExact(f3.getId()));

        // Returner de oprettede cykler
        return new ArrayList<>(List.of(new BicycleDTO(bicycle1), new BicycleDTO(bicycle2), new BicycleDTO(bicycle3)));
    }

    private Gear createGear(String brand, String model, String material, String type, int weight) {
        Gear gear = new Gear();
        gear.setBrand(brand);
        gear.setModel(model);
        gear.setMaterial(material);
        gear.setType(type);
        gear.setWeight(weight);
        return gear;
    }

    private Wheel createWheel(String brand, String material, String type, String model, int weight, int size) {
        Wheel wheel = new Wheel();
        wheel.setBrand(brand);
        wheel.setMaterial(material);
        wheel.setType(type);
        wheel.setModel(model);
        wheel.setWeight(weight);
        wheel.setSize(size);
        return wheel;
    }

    private Saddle createSaddle(String brand, String material, String model, int weight, int width) {
        Saddle saddle = new Saddle();
        saddle.setBrand(brand);
        saddle.setMaterial(material);
        saddle.setModel(model);
        saddle.setWeight(weight);
        saddle.setWidth(width);
        return saddle;
    }

    private Frame createFrame(String brand, String material, String type, int weight, int size) {
        Frame frame = new Frame();
        frame.setBrand(brand);
        frame.setMaterial(material);
        frame.setType(type);
        frame.setWeight(weight);
        frame.setSize(size);
        return frame;
    }
}

