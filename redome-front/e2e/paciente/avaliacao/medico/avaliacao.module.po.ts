import { Util } from '../../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class AvaliacaoModulePage extends Util{

  navigateToConsulta() {
    browser.get('/redome/login');
    this.logar();
    this.clicarNoBotao("idLinkConsultaPaciente");
  }
  

}
