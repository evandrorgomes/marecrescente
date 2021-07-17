import { FakeLoader } from '../../../shared/fake.loader';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { EstagioDoenca } from '../../../shared/dominio/estagio.doenca';
import { EvolucaoComponent } from './evolucao.component';
import { HelperTest } from '../../../shared/helper.test';
import { CepCorreioService } from '../../../shared/cep.correio.service';
import { PacienteConstantes } from '../../paciente.constantes';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MockBackend } from '@angular/http/testing';
import { Pais } from '../../../shared/dominio/pais';
import { CepCorreio } from '../../../shared/cep.correio';
import { HttpClient } from '../../../shared/httpclient.service';
import { HttpModule, XHRBackend, ResponseOptions, Http } from '@angular/http';
import { CadastroComponent } from '../cadastro.component';
import { CadastroModule } from '../cadastro.module';
import { TestBed, async, inject, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup } from "@angular/forms";

describe('EvolucaoComponent', () => {
    let fixture: ComponentFixture<EvolucaoComponent>;
    let component: EvolucaoComponent

    beforeEach(() => {
        fixture = TestBed.createComponent(EvolucaoComponent);
        component = fixture.debugElement.componentInstance;
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o EvolucaoComponent', () => {
        expect(component).toBeTruthy();
    });


    it('deve exibir mensagem de obrigatoriedade caso o motivo esteja vazio', async(() => {
        component.evolucaoForm.controls["motivo"].setValue(null);
        component.evolucaoForm.controls["motivo"].enable();
        component.validateForm();
        expect(component.formErrors['motivo']).toEqual('O campo Motivo é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso o motivo esteja preenchido', async(() => {
        component.validateForm();
        expect(component.formErrors['motivo']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o peso esteja vazio', async(() => {
        component.validateForm();
        expect(component.formErrors['peso']).toEqual('O campo Peso (Kg) é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso o peso esteja preenchido', async(() => {
        component.evolucaoForm.controls["peso"].setValue("36.1");
        component.validateForm();
        expect(component.formErrors['peso']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso a altura esteja vazia', async(() => {
        component.validateForm();
        expect(component.formErrors['altura']).toEqual('O campo Altura é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso a altura esteja preenchida', async(() => {
        component.evolucaoForm.controls["altura"].setValue("1.70");
        component.validateForm();
        expect(component.formErrors['altura']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o tratamento anterior esteja vazio', async(() => {
        component.validateForm();
        expect(component.formErrors['tratamentoAnterior']).toEqual('O campo Tratamento Anterior é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso o tratamento anterior esteja preenchido', async(() => {
        component.evolucaoForm.controls["tratamentoAnterior"].setValue("Algum tratamento anterior");
        component.validateForm();
        expect(component.formErrors['tratamentoAnterior']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o tratamento atual esteja vazio', async(() => {
        component.validateForm();
        expect(component.formErrors['tratamentoAtual']).toEqual('O campo Tratamento Atual é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso o tratamento atual esteja preenchido', async(() => {
        component.evolucaoForm.controls["tratamentoAtual"].setValue("Algum tratamento atual");
        component.validateForm();
        expect(component.formErrors['tratamentoAtual']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso a condição atual esteja vazia', async(() => {
        component.validateForm();
        expect(component.formErrors['condicaoAtual']).toEqual('O campo Condição Atual é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso a condição atual esteja preenchida', async(() => {
        component.evolucaoForm.controls["condicaoAtual"].setValue("Alguma condição atual");
        component.validateForm();
        expect(component.formErrors['condicaoAtual']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso evolução da doença esteja vazia', async(() => {
        component.validateForm();
        expect(component.formErrors['evolucao']).toEqual('O campo Condição do paciente é obrigatório');
    }));

    it('Não deve exibir mensagem de obrigatoriedade caso evolução da doença esteja preenchida', async(() => {
        component.evolucaoForm.controls["evolucao"].setValue("Evolução da doença atual");
        component.validateForm();
        expect(component.formErrors['evolucao']).toEqual('');
    }));
    

});