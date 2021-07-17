import { ModalRetiradaEntregaTransporteMaterialComponent } from './modal.retirada.entrega.transporte.material.component';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { Observable } from 'rxjs/Observable';
import { ModalDirective } from 'ngx-bootstrap';
import { Component, OnInit, ViewChild } from "@angular/core";
import { Paginacao } from "../../../shared/paginacao";
import { Router } from "@angular/router";
import { MockTarefaService } from "../../../shared/mock/mock.tarefa.service";
import { AutenticacaoService } from "../../../shared/autenticacao/autenticacao.service";
import { TranslateService } from "@ngx-translate/core";
import { TiposTarefa } from "../../../shared/enums/tipos.tarefa";
import { Perfis } from "../../../shared/enums/perfis";
import { StatusTarefas } from "../../../shared/enums/status.tarefa";
import { ErroMensagem } from '../../../shared/erromensagem';
import { AtributoOrdenacaoDTO } from '../../../shared/util/atributo.ordenacao.dto';
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { PedidoTransporteDTO } from '../pedido.transporte.dto';
import { StatusPedidoTransporte } from '../status.pedido.transporte';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { BaseForm } from '../../../shared/base.form';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { BsModalService } from 'ngx-bootstrap/modal/bs-modal.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { ValidateDataHora } from '../../../validators/data.validator';
import { PedidoTransporteService } from '../../pedido.transporte.service';
import { MockPedidoTransporteService } from '../../../shared/mock/mock.pedido.transporte.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { ModalEvent } from '../../../shared/eventos/modal.event';
import { MessageBox } from '../../../shared/modal/message.box';
import { Modal } from '../../../shared/modal/factory/modal.factory';
import { ErroUtil } from '../../../shared/util/erro.util';
import { PedidoColeta } from 'app/doador/consulta/coleta/pedido.coleta';
import { PedidoTransporte } from 'app/transportadora/tarefas/pedido.transporte';
import { Match } from 'app/paciente/busca/match';
import { Doador } from 'app/doador/doador';
import { IdentificacaoDoador, DoadorUtil } from 'app/shared/util/doador.util';
import { Busca } from 'app/paciente/busca/busca';
import { CordaoNacional } from 'app/doador/cordao.nacional';


@Component({
    selector: 'retirada-entrega-transporte-material',
    templateUrl: './retirada.entrega.transporte.material.component.html',
    providers: []
})
export class RetiradaEntregaTransporteMaterialComponent implements OnInit {

    paginacaoTarefasTransporteMaterial: Paginacao;
    qtdRegistroTarefasTransporteMaterial: number = 10;

    private _pedidoTransporteDTOSelecionado: PedidoTransporteDTO;

    constructor(private fb: FormBuilder,  private router: Router, private tarefaService: TarefaService,
        private autenticacaoService: AutenticacaoService, private translate: TranslateService,
        private messageBox: MessageBox, private pedidoTransporteService: PedidoTransporteService ) {

        this.paginacaoTarefasTransporteMaterial = new Paginacao('', 0, this.qtdRegistroTarefasTransporteMaterial);
        this.paginacaoTarefasTransporteMaterial.number = 1;

    }

