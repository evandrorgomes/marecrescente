import { AutenticacaoService } from '../shared/autenticacao/autenticacao.service';
import { MockBackend } from '@angular/http/testing';
import { FakeLoader } from '../shared/fake.loader';
import { AppModule } from '../app.module';
import { AppComponent } from '../app.component';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateService } from '@ngx-translate/core';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TestBed, async, inject, getTestBed, ComponentFixture } from '@angular/core/testing';
import { HttpModule, Http, Response, ResponseOptions, ResponseType, XHRBackend } from '@angular/http';
import { LoginModule } from './login.module';
import { LoginComponent } from './login.component';
import { HttpClient } from '../shared/httpclient.service';
import { Router } from '@angular/router';
import { Injector } from "@angular/core/src/di";


/**
 * Classe Spec para teste da classe Login Component
 * @author Filipe Paes
 */
describe('LoginComponent', () => {
    let fixture: ComponentFixture<LoginComponent>;
    let component: LoginComponent

    beforeEach(() => {
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.debugElement.componentInstance;
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o LoginComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deveria pegar o nome do usuário em português', inject([TranslateService], (translate: TranslateService) => {
        translate.get('formLogin.usuario').subscribe((res: string) => {
            expect(res).toEqual('Nome de usuário');
        });
    }));


});