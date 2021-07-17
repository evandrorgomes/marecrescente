import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from '../../../../shared/permissao.rota.componente';
import { AvaliacaoNovaBuscaService } from 'app/shared/service/avaliacao.nova.busca.service';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { TarefaService } from 'app/shared/tarefa.service';
import { TarefaBase } from 'app/shared/dominio/tarefa.base';
import { AvaliacaoNovaBusca } from '../../../../shared/classes/avaliacao.nova.busca';
import { Paginacao } from 'app/shared/paginacao';

/**
 * Classe que representa o componente de consulta de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "avaliar-nova-busca",
    moduleId: module.id,
    templateUrl: "./avaliar.nova.busca.component.html"
})
export class AvaliarNovaBuscaComponent implements OnInit, PermissaoRotaComponente {

    public paginacao: Paginacao;
    public qtdRegistro: number = 10;

    
    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder
        , private router:Router
        , private activatedRouter:ActivatedRoute
        , private messageBox: MessageBox
        , private tarefaService: TarefaService
        , private avaliacaoNovaBuscaService: AvaliacaoNovaBuscaService){
        
        this.paginacao = new Paginacao('', 0, this.qtdRegistro);
        this.paginacao.number = 1;
    }

    ngOnInit() {
        this.listarTarefasAvaliacao(this.paginacao.number);
    }

    nomeComponente(): string {
        return "AvaliarNovaBuscaComponent";
    }

    /**
     * Lista as tarefas de avaliação de nova busca.
     * 
     * @param pagina numero da pagina a ser listada.
     */
    protected listarTarefasAvaliacao(pagina: number) {
        this.avaliacaoNovaBuscaService.listarTarefas(pagina - 1, this.qtdRegistro)
            .then(res=>{
                this.carregarTarefas(res);
            },
            (error: ErroMensagem)=> {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            });
    }

    protected carregarTarefas(result: any): void{
        this.paginacao.content = result.content;
        this.paginacao.totalElements = result.totalElements;
        this.paginacao.quantidadeRegistro = this.qtdRegistro;
    }

    /**
     * Metodo para atribuir a tarefa ao analista de workup
     * @param  {TarefaPedidoWorkup} tarefa
     */
    public atribuirTarefa(tarefa: TarefaBase){
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res=>{
            this.listarTarefasAvaliacao(this.paginacao.number);
        });
    }

    mudarPagina(event: any) {
        this.listarTarefasAvaliacao(event.page);
    }

    selecionarPorPagina(event: any) {
        this.listarTarefasAvaliacao(1);
    }

    /**
     * Tela que ira para a tela de pedido de workup.
     */
    abrirDetalhe(avaliacao: AvaliacaoNovaBusca){
        if(avaliacao){
            this.router.navigateByUrl("/pacientes/" + avaliacao.paciente.rmr + "/busca/avaliacao/" + avaliacao.id);
        }
    }

}