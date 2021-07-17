import { MockAutenticacaoService } from '../../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { DoadorContatoModule } from '../doador.contato.module';
import { AppModule } from '../../../../app.module';
import { DoadorContatoEnderecoComponent } from './doador.contato.endereco.component';
import { AppComponent } from '../../../../app.component';
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

describe('DoadorContatoEnderecoComponent', () => {
    let fixture: ComponentFixture<DoadorContatoEnderecoComponent>;
    let component: DoadorContatoEnderecoComponent;

    let _BRASIL_ID: number = 1;

    beforeEach(() => {
        fixture = TestBed.createComponent(DoadorContatoEnderecoComponent);
        component = fixture.debugElement.componentInstance;
        component.ngOnInit();
        //testHostFixture.detectChanges();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o EnderecoContatoComponent', () => {
        expect(component).toBeTruthy();
    }); 

});