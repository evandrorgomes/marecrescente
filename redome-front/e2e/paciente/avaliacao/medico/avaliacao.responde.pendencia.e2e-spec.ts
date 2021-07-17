import { AvaliacaoModulePage } from './avaliacao.module.po';
import { browser, element, by,  ElementFinder,protractor, ElementArrayFinder } from 'protractor';

describe('redome-front-end nova evolucao page', () => {

    let page: AvaliacaoModulePage;
    let rmr: string;

    beforeAll(() => {
        page = new AvaliacaoModulePage();
        rmr = browser.rmrParaRespostaPendencia;
    })

    function abrirTelaAvaliacao(_rmr: string) {
        page.navigateToConsulta();        
        page.clearInput("txtConsultaRMR");
        page.setInput("txtConsultaRMR", _rmr);
        page.clicarNoBotao("btnConsultaRMR");
        browser.sleep(2000);
        page.clicarNoBotao("idVoltaConsulta");
        browser.sleep(2000);
        page.clicarNoElementoSemJavaScript("glyphicon glyphicon-eye-open");
    }

    beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 120000;
    });

    it('deve responder uma pendência de questionamento', () => {
        abrirTelaAvaliacao(rmr)
        browser.sleep(2000);
        page.getElementoPorId("tabelaPendencias").element(by.tagName("td")).click();
        browser.sleep(2000);
        page.clicarNoBotao("btnResponderPendencia");
        page.setInput("txtAreaComentario", "Respondendo uma pendência de questionamento");
        page.setRadioComId("rdRespondePendenciasSim");
        page.clicarNoBotao("btnSalvarPendencia");

        browser.sleep(2000);
        page.getElementoPorId("statusPendencia3").getText().then( sucesso => {
            expect(sucesso).toBe("RESPONDIDA");
        });
    });
});

