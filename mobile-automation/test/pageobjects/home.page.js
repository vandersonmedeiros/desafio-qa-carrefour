class HomePage {
    get txtTitle() { return $('//*[@text="WEBDRIVER" or @label="WEBDRIVER"]'); }
    get tabHome() { return $('~Home'); }
    get tabWebview() { return $('~Webview'); }
    get tabLogin() { return $('~Login'); }
    get tabForms() { return $('~Forms'); }

    async aguardarCarregamento() {
        await this.txtTitle.waitForDisplayed({ timeout: 15000 });
    }

    async irParaWebview() { await this.tabWebview.click(); }
    async irParaHome() { await this.tabHome.click(); }
    async irParaLogin() { await this.tabLogin.click(); }
    async irParaForms() { await this.tabForms.click(); }
}
module.exports = new HomePage();