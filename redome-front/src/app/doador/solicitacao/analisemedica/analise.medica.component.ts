import { OnInit, Component, ViewChild } from "@angular/core";
import { AnaliseMedicaService } from "./analise.medica.service";
import { TarefaService } from "app/shared/tarefa.service";
import { MessageBox } from "app/shared/modal/message.box";
import { Paginacao } from "app/shared/paginacao";
import { TarefaBase } from "app/shared/dominio/tarefa.base";
import { ErroMensagem } from "app/shared/erromensagem";
import { ErroUtil } from '../../../shared/util/erro.util';
import { Router } from "@angular/router";

@Component({ 
    selector: "analise-medica-component",
    moduleId: module.id,
    templateUrl: "./analise.medica.component.html"
})
export class AnaliseMedicaComponent implements OnInit{

    public paginacaoAnalises: Paginacao;
    public qtdRegistroAvaliacoes= 10;

    @ViewChild('modalErro')
    public modalErro;


    constructor(private router: Router, private analiseMedicaservice: AnaliseMedicaService,
        private tarefaService: TarefaService, private messageBox: MessageBox) {
        this.paginacaoAnalises = new Paginacao('', 0, this.qtdRegistroAvaliacoes);
        this.paginacaoAnalises.number = 1;
    }

    ngOnInit(): void {
        this.listarTarefas(1);
    }
    nomeComponente(): string {
        return "AnaliseMedicaComponent";
    }

    listarTarefas(pagina: number){
        this.analiseMedicaservice.listarTarefas(pagina - 1, this.qtdRegistroAvaliacoes).then(res => {
            const tarefas: TarefaBase[] = [];
            if (res.content) {
                res.content.forEach(tarefa => {
                    tarefas.push(new TarefaBase().jsonToEntity(tarefa));
                });
            }
            this.paginacaoAnalises.content = tarefas;
            this.paginacaoAnalises.totalElements = res.totalElements;
            this.paginacaoAnalises.quantidadeRegistro = this.qtdRegistroAvaliacoes;
        },
        (error: ErroMensagem) => {
            ErroUtil.extrairMensagensErro(error);
        });
    }



    /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    mudarPaginaAvaliacoes(event: any) {
        this.listarTarefas(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeAvaliacoesPorPagina(event: any, modal: any) {
        this.listarTarefas(1);
    }

    public irParaTelaDetalhe(tarefa: TarefaBase) {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl("doadores/analisemedica/" + tarefa.objetoRelacaoEntidade.id)
        },
        error => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        })
    }

}