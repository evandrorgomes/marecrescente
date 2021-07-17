import { Util } from '../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class CadastroModulePage extends Util{
  navigateTo() {
    return browser.get('/redome/login');
  }

  acessarTelaCadastro() {
    this.logar();
    this.clicarNoBotao("idLinkCadastroPaciente");
  }
}
