import { ConsultaExameModulePage } from './consulta.exame.module.po';
import { Util } from '../../../util/util';
import { browser, element, by, ElementFinder, WebElement, ElementArrayFinder } from 'protractor';

//jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;

describe('redome-front-end consulta exames page', () => {
    let page: ConsultaExameModulePage;
    let util: Util;

    beforeAll(() => {
        page = new ConsultaExameModulePage();
        util = new Util();
        page.navigateTo();
        page.acessarTelaExames();

    })

    beforeEach(() => {
    });

    it('deveria ter o cabeçalho de paciente com mesmo rmr', () => {
        // page.acessarTelaPaciente();
        let inputNome: ElementFinder = util.getElementoPorId("txtConsultaNome");
        inputNome.sendKeys("Antonio César Costa");
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
            page.clicarNoBotao("idConsultaExames");

            Promise.resolve(util.getElementoPorTag("header-paciente").all(by.className("tituloIdPaciente")).then((values: ElementFinder[]) => {
                values[0].getText().then(value => {
                    texto = value;
                })
            })).then(() => {
                expect(texto).toContain(rmr);
                expect(texto).toContain(nome);
            })
        })
        .catch(() => {
            expect(true).toBeFalsy();
        });
    });

    it('Combo de exames não deve estar vazio', () => {
        util.getElementoPorId("ddlComboExames").all(by.tagName('option')).then(elements => {
            let selectedElementFinder: ElementFinder = elements[0];
            selectedElementFinder.getText().then(texto => {
                expect(texto).not.toBeNull();
            })
        });

    });

    it('metodologia nao deve estar vazio', () => {
        expect(page.getTextoDoElemento("spMetodologia")).not.toBeNull();
    });

    it('ao menos 1 locus deve estar preenchido', () => {
        util.getElementoPorclassName("box").first().getWebElement().findElement(
            by.tagName("p")).then(elemento => {
                elemento.getText().then(texto => {
                    expect(texto).not.toBeNull();
                })
            })
    });

    it('ao visualizar o arquivo de laudo deve-se abrir, uma nova janela, o anexo', () => {
        util.getElementoPorId("btVisualizar").click().then(() => {
                browser.getAllWindowHandles().then(function (handles) {
                    let newWindowHandle = handles[1];
                    browser.switchTo().window(newWindowHandle).then(function () {
                        // Verificando parte final da URL gerada pela nova página.
                        browser.driver.getCurrentUrl().then(
                            downloadUrl => {
                                let partesUrls:string[] = downloadUrl.split("/");
                                expect(partesUrls[partesUrls.length - 1]).toEqual("download");
                            }
                        )
                        // Verificando se há o elemento title tem o texto "download" no conteúdo 
                        // da nova página.
                        // FIXME: Analisar necessidade deste teste 
                        /* browser.driver.findElement(by.tagName("title")).then(elemento => {
                            console.log("Elemento: " + elemento);
                            elemento.getText().then(texto => {
                                expect(texto).toEqual("download");
                            })
                        }) */
                    });
                });
                
                browser.sleep(6000);
            }
        );
    });
});