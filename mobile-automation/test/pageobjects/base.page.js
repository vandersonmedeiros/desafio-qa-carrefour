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
    return element.getText();
  }

  async isDisplayed(selector, timeout = this.timeout) {
    try {
      const element = await $(selector);
      return element.isDisplayed({ timeout });
    } catch (error) {
      return false;
    }
  }
}

module.exports = BasePage;
