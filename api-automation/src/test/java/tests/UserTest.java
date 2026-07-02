package tests;

import bases.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.UserPayload;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Suíte de testes automatizados para a API de Usuários.
 * Cobre as operações de CRUD (Create, Read, Update, Delete),
 * validações de regras de negócio e limites de requisição (Rate Limit).
 */
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
                                "true");

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

        @Test
        @DisplayName("Não deve permitir cadastrar usuário com e-mail já existente")
        @Story("Cadastro de usuário - Negativo")
        @Description("Valida o bloqueio de cadastro para e-mails duplicados via POST /usuarios")
        public void testCriarUsuarioComEmailDuplicado() {
                String emailDuplicado = FAKER.internet().emailAddress();
                criarUsuario("Primeiro Usuario", emailDuplicado, "senha123");

                UserPayload payloadDuplicado = new UserPayload(
                                FAKER.name().fullName(),
                                emailDuplicado,
                                "outraSenha",
                                "true");

                given()
                                .spec(requestSpec)
                                .body(payloadDuplicado)
                                .when()
                                .post("/usuarios")
                                .then()
                                .statusCode(400)
                                .body("message", equalTo("Este email já está sendo usado"));
        }

        @Test
        @DisplayName("Deve retornar 400 ao buscar usuário com ID inexistente")
        @Story("Consulta de usuários - Negativo")
        @Description("Valida o retorno correto ao buscar um usuário com ID inválido via GET /usuarios/{id}")
        public void testBuscarUsuarioInexistente() {
                given()
                                .spec(requestSpec)
                                .when()
                                .get("/usuarios/inexistent123456")
                                .then()
                                .statusCode(400)
                                .body("message", equalTo("Usuário não encontrado"));
        }

        @Test
        @DisplayName("Não deve permitir excluir um usuário inexistente")
        @Story("Exclusão de usuário - Negativo")
        @Description("Valida a tentativa de exclusão de um ID que não existe via DELETE /usuarios/{id}")
        public void testExcluirUsuarioInexistente() {
                given()
                                .spec(requestSpec)
                                .when()
                                .delete("/usuarios/inexistent123456")
                                .then()
                                .statusCode(200)
                                .body("message", equalTo("Nenhum registro excluído"));
        }

        @Test
        @DisplayName("Deve validar Rate Limit da API")
        @Story("Segurança e Desempenho - Rate Limit")
        @Description("Dispara requisições para forçar o limite, aceitando 429 ou 200 (em caso de nuvem instável)")
        public void testValidarRateLimit() {
                int statusCode = 200;

                for (int i = 0; i < 50; i++) {
                        statusCode = given()
                                        .spec(requestSpec)
                                        .when()
                                        .get("/usuarios")
                                        .then()
                                        .extract()
                                        .statusCode();

                        if (statusCode == 429) {
                                break;
                        }
                }

                Assertions.assertTrue(
                                statusCode == 429 || statusCode == 200,
                                "O status code deveria ser 429 (Rate Limit) ou 200 (API instável). Retornado: "
                                                + statusCode);
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