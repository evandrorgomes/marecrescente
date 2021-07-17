import { Util } from '../../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';

export class NovoExameModulePage extends Util {

  acessarTelaNovoExameBuscandoPorRMR(_rmr: string) {
    this.clearInput("txtConsultaRMR");
    this.setInput("txtConsultaRMR", _rmr);
    this.clicarNoBotao("btnConsultaRMR");
    browser.sleep(2000);
    this.clicarNoBotao("idConsultaExames");
    browser.sleep(2000);
    this.clicarNoBotao("idBtnNovoExame");
  }

  acessarTelaNovoExamePelaAvaliacaoBuscandoPorRMR(_rmr: string) {
    this.clearInput("txtConsultaRMR");
    this.setInput("txtConsultaRMR", _rmr);
    this.clicarNoBotao("btnConsultaRMR");
    browser.sleep(2000);
    this.clicarNoBotao("idVoltaConsulta");
    browser.sleep(2000);
    this.clicarNoElementoSemJavaScript("glyphicon glyphicon-eye-open");
  }

  logarComoMedico() {
    browser.get('/login');
    this.logar();
  }

  acessarTelaNovoExame() {
    this.logar();
    this.clicarNoBotao("idLinkConsultaPaciente");
    this.setInput("txtConsultaNome", "Teste")
    this.clicarNoBotao("btnConsultaRMR");
    //browser.sleep(10000);
    /* this.getElementoPorId("tblConsultaPaciente").getWebElement().findElements(
        by.tagName("span") ).then(function(options){
            options[0].click()
        }
    ) */
    
    browser.sleep(1000);
    this.clicarNoBotao("idConsultaExames");
    browser.sleep(1000);
    this.clicarNoBotao("idBtnNovoExame");
  }

  public static formatarData(data: string): string{
        if(data){
            let dataSplit = data.split("-");
            
            return (dataSplit[2].length == 1 ? "0" + dataSplit[2] : dataSplit[2])
                + "/" + (dataSplit[1].length == 1 ? "0" + dataSplit[1] : dataSplit[1])
                + "/" + dataSplit[0]
        }
        return null;
  }
}
