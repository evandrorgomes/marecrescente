import { Observable } from 'rxjs';
import { Util } from '../../util/util';
import { browser, element, by, protractor, ElementArrayFinder, ElementFinder, WebElement } from 'protractor';
import { PacienteModulePage } from '../pacientemodule.po';

describe('redome-front-end pagina de consulta do paciente', () => {

    let page: PacienteModulePage;
    let util: Util;

    beforeAll(() => {
        util = new Util();

        page = new PacienteModulePage();
        page.navigateTo();
        util.logar();

        page.acessarTelaPaciente();
    });

    it('deve retornar o titulo da página como "Consulta de Paciente" ', () => {
         let titulo: ElementFinder = util.getElementoPorTag('h2').first();
         expect('Consulta de Paciente').toEqual(titulo.getText());
 
     });
 
     it('deve retornar a mensagem "Parâmetros mínimos não fornecidos." quando a busca é feita sem informar o rmr e nome', () => {
         util.clicarNoBotao("btnConsultaRMR");
         let modal: ElementFinder = util.getElementoPorclassName("modal-body").first();
         let msg: ElementFinder =  modal.all(by.tagName("p")).first();
         expect("Parâmetros mínimos não fornecidos.").toEqual(msg.getText());   
         browser.sleep(1000);     
         util.fecharModal();
     });
 
     it('deve retornar a mensagem "Nennhum registro encontrado." quando buscar um rmr inexistente', () => {
         let inputRmr: ElementFinder = util.getElementoPorId("txtConsultaRMR");
         inputRmr.sendKeys("1456789");
         util.clicarNoBotao("btnConsultaRMR");
         browser.sleep(1000);
         let modal: ElementFinder = util.getElementoPorclassName("modal-body").first();
         let msg: ElementFinder =  modal.all(by.tagName("p")).first();
         expect("Nennhum registro encontrado.").toEqual(msg.getText());
         browser.sleep(1000);
         inputRmr.clear();
         util.fecharModal();
     });
 
     it('deve retornar a mensagem "Nennhum registro encontrado." quando buscar um nome inexistente', () => {
         let inputNome: ElementFinder = util.getElementoPorId("txtConsultaNome");
         inputNome.sendKeys("abscedfg");
         util.clicarNoBotao("btnConsultaRMR");
         browser.sleep(1000);
         let modal: ElementFinder = util.getElementoPorclassName("modal-body").first();
         let msg: ElementFinder =  modal.all(by.tagName("p")).first();
         expect("Nennhum registro encontrado.").toEqual(msg.getText());
         browser.sleep(1000);
         util.fecharModal();
         inputNome.clear();
         inputNome.sendKeys(" ");
 
     });
 
     it('deve redirecionar para a pagina de detalhe quando a pesquisa por rmr e o paciente for do médico ', () => {
         let inputRmr: ElementFinder = util.getElementoPorId("txtConsultaRMR");
         inputRmr.sendKeys("3000282");
         util.clicarNoBotao("btnConsultaRMR");
         browser.sleep(1000);
 
         let titulo: ElementFinder = util.getElementoPorTag('h2').first();
         expect('Detalhe do Paciente').toEqual(titulo.getText());
 
         browser.sleep(1000);
         util.clicarNoBotao("idVoltaConsulta");
 
     });
 
     it('não deve redirecionar para a pagina de detalhe quando a pesquisa por rmr e o paciente não for do médico', () => {
         browser.sleep(5000);
         let inputRmr: ElementFinder = util.getElementoPorId("txtConsultaRMR");
         inputRmr.clear();
         inputRmr.sendKeys("102");
         util.clicarNoBotao("btnConsultaRMR");
         browser.sleep(1000);
 
         let titulo: ElementFinder = util.getElementoPorTag('h2').first();
         expect('Consulta de Paciente').toEqual(titulo.getText());
 
         browser.sleep(1000);
     });

    it('deve trazer uma lista de pacientes como o nome "teste"', () => {
        page.acessarTelaPaciente();
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




});