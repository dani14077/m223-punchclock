package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BookingResourceTest {

    @Test
    public void testIndexEndpoint() {
        given()
            .when().get("/bookings")
            .then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    public void testCreateEndpoint() {
        given()
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T10:00:00\" }")
            .when().post("/bookings")
            .then()
            .statusCode(200)
            .body("date", is("2023-01-01T10:00:00"));
    }

    @Test
    public void testUpdateEndpoint() {
        // Create a booking for updating
        int bookingId = given()
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T10:00:00\" }")
            .when().post("/bookings")
            .then()
            .statusCode(200)
            .extract().path("id");

        // Update the booking
        given()
            .pathParam("id", bookingId)
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T11:00:00\" }")
            .when().put("/bookings/{id}")
            .then()
            .statusCode(200)
            .body("date", is("2023-01-01T11:00:00"));
    }

    @Test
    public void testDeleteEndpoint() {
        // Create a booking for deletion
        int bookingId = given()
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T10:00:00\" }")
            .when().post("/bookings")
            .then()
            .statusCode(200)
            .extract().path("id");

        // Delete the booking
        given()
            .pathParam("id", bookingId)
            .when().delete("/bookings/{id}")
            .then()
            .statusCode(204);

        // Check if the booking is deleted
        given()
            .when().get("/bookings")
            .then()
            .statusCode(200)
            .body(is("[]"));
    }
}
