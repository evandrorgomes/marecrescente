import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { HeaderDoadorComponent } from '../../header/header.doador.component';
import { ResultadoWorkup } from '../../../cadastro/resultadoworkup/resultado/resultado.workup';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../../shared/base.form';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { TarefaBase } from '../../../../shared/dominio/tarefa.base';
import { MessageBox } from '../../../../shared/modal/message.box';
import { TarefaService } from '../../../../shared/tarefa.service';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { CentroTransplanteService } from '../../../../admin/centrotransplante/centrotransplante.service';
import { CentroTransplante } from '../../../../shared/dominio/centro.transplante';
import { UsuarioLogado } from '../../../../shared/dominio/usuario.logado';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { ResultadoWorkupService } from './resultadoworkup.service';
import { Paginacao } from 'app/shared/paginacao';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';



/**
 * Classe de component com métodos de funcionalidades de tela da parte de resultado de exame de workup.
 * @author Filipe Paes
 */
@Component({
    selector: "resultado-workup",
    moduleId: module.id,
    templateUrl: "./consulta.resultado.workup.nacional.component.html"
})
export class ConsultaResultadoWorkupNacionalComponent extends BaseForm<ResultadoWorkup> implements OnInit, AfterViewInit {

    data: (string | RegExp)[];

    public paginacaoTarefas: Paginacao;
    public qtdRegistroResultados: number = 10;

    public consultaForm: FormGroup;

    @ViewChild("modalErro")
    public modalErro;

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }



    translate: TranslateService;
    public resultadoForm: FormGroup;
    public resultadoWorkup: ResultadoWorkup = null;
    public usuario: UsuarioLogado;
    public centros: CentroTransplante[];
    private customErros: any = {
        tipoArquivoLaudo: "",
        quantidadeArquivoLaudo: "",
        tamanhoArquivoLaudo: "",
        inserirLaudo: "",
        nenhumLaudoSelecionado: "",
        metodologia: ""
    }

    constructor(private fb: FormBuilder
        , translate: TranslateService
        , private resultadoWorkupService: ResultadoWorkupService
        , private dominioService: DominioService
        , private autenticacaoService: AutenticacaoService
        , private centroTransplanteService: CentroTransplanteService
        , private messageBox: MessageBox
        , private router: Router
        , private tarefaService: TarefaService) {
        super();
        this.translate = translate;
    }

    ngOnInit(): void {
        this.usuario = this.autenticacaoService.usuarioLogado();
        // if (!this.usuario.centros || this.usuario.centros.length < 1) {
        //     this.resultadoForm.get("centro").setValidators(Validators.required);
        //     this.centroTransplanteService.listarCentroTransplantes(null, 0, this.qtdRegistroResultados).then(res => {
        //         this.centros = res.content;
        //     }, (error: ErroMensagem) => {
        //         ErroUtil.exibirMensagemErro(error, this.messageBox)
        //     });
        // }

        this.paginacaoTarefas = new Paginacao('', 0, this.qtdRegistroResultados);
        this.paginacaoTarefas.number = 1;

    }

    ngAfterViewInit() {
        setTimeout(() => {
            // this._centroSelecionado = this.selectCentros.value;
            this.listarTarefas(this.paginacaoTarefas.number);
        });
    }


    public exibirListaParaAnalistaDoReDome() {
        return this.usuario.centros && this.usuario.centros.length > 0;
    }


    public form(): FormGroup {
        return this.resultadoForm;
    }


    public preencherFormulario(entidade: ResultadoWorkup): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return "ResultadoWorkupComponent";
    }

    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarTarefas(pagina: number) {

        this.resultadoWorkupService.listarTarefas(this._centroSelecionado.id, pagina - 1, this.qtdRegistroResultados).then(res => {
            let tarefas: TarefaBase[] = [];
            if (res.content) {
                res.content.forEach(tarefa => {
                    tarefas.push(new TarefaBase().jsonToEntity(tarefa));
                });
            }
            this.paginacaoTarefas.content = tarefas;
            this.paginacaoTarefas.totalElements = res.totalElements;
            this.paginacaoTarefas.quantidadeRegistro = this.qtdRegistroResultados;
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });


    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }

    mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarTarefas(1);
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


     /**
     * Método para ir para tela de avaliação.
     * @param  {ResultadoWorkup} resultadoWorkup
     */
    public carregarResultadoWorkup(tarefa: TarefaBase) {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl("/resultadoworkup/cadastro?idResultadoWorkup=" + tarefa.objetoRelacaoEntidade.id + "&idTarefa=" + tarefa.id);
        }).catch((error:ErroMensagem) => {
            console.log(error);
            ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
                this.listarTarefas(1);
            });
        });

    }
}
