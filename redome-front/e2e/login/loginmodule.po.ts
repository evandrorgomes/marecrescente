import { browser, element, by, ElementArrayFinder, ElementFinder, protractor } from 'protractor';

export class LoginModulePage {
  navigateTo() {
    return browser.get('/login');
  }

  getInputLogin(): ElementFinder {
    return element(by.id('login'));
  }

  teste(){
    let inputLogin = this.getInputLogin();
    inputLogin.sendKeys(protractor.Key.BACK_SPACE, protractor.Key.BACK_SPACE, protractor.Key.BACK_SPACE);

    expect('O campo Nome de usuário é obrigatório').toEqual(this.getLoginObrigatorio().getText());
  }

  private getLoginError(): ElementArrayFinder { 
    return element(by.id('loginError')).all(by.tagName('small'));
  }

  private getLoginErrorNumDigitos(): ElementArrayFinder { 
    return element(by.id('loginError')).all(by.tagName('small'));
  }

  getLoginObrigatorio(): ElementFinder {
    return this.getLoginError().first();
  }

  getLogin4Digitos(): ElementFinder {
    return this.getLoginErrorNumDigitos().last();
  }

  getInputSenha(): ElementFinder {
    return element(by.id('senha'));
  }

  private getSenhaError(): ElementArrayFinder {
    return element(by.id('senhaError')).all(by.tagName('small'));
  }

  getSenhaObrigatorio(): ElementFinder {
    return this.getSenhaError().first();
  }

  getSenha4Digitos(): ElementFinder {
    return this.getSenhaError().last();
  }

  getBotaoSubmit(): ElementFinder {
    return element(by.css('button[type="submit"]'));
  }

  getMensagem(): ElementFinder {
    return element(by.id('mensagem'));
  }
}
