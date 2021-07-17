import { MockCepCorreioService } from '../../mock/mock.cep.correio.service';
import { ExportTranslateModule } from '../../export.translate.module';
import { CepCorreioService } from '../../cep.correio.service';
import { MockDominioService } from '../../mock/mock.dominio.service';
import { DominioService } from '../../dominio/dominio.service';
import { EnderecoContatoModule } from './endereco.contato.module';
import { AppModule } from '../../../app.module';
import { HelperTest } from '../../helper.test';
import { EnderecoContatoComponent } from './endereco.contato.component';
import { AppComponent } from '../../../app.component';
import { CommonModule } from '@angular/common';
import { Injector } from '@angular/core/src/di';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { MockBackend } from '@angular/http/testing';
import { HttpModule, XHRBackend, Response, ResponseOptions, Http, ConnectionBackend, RequestOptions, BaseRequestOptions } from '@angular/http';
import { TestBed, async, inject, getTestBed, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { MockConnection } from "@angular/http/testing";
import { Component, ViewChild } from '@angular/core';
import { AutenticacaoModule } from '../../autenticacao/autenticacao.module';

describe('EnderecoContatoComponent', () => {
    let fixture: ComponentFixture<EnderecoContatoComponent>;
    let component: EnderecoContatoComponent

    let _BRASIL_ID: number = 1;

    beforeEach(() => {
        fixture = TestBed.createComponent(EnderecoContatoComponent); 
        component = fixture.debugElement.componentInstance;        
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

     it('deveria criar o EnderecoContatoComponent', () => {
        expect(component).toBeTruthy();
    }); 

    it('deve exibir a mensagem \'CEP é obrigatório\' caso o país seja Brasil e o CEP seja vazio', async(() => {
                
        let enderecoForm:FormGroup = component.enderecoForm;
        let paisFormGroup:FormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this._BRASIL_ID);
        enderecoForm.controls["cep"].setValue('');

        component.validateForm();

        expect('O campo CEP é obrigatório').toEqual(component.formErrors['cep']);

    }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e o CEP seja preenchido', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["cep"].setValue('20756120')

        component.validateForm();
        expect('').toEqual(component.formErrors['cep']);
    }));

    it('deve exibir a mensagem \'Tipo Logradouro é obrigatório\' caso o país seja Brasil e o tipo logradouro seja vazio',
        async(() => {

            let enderecoForm = component.enderecoForm
            let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
            paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
            enderecoForm.controls["tipoLogradouro"].setValue('')

            component.validateForm();
            expect('O campo Tipo de Logradouro é obrigatório').toEqual(component.formErrors['tipoLogradouro']);
        }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e o tipo logradouro seja preenchido', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["tipoLogradouro"].setValue('Rua')

        component.validateForm();
        expect(component.formErrors['tipoLogradouro']).toEqual('');
    }));

    it('deve exibir a mensagem \'Nome do Logradouro é obrigatório\' caso o país seja Brasil e o nome do logradouro seja vazio',
        async(() => {

            let enderecoForm = component.enderecoForm
            let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
            paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
            enderecoForm.controls["nomeLogradouro"].setValue('')

            component.validateForm();
            expect('O campo Nome do Logradouro é obrigatório').toEqual(component.formErrors['nomeLogradouro']);
        }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e o nome do logradouro seja preenchido', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["nomeLogradouro"].setValue('Goiás')

        component.validateForm();
        expect('').toEqual(component.formErrors['nomeLogradouro']);
    }));

    it('deve exibir a mensagem \'Bairro é obrigatório\' caso o país seja Brasil e o bairro seja vazio', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["bairro"].setValue('')

        component.validateForm();
        expect('O campo Bairro é obrigatório').toEqual(component.formErrors['bairro']);
    }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e o bairro seja preenchido', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["bairro"].setValue('Piedade')

        component.validateForm();
        expect('').toEqual(component.formErrors['bairro']);
    }));

    it('deve exibir a mensagem \'Município é obrigatório\' caso o país seja Brasil e o município seja vazio', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["municipio"].setValue('')

        component.validateForm();
        expect('O campo Município é obrigatório').toEqual(component.formErrors['municipio']);
    }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e o município seja preenchido', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["municipio"].setValue('Belo Horizonte')

        component.validateForm();
        expect('').toEqual(component.formErrors['municipio']);
    }));

    it('deve exibir a mensagem \'UF é obrigatório\' caso o país seja Brasil e a UF seja vazia', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["uf"].setValue('')

        component.validateForm();
        expect('O campo UF é obrigatório').toEqual(component.formErrors['uf']);
    }));

    it('não deve exibir mensagem de erro caso o país seja Brasil e a UF seja preenchida', async(() => {

        let enderecoForm = component.enderecoForm
        let paisFormGroup = <FormGroup>enderecoForm.controls["pais"]
        paisFormGroup.controls["id"].setValue(this.BRASIL_ID)
        enderecoForm.controls["uf"].setValue('RJ')

        component.validateForm();
        expect('').toEqual(component.formErrors['uf']);
    }));

});