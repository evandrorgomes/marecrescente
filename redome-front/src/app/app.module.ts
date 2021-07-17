import { AvaliacaoCamaraTecnicaModule } from './paciente/avaliacaocamaratecnica/avaliacao.camara.tecnica.module';
import { environment } from './../environments/environment';
import { PreCadastroModule } from './precadastro/precadastro.module';
import { LaboratorioModule } from './laboratorio/laboratorio.module';
import { MensagemModalComponente } from './shared/modal/mensagem.modal.component';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { AvaliacaoPedidoColetaModule } from './doador/consulta/coleta/avaliacao/avaliacao.pedido.coleta.module';
import { ResultadoWorkupModule } from './doador/consulta/workup/resultado/resultadoworkup.module';
import { QuestionarioModule } from './shared/questionario/questionario.module';
import { DoadorModule } from './doador/doador.module';
import { ConsultaAvaliacoesModule } from './paciente/consulta/avaliacao/consulta.avaliacoes.module';
import { ConsultaNotificacaoModule } from './paciente/consulta/notificacao/consulta.notificacao.module';
import { TesteModule } from './teste/teste.module';
import { TesteComponent } from './teste/teste.component';
import { HistoricoNavegacao } from './shared/historico.navegacao';
import { AutenticacaoModule } from './shared/autenticacao/autenticacao.module';
import { PacienteModule } from './paciente/paciente.module';
import { BuscaCheckListModule } from './paciente/busca/checklist/buscachecklist.module';
import { AdminModule } from './admin/admin.module';
import { PerfilModule } from './admin/perfil/perfil.module';
import { UsuarioModule } from './admin/usuario/usuario.module';
import { MensagemModule } from './shared/mensagem/mensagem.module';
import { NovaEvolucaoModule } from './paciente/cadastro/evolucao/novaevolucao.module';
import { DiretivasModule } from './shared/diretivas/diretivas.module';
import { BusyModule } from 'angular2-busy';
import { DominioService } from './shared/dominio/dominio.service';
import { HomeModule } from './home/home.module';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule, PaginationModule, AccordionModule, CollapseModule, BsDatepickerModule, AlertModule } from 'ngx-bootstrap';
import { BrowserModule } from '@angular/platform-browser';
import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions } from '@angular/http';
import {routing} from './app.routers';
import { HttpClient} from './shared/httpclient.service';
import { AppComponent } from './app.component';
import {LoginModule} from './login/login.module';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import { LoadingAnimateModule, LoadingAnimateService } from 'ng2-loading-animate';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AuthHttp, AuthConfig } from 'angular2-jwt';
import { TarefaService } from './shared/tarefa.service';
import { CookieService } from 'ngx-cookie-service';
import { DialogoModule } from './shared/dialogo/dialogo.module';
import { AlertaModalComponente } from './shared/modal/factory/alerta.modal.component';
import { ConfirmacaoModalComponente } from './shared/modal/factory/confirmacao.modal.component';
import { ModalFactory } from './shared/modal/factory/modal.factory';
import { MessageBox } from './shared/modal/message.box';
import { CKEditorModule } from 'ng2-ckeditor';
import { RelatorioModule } from './admin/relatorio/relatorio.module';
import { DoadorInternacionalModule } from './doadorinternacional/doador.internacional.module';
import { UploadArquivoModule } from './shared/upload/upload.arquivo.module';
import { HttpClientNoAutentication } from 'app/shared/httpclient.noautentication.service';
import { RecaptchaModule, RecaptchaSettings, RECAPTCHA_SETTINGS, RECAPTCHA_LANGUAGE } from 'ng-recaptcha';
import { SubHeaderPacienteModule } from './paciente/busca/analise/subheaderpaciente/subheader.paciente.module';
import { ModalAlterarSenhaComponent } from './shared/alterarsenha/modal.alterar.senha.component';
import { ModalAlterarSenhaModule } from './shared/alterarsenha/modal.alterar.senha.module';
import { CourierModule } from './admin/courier/courier.module';
import { TransportadoraModule } from 'app/transportadora/transportadora.module';
import { NavbarModule } from './shared/component/navbar/navbar.module';
import { AnaliseMedicaModule } from './doador/solicitacao/analisemedica/analise.medica.module';
import { defineLocale } from 'ngx-bootstrap/bs-moment';
import { ptBr } from 'ngx-bootstrap/locale';
import { ConsultaDoadorNacionalModule } from './doador/consulta/consulta.doador.nacional.module';
import { DoadorNacionalModule } from './doador/atualizacao/visualizar/doador.nacional.module';
import {InvoiceModule} from "./invoice/invoice.module";
import { FormularioPedidoWorkupModule } from './doador/consulta/workup/formulario/formulario.pedido.workup.module';
import { RecebimentoColetaModule } from './doador/cadastro/coleta/recebimentocoleta/recebimento.coleta.module';
import { LogisticaModule } from './doador/cadastro/logistica/logistica.module';
defineLocale('ptBr', ptBr );

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: Http) {
    return new TranslateHttpLoader(http);
}

const recaptchaGlobalSettings: RecaptchaSettings = { siteKey: environment.recaptcha_v2_sitekey };

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    AutenticacaoModule,
    FormsModule,
    ReactiveFormsModule,
    LoginModule,
    PreCadastroModule,
    routing,
    HomeModule,
    BusyModule,
    TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [Http]
            }
        }),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    TypeaheadModule.forRoot(),
    BsDropdownModule.forRoot(),
    PaginationModule.forRoot(),
    AccordionModule.forRoot(),
    CollapseModule.forRoot(),
    BsDatepickerModule.forRoot(),
    DiretivasModule,
    MensagemModule,
    PacienteModule,
    AdminModule,
    BuscaCheckListModule,
    PerfilModule,
    TesteModule,
    ConsultaNotificacaoModule,
    ConsultaAvaliacoesModule,
    DoadorModule,
    ConsultaDoadorNacionalModule,
    QuestionarioModule,
    ResultadoWorkupModule,
    DialogoModule,
    AvaliacaoPedidoColetaModule,
    RecebimentoColetaModule,
    TransportadoraModule,
    LaboratorioModule,
    RelatorioModule,
    DoadorInternacionalModule,
    DoadorNacionalModule,
    AvaliacaoCamaraTecnicaModule,
    SubHeaderPacienteModule,
    RecaptchaModule.forRoot(),
    ModalAlterarSenhaModule,
    DoadorInternacionalModule,
    AnaliseMedicaModule,
    CourierModule,
    NavbarModule,
    InvoiceModule,
    FormularioPedidoWorkupModule,
    LogisticaModule
  ],
  entryComponents: [AlertaModalComponente, ConfirmacaoModalComponente],
  providers: [HttpClient, HttpClientNoAutentication,{ provide: APP_BASE_HREF, useValue: '/redome' }, DominioService, HistoricoNavegacao, TarefaService, CookieService, ModalFactory, MessageBox,
    { provide: RECAPTCHA_SETTINGS, useValue: recaptchaGlobalSettings },
    { provide: RECAPTCHA_LANGUAGE, useValue: 'pt'}],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
