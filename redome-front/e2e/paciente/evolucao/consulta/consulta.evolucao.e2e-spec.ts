import { Util } from '../../../util/util';
import { ConsultaEvolucaoModulePage } from './consulta.evolucao.module.po';
import { browser, element, by, ElementFinder, WebElement, ElementArrayFinder } from 'protractor';

describe('redome-front-end consulta evolucao page', () => {

    let page: ConsultaEvolucaoModulePage;
    let util: Util;

    const VALOR_COMBO_AFEGANISTAO = 1;
    const VALOR_COMBO_MOTIVO = "ACOMPANHAMENTO TRIMESTRAL";
    const VALOR_PESO = "20";
    const VALOR_TRANSPLANTE_PREVIO = "Nenhum";
    const VALOR_TRATAMENTO_ANTERIOR = "tratamento anterior com protractor";
    const VALOR_TRATAMENTO_ATUAL = "tratamento atual com protractor";
    const VALOR_CONDICAO_ATUAL = "condição atual com protractor";
    const VALOR_CONDICAO_PACIENTE = "MELHORA";

    beforeAll(() => {
        page = new ConsultaEvolucaoModulePage();
        util = new Util();
        page.navigateTo();
        page.acessarTelaPacientes();
        
    })

    beforeEach(() => {
    });

    it('deveria ter o cabeçalho de paciente com mesmo rmr', () => {
        let inputNome: ElementFinder = util.getElementoPorId("txtConsultaNome");
        inputNome.sendKeys("teste");
        util.clicarNoBotao("btnConsultaRMR");
        browser.sleep(1000);

        let link: WebElement;
        let rmr: string;
        let nome: string;
        let texto: string;

        Promise.resolve(util.getElementoPorId("tblConsultaPaciente").all(by.tagName("tbody"))
            .each((tbody: ElementFinder) => {
                tbody.all(by.tagName("tr")).each((tr: ElementFinder) => {
                    let tds: ElementArrayFinder = tr.all(by.tagName("td"));
                    tds.last().getWebElement().findElement(by.tagName("span")).then((element: WebElement) => {
                        if (!link) {
                            link = element;
                            tds.first().getText().then(texto => {
                                rmr = texto;
                            });
                            tds.get(1).getText().then(texto => {
                                nome = texto;
                            });
                        }
                    })
                    .catch(() => { });
                });
            })
        ).then(() => {
            link.click();
            page.clicarNoBotao("idBtnConsultaEvolucao");
            browser.sleep(2000);

            Promise.resolve(util.getElementoPorTag("header-paciente").all(by.className("tituloIdPaciente")).then((values: ElementFinder[]) => {
                values[0].getText().then(value => {
                    texto = value;
                })
            })).then(() => {
                expect(texto).toContain(rmr);
                expect(texto).toContain(nome);
            })
        });
    });
    
    it('peso nao deve estar vazio', () => {
        
        expect( page.getTextoDoElemento("lbPeso")).not.toBeNull();
    });

    it('altura nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbAltura")).not.toBeNull();
    });

    it('transplante previo nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbTransplanteAnt")).not.toBeNull();
    });

    it('condição do paciente nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbCondicaoPaci")).not.toBeNull();
    });

    it('tratamento anterior nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbTratamentoAnt")).not.toBeNull();
    });
    
    it('tratamento atual nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbTratamentoAtu")).not.toBeNull();
    });

    it('condição atual nao deve estar vazio', () => {
        expect( page.getTextoDoElemento("lbCondicaoAtu")).not.toBeNull();
    });

});

