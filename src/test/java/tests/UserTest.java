package tests;

import bases.BaseTest;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.UserPayload;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    @DisplayName("Deve criar um novo usuario com sucesso")
    public void testCriarUsuarioComSucesso() {
        String nome = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String administrador = "true";

        UserPayload payload = new UserPayload(nome, email, password, administrador);

        given()
            .header("Authorization", token)
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/usuarios")
        .then()
            .statusCode(201)
            .body("message", equalTo("Cadastro realizado com sucesso"))
            .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Deve listar os usuarios com sucesso")
    public void testListarUsuarios() {
        given()
            .header("Authorization", token)
        .when()
            .get("/usuarios")
        .then()
            .statusCode(200)
            .body("quantidade", greaterThanOrEqualTo(0));
    }
}