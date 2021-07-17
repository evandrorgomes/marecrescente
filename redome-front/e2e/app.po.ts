import { browser, element, by } from 'protractor';

export class RedomeFrontEndPage {
  navigateTo() {
    browser.get('/redome');
  }

  getCurrentUrl() {
    return browser.getCurrentUrl();
  }
}
