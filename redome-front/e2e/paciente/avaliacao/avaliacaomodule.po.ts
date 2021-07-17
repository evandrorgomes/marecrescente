import { Util } from '../../util/util';
import { browser, element, by, ElementArrayFinder, ElementFinder } from 'protractor';
export class AvaliacaoModulePage extends Util {

    navigateToAvaliacao() {
        browser.get('/redome/login');
        this.logarComoAvaliador();        
    }

    logarComoMedico() {
        browser.get('/redome/login');
        this.logar();
    }
}