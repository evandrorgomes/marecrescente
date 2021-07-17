import {MockDominioService} from '../../mock/mock.dominio.service';
import { ExportTranslateModule } from '../../export.translate.module';
import { AppComponent } from '../../../app.component';
import { ContatoTelefoneModule } from './contato.telefone.module';
import { ContatoTelefoneComponent } from './contato.telefone.component';
import { CodigoInternacional } from '../../dominio/codigo.internacional';
import { CommonModule } from '@angular/common';
import { Injector } from '@angular/core/src/di';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { HttpModule, XHRBackend, Response, ResponseOptions, Http, ConnectionBackend, RequestOptions, BaseRequestOptions } from '@angular/http';
import { TestBed, async, inject, getTestBed, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { MockConnection } from "@angular/http/testing";
import { HelperTest } from "../../helper.test";
import { AppModule } from "../../../app.module";
import { FakeLoader } from "../../fake.loader";
import { DominioService } from "../../dominio/dominio.service";
import { MockBackend } from "@angular/http/testing";
import { Component, ViewChild } from '@angular/core';

describe('ContatoTelefoneComponent', () => {
    let fixture: ComponentFixture<ContatoTelefoneComponent>;
    let component: ContatoTelefoneComponent
      
     beforeEach(() => {
        fixture = TestBed.createComponent(ContatoTelefoneComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
    });

     afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

     it('deveria criar o ContatoTelefoneComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir a mensagem \'Os campos marcados em vermelho são obrigatórios\' caso não tenha preenchido algum campo do telefone', async(() => {

        component.validateForm();
        expect('Os campos marcados em vermelho são obrigatórios').toEqual(component.formErrors['telefonesContato']);

    }));

     it('deve exibir a mensagem \'Pelo menos um telefone deve ser o principal\' caso não tenha marcado nenhum telefone como principal', async(() => {
        component.listaTelefoneForm.get('0.codArea').setValue('21');
        component.listaTelefoneForm.get('0.numero').setValue('21574600');
        component.listaTelefoneForm.get('0.nome').setValue('teste');
        component.adicionarTel();
        component.listaTelefoneForm.get('1.codArea').setValue('21');
        component.listaTelefoneForm.get('1.numero').setValue('21574600');
        component.listaTelefoneForm.get('1.nome').setValue('teste');
        component.validateForm();
        expect('Pelo menos um telefone deve ser o principal').toEqual(component.formErrors['telefonePrincipal']);
    })); 
    
});