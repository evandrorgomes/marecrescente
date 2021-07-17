import { DataUtil } from './../../../shared/util/data.util';
import { DoadorInternacional } from './../../../doador/doador.internacional';
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from '@angular/forms';
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { PedidoColeta } from 'app/doador/consulta/coleta/pedido.coleta';
import { CordaoNacional } from 'app/doador/cordao.nacional';
import { Doador } from 'app/doador/doador';
import { Busca } from 'app/paciente/busca/busca';
import { Match } from 'app/paciente/busca/match';
import { DoadorUtil, IdentificacaoDoador } from 'app/shared/util/doador.util';
import { PedidoTransporte } from 'app/transportadora/tarefas/pedido.transporte';
import { AutenticacaoService } from "../../../shared/autenticacao/autenticacao.service";
import { Perfis } from "../../../shared/enums/perfis";
import { StatusTarefas } from "../../../shared/enums/status.tarefa";
import { TiposTarefa } from "../../../shared/enums/tipos.tarefa";
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from "../../../shared/paginacao";
import { TarefaService } from '../../../shared/tarefa.service';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { ErroUtil } from '../../../shared/util/erro.util';
import { PedidoTransporteService } from '../../pedido.transporte.service';
import { PedidoTransporteDTO } from '../pedido.transporte.dto';
import { StatusPedidoTransporte } from '../status.pedido.transporte';
import { ModalRetiradaEntregaTransporteMaterialInternacionalComponent } from './modal.retirada.entrega.transporte.material.inter.component';


@Component({
    selector: 'retirada-entrega-transporte-material-internacional',
    templateUrl: './retirada.entrega.transporte.material.inter.component.html',
    providers: []
})
export class RetiradaEntregaTransporteMaterialInternacionalComponent implements OnInit {

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
        return "RetiradaEntregaTransporteMaterialInternacionalComponent";
    }

    /**
     * Método acionado quando muda a página
     *
     * @author Bruno Sousa
     * @param {*} event
     * @memberof RetiradaEntregaTransporteMaterialComponent
     */
    mudarPaginaTarefasTransporteMaterial(event: any) {
        this.listarTarefasTransporteMaterial(event.page);
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
        this.pedidoTransporteService.listarTarefasMaterialInternacional(pagina - 1, this.qtdRegistroTarefasTransporteMaterial).then(res => {
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
                        pedidoTransporteDTO.dataRetirada = pedidoTransporte.dataRetirada;
                        pedidoTransporteDTO.dataEntrega = pedidoTransporte.dataEntrega;

                        pedidoTransporteDTO.identificacao = identificacao.idChave;
                        pedidoTransporteDTO.rmr = <number> busca.paciente.rmr;
                        pedidoTransporteDTO.nomeCentroTransplante = busca.centroTransplante.nome;

                        pedidoTransporteDTO.status = new StatusPedidoTransporte();
                        pedidoTransporteDTO.status.descricao = pedidoTransporte.status.descricao;

                        if (tarefa.tipoTarefa.id == TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL.id) {
                            pedidoTransporteDTO.tipoTarefa = TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL;
                        }
                        else if (tarefa.tipoTarefa.id == TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL.id) {
                            pedidoTransporteDTO.tipoTarefa = TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL;
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

            this.messageBox.dynamic(data, ModalRetiradaEntregaTransporteMaterialInternacionalComponent)
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

    // public horaPrevistaRetiradaFormatada(dto: PedidoTransporteDTO): string{
    //     return DateMoment.getInstance().format(dto.horaPrevistaRetirada, DateTypeFormats.DATE_TIME);
    // }

}
