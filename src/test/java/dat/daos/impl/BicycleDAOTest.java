package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.*;
import dat.entities.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.Assert.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BicycleDAOTest {

    private static EntityManagerFactory emf;
    private static BicycleDAO bicycleDAO;
    private static FrameDAO frameDAO;
    private static GearDAO gearDAO;
    private static WheelDAO wheelDAO;
    private static SaddleDAO saddleDAO;
    private Bicycle bicycle1;
    private Bicycle bicycle2;
    private Frame frame1;
    private Frame frame2;
    private Gear gear1;
    private Gear gear2;
    private Wheel wheel1;
    private Wheel wheel2;
    private Saddle saddle1;
    private Saddle saddle2;
    private BicycleDTO bicycleDTO1;
    private BicycleDTO bicycleDTO2;
    private FrameDTO frameDTO1;
    private FrameDTO frameDTO2;
    private GearDTO gearDTO1;
    private GearDTO gearDTO2;
    private WheelDTO wheelDTO1;
    private WheelDTO wheelDTO2;
    private SaddleDTO saddleDTO1;
    private SaddleDTO saddleDTO2;


    @BeforeAll
    public static void setup(){
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        bicycleDAO = BicycleDAO.getInstance(emf);
        frameDAO = FrameDAO.getInstance(emf);
        gearDAO = GearDAO.getInstance(emf);
        wheelDAO = WheelDAO.getInstance(emf);
        saddleDAO = SaddleDAO.getInstance(emf);
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void init() {
        bicycle1 = new Bicycle();
        bicycle2 = new Bicycle();
        frame1 = new Frame();
        frame2 = new Frame();
        gear1 = new Gear();
        gear2 = new Gear();
        wheel1 = new Wheel();
        wheel2 = new Wheel();
        saddle1 = new Saddle();
        saddle2 = new Saddle();

        bicycle1.setBrand("Cervelo");
        bicycle1.setModel("PS5");
        bicycle1.setSize(56);
        bicycle1.setPrice(1000);
        bicycle1.setWeight(10);
        bicycle1.setLink("The ultimate racing bike");

        bicycle2.setBrand("Cannondale");
        bicycle2.setModel("CAAD13");
        bicycle2.setSize(54);
        bicycle2.setPrice(2000);
        bicycle2.setWeight(9);
        bicycle2.setLink("The ultimate climbing bike");

        bicycleDTO1 = bicycleDAO.add(new BicycleDTO(bicycle1));
        bicycleDTO2 = bicycleDAO.add(new BicycleDTO(bicycle2));

        frame1.setBrand("Cervelo");
        frame1.setType("Racing");
        frame1.setModel("PS5");
        frame1.setMaterial("Carbon");
        frame1.setWeight(1);
        frame1.setSize(56);

        frame2.setBrand("Cannondale");
        frame2.setType("Climbing");
        frame2.setModel("CAAD13");
        frame2.setMaterial("Aluminium");
        frame2.setWeight(15);
        frame2.setSize(54);

        frameDTO1 = frameDAO.add(new FrameDTO(frame1));
        frameDTO2 = frameDAO.add(new FrameDTO(frame2));

        gear1.setBrand("Shimano");
        gear1.setModel("Ultegra");
        gear2.setSeries("Shimano Ultegra");
        gear1.setMaterial("Aluminium");
        gear1.setType("Racing");
        gear1.setWeight(1);

        gear2.setBrand("SRAM");
        gear2.setModel("Red");
        gear2.setSeries("SRAM Red");
        gear2.setMaterial("Carbon");
        gear2.setType("Climbing");
        gear2.setWeight(1);

        gearDTO1 = gearDAO.add(new GearDTO(gear1));
        gearDTO2 = gearDAO.add(new GearDTO(gear2));

        wheel1.setBrand("Mavic");
        wheel1.setMaterial("Cosmic");
        wheel1.setType("Disc");
        wheel1.setModel("Cosmic Pro");
        wheel1.setWeight(1540);
        wheel1.setSize(23);

        wheel2.setBrand("Fulcrum");
        wheel2.setMaterial("Racing");
        wheel2.setType("Disc");
        wheel2.setModel("Racing Zero");
        wheel2.setWeight(1340);
        wheel1.setSize(23);

        wheelDTO1 = wheelDAO.add(new WheelDTO(wheel1));
        wheelDTO2 = wheelDAO.add(new WheelDTO(wheel2));

        saddle1.setBrand("Specialized");
        saddle1.setMaterial("Carbon");
        saddle1.setModel("Power");
        saddle1.setWeight(200);
        saddle1.setWidth(142);

        saddle2.setBrand("Selle Italia");
        saddle2.setMaterial("Leather");
        saddle2.setModel("Flite");
        saddle2.setWeight(250);
        saddle2.setWidth(140);

        saddleDTO1 = saddleDAO.add(new SaddleDTO(saddle1));
        saddleDTO2 = saddleDAO.add(new SaddleDTO(saddle2));

    }

    @AfterEach
    public void cleanup(){
        if (bicycleDTO1 != null) {
            try {
                bicycleDAO.delete(bicycleDTO1.getId());
            } catch (Exception e) {
                System.err.println("Could not delete bicycleDTO1" + e.getMessage());
            }
        }
        if (bicycleDTO2 != null) {
            try {
                bicycleDAO.delete(bicycleDTO2.getId());
            } catch (Exception e) {
                System.err.println("Could not delete bicycleDTO2" + e.getMessage());
            }
        }
        if (frameDTO1 != null) {
            try {
                frameDAO.delete(Math.toIntExact(frameDTO1.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete frameDTO1" + e.getMessage());
            }
        }
        if (frameDTO2 != null) {
            try {
                frameDAO.delete(Math.toIntExact(frameDTO2.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete frameDTO2" + e.getMessage());
            }
        }
        if (gearDTO1 != null) {
            try {
                gearDAO.delete(Math.toIntExact(gearDTO1.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete gearDTO1" + e.getMessage());
            }
        }
        if (gearDTO2 != null) {
            try {
                gearDAO.delete(Math.toIntExact(gearDTO2.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete gearDTO2" + e.getMessage());
            }
        }
        if (wheelDTO1 != null) {
            try {
                wheelDAO.delete(Math.toIntExact(wheelDTO1.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete wheelDTO1" + e.getMessage());
            }
        }
        if (wheelDTO2 != null) {
            try {
                wheelDAO.delete(Math.toIntExact(wheelDTO2.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete wheelDTO2" + e.getMessage());
            }
        }
        if (saddleDTO1 != null) {
            try {
                saddleDAO.delete(Math.toIntExact(saddleDTO1.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete saddleDTO1" + e.getMessage());
            }
        }
        if (saddleDTO2 != null) {
            try {
                saddleDAO.delete(Math.toIntExact(saddleDTO2.getId()));
            } catch (Exception e) {
                System.err.println("Could not delete saddleDTO2" + e.getMessage());
            }
        }
    }


    @Test
    void add() {
        BicycleDTO newBicycleDTO = new BicycleDTO();
        newBicycleDTO.setBrand("Specialized");
        newBicycleDTO.setModel("Tarmac");
        newBicycleDTO.setSize(56);
        newBicycleDTO.setPrice(3000);
        newBicycleDTO.setWeight(8);
        newBicycleDTO.setLink("The ultimate allround bike");

        BicycleDTO addedBicycleDTO = bicycleDAO.add(newBicycleDTO);
        Assertions.assertNotNull(addedBicycleDTO);
        assertEquals(newBicycleDTO.getBrand(), addedBicycleDTO.getBrand());
    }

    @Test
    void getById() {
        BicycleDTO bicycleDTO = bicycleDAO.getById(Math.toIntExact(bicycleDTO1.getId()));
        Assertions.assertNotNull(bicycleDTO);
        assertEquals(bicycleDTO1.getBrand(), bicycleDTO.getBrand());
    }

    @Test
    void getAll() {
        List<BicycleDTO> bicycleDTOS = bicycleDAO.getAll();
        Assertions.assertNotNull(bicycleDTOS);
        assertEquals(2, bicycleDTOS.size());
    }

    @Test
    void addAllComponentsToBicycle() {
        BicycleDTO bicycleDTO = new BicycleDTO(bicycleDAO.addAllComponentsToBicycle(Math.toIntExact(bicycleDTO1.getId()), Math.toIntExact(frameDTO1.getId()), Math.toIntExact(gearDTO1.getId()), Math.toIntExact(wheelDTO1.getId()), Math.toIntExact(saddleDTO1.getId())));
        Assertions.assertNotNull(bicycleDTO);
        assertEquals(frameDTO1.getBrand(), bicycleDTO.getFrame().getBrand());
        assertEquals(gearDTO1.getBrand(), bicycleDTO.getGear().getBrand());
        assertEquals(wheelDTO1.getBrand(), bicycleDTO.getWheel().getBrand());
        assertEquals(saddleDTO1.getBrand(), bicycleDTO.getSaddle().getBrand());
    }

    @Test
    void update() {
        BicycleDTO bicycleDTO = new BicycleDTO();
        bicycleDTO.setId(Math.toIntExact(bicycleDTO1.getId()));
        bicycleDTO.setBrand("Specialized");
        bicycleDTO.setModel("Tarmac");
        bicycleDTO.setSize(56);
        bicycleDTO.setPrice(3000);
        bicycleDTO.setWeight(8);
        bicycleDTO.setLink("The ultimate allround bike");

        BicycleDTO updatedBicycleDTO = bicycleDAO.update(Math.toIntExact(bicycleDTO.getId()), bicycleDTO);
        Assertions.assertNotNull(updatedBicycleDTO);
        assertEquals(bicycleDTO.getBrand(), updatedBicycleDTO.getBrand());
    }

    @Test
    void delete() {
        bicycleDAO.delete(Math.toIntExact(bicycleDTO1.getId()));
        BicycleDTO bicycleDTO = bicycleDAO.getById(Math.toIntExact(bicycleDTO1.getId()));
        Assertions.assertNull(bicycleDTO);
    }
}