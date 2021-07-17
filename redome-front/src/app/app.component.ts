import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { TranslateService } from "@ngx-translate/core";
import { Recursos } from 'app/shared/enums/recursos';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from 'app/shared/util/erro.util';
import { CookieService } from 'ngx-cookie-service';
import { ModalAlterarSenhaComponent } from './shared/alterarsenha/modal.alterar.senha.component';
import { AutenticacaoService } from './shared/autenticacao/autenticacao.service';
import { NavbarComponent } from './shared/component/navbar/navbar.component';
import { NavBarItem } from './shared/component/navbar/navbar.item';
import { UsuarioLogado } from './shared/dominio/usuario.logado';
import { ComponenteRecurso } from './shared/enums/componente.recurso';
import { EventEmitterService } from './shared/event.emitter.service';
import { HistoricoNavegacao } from './shared/historico.navegacao';
import { MessageBox } from './shared/modal/message.box';
import { MedicoService } from './shared/service/medico.service';


@Component({
  selector: 'body',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit, OnDestroy, AfterViewInit {
  title = 'app works!';

  busy: Promise<any>[] = [];
  private _subscribeLoading: any;
  private _mudarEstiloImagemLoading: any;
  private mensagemLoading:string = "";
  private estiloComponenteLoading = "";

  private logado: boolean;
  private _subscribeLogado: any;

   @ViewChild('mensagemAppModal') mensagemAppModal;

   @ViewChild("appNavbar")
   private appNavbar: NavbarComponent;


   private _subscribeMensagemAppModal: any;
   mensagemApp: string;

   public usuario: UsuarioLogado;

   private _isCollapsed: boolean = true;

   private _menuItens: NavBarItem[] = [];
   public _usuarioItens: NavBarItem[] = [];


  constructor(private translate: TranslateService, private router: Router,
        private autenticacaoService :AutenticacaoService, private messageBox: MessageBox,
        private cookieService: CookieService, private medicoService: MedicoService) {

        translate.addLangs(["pt", "en", "es"]);
        translate.setDefaultLang('pt');

        let browserLang = translate.getBrowserLang();
        translate.use('pt');

        this.logado = !this.autenticacaoService.tokenExpirado();

        this.router.events.filter(e => e instanceof RoutesRecognized)
            .pairwise().subscribe((routes: any[]) => {
                this.controlarNavegacao(routes[1]);
            }
        );

        let _myDate = new Date();
        let _offset: any = _myDate.toString().match(/([-\+][0-9]+)\s/)[1];
        //console.log(_offset);
        //document.cookie = "TIMEZONE_COOKIE=" + _offset;

        this.cookieService.set( 'MODRED_TIMEZONE_COOKIE', _offset );
  }

  /**
     * Pega o estilo dinamicamente para o componente de loading
     *
     * @returns
     * @memberof HomeComponent
     * @author Fillipe Queiroz
     */
    getEstiloComponenteLoading(){
        return this.estiloComponenteLoading;
    }

    ngOnDestroy() {
        if (this._subscribeLoading) {
            this._subscribeLoading.unsubscribe();
        }
        if (this._mudarEstiloImagemLoading) {
            this._mudarEstiloImagemLoading.unsubscribe();
        }
        if (this._subscribeLogado) {
            this._subscribeLogado.unsubscribe();
        }
        if (this._subscribeMensagemAppModal) {
            this._subscribeMensagemAppModal.unsubscribe();
        }
    }

    /**
     * Trata mensagem de loading para todas requisições ao inicializar o home da aplicação
     * quem envia o emitter é o httpclient.service.ts e capturado o promise aqui.
     *
     * @memberof HomeComponent
     * @author Fillipe Queiroz
     */
    ngOnInit(): void {

        this.translate.get("loading").subscribe(res => {
            this.mensagemLoading = res;
        });
        this._subscribeLogado = EventEmitterService.get('onLogin').subscribe(logado => {
            if (logado) {
                this.usuario = this.autenticacaoService.usuarioLogado();
                this.habilitaMenu();
            }
            else {
                this.usuario = null;
                /* this.temPermissaoCadastroPaciente = false;
                this.temPermissaoConsultaPaciente = false;
                this.temPermissaoAvaliacaoPaciente = false;
                this.temPermissaoAvaliacoesPaciente = false; */
            }
            this.logado = logado;
        });

        this._subscribeLoading = EventEmitterService.get('onSubmitLoading').subscribe(promise => {
            this.busy.push(promise);
            this.estiloComponenteLoading = "ng-busy";
            promise.then.call(
                promise,
                () => this.encerrarPromise(promise),
                () => this.encerrarPromise(promise)
            );
        });

        this._mudarEstiloImagemLoading = EventEmitterService.get('mudarEstiloImagemLoading').subscribe(estilo => {
            this.estiloComponenteLoading = estilo;
        });

        this._subscribeMensagemAppModal = EventEmitterService.get('mensagemAppModal').subscribe(mensagem => {
            this.mensagemApp = mensagem;
            this.mensagemAppModal.show();
        });
    }

    ngAfterViewInit() {

        if (this.logado) {
            this.usuario = this.autenticacaoService.usuarioLogado();
            this.habilitaMenu();
        }

    }

    /**
     * Metodo para quando acabar uma requisição retirar do
     * componente de loading e voltar o estilo original desbloqueando o mesmo
     *
     * @param {*} promise
     * @memberof HomeComponent
     * @author Fillipe Queiroz
     */
    encerrarPromise(promise:any){
        let index = this.busy.indexOf(promise);
        this.busy.splice(index, 1);
        if(this.busy.length == 0){
            this.estiloComponenteLoading = "";
        }
    }

    estaLogado() {
        return this.logado ? this.logado : false;
    }

    fecharModal() {
        this.mensagemAppModal.hide();
        this.router.navigateByUrl("/login");
    }

    habilitaMenu() {

        this.translate.get('menu').subscribe(labels => {
            this._usuarioItens = [];
            this._menuItens = [];

            if (this.autenticacaoService.temPerfilMedico()) {
                this._usuarioItens.push(new NavBarItem('', labels['MeusDados'], 'glyphicon glyphicon-user', null, () => {
                    this.medicoService.obterMedicoAssociadoUsuarioLogado().then(res => {
                        if (res && res.id) {
                            this.router.navigateByUrl("admin/medicos/" + res.id);
                        }
                    },
                    (error: ErroMensagem)=> {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                }));
            }
            this._usuarioItens.push(new NavBarItem('', labels['Alterarsenha'], 'glyphicon glyphicon-lock', null, () => {
                let data = {}

                this.messageBox
                    .dynamic(data, ModalAlterarSenhaComponent)
                    .show();
            }));
            this._usuarioItens.push(new NavBarItem('', labels['Sair'], 'glyphicon glyphicon-log-out', null, () => {
                this.autenticacaoService.logout();
            }));


            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaComponent]], 'icon icon-consultar', '/pacientes'));
            }
            if (this.autenticacaoService.temPermissaoAcesso(
                        ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastrarBuscaPreliminarComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaAutorizacaoPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastrarBuscaPreliminarComponent]], 'icon icon-mach', '/pacientes/buscapreliminar'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroComponent])) {
                this._menuItens.push(new NavBarItem('idLinkCadastroPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroComponent]], 'icon icon-cadastrar', '/pacientes/cadastro'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConferenciaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvaliacaoPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConferenciaComponent]], 'icon icon-conferir', '/conferencia'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarNotificacaoComponent])) {
                this._menuItens.push(new NavBarItem('idLinkNotificacoes', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarNotificacaoComponent]], 'icon icon-alerta', '/pacientes/notificacoes'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PendenciaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPacientesAvaliacao', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PendenciaComponent]], 'icon icon-lista-pendencia', '/pacientes/avaliacao/pendencias'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaCentroTransplanteComponent])) {
                this._menuItens.push(new NavBarItem('idLinkManterCentroTransplante', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaCentroTransplanteComponent]], 'icon icon-centro', 'admin/centrostransplante'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.UsuarioComponent])) {
                this._menuItens.push(new NavBarItem('idLinkManterUsuario', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.UsuarioComponent]], 'icon glyphicon glyphicon-asterisk', '/usuarios'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PerfilComponent])) {
                this._menuItens.push(new NavBarItem('idLinkManterPerfil', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PerfilComponent]], 'icon glyphicon glyphicon-asterisk', '/perfis'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaAvaliacoesComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvaliacoesPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaAvaliacoesComponent]], 'icon icon-avaliacao', '/pacientes/avaliacoes'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaTransferenciaCentroComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvaliacoesPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaTransferenciaCentroComponent]], 'icon glyphicon glyphicon-share-alt', '/pacientes/transferencias'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PacienteBuscaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPacienteBusca', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PacienteBuscaComponent]], 'icon icon-mach', '/pacientes/busca'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.EnriquecerDoadorComponent])) {
                this._menuItens.push(new NavBarItem('idLinkEnriquecerDoador', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.EnriquecerDoadorComponent]], 'icon icon-enriquecimento', '/doadores/enriquecer'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ContatoFase2Component])) {
                this._menuItens.push(new NavBarItem('idLinkPedidoFase2', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ContatoFase2Component]], 'icon icon-contato-fase2', '/doadores/contato/fase2'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ContatoFase3Component])) {
                this._menuItens.push(new NavBarItem('idLinkPedidoFase3', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ContatoFase3Component]], 'icon icon-fase3', '/doadores/contato/fase3'));
            }

           if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarDistribuicaoWorkupComponent])) {
              this._menuItens.push(new NavBarItem('idLinkConsultaDistribuicaoWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarDistribuicaoWorkupComponent]], 'icon icon-distribuicao-workup', '/doadores/workup/distribuicao/consulta'));
           }

           if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupComponent]], 'icon icon-workup', '/doadores/workup/consulta'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.DoadorPendenciaWorkupComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPendenciaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.DoadorPendenciaWorkupComponent]], 'icon icon-workup-pendencias', '/doadores/workup/pendencias'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaLogisticaMaterialComponent])) {
                this._menuItens.push(new NavBarItem('idLinkLogisticaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaLogisticaMaterialComponent]], 'icon icon-logistica', '/doadores/workup/logistica'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaContatoDoadorComponent])) {
                this._menuItens.push(new NavBarItem('idLinkContatoPassivo', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaContatoDoadorComponent]], 'icon icon-coleta', '/doadores/consulta/consultaContatoPassivo'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaDoadorNacionalComponent])) {
                this._menuItens.push(new NavBarItem('idLinkDoadorNacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaDoadorNacionalComponent]], 'icon icon-coleta', '/doadores/consulta/nacional'));
            }

            // if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacoesResultadoWorkupComponent])) {
            //     this._menuItens.push(new NavBarItem('idLinkAvaliacoesResultadoWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacoesResultadoWorkupComponent]], 'icon icon-workup', '/doadores/workup/resultado/avaliacao/consulta'));
            // }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoColetaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoColetaComponent]], 'icon icon-coleta', '/doadores/workup/coletas/agendar'));
            }

            // if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroWorkupInternacionalComponent])) {
            //     this._menuItens.push(new NavBarItem('idLinkCadastroWorkupInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroWorkupInternacionalComponent]], 'icon icon-exame-internacional', '/doadoresinternacionais/workup/resultado/cadastrar'));
            // }

            // if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoColetaInternacionalComponent])) {
            //     this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoColetaInternacionalComponent]], 'icon icon-coleta', '/doadores/workup/coletas/agendarinternacional'));
            // }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaAvaliacoesPrescricaoComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaAvaliacoesPrescricaoComponent]], 'icon icon-prescricao', '/prescricoes/avaliacao'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoPedidoColetaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvaliacaoPedidoColeta', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoPedidoColetaComponent]], 'icon icon-coleta', '/doadores/workup/coletas/avaliacao'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.CancelarAgendamentoPedidoColetaComponent])) {
                if (this.autenticacaoService.temRecurso(Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR)) {
                    this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CancelarAgendamentoPedidoColetaComponent]+'46'], 'icon icon-cancela-coleta', '/doadores/workup/coletas/cancelaragendamento?tipo=46'));
                }
                if (this.autenticacaoService.temRecurso(Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA)) {
                    this._menuItens.push(new NavBarItem('idLinkConsultaWorkup', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CancelarAgendamentoPedidoColetaComponent]+'47'], 'icon icon-cancela-coleta', '/doadores/workup/coletas/cancelaragendamento?tipo=47'));
                }
            }

            // if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.RecebimentoColetaComponent])) {
            //     this._menuItens.push(new NavBarItem('idLinkRecebimentoColeta', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.RecebimentoColetaComponent]], 'icon icon-coleta', '/doadores/workup/recebimentocoleta'));
            // }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarPrescricaoComponent])) {
                this._menuItens.push(new NavBarItem('idLinkCadastrarPrescricao', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarPrescricaoComponent]], 'icon icon-prescricao', '/prescricoes'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.DefinirCentroTransplanteComponent])) {
                this._menuItens.push(new NavBarItem('idLinkDefinirCentroTransplante', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.DefinirCentroTransplanteComponent]], 'icon icon-centro', '/pacientes/centrosTransplante/definir'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaTransporteMaterialComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPendenciaEnvioMaterial', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaTransporteMaterialComponent]], 'icon glyphicon glyphicon-calendar', '/transportadoras'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.LaboratorioExameComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPermissaoReceberColetaLaboratorio', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.LaboratorioExameComponent]], 'icon icon-coleta', '/laboratorios'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.RetiradaEntregaTransporteMaterialComponent])) {
                this._menuItens.push(new NavBarItem('idLinkPendenciaEnvioMaterial', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.RetiradaEntregaTransporteMaterialComponent]], 'icon icon-logistica', '/transportadoras/retiradaentrega'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.RelatorioComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAdminRelatorio', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.RelatorioComponent]], 'icon glyphicon glyphicon-duplicate', '/admin/relatorios'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaDoadorInternacionalComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaDoadInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaDoadorInternacionalComponent]], 'icon icon-doador-inter', '/doadores/consulta/internacional'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarAutorizacaoPacienteComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaAutorizacaoPaciente', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarAutorizacaoPacienteComponent]], 'icon icon-avaliacao', '/prescricoes/autorizacaopaciente'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.LogisticaDoadorInternacionalComponent])) {
                this._menuItens.push(new NavBarItem('idLinkLogisticaInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.LogisticaDoadorInternacionalComponent]], 'icon icon-logistica', '/doadores/workup/logistica/internacional'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.RetiradaEntregaTransporteMaterialInternacionalComponent])) {
                this._menuItens.push(new NavBarItem('idLinkRecebimentoInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.RetiradaEntregaTransporteMaterialInternacionalComponent]], 'icon icon-doador-inter', '/transportadoras/retiradaentregainternacional'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnalistaBuscaCentroAvaliadorComponent])) {
                this._menuItens.push(new NavBarItem('idLinkRecebimentoInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnalistaBuscaCentroAvaliadorComponent]], 'icon icon-centro', '/admin/usuarios/centroavaliador'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaPreCadastroMedicoComponent])) {
                this._menuItens.push(new NavBarItem('idLinkRecebimentoInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaPreCadastroMedicoComponent]], 'icon icon-cadastrar', '/admin/precadastromedico'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaMedicoComponent])) {
                this._menuItens.push(new NavBarItem('idLinkRecebimentoInternacional', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaMedicoComponent]], 'icon icon-medico', '/admin/medicos'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoCamaraTecnicaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvalilacaoCamaraTecnica', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoCamaraTecnicaComponent]], 'icon icon-avaliacao', '/pacientes/avaliacaocamaratecnica'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliarNovaBuscaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAvaliarNovaBusca', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliarNovaBuscaComponent]], 'icon icon-avaliacao', '/pacientes/busca/avaliacao'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ListarUsuarioComponent])) {
                this._menuItens.push(new NavBarItem('idLinkListarUsuarios', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ListarUsuarioComponent]], 'icon icon-usuario', '/admin/usuarios'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.TransportadoraCadastroConsultaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkCadastroTransportadora', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.TransportadoraCadastroConsultaComponent]], 'fas fa-truck fa-3x', '/admin/transportadoras'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.CourierConsultaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkCadastroCourier', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.CourierConsultaComponent]], 'icon icon-usuario', '/admin/couriers'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.LaboratorioConsultaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaLaboratorio', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.LaboratorioConsultaComponent]], 'icon icon-coleta', '/admin/laboratorios'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnaliseMedicaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAnaliseMedica', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnaliseMedicaComponent]], 'icon icon-prescricao', '/doadores/analisemedica'));
            }
            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoContatoSmsComponent])) {
                this._menuItens.push(new NavBarItem('idLinkAnaliseMedica', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoContatoSmsComponent]], 'icon icon-prescricao', '/doadores/smsenviado'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.BuscaChecklistConsultaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkBuscaChecklist', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.BuscaChecklistConsultaComponent]], 'icon icon-acompanhar-busca', '/pacientes/buscachecklist'));
            }

            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaInvoiceApagarComponent])) {
              this._menuItens.push(new NavBarItem('idLinkConsultaInvoiceAPagar', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaInvoiceApagarComponent]], 'icon icon-acompanhar-busca', '/invoices/apagar'));
            }
            if (this.autenticacaoService.temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupCentroColetaComponent])) {
                this._menuItens.push(new NavBarItem('idLinkConsultaWorkupCentroColeta', labels[ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupCentroColetaComponent]], 'icon icon-workup', '/doadores/workup/centrocoleta/consulta'));
            }


            setTimeout(() => {
                if (this.appNavbar) {
                    this.appNavbar.update(this._usuarioItens, this._menuItens, this.usuario ? this.usuario.nome : null);
                }
            });
        });
    }

    /**
     * Faz registro e o controle da navegação, a partir da URL informada.
     *
     * @param route rota que foi solicitada para navegação.
     */
    private controlarNavegacao(route:RoutesRecognized):void{
        HistoricoNavegacao.adicionarCaminho(route.urlAfterRedirects);
    }

    public toggleCollapse(): void {
        this._isCollapsed = !this._isCollapsed;
    }

    /**
	 *
	 *
	 * @type {boolean}
	 * @memberOf App Component
	 */
	public get isCollapsed(): boolean {
		return this._isCollapsed;
	}

	/**
	 *
	 *
	 *
	 * @memberOf App Component
	 */
	public set isCollapsed(value: boolean) {
		this._isCollapsed = value;
    }

    public menuItens(): NavBarItem[] {
        return this._menuItens;
    }

    public irParaHome() {
        this.router.navigateByUrl("/home");
    }

}
