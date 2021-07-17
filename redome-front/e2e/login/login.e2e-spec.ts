import { browser, element, by, protractor } from 'protractor';
import { LoginModulePage } from './loginmodule.po';

describe('redome-front-end login page', () => {

    let page: LoginModulePage;

    beforeAll(() => {
        page = new LoginModulePage();
        page.navigateTo();
    })

    beforeEach(() => {
    });

    it('Deve pegar mensagem de erro do login com menos de 4 digitros', () => {

        let inputLogin = page.getInputLogin();
        inputLogin.sendKeys('wer');

        expect('O campo Nome de usuário deve ter ao menos 4 dígitos').toEqual(page.getLogin4Digitos().getText());
    });

    it('Deve pegar mensagem de erro do login obrigatorio', () => {
        page.teste();
        
    });

    it('Deve pegar mensagem de erro da senha com menos de 4 digitros', () => {

        let inputSenha = page.getInputSenha();
        if(inputSenha)
            console.log("tem inputSenha")
        else
            console.log("Não tem inputSenha")
        inputSenha.sendKeys('wer');

        expect('O campo Senha deve ter ao menos 4 dígitos').toEqual(page.getSenha4Digitos().getText());
    });

    it('Deve pegar mensagem de erro da senha obrigatoria', () => {

        let inputSenha = page.getInputSenha();
        inputSenha.sendKeys(protractor.Key.BACK_SPACE, protractor.Key.BACK_SPACE, protractor.Key.BACK_SPACE);

        expect('O campo Senha é obrigatório').toEqual(page.getSenhaObrigatorio().getText());
    });

    it('Deve pegar mensagem do topo quando feito submit', () => {

        let inputLogin = page.getInputLogin();
        inputLogin.sendKeys('qwert');

        let inputSenha = page.getInputSenha();
        inputSenha.sendKeys('12345');

        /*
            Bruno Sousa
            Utilizar o método executeScript para enviar o click do botão. O método click() não funciona no internet explorer.
        */
        let botao = page.getBotaoSubmit();
        browser.executeScript("arguments[0].click();", botao.getWebElement());
        
        expect('Usuário ou senha inválidos.').toEqual(page.getMensagem().getText());
    });
});

