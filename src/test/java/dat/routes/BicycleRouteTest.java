package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.*;
import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
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

    @BeforeAll
    void init() {
        ApplicationConfig.startServer();
        HibernateConfig.setTest(true);

        // Her registrerer vi en bruger
        given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"test123\"}")
                .when()
                .post(BASE_URL + "/auth/register/")
                .then()
                .statusCode(201);  // Forventet 201 Created

        // Her logger vi brugeren ind for at få et JWT-token
        jwtToken = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"test123\"}")
                .when()
                .post(BASE_URL + "/auth/login/")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        // Her tilføjer vi rollen superman til brugeren men vi kunne også have givet ham admin rollen så han har
        // adgang til alle CRUD-operationer
        given()
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(ContentType.JSON)
                .body("{\"role\": \"superman\"}")  // her giver vi useren superman rollen så han kan det hele
                .when()
                .post(BASE_URL + "/auth/user/addrole/")
                .then()
                .statusCode(200);
    }

    @BeforeEach
    void setUp() {
        bicycleDTOS = populator.populate3bicycles();
        b1 = bicycleDTOS.get(0);
        b2 = bicycleDTOS.get(1);
        b3 = bicycleDTOS.get(2);
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.createQuery("DELETE FROM Bicycle").executeUpdate();
            em.createQuery("DELETE FROM Frame").executeUpdate();
            em.createQuery("DELETE FROM Gear").executeUpdate();
            em.createQuery("DELETE FROM Wheel").executeUpdate();
            em.createQuery("DELETE FROM Saddle").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error clearing database", e);
        } finally {
            em.close();
        }
    }

//    @AfterAll
//    void closeDown() {
//        ApplicationConfig.stopServer();
//    }

    @Test
    void testGetAllBicycles() {
        BicycleDTO[] bicycleDTOS =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                        .when()
                        .get(BASE_URL + "/bicycles/")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO[].class);
        // Sammenlign de individuelle felter for at undgå referenceproblemer
        assertThat(bicycleDTOS, arrayContainingInAnyOrder(b1, b2, b3));
    }

    @Test
    void testGetBicycleById() {
        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                        .when()
                        .get(BASE_URL + "/bicycles/" + b1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO.class);
        // Sammenlign de individuelle felter for at undgå referenceproblemer
        assertThat(bicycleDTO, equalTo(b1));
    }

    @Test
    void getAllBicycles() {
        BicycleDTO[] bicycleDTOS =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                        .when()
                        .get(BASE_URL + "/bicycles/")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO[].class);
        // Sammenlign de individuelle felter for at undgå referenceproblemer
        assertThat(bicycleDTOS, arrayContainingInAnyOrder(b1, b2, b3));
    }

    @Test
    void testAddBicycle() {
        BicycleDTO b4 = new BicycleDTO("Scott", "test", 58, 4500, 7.8, "The top bike from Scott for road racing");

        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                        .body(b4)
                        .when()
                        .post(BASE_URL + "/bicycles/")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(BicycleDTO.class);
        // Sammenlign de individuelle felter for at undgå referenceproblemer
        assertThat(bicycleDTO.getBrand(), equalTo(b4.getBrand()));
        assertThat(bicycleDTO.getModel(), equalTo(b4.getModel()));
        assertThat(bicycleDTO.getSize(), equalTo(b4.getSize()));
        assertThat(bicycleDTO.getPrice(), equalTo(b4.getPrice()));
        assertThat(bicycleDTO.getWeight(), equalTo(b4.getWeight()));
        assertThat(bicycleDTO.getDescription(), equalTo(b4.getDescription()));
    }

    @Test
    void testUpdateBicycle() {
        BicycleDTO b4 = new BicycleDTO("Canyon", "CF 10", 62, 5000, 7.2, "The ultimate aero bike from Canyon for road racing");

        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                        .body(b4)
                        .when()
                        .put(BASE_URL + "/bicycles/" + b1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO.class);
        // Sammenlign de individuelle felter for at undgå referenceproblemer
        assertThat(bicycleDTO.getBrand(), equalTo(b4.getBrand()));
        assertThat(bicycleDTO.getModel(), equalTo(b4.getModel()));
        assertThat(bicycleDTO.getSize(), equalTo(b4.getSize()));
        assertThat(bicycleDTO.getPrice(), equalTo(b4.getPrice()));
        assertThat(bicycleDTO.getWeight(), equalTo(b4.getWeight()));
        assertThat(bicycleDTO.getDescription(), equalTo(b4.getDescription()));
    }

    @Test
    void testDeleteBicycle() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)  // Include JWT token for authorization
                .when()
                .delete(BASE_URL + "/bicycles/" + b1.getId())
                .then()
                .log().all()
                .statusCode(200);

        List<Bicycle> bicycles = bicycleDAO.getAll().stream().map(BicycleDTO::toEntity).toList();
        assertEquals(2, bicycles.size());
    }
}