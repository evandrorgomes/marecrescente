import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {ModalAgendarPedidoWorkupComponent} from 'app/doador/cadastro/pedidoworkup/modal.agendar.pedido.workup.component';
import {isNullOrUndefined} from 'util';
import {BaseForm} from '../../../shared/base.form';
import {CentroTransplante} from '../../../shared/dominio/centro.transplante';
import {DominioService} from '../../../shared/dominio/dominio.service';
import {StatusPedidosWorkup} from '../../../shared/enums/status.pedidos.workup';
import {StatusSolicitacao} from '../../../shared/enums/status.solicitacao';
import {TiposDoador} from '../../../shared/enums/tipos.doador';
import {ErroMensagem} from '../../../shared/erromensagem';
import {HistoricoNavegacao} from '../../../shared/historico.navegacao';
import {MessageBox} from '../../../shared/modal/message.box';
import {ErroUtil} from '../../../shared/util/erro.util';
import {HeaderDoadorComponent} from '../../consulta/header/header.doador.component';
import {PedidoWorkup} from '../../consulta/workup/pedido.workup';
import {WorkupService} from '../../consulta/workup/workup.service';
import {ModalCancelarPedidoWorkupComponent} from './modal.cancelar.pedido.workup.component';

/**
 * Classe que representa o componente de inclusão de disponibilidades pelo analista workup.
 * @author Fillipe Queiroz
 */
@Component({
    selector: "agendar-pedido-workup",
    templateUrl: './agendar.pedido.workup.component.html',
    styleUrls: ['./../../doador.css']
})
export class AgendarPedidoWorkupComponent extends BaseForm<PedidoWorkup> implements OnInit {

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    private _centrosColetores: CentroTransplante[] = [];

    private idDoador: number;
    private idPedido: number;
    private _pedidoWorkup: PedidoWorkup;
    private _disponibilidadeForm: FormGroup;

    private _mostraDados: string = '';
    private _mostraFormulario: string = 'hide';

    public data: Array<string | RegExp>

    // Indica se a solicitação/prescrição está cancelada.
    public exibirTagCancelada: boolean;

    /**
     * Indica quando a data selecionada para o agendamento foi uma sugestão enviada pelo CT.
     * @return TRUE somente se a data escolhida foi a sugerida pelo CT.
     */
    public agendarDataSugeridaCT: boolean;


