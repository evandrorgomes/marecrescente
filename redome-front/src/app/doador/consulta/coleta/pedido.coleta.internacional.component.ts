import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoColetaService } from 'app/doador/consulta/coleta/pedido.coleta.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { ErroUtil } from '../../../shared/util/erro.util';
import { PedidoColetaComponent } from './pedido.coleta.component';
import { TarefaService } from 'app/shared/tarefa.service';

/**
 * Classe que registra o comportamento do componente visual
 * que lista as tarefas de coleta de workup do doador.
 *  
 * @author bruno.sousa
 */
@Component({
    selector: "pedido-coleta-internacional",
    moduleId: module.id,
    templateUrl: "./pedido.coleta.component.html",
})
export class PedidoColetaInternacionalComponent extends PedidoColetaComponent implements OnInit {


    constructor(fb: FormBuilder,
            tarefaService: TarefaService, pedidoColetaService: PedidoColetaService,
            router:Router, autenticacaoService: AutenticacaoService, translate: TranslateService,
            messageBox: MessageBox){
        
        super(fb, tarefaService, pedidoColetaService, router, autenticacaoService, translate, messageBox);

        
        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);        
        this.paginacao.number = 1;
    }

    nomeComponente(): string {
        return "PedidoColetaInternacionalComponent";
    }

    /**
     * Listar as tarefas envolvendo logística a 
     * serem realizadas para o doador.
     * 
     * @param pagina numero da pagina a ser consultada
     */
    listarTarefasPedidoColeta(pagina: number) {

        this.pedidoColetaService.listarTarefasAgendadasInternacional(pagina - 1, this.qtdRegistroPorPagina)
        .then(res => {
            if(res.content) {
                let lista: TarefaBase[] = [];
                res.content.forEach(entity => {
                    let tarefa: TarefaBase = new TarefaBase().jsonToEntity(entity);
                    lista.push(tarefa);
                });
                this.paginacao.content = lista;                
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.qtdRegistroPorPagina;
            }
        },
        (error:ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }
   
}