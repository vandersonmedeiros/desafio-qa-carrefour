Markdown
# 🛒 Desafio de Automação de Testes - Carrefour

Este repositório contém a solução técnica para o desafio de automação de testes (API e Mobile) do processo seletivo do Carrefour. O projeto foi estruturado no modelo de **Monorepo**, garantindo a centralização e organização das suítes de teste.

---

## 🛠 Tecnologias Utilizadas

| Escopo | Stack Tecnológica |
| :--- | :--- |
| **API** | Java, Maven, RestAssured, JUnit, DataFaker |
| **Mobile** | JavaScript, Node.js, WebdriverIO, Appium, Mocha, Chai |
| **Padrões de Projeto** | Page Objects Pattern, Data-Driven Testing, Custom Base Page |
| **Relatórios** | Allure Report |
| **CI/CD** | GitHub Actions (Com execução de emuladores em nuvem macOS) |

---

## 📂 Arquitetura do Projeto

Abaixo está a estrutura macro dos diretórios do projeto:

```text
DESAFIO-CARREFOUR/
├── .github/workflows/                # 🤖 Pipelines de CI/CD (GitHub Actions)
│   ├── api-tests.yml
│   └── mobile-tests.yml
│
├── api-automation/                   # ⚙️ Automação de API
│   ├── pom.xml                       # Gerenciador de dependências Maven
│   └── src/test/java/
│       ├── bases/BaseTest.java       # Setup e Autenticação JWT global
│       ├── payloads/UserPayload.java # Classe DTO para serialização JSON
│       └── tests/UserTest.java       # Cenários de teste (CRUD e Rate Limit)
│
├── mobile-automation/                # 📱 Automação Mobile
│   ├── package.json                  # Dependências e Scripts customizados
│   ├── wdio.conf.js                  # Configurações do WebdriverIO
│   └── test/
│       ├── data/                     # Massa de dados estruturada em JSON
│       ├── pageobjects/              # Mapeamento de elementos (Page Objects)
│       └── specs/
│           └── test.e2e.js           # Cenários de teste End-to-End
│
└── README.md

🚀 Como Executar os Projetos
1. Automação de API (RestAssured)
Os testes de API cobrem 100% das operações CRUD de usuários (Criação, Leitura, Atualização e Exclusão), com validação de autenticação via token JWT e regras de negócio estipuladas no desafio.

Pré-requisitos: JDK 17+ e Maven configurados no PATH.

Acesse o diretório da API:

cd api-automation

Execute a suíte de testes:

mvn clean test

Gere e visualize o relatório Allure:

mvn allure:report
mvn allure:serve

2. Automação Mobile (WebdriverIO)

Os testes Mobile abordam as principais jornadas do aplicativo de demonstração, incluindo fluxos de Login, navegação interativa, preenchimento de formulários e validações de erro, utilizando a arquitetura Page Objects e massa de dados (Data-Driven).

Pré-requisitos: Node.js (v18+), Android Studio (Emulador configurado) e Appium Server rodando.

Acesse o diretório Mobile:

cd mobile-automation

Instale as dependências e baixe o aplicativo alvo automaticamente:

npm install
npm run download:demo-app

Com o emulador aberto, execute a suíte de testes:

npm test

Para gerar as evidências e abrir o relatório no navegador:

npm run report

(Nota: A captura de screenshots em caso de falha ocorre automaticamente durante a execução).

📊 Relatórios e Evidências

O projeto utiliza o Allure Report em ambas as frentes (API e Mobile).

Os resultados brutos são gerados automaticamente nas pastas allure-results.

O dashboard compila: resumo das execuções, tempo de resposta, screenshots de falhas passo a passo e detalhes de infraestrutura.

⚙️ Integração Contínua (CI/CD)

Os projetos estão configurados para rodar em pipelines isoladas via GitHub Actions.
A cada novo push ou Pull Request na branch principal, a esteira entra em ação para:

1. Instalar as dependências necessárias.

2. Fazer o provisionamento de um emulador Android via Headless Runner na nuvem (no caso do Mobile).

3. Executar as suítes de teste.

4. Exportar o Relatório HTML do Allure como um artefato disponível para download diretamente na aba Actions do GitHub, garantindo feedback imediato sobre a saúde da aplicação.