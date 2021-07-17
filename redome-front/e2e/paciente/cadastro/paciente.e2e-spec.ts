import { Util } from '../../util/util';
import { CadastroModulePage } from './cadastro.module.po';
import { browser, element, by, protractor, ElementFinder } from 'protractor';

function obterInteiroAleatorio(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
}

const PARA_RESPONDER_PENDENCIA="RP";
const SOMENTE_RMR="R";
const SEM_VARIAVEL="S";
const RMR_PARA_REPROVACAO="PR";


function obterCPFAleatorio() {
    return obterInteiroAleatorio(100, 999) + "" +
           obterInteiroAleatorio(100, 999) + "" +
           obterInteiroAleatorio(100, 999) + "" +
           obterInteiroAleatorio(100, 999) + "" +
           obterInteiroAleatorio(10, 99);
}

function extrairRMR(mensagem: string, page:CadastroModulePage) {
    if(mensagem){
        return  mensagem.split(" ")[2].replace(".", "").replace(")", "");
    }else{
        return "";
    }
    
}

function extrairMensagem(page:CadastroModulePage,tipoVariavel?:string, mensagem?:string){
    
    page.getElementoPorclassName("modal-body").getText().then(mensagem => {
        switch(tipoVariavel){
            case PARA_RESPONDER_PENDENCIA:{
                browser.rmrParaRespostaPendencia = extrairRMR(mensagem[0], page);
                if(browser.rmrParaRespostaPendencia == ""){
                    browser.sleep(3000)
                    extrairMensagem(page,tipoVariavel)
                    break;
                }
                expect(mensagem[0]).toContain("incluído com sucesso.");
                page.clicarNoBotao("modalSucesso");
                break;
            }
            case SOMENTE_RMR:{
                browser.rmr = extrairRMR(mensagem[0], page);
                if(browser.rmr == ""){
                    browser.sleep(3000)
                    extrairMensagem(page,tipoVariavel)
                    break;
                }
                expect(mensagem[0]).toContain("incluído com sucesso.");
                page.clicarNoBotao("modalSucesso");
                break;
            }
            case SEM_VARIAVEL:{
                expect(mensagem[0]).toContain("incluído com sucesso.");
                page.clicarNoBotao("modalSucesso");
                break;
            }
            case RMR_PARA_REPROVACAO:{
                browser.rmrParaReprovacao = extrairRMR(mensagem[0], page);
                if(browser.rmrParaReprovacao == ""){
                    browser.sleep(3000)
                    extrairMensagem(page,tipoVariavel)
                    break;
                }
                expect(mensagem[0]).toContain("incluído com sucesso.");
                page.clicarNoBotao("modalSucesso");
                break;
            }

        }
    });    
}


