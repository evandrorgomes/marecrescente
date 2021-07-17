import { Util } from '../../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class ConsultaExameModulePage extends Util{
  navigateTo() {
    return browser.get('/redome/login');
  }

  acessarTelaExames() {
    this.logar();
    this.clicarNoBotao("idLinkConsultaPaciente");
  }
}
