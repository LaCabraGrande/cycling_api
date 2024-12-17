package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.*;
import dat.dtos.*;
import dat.entities.*;
import dat.utils.ApiProps;
import io.javalin.Javalin;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BicycleRouteTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final BicycleDAO bicycleDAO = new BicycleDAO();
    private static final GearDAO gearDAO = new GearDAO();
    private static final SaddleDAO saddleDAO = new SaddleDAO();
    private static final FrameDAO frameDAO = new FrameDAO();
    private static final WheelDAO wheelDAO = new WheelDAO();
    private static final String BASE_URL = "http://localhost:7070/api";
    private static Populator populator = new Populator(bicycleDAO, frameDAO, gearDAO, wheelDAO, saddleDAO, emf);
    private Javalin app;
    private BicycleDTO b1, b2, b3;
    private List<BicycleDTO> bicycleDTOS;
    private String jwtToken;
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
    void init() {
        app = Javalin.create(ApplicationConfig::configuration);
        app.start(ApiProps.PORT);
        HibernateConfig.setTest(true);

        // Registrering og login for at få JWT token
        given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"test123\"}")
                .when()
                .post(BASE_URL + "/auth/register/")
                .then()
                .statusCode(201);

        jwtToken = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"test123\"}")
                .when()
                .post(BASE_URL + "/auth/login/")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @BeforeEach
    void setUp() {
        // Initialisering af objekterne
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

        // Sæt værdier for cyklerne
        bicycle1.setBrand("Cervelo");
        bicycle1.setModel("PS5");
        bicycle1.setSize(56);
        bicycle1.setPrice(1000);
        bicycle1.setWeight(10);
        bicycle1.setDescription("The ultimate racing bike");
        bicycle1.setUsername("user");

        bicycle2.setBrand("Cannondale");
        bicycle2.setModel("CAAD13");
        bicycle2.setSize(54);
        bicycle2.setPrice(2000);
        bicycle2.setWeight(9);
        bicycle2.setDescription("The ultimate climbing bike");
        bicycle2.setUsername("user");

        // Opret BicycleDTOs fra Bicycle-objekterne
        bicycleDTO1 = bicycleDAO.add(new BicycleDTO(bicycle1));
        bicycleDTO2 = bicycleDAO.add(new BicycleDTO(bicycle2));

        System.out.println("BicycleDTO1: " + bicycleDTO1);

        // Initialisering af frame-objekter
        frame1.setBrand("Cervelo");
        frame1.setType("Racing");
        frame1.setModel("PS5");
        frame1.setMaterial("Carbon");
        frame1.setWeight(1);
        frame1.setSize(56);
        frame1.setUsername("user");

        frame2.setBrand("Cannondale");
        frame2.setType("Climbing");
        frame2.setModel("CAAD13");
        frame2.setMaterial("Aluminium");
        frame2.setWeight(15);
        frame2.setSize(54);
        frame2.setUsername("user");

        // Opret FrameDTOs fra Frame-objekterne
        frameDTO1 = frameDAO.add(new FrameDTO(frame1));
        frameDTO2 = frameDAO.add(new FrameDTO(frame2));

        System.out.println("FrameDTO1: " + frameDTO1);

        // Initialisering af gear-objekter
        gear1.setBrand("Shimano");
        gear1.setModel("Ultegra");
        gear1.setSeries("Shimano Ultegra");
        gear1.setMaterial("Aluminium");
        gear1.setType("Racing");
        gear1.setWeight(24321);
        gear1.setBrakes("Disc");
        gear1.setUsername("user");

        gear2.setBrand("SRAM");
        gear2.setModel("Red");
        gear2.setSeries("SRAM Red");
        gear2.setMaterial("Carbon");
        gear2.setType("Climbing");
        gear2.setWeight(234231);
        gear2.setBrakes("Rim");
        gear2.setUsername("user");

        // Opret GearDTOs fra Gear-objekterne
        gearDTO1 = gearDAO.add(new GearDTO(gear1));
        gearDTO2 = gearDAO.add(new GearDTO(gear2));

        System.out.println("GearDTO1: " + gearDTO1);

        // Initialisering af wheel-objekter
        wheel1.setBrand("Mavic");
        wheel1.setMaterial("Cosmic");
        wheel1.setType("Disc");
        wheel1.setModel("Cosmic Pro");
        wheel1.setWeight(1540);
        wheel1.setSize(23);
        wheel1.setUsername("user");

        wheel2.setBrand("Fulcrum");
        wheel2.setMaterial("Racing");
        wheel2.setType("Disc");
        wheel2.setModel("Racing Zero");
        wheel2.setWeight(1340);
        wheel2.setSize(23);
        wheel2.setUsername("user");

        // Opret WheelDTOs fra Wheel-objekterne
        wheelDTO1 = wheelDAO.add(new WheelDTO(wheel1));
        wheelDTO2 = wheelDAO.add(new WheelDTO(wheel2));

        // Initialisering af saddle-objekter
        saddle1.setBrand("Specialized");
        saddle1.setMaterial("Carbon");
        saddle1.setModel("Power");
        saddle1.setWeight(200);
        saddle1.setWidth(142);
        saddle1.setUsername("user");

        saddle2.setBrand("Selle Italia");
        saddle2.setMaterial("Leather");
        saddle2.setModel("Flite");
        saddle2.setWeight(250);
        saddle2.setWidth(140);
        saddle2.setUsername("user");

        // Opret SaddleDTOs fra Saddle-objekterne
        saddleDTO1 = saddleDAO.add(new SaddleDTO(saddle1));
        saddleDTO2 = saddleDAO.add(new SaddleDTO(saddle2));

        // Hvis du ønsker at tilføje flere cykler, kan du fortsætte med at initialisere dem
        // bicycleDTO3, frameDTO3 osv. på samme måde
    }


    @AfterEach
    void tearDown() {
        clearDatabase();
    }

    @AfterAll
    void closeDown() {
        if (app != null) {
            ApplicationConfig.stopServer(app);
        }
    }

    private void clearDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.createQuery("DELETE FROM Bicycle").executeUpdate();
            em.createQuery("DELETE FROM Frame").executeUpdate();
            em.createQuery("DELETE FROM Gear").executeUpdate();
            em.createQuery("DELETE FROM Wheel").executeUpdate();
            em.createQuery("DELETE FROM Saddle").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error clearing database", e);
        }
    }

    @Test
    void testGetAllBicycles() {
        BicycleDTO[] bicycleDTOS =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .when()
                        .get(BASE_URL + "/bicycles/")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO[].class);

        assertThat(bicycleDTOS, arrayContainingInAnyOrder(b1, b2, b3));
    }

    @Test
    void testGetBicycleById() {
        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .when()
                        .get(BASE_URL + "/bicycles/" + b1.getId())
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO.class);

        assertThat(bicycleDTO, equalTo(b1));
    }

    @Test
    void testAddBicycle() {
        BicycleDTO b4 = new BicycleDTO("Scott", "test", 58, 4500, 7.8, "The top bike from Scott for road racing", "user");

        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .body(b4)
                        .when()
                        .post(BASE_URL + "/bicycles/")
                        .then()
                        .statusCode(201)
                        .extract()
                        .as(BicycleDTO.class);

        assertThat(bicycleDTO.getBrand(), equalTo(b4.getBrand()));
        assertThat(bicycleDTO.getModel(), equalTo(b4.getModel()));
        assertThat(bicycleDTO.getSize(), equalTo(b4.getSize()));
        assertThat(bicycleDTO.getPrice(), equalTo(b4.getPrice()));
        assertThat(bicycleDTO.getWeight(), equalTo(b4.getWeight()));
        assertThat(bicycleDTO.getDescription(), equalTo(b4.getDescription()));
    }

    @Test
    void testUpdateBicycle() {
        BicycleDTO b4 = new BicycleDTO("Canyon", "CF 10", 62, 5000, 7.2, "The ultimate aero bike from Canyon for road racing", "user");

        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .body(b4)
                        .when()
                        .put(BASE_URL + "/bicycles/" + b1.getId())
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO.class);

        assertThat(bicycleDTO.getBrand(), equalTo(b4.getBrand()));
        assertThat(bicycleDTO.getModel(), equalTo(b4.getModel()));
        assertThat(bicycleDTO.getSize(), equalTo(b4.getSize()));
        assertThat(bicycleDTO.getPrice(), equalTo(b4.getPrice()));
        assertThat(bicycleDTO.getWeight(), equalTo(b4.getWeight()));
        assertThat(bicycleDTO.getDescription(), equalTo(b4.getDescription()));
        assertThat(bicycleDTO.getUsername(), equalTo(b4.getUsername()));
    }

    @Test
    void testDeleteBicycle() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .when()
                .delete(BASE_URL + "/bicycles/" + b1.getId())
                .then()
                .statusCode(200);

        List<Bicycle> bicycles = bicycleDAO.getAll().stream().map(BicycleDTO::toEntity).toList();
        assertEquals(2, bicycles.size());
    }
}
