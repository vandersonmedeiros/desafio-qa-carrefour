const { $ } = require('@wdio/globals');

class FormsPage {
    get inputField() { return $('~text-input'); }
    get inputTextResult() { return $('~input-text-result'); }
    get switchBtn() { return $('~switch'); }
    get switchText() { return $('~switch-text'); }

    async preencherCampo(texto) {
        await this.inputField.setValue(texto);
    }

    async interagirComSwitch() {
        await this.switchBtn.click();
    }
}

module.exports = new FormsPage();