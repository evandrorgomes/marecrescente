import { Component, ViewChild, OnInit } from "@angular/core";
import { HeaderDoadorComponent } from "../../../header/header.doador.component";
import { MensagemModalComponente } from "../../../../../shared/modal/mensagem.modal.component";
import { PedidoColeta } from "../../pedido.coleta";
import { Router, ActivatedRoute } from "@angular/router";
import { TarefaService } from "../../../../../shared/tarefa.service";
import { TranslateService } from "@ngx-translate/core";
import { PedidoColetaService } from "../../pedido.coleta.service";
import { PedidoWorkup } from "../../../workup/pedido.workup";
import { CentroTransplante } from "../../../../../shared/dominio/centro.transplante";
import { DataUtil } from "../../../../../shared/util/data.util";
import { Solicitacao } from "../../../../solicitacao/solicitacao";
import { Prescricao } from "../../../workup/prescricao";
import { FonteCelula } from "../../../../../shared/dominio/fonte.celula";
import { ErroMensagem } from "../../../../../shared/erromensagem";
import { HistoricoNavegacao } from "../../../../../shared/historico.navegacao";
import { TiposTarefa } from "../../../../../shared/enums/tipos.tarefa";
import { MockTarefaService } from "../../../../../shared/mock/mock.tarefa.service";
import { MockPedidoColetaService } from "../../../../../shared/mock/mock.pedido.coleta.service";
import { StatusDoador } from "../../../../../shared/dominio/status.doador";
import { Observable } from "rxjs";
import { DateMoment } from "../../../../../shared/util/date/date.moment";
import { WorkupService } from "../../../workup/workup.service";
import { DoadorNacional } from "../../../../doador.nacional";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { Doador } from "app/doador/doador";

/**
 * Classe que representa o componente de detalhe para comunicação de cancelamento do pedido de coleta
 * para o doador e para o centro de coleta.
 *
 * @author Bruno Sousa
 */
@Component({
    selector: "detalhe-cancelar-agendamento-pedido-coleta",
    templateUrl: './detalhe.cancelar.agendamento.component.html'
})
export class DetalheCancelarAgendamentoPedidoColetaComponent implements OnInit {

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('modalMsg')
    private modalMensagem: MensagemModalComponente;

    @ViewChild('modalMsgAgendamento')
    private _modalMsgAgendamento: MensagemModalComponente;

    private _processoId: number;
    private _tarefaId: number;
    private _tipoTarefa: number;
    private _pedidoWorkup: PedidoWorkup;

    constructor(private router: Router, private tarefaService: TarefaService,
        private translate: TranslateService, private activatedRouter: ActivatedRoute,
        private pedidoWorkupService: WorkupService) {

    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     * @memberOf AgendarPedidoColetaComponent
     */
    ngOnInit(): void {
        this.headerDoador.clearCache();

        this.activatedRouter.queryParams.subscribe(params => {
            if (params && params["processoId"] && params["tarefaId"] && params["tipo"]) {
                this._processoId = Number(params["processoId"]);
                this._tarefaId = Number(params["tarefaId"]);
                this._tipoTarefa = Number(params["tipo"]);
            }
            else {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.modalMensagem.mensagem = res;
                    this.modalMensagem.abrirModal();
                });
            }
        })

        this.activatedRouter.params.subscribe(params => {
            if (params) {
                this.pedidoWorkupService.obterPedidoWorkup(Number(params['pedidoId'])).then(res => {

                    let pedidoWorkup: PedidoWorkup = new PedidoWorkup();
                    pedidoWorkup.id = res.id;
                    pedidoWorkup.centroColeta = new CentroTransplante();
                    pedidoWorkup.centroColeta.id = res.centroColeta.id;
                    pedidoWorkup.centroColeta.nome = res.centroColeta.nome;
                    pedidoWorkup.dataColeta = DataUtil.dataStringToDate(res.dataColeta);
                    pedidoWorkup.dataPrevistaDisponibilidadeDoador = DataUtil.dataStringToDate(res.dataPrevistaDisponibilidadeDoador);
                    pedidoWorkup.dataPrevistaLiberacaoDoador = DataUtil.dataStringToDate(res.dataPrevistaLiberacaoDoador);

                    let solicitacao: Solicitacao = new Solicitacao();
                    solicitacao.doador = new DoadorNacional();
                    solicitacao.doador.id = res.solicitacao.doador.id;
                    (<DoadorNacional>solicitacao.doador).dmr = (<DoadorNacional>res.solicitacao.doador).dmr;
                    pedidoWorkup.solicitacao = solicitacao;

                    pedidoWorkup.fonteCelula = new FonteCelula(res.fonteCelula.id, res.fonteCelula.sigla,
                        res.fonteCelula.descricao)

                    this._pedidoWorkup = pedidoWorkup;

                    Promise.resolve(this.headerDoador).then(() => {
                        //remover a linha abaixo quando remover o mock e descomentar a próxima linha
                        //this.headerDoador.popularCabecalhoIdentificacao(this._pedidoColeta.pedidoWorkup.solicitacao.doador);
                        this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._pedidoWorkup.solicitacao.doador.id);
                    });
                },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                })
            }
        });
    }

    /**
     * Método para finalizar a tarefa
     * @returns void
     */
    public finalizarTarefa(): void {
        this.tarefaService.fecharTarefa(this._processoId, this._tarefaId).then(res => {
            this._modalMsgAgendamento.mensagem = res.mensagem;
            this._modalMsgAgendamento.abrirModal();
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    public voltarConsulta() {
        this.tarefaService.removerAtribuicaoTarefa(this._tarefaId).then(res => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }


    /**
     * Nome do componente atual
     *
     * @returns {string}
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "DetalheCancelarAgendamentoPedidoColetaComponent";
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMensagem.mensagem = obj.mensagem;
        })
        this.modalMensagem.abrirModal();
    }

    /**
     * Envia para a tela de doador.
     * @returns void
     */
    public goToDetalheDoador(): void {
        this.router.navigateByUrl('/doadores/detalhe?idDoador=' + this.pedidoWorkup.solicitacao.doador.id);
    }

    /* Getters and Setters */

    /**
     * Pedido de Coleta.
	 * @returns PedidoWorkup
	 */
    public get pedidoWorkup(): PedidoWorkup {
        return this._pedidoWorkup;
    }

    public ehNotificacaoCancelamentoPedidoColetaDoador(): boolean {
        return this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.id;
    }

    public ehNotificacaoCancelamentoPedidoColetaCentroColeta(): boolean {
        return this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.id;
    }

    public naoExibirDadosDoadorInternacional(): boolean {
        if (this._pedidoWorkup) {
            const doador: Doador = this.pedidoWorkup ? this.pedidoWorkup.solicitacao.match.doador :
                this._pedidoWorkup.solicitacao.match.doador;
            if (doador) {
                return doador.tipoDoador === TiposDoador.INTERNACIONAL || doador.tipoDoador === TiposDoador.CORDAO_INTERNACIONAL;
            }
        }
        return null;
    }

};
