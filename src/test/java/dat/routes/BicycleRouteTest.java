package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.daos.impl.BicycleDAO;
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
    private static final String BASE_URL = "http://localhost:7070/api";
    private static Populator populator = new Populator(bicycleDAO,emf);
    private Javalin app;

    private BicycleDTO b1, b2, b3;
    private List<BicycleDTO> bicycleDTOS;


    @BeforeAll
    void init() {
        app = ApplicationConfig.startServer(7070);
        HibernateConfig.setTest(true);
    }

    @BeforeEach
    void setUp() {
        bicycleDTOS = populator.populate3bicyles();
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

    @AfterAll
    void closeDown() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetBicycle() {
        BicycleDTO[] bicycle =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL + "/bicycles")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO[].class);
        assertEquals(3, bicycleDTOS.size());
        assertThat(bicycle, arrayContainingInAnyOrder(b1, b2, b3));
    }

    @Test
    void testGetBicyclesById() {
        BicycleDTO bicycle =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL + "/bicycles/" + b1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(BicycleDTO.class);
        assertThat(bicycle, equalTo(b1));
    }

    @Test
    void testAddBicycle() {
        BicycleDTO b4 = new BicycleDTO("Bicycle4", "test", 1, 1, 1, "test god");
        BicycleDTO bicycleDTO =
                given()
                        .contentType(ContentType.JSON)
                        .body(b4)
                        .when()
                        .post(BASE_URL + "/bicycles/")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(BicycleDTO.class);
        assertThat(bicycleDTO, equalTo(b4));
    }


}