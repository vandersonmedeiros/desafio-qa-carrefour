const { $ } = require('@wdio/globals');

/**
 * Classe base (Core) para os Page Objects.
 * Centraliza os comandos do WebdriverIO criando "Custom Wrappers".
 * Garante esperas explícitas automáticas, reduzindo a intermitência (flakiness) dos testes.
 */
class BasePage {
  constructor() {
    this.timeout = 10000;
  }

  async waitForElement(selector, timeout = this.timeout) {
    const element = await $(selector);
    await element.waitForDisplayed({ timeout });
    return element;
  }

  async click(selector) {
    const element = await this.waitForElement(selector);
    await element.click();
  }

  async type(selector, text) {
    const element = await this.waitForElement(selector);
    await element.setValue(text);
  }

  async getText(selector) {
    const element = await this.waitForElement(selector);
    return await element.getText();
  }

  async isDisplayed(selector) {
    try {
      const element = await $(selector);
      return await element.isDisplayed();
    } catch (error) {
      return false;
    }
  }
}

module.exports = BasePage;