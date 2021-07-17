import { Metodologia } from 'app/paciente/cadastro/exame/metodologia';
import { browser, by, ElementFinder, WebElement } from 'protractor';
import { async } from '@angular/core/testing';
import { NovoExameModulePage } from './novo.exame.module.po';

jasmine.DEFAULT_TIMEOUT_INTERVAL = 30000;

describe('redome-front-end novo exame page', () => {

    let page: NovoExameModulePage;
    let rmrResponderPendencia: string;

    const DATA_EXAME: string = NovoExameModulePage.formatarData(new Date().toLocaleDateString());
    const METODOLOGIA: string = "NGS - Next Generation Sequencing";    
    const LOCUS: string = "C*";
    const ALELO1: string = "01:02:01";
    const ALELO2: string = "-";
    
    beforeAll(() => {
        page = new NovoExameModulePage();

        rmrResponderPendencia = browser.rmrParaRespostaPendencia;
    });

    beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 120000;
    });

    it('deve encerrar uma pendência com um novo exame', async() => {
        page.logarComoMedico();
        page.clicarNoBotao("idLinkConsultaPaciente");
        page.acessarTelaNovoExameBuscandoPorRMR(rmrResponderPendencia);
        page.setInput("txtDataExame", DATA_EXAME);
        page.setCheckBoxComId("chkMetodologia", 0);
        page.setCombo("locusSelecionado", LOCUS);
        page.setInput("primeiroalelo0", ALELO1);
        page.setInput("segundoalelo0", ALELO2);
        page.enviarArquivo();
        browser.sleep(2000);
        page.setRadioComId("rdPerguntaSim");
        page.getElementoPorId("ckSelecionado0").click();
        page.getElementoPorId("rdRespondePendenciasSim").click();
        page.clicarNoBotao("btnGuardarExame");

        browser.sleep(2000);
        expect(page.getElementoPorId("mensagemSucesso").getText()).toBe("Exame do paciente incluído com sucesso.");

        page.clicarNoBotao("btnFecharModal");
    });


    it('deve encerrar uma pendência com um novo exame acessando pela avaliacao', async() => {
        page.logarComoMedico();
        page.clicarNoBotao("idLinkConsultaPaciente");
        page.acessarTelaNovoExamePelaAvaliacaoBuscandoPorRMR(rmrResponderPendencia);
        browser.sleep(2000);
        page.getElementoPorId("tabelaPendencias").element(by.tagName("td")).click();
        browser.sleep(2000);
        page.clicarNoBotao("btnNovoExame");
        page.setInput("txtDataExame", DATA_EXAME);
        page.setCheckBoxComId("chkMetodologia", 0);
        page.setCombo("locusSelecionado", LOCUS);
        page.setInput("primeiroalelo0", ALELO1);
        page.setInput("segundoalelo0", ALELO2);
        page.enviarArquivo();
        browser.sleep(2000);
        page.clicarNoBotao("btnGuardarExame");

        browser.sleep(2000);
        expect(page.getElementoPorId("mensagemSucesso").getText()).toBe("Exame do paciente incluído com sucesso.");

        page.clicarNoBotao("btnFecharModal");
    });
});