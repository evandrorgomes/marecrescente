import { AvaliacaoModulePage } from './avaliacaomodule.po';
import { Util } from '../../util/util';
import { browser, element, by, protractor } from 'protractor';

describe('Teste da tela de avaliação de paciente', () => {

    let page: AvaliacaoModulePage;
    let rmr: string;
    let rmrParaReprovacao: string;
    let rmrParaRespostaPendencia: string;

    beforeAll(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 200000;
        page = new AvaliacaoModulePage();

        //o rmr foi gerado dinamicamente ao executar o
        //teste de cadastro de paciente
        rmr = browser.rmr;
        // rmr = "3001250";
        rmrParaReprovacao = browser.rmrParaReprovacao;
        // rmrParaReprovacao = "3000352";
        rmrParaRespostaPendencia = browser.rmrParaRespostaPendencia;
        // rmrParaRespostaPendencia = "3001025";
    })

    function abrirTelaAvalicao(_rmr: string) {
        page.navigateToAvaliacao();
        page.clicarNoBotao("idLinkAvaliacaoPaciente");
        page.setInput("txtConsultaRMR", _rmr);
        page.clicarNoBotao("btnConsultaRMR");
        browser.sleep(3000);
    }

    function criarPendenciaDeExame(texto) {
        abrirTelaAvalicao(rmrParaRespostaPendencia);

        page.clicarNoBotao("btnCrirarPendencia");
        page.setRadioComId("rdTipoPendencia0");
        page.setInput("txtAreaDescricaoPendencia", texto);
        page.clicarNoBotao("btnSalvarNovaPendencia");
    }

    function criarPendenciaDeEvolucao(texto) {
        page.clicarNoBotao("btnCrirarPendencia");
        page.setRadioComId("rdTipoPendencia1");
        page.setInput("txtAreaDescricaoPendencia", texto);
        page.clicarNoBotao("btnSalvarNovaPendencia");
    }

    function crirarPendenciaQuestionamento(texto) {
        page.clicarNoBotao("btnCrirarPendencia");
        page.setRadioComId("rdTipoPendencia2");
        page.setInput("txtAreaDescricaoPendencia", texto);
        page.clicarNoBotao("btnSalvarNovaPendencia");
    }

    it('deve criar uma pendência', () => {
        abrirTelaAvalicao(rmr);
        page.clicarNoBotao("btnCrirarPendencia");
        page.setRadioComId("rdTipoPendencia0");
        page.setInput("txtAreaDescricaoPendencia", "Uma descrição com protractor.");
        page.clicarNoBotao("btnSalvarNovaPendencia");

        browser.sleep(3000);
        expect(page.getElementoPorTag("tr").count()).toBe(2);
    });

    it('deve incluir mais um comentário em uma pendência já criada', () => {
         abrirTelaAvalicao(rmr);

        //o padrão do protractor é sempre usar a primeira ocorrência caso haja mais de uma
        page.getElementoPorclassName("visualizaPendencia").click();
        page.clicarNoBotao("btnResponderPendencia");
        page.setInput("txtAreaComentario", "Um nova descrição.");
        page.clicarNoBotao("btnComentarPendencia");

        browser.sleep(3000);
        page.getElementoPorId("tabelaPendencias").element(by.tagName("td")).click();

        expect(2).toBe(page.getElementoPorclassName('balaoPendenciaResposta').count());
    });

    it('deve cancelar uma pendência', () => {
        abrirTelaAvalicao(rmr);
        page.getElementoPorclassName("visualizaPendencia").click();
        page.clicarNoBotao("btnCancelarPendencia");
        page.clicarNoBotao("pgCancelarPendenciabtnSim");

        browser.sleep(3000);
        expect(page.getElementoPorId("statusPendencia0").getText()).toBe("CANCELADA");
    });

    it('deve aprovar a avaliação do paciente', () => {
        abrirTelaAvalicao(rmr);
        page.clicarNoBotao("btnAprovarAvaliacao");
        page.setInput("txtAreaObservacao", "Uma super observação de aprovação do protractor.");
        page.clicarNoBotao("btnConfirmarAprovacao");

        browser.sleep(3000);

        page.getElementoPorclassName("modal-body").getText().then( sucesso => {
            expect(sucesso[1]).toBe("Avaliação aprovada com sucesso.");
        });
    });

    it('deve reprovar uma busca', () => {
        abrirTelaAvalicao(rmrParaReprovacao);
        page.clicarNoBotao("btnReprovarBusca");
        page.setInput("txtAreaObservacao", "Um super observação de reprovação do protractor.");
        page.clicarNoBotao("btnConfirmarReprovacao");

        browser.sleep(3000);
         page.getElementoPorclassName("modal-content").getText().then( sucesso => {
            expect(sucesso[2]).toBe("Avaliação reprovada com sucesso.");
        });
    });

    it('deve criar uma pendência de exame', () => {
        criarPendenciaDeExame("Uma pendência de exame com protractor.");
        browser.sleep(3000);

        criarPendenciaDeExame("outra pendência de exame com protractor.");
        browser.sleep(3000);

        criarPendenciaDeEvolucao("Uma pendência de evolução com protractor.");
        browser.sleep(3000);

        criarPendenciaDeEvolucao("Outra pendência de evolução com protractor.");
        browser.sleep(3000);

        crirarPendenciaQuestionamento("Uma pendência de questionamento com protractor.");
        
        browser.sleep(3000);
        expect(page.getElementoPorTag("tr").count()).toBe(6);
    });

    it('deve listar as pendências de um paciente - visão médico', () => {
        page.logarComoMedico();
        page.clicarNoBotao("idLinkConsultaPaciente");
        page.setInput("txtConsultaRMR", rmr);
        page.clicarNoBotao("btnConsultaRMR");
        page.clicarNoBotao("idVoltaConsulta");
        page.clicarNoBotao("iconeVisualizarPendencias");
        browser.sleep(3000);

        expect(page.getElementoPorTag("tr").count()).toBeGreaterThan(1);
    });
});