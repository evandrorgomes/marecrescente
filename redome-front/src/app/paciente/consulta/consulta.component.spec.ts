import { MockAutenticacaoService } from '../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../shared/autenticacao/autenticacao.service';
import { Router } from '@angular/router';
import { MockPacienteService } from '../../shared/mock/mock.paciente.service';
import { ErroMensagem } from '../../shared/erromensagem';
import { ConsultaComponent } from './consulta.component';
import { FakeLoader } from '../../shared/fake.loader';
import { AppModule } from '../../app.module';
import { AppComponent } from '../../app.component';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HelperTest } from '../../shared/helper.test';
import { DominioService } from '../../shared/dominio/dominio.service';
import { PacienteConstantes } from '../paciente.constantes';
import { Pais } from '../../shared/dominio/pais';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PacienteService } from '../paciente.service';
import { ConsultaModule } from './consulta.module';
import { HttpClient } from '../../shared/httpclient.service';
import { TestBed, inject, ComponentFixture, async, fakeAsync, tick} from '@angular/core/testing';
import {
    HttpModule,
    Http,
    Response,
    ResponseOptions,
    ResponseType,
    XHRBackend
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';

/**
 * Classe Spec para teste da classe Consulta Component
 * @author Bruno Sousa
 */
describe('consultaComponent', () => {
    let fixture: ComponentFixture<ConsultaComponent>;
    let component: ConsultaComponent

    beforeEach(() => {        
        fixture = TestBed.createComponent(ConsultaComponent);
        component = fixture.debugElement.componentInstance;
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

});