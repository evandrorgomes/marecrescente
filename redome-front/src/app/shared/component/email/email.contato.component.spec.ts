import { TranslateService } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';
import { AutenticacaoModule } from '../../autenticacao/autenticacao.module';
import { ExportTranslateModule } from '../../export.translate.module';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';
import { EmailContatoComponent } from './email.contato.component';
import { EmailContatoModule } from './email.contato.module';

describe('EmailContatoComponent', () => {
    let fixture: ComponentFixture<EmailContatoComponent>;
    let component: EmailContatoComponent

    beforeEach(() => {
        fixture = TestBed.createComponent(EmailContatoComponent);
        component = fixture.componentInstance;        
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o EnderecoContatoComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir a mensagem \'Os campos marcados em vermelho são obrigatórios\' caso não tenha preenchido algum campo do telefone', async(() => {
        
        component.validateForm();
        expect('Os campos marcados em vermelho são obrigatórios').toEqual(component.formErrors['emailsContato']);
        
    }));

    it('deve exibir a mensagem \'Pelo menos um email deve ser o principal\' caso não tenha marcado nenhum email como principal', async(() => {
        component.listaEmailForm.get('0.email').setValue('teste@gmail.com');
        component.adicionarEmail();
        component.listaEmailForm.get('1.email').setValue('teste2@gmail.com');
        component.validateForm();
        expect('Pelo menos um email deve ser o principal').toEqual(component.formErrors['emailPrincipal']);
    }));

    it('não deve exibir a mensagem \'Pelo menos um email deve ser o principal\' caso a propiedade "validarPrincipal" seja false', async(() => {
        component.validarPrincipal = "false";
        component.listaEmailForm.get('0.email').setValue('teste@gmail.com');
        component.adicionarEmail();
        component.listaEmailForm.get('1.email').setValue('teste2@gmail.com');
        component.validateForm();
        expect('').toEqual(component.formErrors['emailPrincipal']);
    }));


});