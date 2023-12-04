package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EntryResourceTest {

    @Test
    public void testIndexEndpoint() {
        given()
          .when().get("/entries")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

    @Test
    public void testCreateEndpoint() {
        given()
          .contentType(ContentType.JSON)
          .body("{ \"checkIn\": \"2023-01-01T10:00:00\", \"checkOut\": \"2023-01-01T12:00:00\" }")
          .when().post("/entries")
          .then()
             .statusCode(200)
             .body("checkIn", is("2023-01-01T10:00:00"))
             .body("checkOut", is("2023-01-01T12:00:00"));
    }

    @Test
    public void testUpdateEndpoint() {
        // Erstellen Sie einen Eintrag zum Aktualisieren
        int entryId = given()
            .contentType(ContentType.JSON)
            .body("{ \"checkIn\": \"2023-01-01T10:00:00\", \"checkOut\": \"2023-01-01T12:00:00\" }")
            .when().post("/entries")
            .then()
                .statusCode(200)
                .extract().path("id");

        // Aktualisieren Sie den Eintrag
        given()
            .pathParam("id", entryId)
            .contentType(ContentType.JSON)
            .body("{ \"checkIn\": \"2023-01-01T11:00:00\", \"checkOut\": \"2023-01-01T13:00:00\" }")
            .when().put("/entries/{id}")
            .then()
                .statusCode(200)
                .body("checkIn", is("2023-01-01T11:00:00"))
                .body("checkOut", is("2023-01-01T13:00:00"));
    }

    @Test
    public void testDeleteEndpoint() {
        // Erstellen Sie einen Eintrag zum Löschen
        int entryId = given()
            .contentType(ContentType.JSON)
            .body("{ \"checkIn\": \"2023-01-01T10:00:00\", \"checkOut\": \"2023-01-01T12:00:00\" }")
            .when().post("/entries")
            .then()
                .statusCode(200)
                .extract().path("id");

        // Löschen Sie den Eintrag
        given()
            .pathParam("id", entryId)
            .when().delete("/entries/{id}")
            .then()
                .statusCode(204);

        // Überprüfen Sie, ob der Eintrag gelöscht wurde
        given()
            .when().get("/entries")
            .then()
                .statusCode(200)
                .body(is("[]"));
    }
}