import { TestBed  } from '@angular/core/testing';
import { PacienteService } from 'app/paciente/paciente.service';
import { forEach } from '@angular/router/src/utils/collection';
import { AvaliacaoModulePage } from './avaliacaomodule.po';
import { Observable } from 'rxjs';
import { Util } from '../../util/util';
import { browser, element, by, protractor, ElementArrayFinder, ElementFinder, WebElement } from 'protractor';

describe('Teste da tela de avaliação de paciente', () => {

    let page: AvaliacaoModulePage;
    let rmr: string;

    beforeAll(() => {
        page = new AvaliacaoModulePage();

        //o rmr foi gerado dinamicamente ao executar o
        //teste de cadastro de paciente
        rmr = browser.rmrParaRespostaPendencia;        
    })

    function abrirTelaAvalicao(_rmr: string) {
        page.navigateToAvaliacao();
        page.clicarNoBotao("idLinkAvaliacaoPaciente");
        page.setInput("txtConsultaRMR", _rmr);
        page.clicarNoBotao("btnConsultaRMR");
        browser.sleep(3000);
    }

    it('deve fechar uma pendência', () => {
        abrirTelaAvalicao(rmr);
        page.getElementoPorId("tabelaPendencias").element(by.tagName("td")).click();
        browser.sleep(2000);
        page.clicarNoBotao("btnFecharPendencia");

        browser.sleep(2000);
        expect(page.getElementoPorId("statusPendencia4").getText()).toBe("FECHADA");
    });
});