const { expect } = require('@wdio/globals');
const HomePage = require('../pageobjects/home.page');
const LoginPage = require('../pageobjects/login.page');
const FormsPage = require('../pageobjects/forms.page');
const loginData = require('../data/loginData.json'); // <-- Importando a massa de dados

describe('Desafio Carrefour - Automação Mobile WebdriverIO', () => {
    
    // ... (Seus Blocos 1 e 2 continuam EXATAMENTE IGUAIS) ...

    // --- BLOCO 3: LOGIN E MENSAGENS DE ERRO (DATA-DRIVEN) ---
    it('Cenário 7: deve navegar para a aba Login', async () => {
        await HomePage.irParaLogin();
        await expect(HomePage.tabLogin).toBeSelected();
    });

    // Loop que lê o JSON e gera os Cenários 8 e 9 automaticamente
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

    it('Cenário 10: deve realizar login com sucesso com credenciais válidas', async () => {
        await LoginPage.realizarLogin('teste@qa.com', '12345678');
        await LoginPage.alertSuccessTitle.waitForDisplayed({ timeout: 5000 });
        await expect(LoginPage.alertSuccessTitle).toHaveText('Success');
        await LoginPage.btnAlertOk.click();
    });
});