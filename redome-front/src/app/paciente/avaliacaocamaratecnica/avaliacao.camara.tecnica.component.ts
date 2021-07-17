import { TarefaService } from './../../shared/tarefa.service';
import { activatedRoute } from './../../export.mock.spec';
import { AvaliacaoCamaraTecnica } from './avaliacao.camara.tecnica';
import { BaseForm } from '../../shared/base.form';
import { OnInit, Component, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Paginacao } from '../../shared/paginacao';
import { Router, ActivatedRoute } from '@angular/router';
import { AvalicacaoCamaraTecnicaService } from './avaliacao.camara.tecnica.service';
import { ErroMensagem } from '../../shared/erromensagem';
import { TarefaBase } from '../../shared/dominio/tarefa.base';
import { Paciente } from '../paciente';
import { StatusAvaliacaoCamaraTecnica } from './status.avaliacao.camara.tecnica';
import { MessageBox } from '../../shared/modal/message.box';
import { ErroUtil } from '../../shared/util/erro.util';
/**
 * Component responsavel por avaliação de camara técnica.
 * @author Filipe Paes
 * @export
 * @class AvaliacaoCamaraTecnicaComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'avaliacao-camara-tecnica',
    templateUrl: './avaliacao.camara.tecnica.component.html'
})
export class AvaliacaoCamaraTecnicaComponent extends BaseForm<AvaliacaoCamaraTecnica> implements OnInit{
    qtdRegistros: number = 10;

    paginacaoAberta: Paginacao;
    paginacaoEmAndamento: Paginacao;
    quantidadeRegistro: number = 10;
    public avaliacaoCamaraTecnica: AvaliacaoCamaraTecnica;
    private STATUS_AGUARDANDO: number = 1;

/**
     * Cria uma instancia de AvaliacaoCamaraTecnicaComponent.
     * @param avaliacaoCamaraTecnicaService serviço de tarefas
     * @param router rotas
     * @param activatedRoute parametros
     * @memberof AvaliacaoCamaraTecnicaComponent
     */
    constructor(private avaliacaoCamaraTecnicaService: AvalicacaoCamaraTecnicaService,
                private router:Router,private messageBox: MessageBox, private tarefaService: TarefaService,
                private activatedRouter:ActivatedRoute) {
        super();
        this.paginacaoAberta = new Paginacao('', 0, this.quantidadeRegistro);        
        this.paginacaoAberta.number = 1;

        this.paginacaoEmAndamento = new Paginacao('', 0, this.quantidadeRegistro);        
        this.paginacaoEmAndamento.number = 1;
    }

      /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof AvaliacaoCamaraTecnicaComponent
     */
    ngOnInit(): void {
        this.listarTarefasAbertas(this.paginacaoAberta.number);
        this.listarTarefasAtribuidas(this.paginacaoAberta.number);
    }

    /**
     * Método para listar as tarefas abertas de avaliação de camara técnica.
     * 
     * @private
     * @param {number} pagina 
     * @memberof AvaliacaoCamaraTecnicaComponent
     */
    private listarTarefasAbertas(pagina:number){
        this.avaliacaoCamaraTecnicaService.listarTarefasAvaliacaoCamaraTecnica(pagina - 1, this.quantidadeRegistro).then(res=>{
            let tarefas: TarefaBase[] = [];
            if (res && res.content) {
                res.content.forEach(content => {
                    let tarefa: TarefaBase = new TarefaBase().jsonToEntity(content);
                    if(tarefa.objetoRelacaoEntidade.status.id == this.STATUS_AGUARDANDO){
                        tarefas.push(tarefa);
                    }
                })
            }

            this.paginacaoAberta.content = tarefas;
            this.paginacaoAberta.totalElements = res.totalElements;
            this.paginacaoAberta.quantidadeRegistro = this.qtdRegistros;
            this.paginacaoAberta.number = pagina;
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }


     /**
     * Método para listar as tarefas atribuidas de avaliação de camara técnica.
     * 
     * @private
     * @param {number} pagina 
     * @memberof AvaliacaoCamaraTecnicaComponent
     */
    private listarTarefasAtribuidas(pagina:number){
        this.avaliacaoCamaraTecnicaService.listarTarefasAvaliacaoCamaraTecnica(pagina - 1, this.quantidadeRegistro).then(res=>{
            let tarefas: TarefaBase[] = [];
            if (res && res.content) {
                res.content.forEach(content => {
                    let tarefa: TarefaBase = new TarefaBase().jsonToEntity(content);
                    if(tarefa.objetoRelacaoEntidade.status.id != this.STATUS_AGUARDANDO){
                        tarefas.push(tarefa);
                    }
                })
            }

            this.paginacaoEmAndamento.content = tarefas;
            this.paginacaoEmAndamento.totalElements = res.totalElements;
            this.paginacaoEmAndamento.quantidadeRegistro = this.qtdRegistros;
            this.paginacaoEmAndamento.number = pagina;
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    private exibirMensagemErro(erro: ErroMensagem): void{
        if (erro.listaCampoMensagem != null && erro.listaCampoMensagem.length != 0) {
            this.messageBox.alert(erro.listaCampoMensagem[0].mensagem)
            .withTarget(this)
            .show();
        }
    }
 /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.listarTarefasAbertas(event.page);
    }

    
    /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    selecionarQuantidadePorPagina(event: any, modal: any) {
        this.listarTarefasAbertas(1);
    }


    public form():FormGroup {
        throw new Error("Method not implemented.");
    }
    public preencherFormulario(entidade: AvaliacaoCamaraTecnica): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return "AvaliacaoCamaraTecnicaComponent";
    }

    abrirDetalheAvaliacao(tarefa: TarefaBase): void {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl("/pacientes/avaliacaocamaratecnica/" + tarefa.objetoRelacaoEntidade.id + "?idTarefa=" + tarefa.id);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox, ()=> {
                this.listarTarefasAbertas(1);
                this.listarTarefasAtribuidas(1);
            });
        });
    } 

}