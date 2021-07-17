import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Paginacao } from 'app/shared/paginacao';
import { Tarefa } from '../../paciente/consulta/pendencia/tarefa';
import { AutenticacaoService } from '../../shared/autenticacao/autenticacao.service';
import { Perfis } from '../../shared/enums/perfis';
import { StatusTarefas } from '../../shared/enums/status.tarefa';
import { TiposTarefa } from '../../shared/enums/tipos.tarefa';
import { ErroMensagem } from '../../shared/erromensagem';
import { MessageBox } from '../../shared/modal/message.box';
import { PermissaoRotaComponente } from '../../shared/permissao.rota.componente';
import { TarefaService } from '../../shared/tarefa.service';
import { PedidoExame } from '../pedido.exame';
import { PedidoExameService } from '../pedido.exame.service';
import { LaboratorioExameReceberModal } from '../receber/laboratorio.exame.receber.modal';

/**
 * Classe que representa o componente de laboratório.
 * @author Filipe Paes
 */
@Component({
    selector: "laboratorio-exame",
    templateUrl: './laboratorio.exame.component.html'
})
export class LaboratorioExameComponent implements PermissaoRotaComponente , OnInit{

    private pedido:PedidoExame = null;
    paginacaoTarefasRecebimentoExame: Paginacao;
    qtdRegistroTarefas: number = 10;

    paginacaoTarefasCadastroResultado: Paginacao;
    qtdRegistroExameLaboratorio: number = 10;

    public MODAL_RECEBIMENTO:string = "MODAL_RECEBIMENTO";

    @ViewChild("modalMsg")
    public modalMsg;

    constructor(private router: Router, private tarefaService: TarefaService,
        private autenticacaoService: AutenticacaoService, private pedidoExameService:PedidoExameService,
        private translate: TranslateService, private messageBox: MessageBox) {

        this.paginacaoTarefasRecebimentoExame = new Paginacao('', 0, this.qtdRegistroTarefas);
        this.paginacaoTarefasRecebimentoExame.number = 1;

        this.paginacaoTarefasCadastroResultado = new Paginacao('', 0, this.qtdRegistroExameLaboratorio);
        this.paginacaoTarefasCadastroResultado.number = 1;
    }

    ngOnInit() {
        this.listarTarefasColetaExame(this.paginacaoTarefasRecebimentoExame.number);
        this.listarTarefasCadastroResultado(this.paginacaoTarefasCadastroResultado.number);
    }


    /**
     * Lista tarefas para executar resultado.
     *
     * @param {*} pagina
     * @returns {*}
     * @memberof LaboratorioExameComponent
     */
    listarTarefasCadastroResultado(pagina: any): any {
        this.tarefaService.listarTarefasResultadosPaginadas(pagina - 1, this.qtdRegistroTarefas
                ).then(res => {
                    try {
                        this.paginacaoTarefasCadastroResultado.content = res.content;
                        this.paginacaoTarefasCadastroResultado.totalElements = res.totalElements;
                        this.paginacaoTarefasCadastroResultado.quantidadeRegistro = this.qtdRegistroTarefas;
                        this.paginacaoTarefasCadastroResultado.number = pagina;
                    } catch (e) {
                        this.translate.get("transporteMaterial.erro").subscribe(res => {
                            this.modalMsg.mensagem = res;
                            this.modalMsg.abrirModal();
                        })
                    }
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    listarTarefasColetaExame(pagina: any): any {
        this.tarefaService.listarTarefasDeColetaPedidoExameLaboratorio(pagina - 1, this.qtdRegistroTarefas
                ).then(res => {
                try {
                    this.paginacaoTarefasRecebimentoExame.content = res.content;
                    this.paginacaoTarefasRecebimentoExame.totalElements = res.totalElements;
                    this.paginacaoTarefasRecebimentoExame.quantidadeRegistro = this.qtdRegistroTarefas;
                    this.paginacaoTarefasRecebimentoExame.number = pagina;

                } catch (e) {
                    this.translate.get("laboratorioReceberColeta.erro").subscribe(res => {
                        this.modalMsg.mensagem = res;
                        this.modalMsg.abrirModal();
                    })
                }

            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }


    nomeComponente(): string {
        return "LaboratorioExameComponent";
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }


    /**
       * Método acionado quando muda a página
       *
       * @param {*} event
       * @param {*} modal
       *
       * @memberOf ConsultaComponent
       */
      mudarPaginaRecebimentoExame(event: any) {
        this.listarTarefasColetaExame(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecionaQuantidadeRecebimentoExamePorPagina(event: any, modal: any) {
        this.listarTarefasColetaExame(1);
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaCadastroExame(event: any) {
        this.listarTarefasCadastroResultado(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecionaQuantidadeCadastroExamePorPagina(event: any, modal: any) {
        this.listarTarefasCadastroResultado(1);
    }
    /**
     * Abre o modal de receber coleta.
     * @param tarefa
     * @param pedidoExame
     */
    abrirModalRecebimento(tarefa:Tarefa, pedidoExame:PedidoExame){
        this.pedido = pedidoExame;
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res=>{
            let modal = this.messageBox.dynamic(this.pedido, LaboratorioExameReceberModal);
            modal.closeOption = this.resultadoModal;
            modal.target = this;
            modal.show();
        });
    }

    /**
     * Abre tela de cadastro de exame para que o resultado de exame seja inserido.
     * @param tarefa
     */
    abrirCadastroExame(tarefa:Tarefa, pedidoExame:PedidoExame){
        this.pedido = pedidoExame;
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res=>{
             this.router.navigateByUrl("/pacientes/" + this.pedido.solicitacao.paciente.rmr + "/exames/novo?idPedidoExame=" + this.pedido.id);
        });
    }

    /**
     * Método executado no fechamento do modal de recebimento.
     * @param target
     */
    resultadoModal(target:any):void{
        target.ngOnInit();
    }

    /**
     * Baixa o relatorio de CT
     * @param idPedidoExame
     */
    baixarRelatorioPedidoCt(idPedidoExame:number){
        this.pedidoExameService.downloadRelaotorioPedidoExameCT(idPedidoExame);
    }

};
