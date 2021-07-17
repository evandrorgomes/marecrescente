import { CordaoNacional } from 'app/doador/cordao.nacional';
import { DoadorNacional } from 'app/doador/doador.nacional';
import { CordaoInternacional } from './../../cordao.internacional';
import { DoadorInternacional } from './../../doador.internacional';
import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { Router } from '@angular/router';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';
import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { TarefaService } from '../../../shared/tarefa.service';
import { ErroUtil } from '../../../shared/util/erro.util';
import { AvaliacaoResultadoWorkupService } from '../../cadastro/resultadoworkup/avaliacao/avaliacao.resultado.workup.service';
import { ResultadoWorkup } from '../../cadastro/resultadoworkup/resultado/resultado.workup';
import { Doador } from "app/doador/doador";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import {AutenticacaoService} from "../../../shared/autenticacao/autenticacao.service";


/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "avaliacoes-resultado-workup",
    moduleId: module.id,
    templateUrl: "./avaliacoes.resultado.workup.component.html"
})
export class AvaliacoesResultadoWorkupComponent implements OnInit, AfterViewInit {

    public paginacaoAvaliacoes: Paginacao;
    public qtdRegistroAvaliacoes: number = 10;

    public consultaForm: FormGroup;

    @ViewChild("modalErro")
    public modalErro;

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    constructor(private fb: FormBuilder, private avaliacaoResultadoWorkupService: AvaliacaoResultadoWorkupService,
        private tarefaService: TarefaService,
        private router: Router, private messageBox: MessageBox) {
        this.paginacaoAvaliacoes = new Paginacao('', 0, this.qtdRegistroAvaliacoes);
        this.paginacaoAvaliacoes.number = 1;
    }

    /**
     * Método de inicialização do componente.
     */
    ngOnInit() {
        // this.listarDoadores(this.paginacaoDoadores.number);
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarAvaliacoes(this.paginacaoAvaliacoes.number);
        });
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "AvaliacoesResultadoWorkupComponent";
    }

    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarAvaliacoes(pagina: number) {

        this.avaliacaoResultadoWorkupService.listarTarefas(this._centroSelecionado.id, pagina - 1, this.qtdRegistroAvaliacoes).then(res => {
            let tarefas: TarefaBase[] = [];
            if (res.content) {
                res.content.forEach(tarefa => {
                    tarefas.push(new TarefaBase().jsonToEntity(tarefa));
                });
            }
            this.paginacaoAvaliacoes.content = tarefas;
            this.paginacaoAvaliacoes.totalElements = res.totalElements;
            this.paginacaoAvaliacoes.quantidadeRegistro = this.qtdRegistroAvaliacoes;
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

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaAvaliacoes(event: any) {
        this.listarAvaliacoes(event.page);
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
        this.listarAvaliacoes(1);
    }
    /**
     * Método para ir para tela de avaliação.
     * @param  {ResultadoWorkup} resultadoWorkup
     */
    public irParaProximaEtapa(tarefa: TarefaBase) {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl("/doadores/resultadoWorkup/" + tarefa.objetoRelacaoEntidade.id + "/avaliacao?rmr=" + tarefa.objetoRelacaoEntidade.resultadoWorkup.pedidoWorkup.solicitacao.match.busca.paciente.rmr);
        }).catch((error:ErroMensagem) => {
            console.log(error);
            ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
                this.listarAvaliacoes(1);
            });
        });

    }

    mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarAvaliacoes(1);
    }

    public exibirIdentificacaoDoador(doador: Doador): string{
        if(doador instanceof DoadorInternacional || doador instanceof CordaoInternacional){
            return doador.idRegistro;
        }else if(doador instanceof DoadorNacional){
            return doador.dmr.toString();
        }
        return null;
    }
}
