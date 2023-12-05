package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestSecurity(user = "test@example.com", roles = "admin")
public class UserResourceTest {

    @Test
    public void testIndexEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .when().get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password\", \"role\": \"admin\" }")
                .when().post("/users")
                .then()
                .statusCode(200)
                .body("firstName", is("John"))
                .body("lastName", is("Doe"))
                .body("email", is("john.doe@example.com"))
                .body("password", is("password"))
                .body("role", is("admin"));
    }

    @Test
    public void testUpdateEndpoint() {
        int userId = given()
                .contentType(ContentType.JSON)
                .body("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password\" }")
                .when().post("/users")
                .then()
                .statusCode(200)
                .extract().path("id");

        given()
                .pathParam("id", userId)
                .contentType(ContentType.JSON)
                .body("{ \"firstName\": \"UpdatedName\", \"lastName\": \"UpdatedLastName\" }")
                .when().put("/users/{id}")
                .then()
                .statusCode(200)
                .body("firstName", is("UpdatedName"))
                .body("lastName", is("UpdatedLastName"));
    }

    @Test
    public void testDeleteEndpoint() {
        int userId = given()
                .contentType(ContentType.JSON)
                .body("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password\" }")
                .when().post("/users")
                .then()
                .statusCode(200)
                .extract().path("id");

        given()
                .pathParam("id", userId)
                .when().delete("/users/{id}")
                .then()
                .statusCode(204);

        // Check if the user is deleted
        given()
                .when().get("/users")
                .then()
                .statusCode(200);
    }
}
