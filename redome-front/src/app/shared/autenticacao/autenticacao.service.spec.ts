import { IdentificacaoComponent } from '../../paciente/cadastro/identificacao/identificacao.component';
import { AppComponent } from '../../app.component';
import { PacienteService } from '../../paciente/paciente.service';
import { MockDominioService } from '../mock/mock.dominio.service';
import { DominioService } from '../dominio/dominio.service';
import { AppModule } from '../../app.module';
import { FakeLoader } from '../fake.loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { MockAutenticacaoService } from '../mock/mock.autenticacao.service';
import { Login } from '../../login/login';
import { AutenticacaoService } from './autenticacao.service';
import { AutenticacaoModule } from './autenticacao.module';
import { TestBed, async, inject, ComponentFixture } from '@angular/core/testing';
import {
    HttpModule,
    Http,
    Response,
    ResponseOptions,
    ResponseType,
    XHRBackend
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { HttpClient } from '../httpclient.service';
import { Observable } from 'rxjs';
import { ErroMensagem } from '../erromensagem';
import { autenticacaoService } from '../../export.mock.spec';
/**
 * Classe Spec para teste da classe Login Service
 * @author Filipe Paes
 */
describe('LoginService', () => {

    let app: AppComponent;
    let fixtureApp: ComponentFixture<AppComponent>;

    beforeEach(() => {
        autenticacaoService.logar = function (login: Login): Promise<any> {

            if(login.username == 'medico' && login.password == '123456') {
                return Observable.of(true).toPromise();         
            } else {
                return Observable.of(new ErroMensagem(401, "", "Usuário ou senha inválidos")).toPromise();         
            }
        };
    })

    /**
     *Método de teste para retorno verdadeiro para login
     * @author Filipe Paes
     */
        it('deveria retornar true', async() => {
            
            let login: Login = new Login({ username: "medico", password: "123456" });

            autenticacaoService.logar(login).then((res) => {
                expect(res).toBeTruthy()
            });

        });

        /**
         *Método de teste para retorno falso para login
         * @author Filipe Paes
         */
        it('deveria retornar error 401',
            inject([AutenticacaoService], (autenticacaoService) => {
                let login: Login = new Login({ username: "admin", password: "12" });
                autenticacaoService.logar(login).then(res => { }, error => {
                    expect(error.listaCampoMensagem[0].mensagem).toBe("Usuário ou senha inválidos")
                });
            }));
    });