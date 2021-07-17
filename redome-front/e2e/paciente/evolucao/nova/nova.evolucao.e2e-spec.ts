import { NovaEvolucaoModulePage } from './nova.evolucao.module.po';
import { browser, element, by,  ElementFinder,protractor, ElementArrayFinder } from 'protractor';

describe('redome-front-end nova evolucao page', () => {

    let page: NovaEvolucaoModulePage;
    let rmr: string;

    const VALOR_COMBO_AFEGANISTAO = 1;
    const VALOR_COMBO_MOTIVO = "ACOMPANHAMENTO TRIMESTRAL";
    const VALOR_PESO = "20";
    const VALOR_TRANSPLANTE_PREVIO = "Nenhum";
    const VALOR_TRATAMENTO_ANTERIOR = "tratamento anterior com protractor";
    const VALOR_TRATAMENTO_ATUAL = "tratamento atual com protractor";
    const VALOR_CONDICAO_ATUAL = "condição atual com protractor";
    const VALOR_CONDICAO_PACIENTE = "MELHORA";

    beforeAll(() => {
        page = new NovaEvolucaoModulePage();
        rmr = browser.rmrParaRespostaPendencia;
    })

    function abrirTelaNovaEvolucao(_rmr: string) {        
        page.navigateToConsulta();        
        page.clearInput("txtConsultaRMR");
        page.setInput("txtConsultaRMR", _rmr);
        page.clicarNoBotao("btnConsultaRMR");
        browser.sleep(2000);
        page.clicarNoBotao("idBtnConsultaEvolucao");
        browser.sleep(2000);
        page.clicarNoBotao("btnNovaEvolucao");    
    }

    function abrirTelaNovaEvolucaoPelaAvaliacao(_rmr: string) {
        page.navigateToConsulta();        
        page.clearInput("txtConsultaRMR");
        page.setInput("txtConsultaRMR", _rmr);
        page.clicarNoBotao("btnConsultaRMR");
        browser.sleep(2000);
        page.clicarNoBotao("idVoltaConsulta");
        browser.sleep(2000);
        page.clicarNoElementoSemJavaScript("glyphicon glyphicon-eye-open");
        browser.sleep(2000);
        page.clicarNoBotao("btnTodasEvolucoes");
        browser.sleep(2000);
        page.clicarNoBotao("btnNovaEvolucao");
    }

    beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 120000;
    });

    it('deve criar uma nova evolução sem associar a pendência', () => {
        abrirTelaNovaEvolucao(rmr)
        page.setCombo("ddlMotivoEvolucao",VALOR_COMBO_MOTIVO);
        page.setInput("txtPeso",VALOR_PESO);
        page.setCombo("ddlCmv", "Positivo");
        page.setCombo("ddlEstagioDoenca", "2ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");

        page.setInput("areaTratamentoAnterior", VALOR_TRATAMENTO_ANTERIOR);
        page.setInput("areaTratamentoAtual", VALOR_TRATAMENTO_ATUAL);
        page.setInput("areaCondicaoAtual",VALOR_CONDICAO_ATUAL);

        page.clicarNoBotao("btnGuardarEvolucao");        
        browser.sleep(2000);
        page.getElementoPorclassName("modal-body").getText().then( sucesso => {
            expect(sucesso[0]).toBe("Evolução do paciente incluída com sucesso.");
        });
    });

    it('deve criar uma nova evolução associando uma pendência', () => {
        abrirTelaNovaEvolucao(rmr)
        page.setCombo("ddlMotivoEvolucao",VALOR_COMBO_MOTIVO);
        page.setInput("txtPeso",VALOR_PESO);
        page.setCombo("ddlCmv", "Positivo");
        page.setCombo("ddlEstagioDoenca", "2ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");

        page.setInput("areaTratamentoAnterior", VALOR_TRATAMENTO_ANTERIOR);
        page.setInput("areaTratamentoAtual", VALOR_TRATAMENTO_ATUAL);
        page.setInput("areaCondicaoAtual",VALOR_CONDICAO_ATUAL);

        page.clicarNoBotao("btnGuardarEvolucao");        
        browser.sleep(2000);
        page.getElementoPorclassName("modal-content").getText().then( sucesso => {
            expect(sucesso[0]).toBe("×\nEvolução do paciente incluída com sucesso.");
        });

    });

    it('deve criar uma nova evolução associando uma pendência pela tela de avaliação', () => {
        abrirTelaNovaEvolucaoPelaAvaliacao(rmr)
        page.setCombo("ddlMotivoEvolucao",VALOR_COMBO_MOTIVO);
        page.setInput("txtPeso",VALOR_PESO);
        page.setCombo("ddlCmv", "Positivo");
        page.setCombo("ddlEstagioDoenca", "2ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");

        page.setInput("areaTratamentoAnterior", VALOR_TRATAMENTO_ANTERIOR);
        page.setInput("areaTratamentoAtual", VALOR_TRATAMENTO_ATUAL);
        page.setInput("areaCondicaoAtual",VALOR_CONDICAO_ATUAL);
        page.setRadioComId("rdPerguntaSim");
        page.getElementoPorId("ckSelecionado0").click();
        page.setInput("txResposta", "Respondendo uma pendência com evolução");
        page.setRadioComId("rdRespondePendenciasSim");

        page.clicarNoBotao("btnGuardarEvolucao");        
        browser.sleep(2000);
        page.getElementoPorclassName("modal-content").getText().then( sucesso => {
            expect(sucesso[0]).toBe("×\nEvolução do paciente incluída com sucesso.");
        });
    });
});