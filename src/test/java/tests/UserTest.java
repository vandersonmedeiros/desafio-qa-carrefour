package tests;

import bases.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.UserPayload;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("API - Serverest")
@Feature("Gestão de usuários")
public class UserTest extends BaseTest {

    private static final Faker FAKER = new Faker();

    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    @Story("Cadastro de usuário")
    @Description("Valida a criação de um novo usuário via POST /usuarios")
    public void testCriarUsuarioComSucesso() {
        UserPayload payload = new UserPayload(
                FAKER.name().fullName(),
                FAKER.internet().emailAddress(),
                FAKER.internet().password(),
                "true"
        );

        given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Deve listar os usuários com sucesso")
    @Story("Consulta de usuários")
    @Description("Valida a listagem de usuários via GET /usuarios")
    public void testListarUsuarios() {
        given()
                .spec(requestSpec)
                .when()
                .get("/usuarios")
                .then()
                .statusCode(200)
                .body("quantidade", greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso")
    @Story("Atualização de usuário")
    @Description("Valida a atualização de um usuário existente via PUT /usuarios/{id}")
    public void testAtualizarUsuario() {
        String email = FAKER.internet().emailAddress();
        String idUsuario = criarUsuario("Nome Inicial", email, "senha123");

        UserPayload novoPayload = new UserPayload("Nome Alterado", email, "novaSenha123", "true");

        given()
                .spec(requestSpec)
                .body(novoPayload)
                .when()
                .put("/usuarios/" + idUsuario)
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @DisplayName("Deve excluir um usuário com sucesso")
    @Story("Exclusão de usuário")
    @Description("Valida a exclusão de um usuário via DELETE /usuarios/{id}")
    public void testExcluirUsuario() {
        String email = FAKER.internet().emailAddress();
        String idUsuario = criarUsuario("Para Excluir", email, "senha123");

        given()
                .spec(requestSpec)
                .when()
                .delete("/usuarios/" + idUsuario)
                .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    private String criarUsuario(String nome, String email, String password) {
        return given()
                .spec(requestSpec)
                .body(new UserPayload(nome, email, password, "true"))
                .when()
                .post("/usuarios")
                .then()
                .statusCode(201)
                .extract()
                .path("_id");
    }
}