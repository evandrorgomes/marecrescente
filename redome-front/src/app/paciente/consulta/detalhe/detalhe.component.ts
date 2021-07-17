import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Recursos } from 'app/shared/enums/recursos';
import { MessageBox } from 'app/shared/modal/message.box';
import { PacienteUtil } from 'app/shared/paciente.util';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { ErroMensagem } from '../../../shared/erromensagem';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { Busca } from '../../busca/busca';
import { BuscaService } from '../../busca/busca.service';
import { NotificacaoService } from '../../notificacao.service';
import { Paciente } from '../../paciente';
import { PacienteConstantes } from '../../paciente.constantes';
import { PacienteService } from '../../paciente.service';
import { HeaderPacienteComponent } from '../identificacao/header.paciente.component';
import { TiposExame } from './../../../shared/enums/tipos.exame';
import { RouterUtil } from './../../../shared/util/router.util';
import { ModalTransfereciaCentroAvaliadorComponent } from './../transferencia/centroavaliador/modal.transferencia.centro.avaliador.component';
/**
 * Classe que representa o componente de detalhe do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'datelhe-paciente',
    templateUrl: './detalhe.component.html'
})
export class DetalheComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {

    @ViewChild("headerPaciente")
    private headerPaciente: HeaderPacienteComponent;

    public exibirAlerta: boolean = false;
    public exibirAlertaFase3: boolean = false;
    public msgPedidoExameCT: string;

    private rmr: number;

    private _paciente: Paciente;

    private _busca: Busca;

    public temPermissaoConsultaEvolucao: boolean;
    public temPermissaoConsultaExames: boolean;
    public temPermissaoCancelarBusca: boolean;
    public deveExibirBotaoAvaliacao: boolean;
    public deveExibirBotaoNotificacoes: boolean;
    public deveExibirBotaoCancelarBusca: boolean;
    public deveExibirBotaoAcompanharBusca: boolean;
    public temPermissaoEditarDadosPessoais: boolean;
    public temPermissaoEditarContato: boolean;
    public pacienteEmObito: boolean;
    public deveExibirBotaoAnaliseMatch: boolean;
    public temPermissaoAnaliseMatch: boolean;
    public temPermissaoVisualizarHistorico: boolean;
    public temPermissaoPedidoExame:boolean;
    public temPermissaoTransferirPaciente: boolean;
    public temPermissaoEditarDiagnostico: boolean;
    public temPermissaoEditarMismatch: boolean;

    public liberadoParaSolicitarNovaBusca: boolean;

    public _idadeFormatada: string;
    public _isContempladoPortaria: string;

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private servicePaciente: PacienteService, private router: Router, private serviceBusca: BuscaService,
        private translate: TranslateService, private activatedRouter: ActivatedRoute,
        private autenticacaoService: AutenticacaoService, private notificacaoService: NotificacaoService,
        private messageBox: MessageBox) {
        this._paciente = new Paciente();
        this._paciente.enderecosContato = [];
    }

    /**
     * 
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {
    }

    ngAfterViewInit() {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(value => {
            this.rmr = value;
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
            this.obterFichaPaciente();
        })
    }

    private obterFichaPaciente(): void {
        this.servicePaciente.obterFichaPaciente(this.rmr).then(res => {
    
            this._paciente = new Paciente().jsonToEntity(res);
            PacienteUtil.retornarIdadeFormatada(this._paciente.dataNascimento, this.translate).then(res => {
                this._idadeFormatada = res;
            })

            if (this.paciente.diagnostico.cid.transplante) {
                this._isContempladoPortaria = 'azul';
            } else {
                this._isContempladoPortaria = 'amarelo';
            }
            
            this.pacienteEmObito = res.statusObito;
            this.deveExibirBotaoNotificacoes = this._paciente.quantidadeNotificacoes > 0;
            this.deveExibirBotaoCancelarBusca = this._paciente.temBuscaAtiva;
            this.deveExibirBotaoAnaliseMatch = 
                (this.autenticacaoService.verificaUsuarioLogadoEMedicoResponsavelPaciente(this._paciente) 
                    || this.autenticacaoService.verificaUsuarioLogadoEMedicoTransplantadorAssociadoABuscaDoPaciente(this._paciente))
                        && this.paciente.temBuscaAtiva;

            if (res.genotipo && res.genotipo.valores) {
                let temLocusC = res.genotipo.valores.some(element => {
                    return element.locus.codigo == "C";
                });
                let temLocusDRB1 = res.genotipo.valores.some(element => {
                    return element.locus.codigo == "DRB1";
                });
                let temLocusDQB1 = res.genotipo.valores.some(element => {
                    return element.locus.codigo == "DQB1";
                });

                this.exibirAlerta = this._paciente.temAvaliacaoAprovada && (!temLocusC || !temLocusDRB1 || !temLocusDQB1);
            }

            if(this._paciente.idTipoExameFase3 != null){
                if(this._paciente.idTipoExameFase3 === TiposExame.TESTE_CONFIRMATORIO){
                    this.exibirAlertaFase3 = true;
                    this.translate.get("detalhePaciente").subscribe(res => {
                        this.msgPedidoExameCT = res['msgPedidoExameCT'];
                    });
                }
                else if(this._paciente.idTipoExameFase3 === TiposExame.TESTE_CONFIRMATORIO_SWAB){
                    this.exibirAlertaFase3 = true;
                    this.translate.get("detalhePaciente").subscribe(res => {
                        this.msgPedidoExameCT = res['msgPedidoExameSwabCT'];
                    });
                }
                else if(this._paciente.idTipoExameFase3 === TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB){
                    this.exibirAlertaFase3 = true;
                    this.translate.get("detalhePaciente").subscribe(res => {
                        this.msgPedidoExameCT = res['msgPedidoExameSangueSwabCT'];
                    });
                } 
            }
    
            this.deveExibirBotaoAvaliacao = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoComponent]);

            this.temPermissaoConsultaEvolucao = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarEvolucaoComponent]);

            this.temPermissaoConsultaExames = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarExameComponent]);

            this.temPermissaoCancelarBusca = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.CancelamentoBuscaComponent]);

            this.temPermissaoEditarDadosPessoais = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarIdentificacaoDadosPessoaisComponent]);

            this.temPermissaoEditarContato = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarContatoPacienteComponent]);

            this.temPermissaoAnaliseMatch = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnaliseMatchComponent]);

            let botaoBusca: boolean = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PacienteBuscaComponent]);

            this.temPermissaoVisualizarHistorico =
                this.autenticacaoService.temPermissaoAcesso(
                    ComponenteRecurso.Componente[ComponenteRecurso.Componente.LogEvolucaoComponent]);
            this.temPermissaoPedidoExame = this.autenticacaoService
                .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.PedidoExameFase3Component]);

            this.temPermissaoTransferirPaciente = this.autenticacaoService.temRecurso(Recursos.TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR)
                && this.autenticacaoService.usuarioLogado().username == this.paciente.medicoResponsavel.usuario.username
                && !this.paciente.temAvaliacaoIniciada && !this.paciente.temPedidoTransferenciaCentroAvaliadorEmAberto;

            this.temPermissaoEditarDiagnostico = this.autenticacaoService.temRecurso(Recursos.EDITAR_DIAGNOSTICO_PACIENTE)
                && (!this.paciente.temAvaliacaoIniciada && !this.paciente.temAvaliacaoAprovada);

            this.temPermissaoEditarMismatch = this.autenticacaoService.temRecurso(Recursos.EDITAR_MISMATCH_PACIENTE);

            if (botaoBusca || this.temPermissaoPedidoExame) {
                this.serviceBusca.obterBuscaPorRMR(this.rmr)
                    .then(res => {
                        this._busca = res;
                        if (this.busca.usuario == null) {
                            this.deveExibirBotaoAcompanharBusca = true;
                        }
                    }, (error: ErroMensagem) => {
                        throw new Error("Erro localizar busca do paciente");
                    });
            }

            /** Libera o acesso a opção de nova busca */
            this.liberadoParaSolicitarNovaBusca = 
                this.autenticacaoService.temRecurso(Recursos.SOLICITAR_NOVA_BUSCA_PACIENTE) && 
                    !this.paciente.temBuscaAtiva && !this.paciente.temAvaliacaoIniciada;

        }, (error: ErroMensagem) => {
            throw new Error("Erro ao buscar a ficha do paciente com RMR: " + this.rmr);
        });
    }

    /**
     * Aplica a mascara no cpf
     * 
     * @private
     * @param {string} texto 
     * @param {string} mascara 
     * @returns {string} 
     * 
     * @memberOf DetalheComponent
     */
    aplicarMascaraCpf(texto: string): string {

        return FormatterUtil.aplicarMascaraCpf(texto);

    }

    /**
     * Verifica se o endereço é do brasil, caso a entity não esteja preenchida retorna que é do Brasil
     * 
     * @returns {boolean} 
     * 
     * @memberOf DetalheComponent
     */
    temEnderecoBrasil(): boolean {
        if (!this.paciente.enderecosContato || !this._paciente.enderecosContato[0]) {
            return true;
        }
        return this._paciente.enderecosContato[0].pais.id == PacienteConstantes.BRASIL_ID;
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalheComponent];
    }

    /**
     * Visualiza as notificações associadas ao paciente selecionado.
     * Se o não existir mais notificações, o botão deverá ser omitido da tela.
     */
    public visualizarNotificacoes(): void {
        this.router.navigateByUrl('/pacientes/notificacoes?idPaciente=' + this.rmr);
    }

    public editarDadosContato(): void {
        this.router.navigateByUrl('/pacientes/' + this.rmr + '/editar/contatos');
    }

    public editarDadosIdentificacaoDadosPessoais(): void {
        this.router.navigateByUrl('/pacientes/' + this.rmr + '/editar/identificacaoDadosPessoais');
    }

    public editarDadosDiagnostico(): void {
        this.router.navigateByUrl('/pacientes/' + this.rmr + '/editar/diagnostico');
    }

    public pedidosDeExamePaciente():void{
        this.router.navigateByUrl('/pacientes/' + this.rmr + '/pedidoexamefase3?buscaId=' + this.busca.id);   
    }

    public get paciente(): Paciente {
        return this._paciente;
    }

    /**
     * Getter $busca
     * @return {Busca}
     */
	public get busca(): Busca {
		return this._busca;
    }
    
    public abrirModalTransferencia() {
        let data: any = {
            rmr: this.paciente.rmr,
            centroAvaliador: this.paciente.centroAvaliador,
            recarregarTelaDetalhe: () => {
                this.obterFichaPaciente();
            }
        }
        this.messageBox.dynamic(data, ModalTransfereciaCentroAvaliadorComponent)
            .show();
    }

    /**
     * @description Verifica se o avaliação já está em andamento.
     * @readonly
     * @type {boolean} retorna TRUE, caso afirmativo.
     */
    public get hasAvaliacaoIniciada(): boolean{
        return this.paciente && this.paciente.temAvaliacaoIniciada;
    }

    public obterLocusMismatch(): string {
        if (this.paciente && this.paciente.locusMismatch && this.paciente.locusMismatch.length != 0) {
            return this.paciente.locusMismatch.map(locus => locus.codigo).join(", ");
        }
        return "";
    }

    public editarDadosMismatch(): void {
        this.router.navigateByUrl('/pacientes/' + this.rmr + '/editar/avaliacao');
    }

    public exibirMensagemFase3(){
        if(this._paciente.idTipoExameFase3 != null){
            return this.msgPedidoExameCT;
        }
    } 

    public exibirInstrucaoColetaExameCT(): boolean {
        return this.exibirAlertaFase3;
    }

}