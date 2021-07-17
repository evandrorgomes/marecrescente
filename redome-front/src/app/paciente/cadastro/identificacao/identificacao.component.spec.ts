import { MockDominioService } from '../../../shared/mock/mock.dominio.service';
import { IdentificacaoComponent } from './identificacao.component';
import { FakeLoader } from '../../../shared/fake.loader';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HelperTest } from '../../../shared/helper.test';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { PacienteConstantes } from '../../paciente.constantes';
import { Pais } from '../../../shared/dominio/pais';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PacienteService } from '../../paciente.service';
import { CadastroModule } from '../cadastro.module';
import { HttpClient } from '../../../shared/httpclient.service';
import { TestBed, inject, ComponentFixture, async } from '@angular/core/testing';
import {
    HttpModule,
    Http,
    Response,
    ResponseOptions,
    ResponseType,
    XHRBackend
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { MockPacienteService } from '../../../shared/mock/mock.paciente.service';

/**
 * Classe Spec para teste da classe Dados Basicos Component
 * @author Fillipe Queiroz
 */
describe('IdentificacaoComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<IdentificacaoComponent>;
    let component: IdentificacaoComponent;


    beforeEach(() => { 
        fixture = TestBed.createComponent(IdentificacaoComponent);
        component = fixture.debugElement.componentInstance;
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de cadastro foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o IdentificacaoComponent', () => {
        expect(component).toBeTruthy();
    });

    it('data de Nascimento deveria ser obrigatório', () => {
        
        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("");
        component.validateForm();

        expect('O campo Data de Nascimento é obrigatório').toEqual(component.formErrors['dataNascimento']);
        

    });

    it('data de Nascimento deveria ser MENOR que data atual', () => {

        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/2020");
        component.validateForm();

        expect('Data de Nascimento deve ser menor ou igual a data atual.').toEqual(component.formErrors['dataNascimento']);

    });

    it('data de Nascimento preenchida e válida, NÃO deve emitir mensagem de erro', () => {
    
        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("1987-03-31");
        component.validateForm();

        expect('').toEqual(component.formErrors['dataNascimento']);

    });

    it('CPF obrigatório se o paciente é maior de idade', () => {

        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/1987");
        component.validateForm();

        expect('O campo CPF é obrigatório').toEqual(component.formErrors['cpf']);

    });

    it('CNS obrigatório, se o paciente NÃO tem CPF e é maior de idade', () => {

        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/1987");
        component.identificacaoForm.controls['cpf'].setValue("");
        component.validateForm();

        expect('O campo CNS é obrigatório').toEqual(component.formErrors['cns']);

    });

    it('CPF NÃO é obrigatório, se o paciente é maior de idade e o CNS for preenchido.', () => {
    
        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/1987");
        component.identificacaoForm.controls['cns'].setValue("19873219731");
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['cpf']);

    });

    it('CPF NÃO é obrigatório, se o paciente é menor de idade', () => {
    
        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/2016");
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['cpf']);

    });

    it('CNS NÃO é obrigatório, se o paciente é maior de idade e o CPF for preenchido.', () => {

        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/1987");
        component.identificacaoForm.controls['cpf'].setValue("12087463728");
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['cns']);

    });

    it('CNS NÃO é obrigatório, se o paciente é MENOR de idade.', () => {

        component.identificacaoForm.reset();    
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/2016");
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['cns']);

    });

    it('Nome da mãe é obrigatório, se o paciente é menor de idade e o médico NÃO informar a inexistência no cadastro do paciente.', () => {

        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/2016");
        component.maeDesconhecida = false;
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('O campo Nome da Mãe é obrigatório').toEqual(component.formErrors['nomeMae']);

    });

    it('Nome da mãe NÃO é obrigatório, se o paciente é maior de idade.', () => {

        component.identificacaoForm.reset();
        component.identificacaoForm.controls['dataNascimento'].setValue("31/03/1987");
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['nomeMae']);

    });

    it('Nome da mãe NÃO é obrigatório, se o médico informar a inexistência no cadastro do paciente.', () => {

        component.identificacaoForm.reset();
        component.maeDesconhecida = true;
        component.realizarValidacoesDinamicasPorData();
        component.validateForm();

        expect('').toEqual(component.formErrors['nomeMae']);

    });

    it('Nome é obrigatório', () => {

        component.identificacaoForm.reset();
        component.identificacaoForm.controls['nome'].setValue("");
        component.validateForm();

        expect('O campo Nome é obrigatório').toEqual(component.formErrors['nome']);

    });
});