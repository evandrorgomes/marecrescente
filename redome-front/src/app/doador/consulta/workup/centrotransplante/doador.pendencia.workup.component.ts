import { SelectCentrosComponent } from './../../../../shared/component/selectcentros/select.centros.component';
import { ErroUtil } from './../../../../shared/util/erro.util';
import { MessageBox } from './../../../../shared/modal/message.box';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Paginacao } from '../../../../shared/paginacao';
import { WorkupService } from '../workup.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Router } from '@angular/router';
import { PedidoWorkup } from '../pedido.workup';
import { Paciente } from '../../../../paciente/paciente';
import { TiposTarefa } from '../../../../shared/enums/tipos.tarefa';
import { PerfilUsuario } from '../../../../shared/enums/perfil.usuario';
import { Perfil } from '../../../../shared/dominio/perfil';
import { TarefaService } from '../../../../shared/tarefa.service';
import { OrdenacaoMatchUtil } from '../../../../paciente/busca/analise/ordenacao.match.util';
import { OrdenacaoTarefa } from '../../../../shared/enums/ordenacao.tarefas';
import { TarefaBase } from '../../../../shared/dominio/tarefa.base';
import { StatusTarefas } from '../../../../shared/enums/status.tarefa';
import { PedidoColeta } from '../../coleta/pedido.coleta';
import { PedidoColetaService } from 'app/doador/consulta/coleta/pedido.coleta.service';

@Component({
    moduleId: module.id,
    selector: "doador-pendencia-workup",
    templateUrl: "./doador.pendencia.workup.component.html"
})
export class DoadorPendenciaWorkupComponent implements OnInit, AfterViewInit {

    public paginacaoPendenciasWorkup: Paginacao;
    public qtdRegistroPendenciasWorkup: number = 10;

    public paginacaoPendenciasCordao: Paginacao;
    public qtdRegistroPendenciasCordao: number = 10;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    @ViewChild('selectCentros')
    private selectCentros: SelectCentrosComponent;

    constructor(private workupService: WorkupService, private router: Router,
        private tarefaService:TarefaService, private messageBox: MessageBox,
        private pedidoColetaService: PedidoColetaService){
        this.paginacaoPendenciasWorkup = 
            new Paginacao('', 0, this.qtdRegistroPendenciasWorkup);        
        this.paginacaoPendenciasWorkup.number = 1;

        this.paginacaoPendenciasCordao = 
            new Paginacao('', 0, this.qtdRegistroPendenciasCordao);        
        this.paginacaoPendenciasCordao.number = 1;
    }

    ngOnInit(): void {
        
    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarTarefasPendentes(1);
            this.listarTarefasPendentesCordao(1);
        })
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "DoadorPendenciaWorkupComponent";
    }

    /**
     * Lista as tarefas pendentes para o médico do centro de transplante.
     */
    public listarTarefasPendentes(pagina: number): void {

        this.workupService.listarDisponibilidades(this._centroSelecionado.id, pagina -1, this.qtdRegistroPendenciasWorkup).then(res=>{
            let tarefas: TarefaBase [] = [];
            if(res.content){
                res.content.forEach(tarefaJson => {
                    let tarefa:TarefaBase = new TarefaBase().jsonToEntity(tarefaJson);
                    tarefas.push(tarefa);
                });
            }
            /* if(res.content){
                tarefas = this.workupService.obterDatasWorkup(res, tarefas);
            } */
            this.paginacaoPendenciasWorkup.content = tarefas;
            this.paginacaoPendenciasWorkup.totalElements = res.totalElements;
            this.paginacaoPendenciasWorkup.quantidadeRegistro = this.qtdRegistroPendenciasWorkup;
        },
        (error: ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    public listarTarefasPendentesCordao(pagina: number): void {

        this.pedidoColetaService.listarDisponibilidades(this._centroSelecionado.id, pagina - 1, this.qtdRegistroPendenciasCordao).then(res => {
            let tarefas: TarefaBase [] = [];
            if(res.content){
                res.content.forEach(tarefaJson => {
                    let tarefa:TarefaBase = new TarefaBase().jsonToEntity(tarefaJson);
                    tarefas.push(tarefa);
                });
            }
            this.paginacaoPendenciasCordao.content = tarefas;
            this.paginacaoPendenciasCordao.totalElements = res.totalElements;
            this.paginacaoPendenciasCordao.quantidadeRegistro = this.qtdRegistroPendenciasCordao;
        },
        (error: ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
        
    }

    /**
     * Troca a página na paginação das tarefas de workup. 
     * 
     * @param event 
     */
    public proximaPagina(pagina: number) {
        this.listarTarefasPendentes(pagina);
    }

    /**
     * Troca a página na paginação das tarefas de workup. 
     * 
     * @param event 
     */
    public proximaPaginaCordao(pagina: number) {
        this.listarTarefasPendentesCordao(pagina);
    }

    /**
     * Quando o usuário acessa a pendência disponível na lista.
     * Neste ponto, deve-se abrir a tela com a disponibilidade informada
     * pelo analista workup e aprová-las ou não.
     * 
     * @param  {TarefaPedidoWorkup} tarefa
     */
    public abrirDetalhePendencia(tarefa: TarefaBase){
        let pedido: PedidoWorkup = tarefa.objetoRelacaoEntidade;
        let paciente: Paciente = pedido.solicitacao.paciente;

        this.router.navigateByUrl(
            '/doadores/workup/pendencias/detalhe?rmr=' + 
                paciente.rmr + "&pedidoId=" + pedido.id);
    }

    /**
     * Quando o usuário acessa a pendência disponível na lista.
     * Neste ponto, deve-se abrir a tela com a disponibilidade informada
     * pelo analista workup e aprová-las ou não.
     * 
     * @param  {TarefaPedidoWorkup} tarefa
     */
    public abrirDetalhePendenciaCordao(tarefa: TarefaBase){
        let pedido: PedidoColeta = tarefa.objetoRelacaoEntidade;
        // let paciente: Paciente = pedido.solicitacao.paciente;

        this.router.navigateByUrl(
            '/doadores/workup/coleta/'+pedido.id+'/disponibilidade?pedidoColetaId='+pedido.id);
    }

    public mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarTarefasPendentes(1);
        this.listarTarefasPendentesCordao(1);
    }

}