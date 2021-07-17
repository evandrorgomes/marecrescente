import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Doador } from 'app/doador/doador';
import { Busca } from 'app/paciente/busca/busca';
import { PedidoColeta } from '../../../doador/consulta/coleta/pedido.coleta';
import { Match } from '../../../paciente/busca/match';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { TarefaService } from '../../../shared/tarefa.service';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { DoadorUtil, IdentificacaoDoador } from '../../../shared/util/doador.util';
import { PedidoTransporteService } from '../../pedido.transporte.service';
import { PedidoTransporte } from '../pedido.transporte';
import { PedidoTransporteDTO } from '../pedido.transporte.dto';
import { StatusPedidoTransporte } from '../status.pedido.transporte';
import { CordaoNacional } from 'app/doador/cordao.nacional';


@Component({
    selector: 'consulta-transporte-material',
    templateUrl: './consulta.transporte.material.component.html'
})
export class ConsultaTransporteMaterialComponent implements PermissaoRotaComponente, OnInit {

    paginacaoTarefasTransporteMaterial: Paginacao;
    qtdRegistroTarefasTransporteMaterial: number = 10;

    paginacaoTransporteMaterial: Paginacao;
    qtdRegistroTransporteMaterial: number = 10;

    constructor(private router: Router, private tarefaService: TarefaService,
        private autenticacaoService: AutenticacaoService, private translate: TranslateService,
        private pedidoTransporteService:PedidoTransporteService, private messageBox: MessageBox) {

        this.paginacaoTarefasTransporteMaterial = new Paginacao('', 0, this.qtdRegistroTarefasTransporteMaterial);
        this.paginacaoTarefasTransporteMaterial.number = 1;

        this.paginacaoTransporteMaterial = new Paginacao('', 0, this.qtdRegistroTransporteMaterial);
        this.paginacaoTransporteMaterial.number = 1;
    }

    ngOnInit() {
        this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number);
        this.listarTransportesMaterial(this.paginacaoTransporteMaterial.number);
    }

    nomeComponente(): string {
        return "ConsultaTransporteMaterialComponent";
    }

    /**
       * Método acionado quando muda a página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
    mudarPaginaTarefasTransporteMaterial(event: any) {
        this.listarTarefasTransporteMaterial(this.paginacaoTarefasTransporteMaterial.number)
    }

    /**
       * Método acionado quando muda a página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
    mudarPaginaTransporteMaterial(event: any) {
        this.listarTransportesMaterial(this.paginacaoTransporteMaterial.number)
    }

    public irParaAgendamento(pedidoTransporteDTO: any): void {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(pedidoTransporteDTO.idTarefa)
            .then(res => {
                this.router.navigateByUrl('/transportadoras/' + pedidoTransporteDTO.idPedidoTransporte + '/agendar?tarefaId='+pedidoTransporteDTO.idTarefa);
            })
            .catch((error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeTarefasTransportesPorPagina(event: any, modal: any) {
        this.listarTarefasTransporteMaterial(1);
    }

      /**
       * Método acionado quando é alterado a quantidade de registros por página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeTransportesPorPagina(event: any, modal: any) {
        this.listarTransportesMaterial(1);
    }

    /**
     * Método para listar as avaliações pendentes
     * 
     * @param pagina numero da pagina a ser consultada
     * 
     * @memberOf ConsultaComponent
     */
    private listarTarefasTransporteMaterial(pagina: number) {
        this.pedidoTransporteService.listarTarefas(pagina - 1, this.qtdRegistroTarefasTransporteMaterial)
            .then(res => {
                let dateMoment: DateMoment = DateMoment.getInstance();

                let tarefas: any[] = [];
                let totalElements = 0;
                if (res.content) {

                    res.content.forEach(content => {
                        let tarefa: any = content;
                        tarefa.horaPrevistaRetirada =
                            dateMoment.parse(<any> content.horaPrevistaRetirada);
                        tarefas.push(tarefa);
                    });

                    totalElements = res.totalElements;
                }

                this.paginacaoTarefasTransporteMaterial.content = tarefas;
                this.paginacaoTarefasTransporteMaterial.totalElements = totalElements;
                this.paginacaoTarefasTransporteMaterial.quantidadeRegistro = this.qtdRegistroTarefasTransporteMaterial
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    public horaPrevistaRetiradaFormatada(dto: PedidoTransporteDTO): string{
        return dto.horaPrevistaRetirada? DateMoment.getInstance().format(dto.horaPrevistaRetirada, DateTypeFormats.DATE_TIME) : null;
    }

    /**
     * Método para listar as avaliações pendentes
     * 
     * @param pagina numero da pagina a ser consultada
     * 
     * @memberOf ConsultaComponent
     */
    private listarTransportesMaterial(pagina: number) {
        this.pedidoTransporteService.listarPedidosTransporte(pagina -1, this.qtdRegistroTransporteMaterial).then(res => {
            let dateMoment: DateMoment = DateMoment.getInstance();

            let pedidos: any[] = [];
            let totalElements = 0;
            if (res.content) {

                res.content.forEach(content => {
                    let pedido: any = content;
                    pedido.horaPrevistaRetirada =
                       dateMoment.parse(<any> content.horaPrevistaRetirada);
                    pedidos.push(pedido);
                });

                totalElements = res.totalElements;
            }

            this.paginacaoTransporteMaterial.content = pedidos;
            this.paginacaoTransporteMaterial.totalElements = totalElements;
            this.paginacaoTransporteMaterial.quantidadeRegistro = this.qtdRegistroTransporteMaterial;

        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let res: string = "";
        error.listaCampoMensagem.forEach(obj => {
            res = res + (obj.mensagem + "\n");
        })
        this.messageBox.alert(res).show();
    }

}