    ngOnInit() {
        this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number);
    }

    nomeComponente(): string {
        return "RetiradaEntregaTransporteMaterialComponent";
    }

    /**
     * Método acionado quando muda a página
     *
     * @author Bruno Sousa
     * @param {*} event
     * @memberof RetiradaEntregaTransporteMaterialComponent
     */
    mudarPaginaTarefasTransporteMaterial(event: any) {
        this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number)
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @author Bruno Sousa
     * @param {*} event
     * @memberof RetiradaEntregaTransporteMaterialComponent
     */
    selecionaQuantidadeAvaliacoesPendentesPorPagina(event: any) {
        this.listarTarefasTransporteMaterial(1);
    }

    /**
     * Método para listar as tarefas de retirada e entrega de transporte de material.
     *
     * @author Bruno Sousa
     * @private
     * @param {number} pagina
     * @memberof RetiradaEntregaTransporteMaterialComponent
     */
    private listarTarefasTransporteMaterial(pagina: number) {
        this.tarefaService.listarTarefasPaginadas(TiposTarefa.PEDIDO_TRANSPORTE_RETIRADA_ENTREGA.id, Perfis.TRANSPORTADORA,
            null, pagina - 1, this.qtdRegistroTarefasTransporteMaterial,
            false, null, StatusTarefas.ABERTA.id, null, null, true).then(res => {
                let dateMoment: DateMoment = DateMoment.getInstance();
                let pedidos: PedidoTransporteDTO[] = [];

                if (res.content) {
                    res.content.forEach(tarefa => {
                        let pedidoTransporteDTO: PedidoTransporteDTO = new PedidoTransporteDTO();
                        let pedidoTransporte: PedidoTransporte = <PedidoTransporte> tarefa.objetoRelacaoEntidade;
                        let pedidoColeta: PedidoColeta = pedidoTransporte.pedidoLogistica.pedidoColeta;
                        let match: Match = pedidoColeta.solicitacao.match;
                        let doador: Doador = match.doador;
                        let identificacao: IdentificacaoDoador = DoadorUtil.obterNomeIdentificacaoDoador(doador);
                        let busca: Busca = match.busca;

                        pedidoTransporteDTO.idPedidoTransporte = <number> pedidoTransporte.id;
                        pedidoTransporteDTO.idTarefa = <number> tarefa.id;
                        pedidoTransporteDTO.horaPrevistaRetirada =
                            dateMoment.parse(<any> pedidoTransporte.horaPrevistaRetirada);

                        pedidoTransporteDTO.identificacao = identificacao.idChave;
                        if(DoadorUtil.isMedula(doador)){
                            pedidoTransporteDTO.nomeLocalRetirada =
                                pedidoColeta.pedidoWorkup.centroColeta.nome;
                        }
                        else {
                            pedidoTransporteDTO.nomeLocalRetirada =
                                (<CordaoNacional> doador).bancoSangueCordao.nome;
                        }
                        pedidoTransporteDTO.rmr = <number> busca.paciente.rmr;
                        pedidoTransporteDTO.nomeCentroTransplante = busca.centroTransplante.nome;

                        pedidoTransporteDTO.status = new StatusPedidoTransporte();
                        pedidoTransporteDTO.status.descricao = pedidoTransporte.status.descricao;

                        if (tarefa.tipoTarefa.id == TiposTarefa.PEDIDO_TRANSPORTE_RETIRADA.id) {
                            pedidoTransporteDTO.tipoTarefa = TiposTarefa.PEDIDO_TRANSPORTE_RETIRADA;
                        }
                        else if (tarefa.tipoTarefa.id == TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA.id) {
                            pedidoTransporteDTO.tipoTarefa = TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA;
                        }

                        pedidos.push(pedidoTransporteDTO);
                    });
                }
                this.paginacaoTarefasTransporteMaterial.content = pedidos;
                this.paginacaoTarefasTransporteMaterial.totalElements = res.totalElements;
                this.paginacaoTarefasTransporteMaterial.quantidadeRegistro = this.qtdRegistroTarefasTransporteMaterial;

            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    abrirModal(pedidoTransporteDTO: PedidoTransporteDTO) {

        this.tarefaService.atribuirTarefaParaUsuarioLogado(pedidoTransporteDTO.idTarefa).then(res => {
            this._pedidoTransporteDTOSelecionado = pedidoTransporteDTO;

            let data: any = {
                '_pedidoTransporteDTOSelecionado': this._pedidoTransporteDTOSelecionado,
                'fechar': () => {
                    this.paginacaoTarefasTransporteMaterial.number = 1;
                    this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number);
                }
            };

            this.messageBox.dynamic(data, ModalRetiradaEntregaTransporteMaterialComponent)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.tarefaService.removerAtribuicaoTarefa(this._pedidoTransporteDTOSelecionado.idTarefa).then(res => {
                        this.paginacaoTarefasTransporteMaterial.number = 1;
                        this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number);
                    }, (erro: ErroMensagem) => {
                        this.messageBox.alert(erro.mensagem.toString()).show();
                    });
                }).show();

        }, (erro: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(erro, this.messageBox, () => {
                this.listarTarefasTransporteMaterial(1);
            });
        } );
    }

    public horaPrevistaRetiradaFormatada(dto: PedidoTransporteDTO): string{
        return DateMoment.getInstance().format(dto.horaPrevistaRetirada, DateTypeFormats.DATE_TIME);
    }

}
