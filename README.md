# Automação de Testes de API - Carrefour

![Build](https://github.com/vandersonoliveira/api-automation/actions/workflows/api-tests.yml/badge.svg)

Este projeto contém a automação de testes da API RESTful de usuários (Serverest), atendendo ao desafio técnico com 100% de cobertura das operações CRUD.

## 🛠️ Tecnologias Utilizadas
* **Java 17**
* **RestAssured** (Motor de testes de API)
* **JUnit 5** (Orquestrador de testes)
* **DataFaker** (Geração de massa de dados dinâmica)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Configuração e Execução
1. Certifique-se de ter o **Java 17** e o **Maven** instalados na sua máquina.
2. Clone este repositório.
3. Abra o terminal na raiz do projeto (pasta `api-automation`) e execute o comando:
   ```bash
   mvn clean test
   ```

## 📊 Relatório Allure
Para gerar o relatório visual dos testes, execute:
```bash
mvn allure:report
```
O relatório ficará disponível em `target/site/allure-maven-plugin/index.html`.