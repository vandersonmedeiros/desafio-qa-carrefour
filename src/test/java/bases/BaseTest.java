package bases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static String token;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://serverest.dev";
        
        String loginPayload = "{ \"email\": \"fulano@qa.com\", \"password\": \"teste\" }";

        token = given()
                    .contentType(ContentType.JSON)
                    .body(loginPayload)
                .when()
                    .post("/login")
                .then()
                    .statusCode(200)
                    .extract()
                    .path("authorization");
    }
}