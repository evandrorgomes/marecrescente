import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { TarefaLogisticaMaterialDTO } from "app/shared/dto/tarefa.logistica.material.dto";
import { ErroMensagem } from "app/shared/erromensagem";
import { MessageBox } from "app/shared/modal/message.box";
import { PedidoLogistica } from "app/shared/model/pedido.logistica";
import { Paginacao } from "app/shared/paginacao";
import { LogisticaService } from "app/shared/service/logistica.service";
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { DateMoment } from "app/shared/util/date/date.moment";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { ErroUtil } from "app/shared/util/erro.util";
import { PedidoTransporteService } from "app/transportadora/pedido.transporte.service";


/**
 * Classe que registra o comportamento do componente visual
 * que lista as tarefas de logística do doador.
 *
 * @author ergomes
 */
@Component({
    selector: "logistica-material",
    moduleId: module.id,
    templateUrl: "./consulta.logistica.material.component.html",
})
export class ConsultaLogisticaMaterialComponent implements OnInit {

    public TIPO_LOGISTICA_MATERIAL:number = 2;
    public TIPO_LOGISTICA_MATERIAL_COM_AEREO: number = 3;

    public paginacao: Paginacao;
    public paginacaoTransporte: Paginacao;
    public qtdRegistroPorPagina: number = 10;

    // Labels trazidos da internacionalização
    private labels: any;

    // Indica quando deve ser exibida a lista em andamento com a as transportadoras (somente nacional).
    public exibirAcompanhamentoTransportadora: boolean = true;

    DESCRICAO_AGUARDANDO_RETIRADA: string = 'AGUARDANDO RETIRADA';
    DESCRICAO_AGUARDANDO_CONFIRMACAO: string = 'AGUARDANDO CONFIRMAÇÃO';
    PEDIDO_AEREO: number = 3;

    constructor(private fb: FormBuilder,
        private router:Router,
        private translate: TranslateService,
        protected logisticaService: LogisticaService,
        protected pedidoTransporteService: PedidoTransporteService,
        protected pedidoWorkupService: PedidoWorkupService,
        protected messageBox: MessageBox){

        this.translate.get('textosGenericos').subscribe(res => {
            this.labels = res;
        });

        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacao.number = 1;

        this.paginacaoTransporte = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacaoTransporte.number = 1;
    }

    ngOnInit() {
       this.listarTarefasLogisticas(this.paginacao.number);
     //  this.listarLogisticaTransporte(this.paginacaoTransporte.number);
    }

    nomeComponente(): string {
        return "ConsultaLogisticaMaterialComponent";
    }

    /**
     * Listar as pedidos de logistica do tipo de transporte.
     *
     * @param pagina numero da pagina a ser consultada
     */
    listarLogisticaTransporte(pagina:number){
        this.logisticaService.listarPedidosLogisticaPorTransporteEmAndamento(pagina - 1, this.qtdRegistroPorPagina)
        .then(res => {
            this.preencherListaPedidosTransportes(pagina, res);
        },
        (error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });
    }

    /**
     * Recupera o retorno da consulta a pedidos de transporte e
     * preenche a lista paginada.
     *
     *
     * @param resultado
     */
    protected preencherListaPedidosTransportes(pagina: number, resultado: any): void{
        let lista: PedidoLogistica[] = [];
            resultado.content.forEach(entity => {
                let pedidoLogistica: PedidoLogistica = <PedidoLogistica> entity;
                pedidoLogistica.pedidoTransporte.horaPrevistaRetirada =
                     DateMoment.getInstance().parse(entity.pedidoTransporte.horaPrevistaRetirada);
                lista.push(pedidoLogistica);
            });

            this.paginacaoTransporte.number = pagina;
            this.paginacaoTransporte.content = lista;
            this.paginacaoTransporte.totalElements = resultado.totalElements;
            this.paginacaoTransporte.quantidadeRegistro = this.qtdRegistroPorPagina;
    }

    /**
     * Listar as tarefas envolvendo logística a
     * serem realizadas para o doador.
     *
     * @param pagina numero da pagina a ser consultada
     */
    listarTarefasLogisticas(pagina: number) {

        let lista: TarefaLogisticaMaterialDTO[] = [];

        this.logisticaService.listarTarefasPedidoLogisticaMaterialColetaNacionalPaginadas(pagina - 1, this.qtdRegistroPorPagina)
        .then(resultado => {
          if(resultado.content) {

              resultado.content.forEach(entity => {
                  let tarefaLogistica: TarefaLogisticaMaterialDTO = new TarefaLogisticaMaterialDTO().jsonToEntity(entity);

                  lista.push(tarefaLogistica);
              });
          }

          this.paginacao.number = pagina;
          this.paginacao.content = lista;
          this.paginacao.totalElements = resultado.totalElements;
          this.paginacao.quantidadeRegistro = this.qtdRegistroPorPagina;

        },
        (error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });
    }

    protected exibirMensagemErro(error: ErroMensagem) {
        let mensagem: string = ErroUtil.extrairMensagensErro(error);
        this.messageBox.alert(mensagem).show();
    }

    /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPagina(event: any) {
        this.listarTarefasLogisticas(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPagina(event: any) {
        this.listarTarefasLogisticas(1);
    }

    /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPaginaTransporte(event: any) {
        this.listarLogisticaTransporte(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPaginaTransporte(event: any) {
        this.listarLogisticaTransporte(1);
    }

    /**
     * Exibir o label formatado com data limite (dd/MM/yyyy) com o
     * count down em dias restante.
     *
     * @param dataLimite data a ser formatada com countdown.
     */
    public exibirDataLimiteComCountDown(dataLimite: Date): string{
        let dateFormat: DateMoment = DateMoment.getInstance();
        let dataLimiteFormatada: string = dateFormat.format(dataLimite);
        let diferencaDias: number = dateFormat.diffDays(dataLimite, new Date());
        return diferencaDias + " " + this.labels["dias"] + " (" + dataLimiteFormatada + ")";
    }

    private detalharTarefa(tarefa: TarefaLogisticaMaterialDTO){
      if(tarefa.tipoAereo){
        this.router.navigateByUrl(`/doadores/material/logistica/${tarefa.idPedidoLogistica}/aereo?edicao=true`);
      }else{
        this.router.navigateByUrl(`/doadores/material/logistica/${tarefa.idPedidoLogistica}/nacional`);
      }
    }

    public formatarDataHora(data: Date): string{
        return DateMoment.getInstance().format(data, DateTypeFormats.DATE_TIME);
    }

    public exibirEdicao(pedido){
        return (
            pedido.pedidoTransporte.status.descricao == this.DESCRICAO_AGUARDANDO_RETIRADA||
            pedido.pedidoTransporte.status.descricao == this.DESCRICAO_AGUARDANDO_CONFIRMACAO
            ) && pedido.tipo.id == this.PEDIDO_AEREO? true: false;
    }

    public abrirEdicaoLogisticaAereo(idPedidoAereo:number){
        this.router.navigateByUrl('/doadores/material/logistica/'+ idPedidoAereo +'/aereo?edicao=false');
    }

}
