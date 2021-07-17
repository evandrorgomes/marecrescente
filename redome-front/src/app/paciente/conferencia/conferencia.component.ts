import { Tarefa } from './../consulta/pendencia/tarefa';
import { exameService, tarefaService } from './../../export.mock.spec';
import { Component, OnInit } from "@angular/core";
import { ErroMensagem } from "../../shared/erromensagem";
import { FormBuilder, FormGroup } from "@angular/forms";
import { AutenticacaoService } from "../../shared/autenticacao/autenticacao.service";
import { Router, ActivatedRoute } from "@angular/router";
import { ExameService } from "../cadastro/exame/exame.service";
import { MessageBox } from "../../shared/modal/message.box";
import { Paginacao } from "../../shared/paginacao";
import { TranslateService } from "@ngx-translate/core";
import { ErroUtil } from '../../shared/util/erro.util';
import { TarefaService } from '../../shared/tarefa.service';

@Component({
    selector: "conferencia-component",
    moduleId: module.id,
    templateUrl: "./conferencia.component.html",
})
export class ConferenciaComponent implements OnInit {

    public paginacao: Paginacao;
    public qtdRegistroPorPagina: number = 10;
    // Labels trazidos da internacionalização
    private labels: any;
    consultaForm: FormGroup;

    constructor(private fb: FormBuilder,
        private autenticacaoService: AutenticacaoService,
        private tarefaService:TarefaService,
        private router:Router,
        private activatedRouter:ActivatedRoute,
        private translate: TranslateService,
        private exameService: ExameService,
        private autenticacao: AutenticacaoService,
        protected messageBox: MessageBox){

        this.translate.get('textosGenericos').subscribe(res => {
            this.labels = res;
        });

        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacao.number = 1;

    }

    ngOnInit() {
        this.consultaForm = this.fb.group({
            'rmr': [null, null],
            'nome': [null, null]
        });
        this.listarTarefasExame(1);
    }

    /**
     * Listar as tarefas envolvendo exames a serem aprovados.
     *
     * @param pagina numero da pagina a ser consultada
     */
    listarTarefasExame(pagina: number) {
        this.exameService.listarTarefasExamePendentesPaginadas(this.consultaForm.get("rmr").value?this.consultaForm.get("rmr").value:""
        , this.consultaForm.get("nome").value?this.consultaForm.get("nome").value:"",  pagina - 1, this.qtdRegistroPorPagina)
        .then(res => {
            this.paginacao.number = pagina;
            this.paginacao.content = res.content;
            this.paginacao.totalElements = res.totalElements;
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



    nomeComponente(): string {
        return "ConferenciaComponent";
    }

     /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPagina(event: any) {
        this.listarTarefasExame(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPagina(event: any) {
        this.listarTarefasExame(1);
    }

     /**
     * Método acionado pelo botão de consulta
     * @author Filipe Paes
     */
    onSubmit(pagina: number) {
        return this.listarTarefasExame(pagina);
    }

    redirecionarParaDetalhe(tarefa: any){
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl('/conferencia/detalhe/' + tarefa.objetoRelacaoEntidade.id + "?idTarefa=" + tarefa.id);
        },
        (error:ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

}
