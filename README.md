Markdown
# 🛒 Desafio de Automação de Testes - Carrefour

Este repositório contém a solução técnica para o desafio de automação de testes (API e Mobile) do processo seletivo do Carrefour. O projeto foi estruturado no modelo de **Monorepo**, garantindo a centralização e organização das suítes de teste.

---

## 🛠 Tecnologias Utilizadas

| Escopo | Stack Tecnológica |
| :--- | :--- |
| **API** | Java, Maven, RestAssured, JUnit |
| **Mobile** | JavaScript, WebdriverIO, Appium, Mocha, Chai |
| **Padrões** | Page Objects (Mobile), Data-driven |
| **Relatórios** | Allure Report |
| **CI/CD** | Pipeline Integrada (GitHub Actions / GitLab CI) |

---

## 📂 Arquitetura do Projeto

Abaixo está a estrutura real dos diretórios do projeto:

```text
DESAFIO-CARREFOUR/
├── api-automation/                   # ⚙️ Automação de API
│   ├── pom.xml                       # Gerenciador de dependências Maven
│   └── src/test/java/
│       ├── bases/BaseTest.java       # Configurações base (URIs, Headers)
│       ├── payloads/UserPayload.java # Massa de dados e construtores de JSON
│       └── tests/UserTest.java       # Cenários de teste de API (CRUD)
│
├── mobile-automation/                # 📱 Automação Mobile
│   ├── apps/
│   │   └── android-demo.apk          # Aplicativo alvo dos testes
│   ├── wdio.conf.js                  # Configurações do WebdriverIO e Appium
│   └── test/
│       ├── pageobjects/              # Mapeamento de elementos (Page Objects)
│       │   ├── base.page.js
│       │   ├── forms.page.js
│       │   ├── home.page.js
│       │   ├── login.page.js
│       │   └── secure.page.js
│       └── specs/
│           └── test.e2e.js           # Cenários de teste End-to-End
│
└── README.md
🚀 Como Executar os Projetos
1. Automação de API (RestAssured)
Os testes de API cobrem 100% das operações CRUD de usuários (Criação, Leitura, Atualização e Exclusão), com validação de autenticação via token JWT e regras de negócio estipuladas no desafio.

Pré-requisitos: JDK 17+ e Maven configurados no PATH.

Acesse o diretório da API:

Bash
cd api-automation
Instale as dependências e execute os testes:

Bash
mvn clean test
Gere e visualize o relatório Allure:

Bash
mvn allure:report
mvn allure:serve
2. Automação Mobile (WebdriverIO)
Os testes Mobile abordam as principais jornadas do aplicativo android-demo.apk, incluindo Login, navegação, preenchimento de formulários e validações de erro, utilizando a arquitetura Page Objects.

Pré-requisitos: Node.js (v18+), Android Studio (Emulador configurado) e Appium Server rodando.

Acesse o diretório Mobile:

Bash
cd mobile-automation
Instale as dependências do projeto:

Bash
npm install
Com o emulador aberto, execute a suíte de testes:

Bash
npx wdio run wdio.conf.js
Para gerar as evidências e o relatório:

Bash
npm run allure:generate
(Nota: A captura de screenshots em caso de falha ocorre automaticamente durante a execução).

📊 Relatórios e Evidências
O projeto utiliza o Allure Report em ambas as frentes (API e Mobile).

Os resultados brutos são gerados automaticamente nas pastas allure-results.

O relatório compila Resumo dos testes, Screenshots das falhas, Logs de execução e Informações sobre o ambiente.

⚙️ Integração Contínua (CI/CD)
Os projetos estão configurados para rodar em pipeline de CI/CD automatizada. A cada novo commit ou Merge Request na branch principal, a esteira baixa as dependências, executa as suítes de API e Mobile, e disponibiliza o relatório Allure como artefato de execução, garantindo feedback rápido sobre a qualidade da aplicação.