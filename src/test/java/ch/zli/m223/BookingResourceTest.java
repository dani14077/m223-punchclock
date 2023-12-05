package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestSecurity(user = "test@example.com", roles = "admin")
public class BookingResourceTest {

    @Test
    public void testIndexEndpoint() {
        given()
            .when().get("/bookings")
            .then()
            .statusCode(200);
    }

    @Test
    public void testCreateEndpoint() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", LocalDateTime.parse("2023-01-01T10:00:00"));
        requestBody.put("type", "TestType");
        requestBody.put("status", "TestStatus");

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/bookings")
            .then()
            .statusCode(200)
            .body("date", is("2023-01-01T10:00:00"))
            .body("type", is("TestType"))
            .body("status", is("TestStatus"));
    }

    @Test
    public void testUpdateEndpoint() {
        // Create a booking for updating
        int bookingId = given()
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T10:00:00\", \"type\": \"TestType\", \"status\": \"TestStatus\" }")
            .when().post("/bookings")
            .then()
            .statusCode(200)
            .extract().path("id");

        // Update the booking
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", LocalDateTime.parse("2023-01-01T11:00:00"));
        requestBody.put("type", "UpdatedType");
        requestBody.put("status", "UpdatedStatus");

        given()
            .pathParam("id", bookingId)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().put("/bookings/{id}")
            .then()
            .statusCode(200)
            .body("date", is("2023-01-01T11:00:00"))
            .body("type", is("UpdatedType"))
            .body("status", is("UpdatedStatus"));
    }

    @Test
    public void testDeleteEndpoint() {
        // Create a booking for deletion
        int bookingId = given()
            .contentType(ContentType.JSON)
            .body("{ \"date\": \"2023-01-01T10:00:00\", \"type\": \"TestType\", \"status\": \"TestStatus\" }")
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
            .statusCode(200);
    }
}
