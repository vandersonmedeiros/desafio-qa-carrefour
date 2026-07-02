const { expect } = require('@wdio/globals');
const HomePage = require('../pageobjects/home.page');
const LoginPage = require('../pageobjects/login.page');
const FormsPage = require('../pageobjects/forms.page');
const loginData = require('../data/loginData.json');

/**
 * Suíte de Testes E2E Mobile - App Demo WebDriverIO
 * Valida fluxos de navegação, formulários e autenticação (Data-Driven).
 */
describe('Desafio Carrefour - Automação Mobile WebdriverIO', () => {

    // ... (Cenários de 1 a 6 ficam aqui em cima) ...

    describe('Fluxo de Autenticação (Login)', () => {

        it('Cenário 7: Deve navegar para a aba Login', async () => {
            await HomePage.irParaLogin();
            await expect(HomePage.tabLogin).toBeSelected();
        });

        loginData.forEach((dados) => {
            it(`${dados.cenario}`, async () => {
                await LoginPage.realizarLogin(dados.email, dados.senha);

                if (dados.tipoErro === 'email') {
                    await expect(LoginPage.msgErrorEmail).toBeDisplayed();
                } else {
                    await expect(LoginPage.msgErrorPassword).toBeDisplayed();
                }
            });
        });

        it('Cenário 10: Deve realizar login com sucesso com credenciais válidas', async () => {
            const emailValido = 'teste@qa.com';
            const senhaValida = '12345678';

            await LoginPage.realizarLogin(emailValido, senhaValida);

            await LoginPage.alertSuccessTitle.waitForDisplayed({ timeout: 5000 });
            await expect(LoginPage.alertSuccessTitle).toHaveText('Success');
            await LoginPage.btnAlertOk.click();
        });
    });
});