    constructor(private router: Router, translate: TranslateService,
        private activatedRouter: ActivatedRoute, private workupService: WorkupService
        , private dominioService: DominioService, private fb: FormBuilder,
        private messageBox: MessageBox ) {
        super();
        this.translate = translate;
        this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
        this.buildForm();
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     * @memberof PacienteBuscaComponent
     */
    ngOnInit(): void {
        this.translate.get("workup.pedido").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this._disponibilidadeForm);
            this.setMensagensErro(this._disponibilidadeForm);
        });

        this.headerDoador.clearCache();

        this.activatedRouter.queryParamMap.subscribe(queryParam => {
            if (queryParam.keys.length != 0) {
                this.idDoador = Number(queryParam.get('idDoador'));
                this.idPedido = Number(queryParam.get('idPedido'));
            }
            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this.idDoador);
            });
        });

        this.carregarAgendamentoPorParametroExterno();

        this.dominioService.listarCentroColeta().then(res => {
            this._centrosColetores = res;
            this.cancelarInclusao();

        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    private carregarAgendamentoPorParametroExterno() {
        this.workupService.obterPedidoWorkup(this.idPedido).then(res => {
            this._pedidoWorkup = new PedidoWorkup().jsonToEntity(res);
            this.exibirTagCancelada =
                this._pedidoWorkup.solicitacao.status == StatusSolicitacao.CANCELADA;
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Abre o modal de agendamento.
     */
    public abrirModalAgendamentoWorkup() {
        let data = {
            observacao: this._pedidoWorkup.ultimaDisponibilidade.descricao
        }

        this.abrirModalAgendarPedidoWorkup(data);
    }

    private abrirModalAgendarPedidoWorkup(data: any) {
        data["pedidoWorkup"] = this._pedidoWorkup;
        if (this.isDoadorNacional()) {
            if (this._pedidoWorkup.ultimaDisponibilidade && this._pedidoWorkup.ultimaDisponibilidade.centroTransplanteQuerColetar &&
                    isNullOrUndefined(this._pedidoWorkup.ultimaDisponibilidade.descricao) ) {
                        data["centroColeta"] = this._pedidoWorkup.ultimaDisponibilidade.centroColeta.id;
            }
            else {
                data["centroColeta"] = null;
            }
        }
        data['fechar'] = () => {
            this.voltarConsulta();
        }

        this.messageBox.dynamic(data, ModalAgendarPedidoWorkupComponent)
            .withTarget(this)
            .show();
    }

    /**
     * Abre o modal de cancelamento.
     */
    public abrirModalCancelamento() {
        let data: any = {
            pedidoWorkupId: this._pedidoWorkup.id,
            fechar: () => {
                this.voltarConsulta();
            }
        }
        this.messageBox.dynamic(data, ModalCancelarPedidoWorkupComponent)
            .withTarget(this)
            .show();
    }

    public finalizarPedidoWorkup() {
        this.workupService.finalizarPedidoWorkup(this.pedidoWorkup.id).then(res=>{
            this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target: any) => {
                        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
                    })
                    .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public pedidoAtivo(): boolean {
        if (this.pedidoWorkup) {
            return this.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.EM_ANALISE
                || this.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.CONFIRMADO_CT
                || this.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.INICIAL;
        }
        return false;
    }

    public pedidoConfirmadoCT(): boolean {
        if (this.pedidoWorkup) {
            return this.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.CONFIRMADO_CT;
        }
        return false;
    }

    public hideEditarProgramacao(): boolean {
        if (this.pedidoWorkup) {
            if(this.pedidoWorkup.ultimaDisponibilidade){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm(): void {
        this._disponibilidadeForm = this.fb.group({
            'observacao': [null, null],
            'motivoCancelamento': [null, null]
        });
    }

    public voltarConsulta() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    /**
     * Envia um texto explicando quais são as condições de agendamento disponíveis no momento.
     * Para doador nacional, normalmente, é um resumo das datas disponíveis para os centros de coleta contactados.
     * Para doador internacional torna-se uma caixa de texto livre onde a negociações deve ocorrer com o CT.
     */
    public incluirResumoNovaDisponibilidade() {
        this.setFieldRequiredSemForm("observacao");

        if (super.validateForm()) {
            this.workupService.incluirDisponibilidade(this.pedidoWorkup.id, this._disponibilidadeForm.get("observacao").value).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target: any) => {
                        this.cancelarInclusao();
                        this.carregarAgendamentoPorParametroExterno();
                        this._pedidoWorkup.ultimaDisponibilidade.descricao = this._disponibilidadeForm.get("observacao").value;
                    })
                    .show();
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                }
            );
        }
    }

    /**
     * Nome do componente atual
     *
     * @returns {string}
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "IncluirDisponibilidadeComponent";
    }

    /**
     * Metodo obrigatorio para retornar o formulario atual
     * @returns FormGroup
     */
    public form(): FormGroup {
        return this._disponibilidadeForm;
    }
    /**
     * Não há a necessidade de implemnetar nessa funcionalidade
     * @param  {PedidoWorkup} entidade
     * @returns void
     */
    public preencherFormulario(entidade: PedidoWorkup): void {
        throw new Error("Method not implemented.");
    }

    /**
     * Abre o formulário para inclusão de uma nova disponibilidade de data
     * confirmado com o centro de coleta.
     * Utilizado somente para doador nacional (medula).
     */
    public abrirFormularioNovaDisponibilidadeCentroColeta() {
        this.form().markAsUntouched();
        this.mostraDados = 'hide';
        this.mostraFormulario = '';

        this.resetFieldRequiredSemForm("observacao");
        this.resetFieldRequiredSemForm("fonteCelula");
        this.clearMensagensErro(this.form());
    }
    /**
     * Envia para a tela de doador.
     * @returns void
     */
    public goToDetalheDoador(): void {
        this.router.navigateByUrl('/doadores/detalhe?idDoador=' + this.pedidoWorkup.solicitacao.doador.id);
    }
    /**
     * Envia para a tela com o detalhamento dos dados de ct
     * @returns void
     */
    public goToDetalheCT(): void {
        this.router.navigateByUrl('/doadores/detalheCT?idPedido=' + this.pedidoWorkup.id);
    }
    /**
     * @param  {number} tipoTelefone
     */
    public getTipoTelefone(tipoTelefone: number) {
        return tipoTelefone == 1 ? this._formLabels["fixo"] : this._formLabels["celular"];
    }

    public getEstiloDoLinkDaMigalhaDePao(status): string {
        if (this.pedidoWorkup) {

            switch (status) {
                case StatusPedidosWorkup.INICIAL:
                    return "inicial";
                case StatusPedidosWorkup.AGENDADO:
                    return "agendado";
                case StatusPedidosWorkup.AGUARDANDO_CT:
                    return "aguardandoct";
                case StatusPedidosWorkup.CANCELADO:
                    return "cancelado";
                case StatusPedidosWorkup.EM_ANALISE:
                    return "emanalise";
                case StatusPedidosWorkup.REALIZADO:
                    return "realizado";
                case StatusPedidosWorkup.CONFIRMADO_CT:
                    return "workup-status emanalise";
            }
        }

        return "";

    }

    private limparFormulario() {
        this.clearMensagensErro(this.form());
        this.form().get("observacao").setValue(null);
    }
    /**
     * Cancela a inclusão de uma nova disponibilidade acordado com o centro de coleta.
     * Utilizado somente para doadores nacionais (medula).
     */
    public cancelarInclusao() {
        this.mostraDados = '';
        this.mostraFormulario = 'hide';
        this.limparFormulario();
    }

    public getEstiloDoStatusPedidoWorkup(pedidoWorkup: PedidoWorkup): string {
        if (pedidoWorkup) {
            switch (pedidoWorkup.statusPedidoWorkup.id) {
                case StatusPedidosWorkup.INICIAL:
                    return "workup-status inicial";
                case StatusPedidosWorkup.AGENDADO:
                    return "workup-status agendado";
                case StatusPedidosWorkup.AGUARDANDO_CT:
                    return "workup-status aguardandoct";
                case StatusPedidosWorkup.CANCELADO:
                    return "workup-status cancelado";
                case StatusPedidosWorkup.EM_ANALISE:
                    return "workup-status emanalise";
                case StatusPedidosWorkup.REALIZADO:
                    return "workup-status realizado";
                case StatusPedidosWorkup.CONFIRMADO_CT:
                    return "workup-status emanalise";
            }
        }
        return "";
    }

    public exibeCancelamento(): boolean {
        if (this.pedidoWorkup) {
            return this.pedidoWorkup.statusPedidoWorkup.id != StatusPedidosWorkup.CANCELADO;
        }
        return false;
    }


    /* Getters and Setters */

	/**
     *
     * todos os centros de transplante com a funçãode coleta.
	 * @returns CentroTransplante
	 */
    public get centrosColetores(): CentroTransplante[] {
        return this._centrosColetores;
    }

    /**
     * Formulario da tela
	 * @returns FormGroup
	 */
    public get disponibilidadeForm(): FormGroup {
        return this._disponibilidadeForm;
    }

	/**
     * Controla na tela se deve exibir o botão ou não
	 * @returns string
	 */
    public get mostraDados(): string {
        return this._mostraDados;
    }
	/**
     * Controla na tela se deve exibir o botão ou não
	 * @param  {string} value
	 */
    public set mostraDados(value: string) {
        this._mostraDados = value;
    }
	/**
     * Mostra ou não o formulario para inclusão de um novo cc com data.
	 * @returns string
	 */
    public get mostraFormulario(): string {
        return this._mostraFormulario;
    }
	/**
     * Mostra ou não o formulario para inclusão de um novo cc com data.
	 * @param  {string} value
	 */
    public set mostraFormulario(value: string) {
        this._mostraFormulario = value;
    }

    /**
     * Pedido de workup a se trabalhar.
	 * @returns PedidoWorkup
	 */
    public get pedidoWorkup(): PedidoWorkup {
        return this._pedidoWorkup;
    }
	/**
     * * Pedido de workup a se trabalhar.
	 * @param  {PedidoWorkup} value
	 */
    public set pedidoWorkup(value: PedidoWorkup) {
        this._pedidoWorkup = value;
    }

    public exibeFonteCelulaOpcao1(): boolean {
        if (this._pedidoWorkup && this._pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1) {
            if (this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao && this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula) {
                if (this._pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1.id != this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula.id) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

    public exibeFonteCelulaOpcao2(): boolean {
        if (this._pedidoWorkup && this._pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao2) {
            if (this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao && this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula) {
                if (this._pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao2.id != this._pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula.id) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica se o doador associado ao pedido de workup é nacional
     * (somente medula, por se tratar de workup).
     * @return TRUE se for nacional.
     */
    public isDoadorNacional(): boolean{
        return this._pedidoWorkup && this._pedidoWorkup.solicitacao.doador.tipoDoador == TiposDoador.NACIONAL;
    }

    /**
     * Indica se o doador associado ao pedido de workup é medula internacional
     * @return TRUE se for internacional.
     */
    public isDoadorInternacional(): boolean{

        return this._pedidoWorkup && this._pedidoWorkup.solicitacao.doador.tipoDoador == TiposDoador.INTERNACIONAL;
    }

};
