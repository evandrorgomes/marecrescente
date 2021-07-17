import { Util } from '../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class PacienteModulePage {

  navigateTo() {
    return browser.get('/redome/login');
  }

  acessarTelaPaciente() {
    return browser.get('/redome/pacientes');
  }

  acessarTelaCadastroPaciente() {
    return browser.get('/redome/pacientes/cadastro');
  }

  submeterLogin() {
    let botao = element(by.css('button[type="submit"]'));
    browser.executeScript("arguments[0].click();", botao.getWebElement());
  }

  getElementoPorId(id): ElementFinder {
    return element(by.id(id));
  }

  clicarNoBotao(id) {
    let botao = this.getElementoPorId(id);
    browser.executeScript("arguments[0].click();", botao.getWebElement());
  }

  clicarBotao(elemento: ElementFinder) {
    browser.executeScript("arguments[0].click();", elemento.getWebElement());
  }

  clicarNoBotaoUsandoClasse(classe: string) {
    this.clicarBotao(element(by.className(classe)));
  }

  setCombo(id, value) {
    let combo = this.getElementoPorId(id);
    combo.click();
    combo.element(by.cssContainingText('option', value)).click();
  }

  setInput(id, value) {
    let input = this.getElementoPorId(id);
    //browser.executeScript("arguments[0].focus();", input.getWebElement());
    input.sendKeys(value);
  }

  setRadioComId(id) {
    let radioSexo = this.getElementoPorId(id);
    radioSexo.click();
  }

}
