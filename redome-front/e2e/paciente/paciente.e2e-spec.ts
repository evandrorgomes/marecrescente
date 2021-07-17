import { browser, element, by, protractor } from 'protractor';
import { PacienteModulePage } from './pacientemodule.po';

describe('redome-front-end paciente page', () => {

    let page: PacienteModulePage;

    const VALOR_COMBO_AFEGANISTAO = 1;

    beforeAll(() => {
        page = new PacienteModulePage();
        page.navigateTo();
    })

    beforeEach(() => {
        let inputLogin = page.getElementoPorId("login");
        inputLogin.sendKeys("medico")
        let inputSenha = page.getElementoPorId("senha");
        inputSenha.sendKeys("123456")
        
        page.submeterLogin();
        page.clicarNoBotao("idLinkCadastroPaciente");
    });

    it('pegar a data de nascimento', () => {
        page.setInput("txtDataNascimento","20/03/2010")
        page.setInput("txtNome","Nome teste protactor")
        page.setInput("txtNomeMae","Teste mãe protactor")
        page.setInput("txtCPFResponsavel","326.248.128-90");
        page.setInput("txtNomeResponsavel", "Responsável do protactor")
        page.setInput("txtParentesco", "Pai")

        page.clicarNoBotao("idProximaEtapa");
        browser.sleep(1000);
        
        page.setCombo("ddlNacionalidade", "BRASIL");

        page.setRadioComId("ddlSexoM");
        
        page.setCombo("ddlNaturalidade", "MATO GROSSO");
        page.setCombo("ddlABO", "A+")
        page.setCombo("ddlRaca", "INDÍGENA")
        page.setCombo("ddlEtnia", "AMONDÁWA")

        page.clicarNoBotao("idProximaEtapa");

        page.setInput("txtCEP", "21060-610");
        page.setInput("txtNumLogradouro", 70);
        page.setInput("txtComplemento","complemento")
        page.setInput("txtEmail","email@gmail.com")

        //Exemplo para iterar elemntos
        // comboPaisNacionalidade.getWebElement().findElements(by.tagName('option')).then(function (options) {
        //     options[5].click();
        //     options.forEach(opt =>{console.log(opt.getText())})
        //     // console.log(options)

        // });

        browser.sleep(5000);
    });
});