describe('redome-front-end paciente page', () => {

    let page: CadastroModulePage;
    let util: Util;

    beforeAll(() => {
        util = new Util();
        page = new CadastroModulePage();
    })

    beforeEach(() => {
        page.navigateTo();
        page.acessarTelaCadastro();
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 200000;
    });

    it('Cadastrar paciente menor de idade', () => {

         let botao = util.getElementoPorclassName("confirmation-cancel").first();

        if(botao) {
            browser.executeScript("arguments[0].click();", botao.getWebElement());
        }
 
        browser.sleep(3000)
        page.setInput("txtDataNascimento","20032010");
        page.setInput("txtNome","Nome teste protactor");
        page.setInput("txtNomeMae","Teste mãe protactor");
        page.setInput("txtCPFResponsavel","32624812890");
        page.setInput("txtNomeResponsavel", "Responsável do protactor")
        page.setInput("txtParentesco", "Pai")

        page.clicarNoBotao("idProximaEtapa");
        browser.sleep(1000)
        page.setCombo("ddlNacionalidade", "BRASIL");                    
        page.setRadioComId("ddlSexoM");
        
        page.setCombo("ddlNaturalidade", "MATO GROSSO");
        page.setCombo("ddlABO", "A+");
        page.setCombo("ddlRaca", "INDÍGENA");
        page.setCombo("ddlEtnia", "AMONDÁWA");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtCEP", "21060-610");
        page.setInput("txtNumLogradouro", 70);
        page.setInput("txtComplemento","complemento");
        page.setInput("txtEmail","email@gmail.com");
        page.setInput("txtCodInter", "55");
        page.setInput("txtCodArea", "21");
        page.setInput("txtNumTelefone","22696969");
        page.setInput("txtCompTelefone", "fundos");
        page.setInput("txtNomeContato", "Nome Completo para teste");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataDiagnóstico", "04092017");
        page.setInput("txtBuscarCid", "C90.1");
        page.selecionarPrimeiroElementoDropDown("dropdown-menu");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtPeso", "800");
        page.setInput("txtAltura", "171");
        page.setCombo("ddlCmv", "Negativo");
        page.setCombo("ddlEstagioDoenca", "1ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");
        page.setInput("areaTratamentoAnterior", "Tratamento anterior");
        page.setInput("areaTratamentoAtual", "Tratamento atual");
        page.setInput("areaCondicaoAtual", "Condição atual");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataExame", "04092017");
        page.setCheckBoxComId("chkMetodologia", 1);
        page.setInput("primeiroalelo0", "01:01");
        page.setInput("segundoalelo0", "-");

        browser.sleep(3000)
        page.tab("segundoalelo0");
        
        page.setInput("primeiroalelo1", "13:73");
        page.setInput("segundoalelo1", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo1");
        
        page.setInput("primeiroalelo2", "13:01");
        page.setInput("segundoalelo2", "-");
        
        browser.sleep(3000)                          
        page.tab("segundoalelo2");

        page.enviarArquivo();
    
        browser.sleep(2000)
        page.clicarNoBotao("idProximaEtapa");

        page.setRadioComId("rdMotivoAparentado");
        page.clicarNoBotao("salvarCadastro");

        browser.sleep(20000)
            // page.getElementoPorclassName("modal-body").getText().then(res => {
            //     expect(res[0]).toContain("incluído com sucesso.");
            //     page.clicarNoBotao("modalSucesso");
            // });
        extrairMensagem(page, SEM_VARIAVEL);
    });

    it('Cadastrar paciente maior de idade', () => {

        let botao = util.getElementoPorclassName("confirmation-cancel").first();

        if(botao) {
            browser.executeScript("arguments[0].click();", botao.getWebElement());
        }
 
        browser.sleep(3000)
        page.setInput("txtDataNascimento","20031983");
        page.setInput("txtCPF", obterCPFAleatorio());
        page.setInput("txtNome","Nome teste protractor maior");
        page.clicarNoBotao("idProximaEtapa");
        browser.sleep(1000)
        page.setCombo("ddlNacionalidade", "BRASIL");                    
        page.setRadioComId("ddlSexoM");
        
        page.setCombo("ddlNaturalidade", "MATO GROSSO");
        page.setCombo("ddlABO", "A+");
        page.setCombo("ddlRaca", "INDÍGENA");
        page.setCombo("ddlEtnia", "AMONDÁWA");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtCEP", "21060-610");
        page.setInput("txtNumLogradouro", 70);
        page.setInput("txtComplemento","complemento");
        page.setInput("txtEmail","email@gmail.com");
        page.setInput("txtCodInter", "55");
        page.setInput("txtCodArea", "21");
        page.setInput("txtNumTelefone","22696969");
        page.setInput("txtCompTelefone", "fundos");
        page.setInput("txtNomeContato", "Nome Completo para teste");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataDiagnóstico", "04092017");
        page.setInput("txtBuscarCid", "C90.1");
        page.selecionarPrimeiroElementoDropDown("dropdown-menu");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtPeso", "800");
        page.setInput("txtAltura", "171");
        page.setCombo("ddlCmv", "Negativo");
        page.setCombo("ddlEstagioDoenca", "1ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");
        page.setInput("areaTratamentoAnterior", "Tratamento anterior");
        page.setInput("areaTratamentoAtual", "Tratamento atual");
        page.setInput("areaCondicaoAtual", "Condição atual");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataExame", "04092017");
        page.setCheckBoxComId("chkMetodologia", 1);
        page.setInput("primeiroalelo0", "01:01");
        page.setInput("segundoalelo0", "-");

        browser.sleep(3000)
        page.tab("segundoalelo0");
        
        page.setInput("primeiroalelo1", "13:73");
        page.setInput("segundoalelo1", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo1");
        
        page.setInput("primeiroalelo2", "13:01");
        page.setInput("segundoalelo2", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo2");

        page.enviarArquivo();
    
        browser.sleep(2000)
        page.clicarNoBotao("idProximaEtapa");

        page.setRadioComId("rdMotivoNaoAparentado");
        page.setRadioComId("rdNaoAceitaMismatch");
        page.setCombo("ddlCentroAvaliador", "HOSPITAL DAS CLÍNICAS DE PORTO ALEGRE - UFRGS");
        page.setCombo("ddlCentroTransplantador", "CTMO - INCA");
        page.clicarNoBotao("salvarCadastro");

        browser.sleep(20000)
        // page.getElementoPorclassName("modal-body").getText().then(mensagem => {
        //     browser.rmrParaReprovacao = extrairRMR(mensagem[0]);
        //     expect(mensagem[0]).toContain("incluído com sucesso.");
        //     page.clicarNoBotao("modalSucesso");
        // });
        extrairMensagem(page, RMR_PARA_REPROVACAO);
    });

    it('Cadastrar paciente estrangeiro maior de idade não aparentado', () => {

         let botao = util.getElementoPorclassName("confirmation-cancel").first();

        if(botao) {
            browser.executeScript("arguments[0].click();", botao.getWebElement());
        }
 
        browser.sleep(3000)
        page.setInput("txtDataNascimento","20031983");
        page.setInput("txtCPF", obterCPFAleatorio());
        page.setInput("txtNome","Nome teste protactor estrangeiro");
        page.clicarNoBotao("idProximaEtapa");

        browser.sleep(1000)
        page.setCombo("ddlNacionalidade", "VENEZUELA");                    
        page.setRadioComId("ddlSexoM");
        
        page.setCombo("ddlABO", "A+");
        page.setCombo("ddlRaca", "INDÍGENA");
        page.setCombo("ddlEtnia", "AMONDÁWA");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("ddlPaisResidencia","VENEZUELA");
        page.setInput("txtEndEstrangeiro", "Um super endereço estrangeiro");
        page.setInput("txtEmail","email@gmail.com");
        page.setInput("txtCodInter", "55");
        page.setInput("txtCodArea", "21");
        page.setInput("txtNumTelefone","22696969");
        page.setInput("txtCompTelefone", "fundos");
        page.setInput("txtNomeContato", "Nome Completo para teste");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataDiagnóstico", "04092017");
        page.setInput("txtBuscarCid", "C90.1");
        page.selecionarPrimeiroElementoDropDown("dropdown-menu");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtPeso", "800");
        page.setInput("txtAltura", "171");
        page.setCombo("ddlCmv", "Negativo");
        page.setCombo("ddlEstagioDoenca", "1ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");
        page.setInput("areaTratamentoAnterior", "Tratamento anterior");
        page.setInput("areaTratamentoAtual", "Tratamento atual");
        page.setInput("areaCondicaoAtual", "Condição atual");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataExame", "04092017");
        page.setCheckBoxComId("chkMetodologia", 1);
        page.setInput("primeiroalelo0", "01:01");
        page.setInput("segundoalelo0", "-");

        browser.sleep(3000)
        page.tab("segundoalelo0");
        
        page.setInput("primeiroalelo1", "13:73");
        page.setInput("segundoalelo1", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo1");
        
        page.setInput("primeiroalelo2", "13:01");
        page.setInput("segundoalelo2", "-");
        
        page.tab("segundoalelo2");

        page.enviarArquivo();
    
        browser.sleep(2000)
        page.clicarNoBotao("idProximaEtapa");

        page.setRadioComId("rdMotivoNaoAparentado");
        page.setRadioComId("rdNaoAceitaMismatch");
        page.setCombo("ddlCentroAvaliador", "HOSPITAL DAS CLÍNICAS DE PORTO ALEGRE - UFRGS");
        page.setCombo("ddlCentroTransplantador", "CTMO - INCA");
        page.clicarNoBotao("salvarCadastro");

        browser.sleep(20000);
        // page.getElementoPorclassName("modal-body").getText().then(mensagem => {
        //     browser.rmr = extrairRMR(mensagem[0]);
        //     expect(mensagem[0]).toContain("incluído com sucesso.");
        //     page.clicarNoBotao("modalSucesso");
        // })
        extrairMensagem(page, SOMENTE_RMR);
    });

    it('Cadastrar paciente maior de idade para responder pendências', () => {

        let botao = util.getElementoPorclassName("confirmation-cancel").first();

        if(botao) {
            browser.executeScript("arguments[0].click();", botao.getWebElement());
        }
    
        browser.sleep(3000)
        page.setInput("txtDataNascimento","20031983");
        page.setInput("txtCPF", obterCPFAleatorio());
        page.setInput("txtNome","Nome teste protractor maior");
        page.clicarNoBotao("idProximaEtapa");
        browser.sleep(1000)
        page.setCombo("ddlNacionalidade", "BRASIL");                    
        page.setRadioComId("ddlSexoM");
        
        page.setCombo("ddlNaturalidade", "MATO GROSSO");
        page.setCombo("ddlABO", "A+");
        page.setCombo("ddlRaca", "INDÍGENA");
        page.setCombo("ddlEtnia", "AMONDÁWA");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtCEP", "21060-610");
        page.setInput("txtNumLogradouro", 70);
        page.setInput("txtComplemento","complemento");
        page.setInput("txtEmail","email@gmail.com");
        page.setInput("txtCodInter", "55");
        page.setInput("txtCodArea", "21");
        page.setInput("txtNumTelefone","22696969");
        page.setInput("txtCompTelefone", "fundos");
        page.setInput("txtNomeContato", "Nome Completo para teste");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataDiagnóstico", "04092017");
        page.setInput("txtBuscarCid", "C90.1");
        page.selecionarPrimeiroElementoDropDown("dropdown-menu");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtPeso", "800");
        page.setInput("txtAltura", "171");
        page.setCombo("ddlCmv", "Negativo");
        page.setCombo("ddlEstagioDoenca", "1ª Remissão");
        page.setRadioComId("rdCMVPositivo");
        page.setCombo("ddlEvolucaoPaciente", "ESTÁVEL");
        page.setInput("areaTratamentoAnterior", "Tratamento anterior");
        page.setInput("areaTratamentoAtual", "Tratamento atual");
        page.setInput("areaCondicaoAtual", "Condição atual");

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtDataExame", "04092017");
        page.setCheckBoxComId("chkMetodologia", 1);
        page.setInput("primeiroalelo0", "01:01");
        page.setInput("segundoalelo0", "-");

        browser.sleep(3000)
        page.tab("segundoalelo0");
        
        page.setInput("primeiroalelo1", "13:73");
        page.setInput("segundoalelo1", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo1");
        
        page.setInput("primeiroalelo2", "13:01");
        page.setInput("segundoalelo2", "-");
        
        browser.sleep(3000)
        page.tab("segundoalelo2");

        page.enviarArquivo();
    
        browser.sleep(2000)
        page.clicarNoBotao("idProximaEtapa");

        page.setRadioComId("rdMotivoNaoAparentado");
        page.setRadioComId("rdNaoAceitaMismatch");
        page.setCombo("ddlCentroAvaliador", "HOSPITAL DAS CLÍNICAS DE PORTO ALEGRE - UFRGS");
        page.setCombo("ddlCentroTransplantador", "CTMO - INCA");
        page.clicarNoBotao("salvarCadastro");

        browser.sleep(20000)
        // page.getElementoPorclassName("modal-body").getText().then(mensagem => {
        //     browser.rmrParaRespostaPendencia = extrairRMR(mensagem[0]);
        //     expect(mensagem[0]).toContain("incluído com sucesso.");
        //     page.clicarNoBotao("modalSucesso");
        // });
        extrairMensagem(page, PARA_RESPONDER_PENDENCIA);
    });
});