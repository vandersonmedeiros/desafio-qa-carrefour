package bases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static final String BASE_URI = "https://serverest.dev";
    protected static String token;
    protected static RequestSpecification requestSpec;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        token = given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "fulano@qa.com", "password", "teste"))
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("authorization");

        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token);
    }
}