import { browser, element, by, ElementArrayFinder, ElementFinder, Key } from 'protractor';

export class Util {

  getElementoPorId(id): ElementFinder {
    return element(by.id(id));
  }

  clicarNoBotao(id) {
    let botao = this.getElementoPorId(id);
    browser.executeScript("arguments[0].click();", botao.getWebElement());
  }

  clicarNoElemento(className: string) {
    let icone = this.getElementoPorclassName(className).first();
    icone.click();
    browser.executeScript("arguments[0].click();", icone.getWebElement()); 
  }

  clicarNoElementoSemJavaScript(className: string) {
    let icone = this.getElementoPorclassName(className).first();
    icone.click();
  }

  setCombo(id, value) {
    let combo = this.getElementoPorId(id);
    combo.click();
    combo.element(by.cssContainingText('option', value)).click();
  }

  setInput(id, value) {
    let input = this.getElementoPorId(id);
    input.sendKeys(value);
  }

  clearInput(id) {
    let input = this.getElementoPorId(id);
    input.clear();
  }

  setRadioComId(id) {
    let radio = this.getElementoPorId(id);
    radio.click();
  }

  setCheckBoxComId(id: string, index: number) {
    let checkBox = element.all(by.id(id) ).get(index);
    checkBox.click();
  }

  tab(id: string) {
    this.getElementoPorId(id).sendKeys(Key.TAB);
  }

  logar() {
    let inputLogin = this.getElementoPorId("login");
    inputLogin.sendKeys("medico")
    let inputSenha = this.getElementoPorId("senha");
    inputSenha.sendKeys("123456")
    let botao = element(by.css('button[type="submit"]'));
    browser.executeScript("arguments[0].click();", botao.getWebElement());
  }

  logarComoAvaliador() {
    let inputLogin = this.getElementoPorId("login");
    inputLogin.sendKeys("avaliador")
    let inputSenha = this.getElementoPorId("senha");
    inputSenha.sendKeys("123456")
    let botao = element(by.css('button[type="submit"]'));
    browser.executeScript("arguments[0].click();", botao.getWebElement());
  }

  getElementoPorTag(tagName): ElementArrayFinder {
    return element.all(by.tagName(tagName));
  }

  getElementoPorclassName(className): ElementArrayFinder {
    return element.all((by.className(className)));
  }

  fecharModal() {
    let modalHeader: ElementFinder = this.getElementoPorclassName("modal-header").first();
    let botaoFechar = modalHeader.all(by.id("close")).first();
    browser.executeScript("arguments[0].click();", botaoFechar.getWebElement());    
  }

  selecionarPrimeiroElementoDropDown(className: string) {
    this.getElementoPorclassName(className).first().click().then(res =>{});
  }
  
  temMensagemErro(mensagemErro: string, lista: ElementArrayFinder): Promise<boolean> {
    return Promise.resolve(lista.filter((element: ElementFinder) => {
      return element.getText().then((mensagem: string) => {
        return mensagem == mensagemErro;
      });
    })
    .count().then(count => {
      return count >= 1;
    }))
  }

  getTextoDoElemento(id:string):Promise<string>{
    return Promise.resolve(this.getElementoPorId(id).getText().then(texto=>{return texto}))
  }

  enviarArquivo() {
    let path = require('path');
    let file = '../laudo.JPG',
    absolutePath = path.resolve(__dirname, file);

    this.getElementoPorId("fileInput").sendKeys(absolutePath);    
  }
}
