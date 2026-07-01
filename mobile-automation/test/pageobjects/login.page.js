class LoginPage {
    get inputEmail() { return $('~input-email'); }
    get inputPassword() { return $('~input-password'); }
    get btnLogin() { return $('~button-LOGIN'); }
    
    get msgErrorEmail() { return $('//*[@text="Please enter a valid email address"]'); }
    get msgErrorPassword() { return $('//*[@text="Please enter at least 8 characters"]'); }
    
    get alertSuccessTitle() { return $('//*[@text="Success"]'); }
    get btnAlertOk() { return $('//*[@text="OK"]'); }

    async realizarLogin(email, senha) {
        await this.inputEmail.setValue(email);
        await this.inputPassword.setValue(senha);
        await this.btnLogin.click();
    }
}
module.exports = new LoginPage();