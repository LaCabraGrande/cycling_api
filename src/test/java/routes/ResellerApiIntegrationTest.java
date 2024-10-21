package routes;

import dat.config.Populate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManagerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Dette er en integrationstest, der tester Reseller API'et i en fuld kontekst med en kørende server. Den dækker
// CRUD-operationer, herunder oprettelse, opdatering og sletning af data. Testene er skrevet med JUnit 5
// og Rest Assured. Denne her testklasse bruger Hamcrest til at teste JSON-responsdata.
// Inden du kører testene, skal du sørge for at have en kørende server, der kører på port 7070 og have nogle Resellers
// i databasen. Dette finder sted i Populate-klassen i dat.config-pakken som bliver kaldt fra ApplicationConfig-klassen.


public class ResellerApiIntegrationTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = "http://localhost"; // Her sætter jeg base URI for Rest Assured
        RestAssured.port = 7070;
        Populate populate = new Populate();
        populate.populateDatabase();  // Her fylder vi Databasen op med 2 Resellers og 10 Plants
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    // Her bruger jeg Rest Assured til at teste at jeg kan hente alle Resellers
    public void testgetAllResellers() {

        // Her sender jeg en GET-anmodning for at hente alle Resellers
        given()
                .when()
                .get("/api/resellers/")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));  // Her bruger jeg Hamcrest til at teste at der er mere end 0 Resellers
    }

    @Test
    // Her bruger jeg Rest Assured og en JSON-streng til at teste at jeg kan oprette en Reseller
    public void testAddReseller() {
        String newResellerJson = "{ \"name\": \"Hansens Gartneri\", \"address\": \"Kongevejen 340\", \"phone\": \"84392203\" }";

        Response response = given()
                    .contentType("application/json")
                    .body(newResellerJson)
                .when()
                    .post("/api/resellers");

        response.then()
                    .statusCode(201);
    }

    @Test
    // Her bruger jeg Rest Assured til at teste at jeg kan hente en Reseller baseret på ID
    public void testGetResellerById() {
        int resellerId = 2;

        Response response = given()
                .when()
                .get("/api/resellers/" + resellerId);

        response.then()
                .statusCode(200)
                .body("name", notNullValue())
                .body("address", notNullValue());

        String resellerName = response.jsonPath().getString("name");
        assertThat(resellerName, not(emptyOrNullString()));  // Her bruger jeg Hamcrest til at teste at Resellernavnet ikke er tomt
    }

    @Test
    // Her bruger jeg Rest Assured og en JSON-streng til at teste at jeg kan opdatere en Reseller
    public void testUpdateReseller() {
        String updateResellerJson = "{ \"name\": \"Klausens Gartneri\", \"address\": \"Nordre Strandvej 84A\", \"phone\": \"28736272\" }";

        // Her sender jeg en PUT-anmodning for at opdatere Reselleren med ID'et 2
        Response response = given()
                .contentType("application/json")
                .body(updateResellerJson)
                .when()
                .put("/api/resellers/2");

        response.then()
                .statusCode(200);
    }

    @Test
    // Her bruger jeg Rest Assured til at teste at jeg kan slette en Reseller ved hjælp af et ID
    public void testDeleteReseller() {
        int resellerIdToDelete = 1;

        // Her sender jeg en DELETE-anmodning for at slette en Reseller med ID'et 1
        given()
                .when()
                .delete("/api/resellers/" + resellerIdToDelete)
                .then()
                .statusCode(204);
    }
}
