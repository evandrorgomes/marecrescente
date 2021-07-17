import { Util } from '../../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class ConsultaEvolucaoModulePage extends Util{
  navigateTo() {
    return browser.get('/login');
  }

  acessarTelaPacientes() {
    this.logar();
    this.clicarNoBotao("idLinkConsultaPaciente");
    
  }


}
