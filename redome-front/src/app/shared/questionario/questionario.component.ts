import { Component, Input } from '@angular/core';
import { Formulario } from '../classes/formulario';
import { Pagina } from '../classes/pagina';
import { Pergunta } from '../classes/pergunta';
import { PerguntaDependente } from '../classes/pergunta.dependente';
import { Secao } from '../classes/secao';
import { PerguntaComponente } from '../pergunta/pergunta.component';

/**
 * Classe Component para padronizar o modal
 * @author Fillipe Queiroz
 */
@Component({
    moduleId: module.id,
    selector:'questionario',
    templateUrl: './questionario.component.html'
})
export class QuestionarioComponent {

    private _perguntaAtual: number = 0;
    private _formulario: Formulario;

    private _mensagemPerguntaObrigatoria: string;
    private _pagina: number;
    private _totalElements: number = 0;
    private _quantidadeRegistro: number = 1;

    private _tituloPagina: string = "";
    private _secoes: Secao[] = [];
    private _perguntas: Pergunta[] = [];
    private _paginasComErro: Pagina[] = [];

    private _desabilita: string;

    constructor() {
      
    }

    @Input()
    public set desabilita(value: string) {
        this._desabilita = value;
    }

    public get desabilita(): string  {
      return this._desabilita;
    }

    @Input()
    get formulario(): Formulario {
        return this._formulario;
    }

    set formulario(value: Formulario) {
        this._formulario = value;

        if(this._formulario){

            this.popularPerguntasEValidacoes();
            this.listarPerguntasSemDependencia()
                .filter(pergunta => pergunta.dependentes.length > 0 )
                .forEach(pergunta => {
                    this.popularPerguntasDependentes(pergunta.dependentes);
                });
                this.buscarPaginas(1);
        }
    }

    private buscarPaginas(pagina: number) {
        this._secoes = this._formulario.paginas[pagina - 1].secoes;
        this._secoes.forEach(secao => {
            secao.perguntas = this.listarPerguntasSemDependenciaPorPagina(secao.perguntas);
        });
        this._paginasComErro = this._formulario.paginas.filter(pagina => pagina.comErro);
        this._tituloPagina = this._formulario.paginas[pagina - 1].titulo;
        this._totalElements = this._formulario.paginas.length;
        this._pagina = pagina;
    }

    private popularPerguntasEValidacoes(){
        this._formulario.paginas.forEach((pagina,index) =>{
            pagina.identificador = index;
            pagina.secoes.forEach(secao => {
                secao.perguntas.forEach(pergunta =>{
                    this._perguntas.push(pergunta);
                    if(pergunta.comErro){
                        pagina.comErro = true;
                        pagina.qtdPerguntaComErro += 1;
                    }
                });
            });
        });
    }

    private popularPerguntasDependentes(dependentes: PerguntaDependente[])  {
        if (dependentes && dependentes.length > 0) {
            dependentes.forEach(perguntaDependente => {
                let pergunta: Pergunta = this.obterPergunta(perguntaDependente.idPergunta);
                if(pergunta && pergunta.dependentes){
                    this.popularPerguntasDependentes(pergunta.dependentes);
                }
                perguntaDependente.pergunta = pergunta;
            })
        }
    }

    mudarPagina(event: any) {
        this.buscarPaginas(event.page);
    }

    listarPerguntasSemDependenciaPorPagina(perguntas: Pergunta[]): Pergunta[] {
        return perguntas.filter(p => !p.possuiDependencia);
    }

    listarPerguntasSemDependencia(): Pergunta[] {
        return this._perguntas.filter(p => !p.possuiDependencia);
    }

    listarDeErrosPorPagina(): Pagina[] {
        return this._paginasComErro;
    }

    temPaginasComErro(): boolean {
        return this._paginasComErro.length > 0;
    }

    exibirNaPagina(): boolean{
        return this._formulario.paginas.length > 1;
    }

    obterListaDadosValidacao(idPagina: number) {
        this._pagina = idPagina;
    }

    private obterPergunta(id: string): Pergunta {
        return this._perguntas.find(pergunta => pergunta.id == id);
    }

    public proximaPerguntaSemDependencia(): boolean {
      this.limparMensagemPerguntaObrigatoria();
      let proxima = this._perguntaAtual+1;
      let perguntas: Pergunta[] = this.listarPerguntasSemDependencia();
      if (proxima <= perguntas.length-1) {
          this._perguntaAtual = proxima;
      }
      return true;
    }

    get mensagemPerguntaObrigatoria(): string {
        return this._mensagemPerguntaObrigatoria;
    }

    private limparMensagemPerguntaObrigatoria() {
        this._mensagemPerguntaObrigatoria = "";
    }

    public verificarTodasPreenchidas(): number {
        if(this._formulario){
            let perguntasPai: Pergunta[] = this.listarPerguntasSemDependencia();
            for(let i: number = 0; i < perguntasPai.length; i++){
                if(!this.validarPergunta(perguntasPai[i])){
                    return i;
                }
            }
        }
        return null;
    }

    private validarPergunta(pergunta: Pergunta): boolean {
        let valid: boolean = false;
        if (pergunta.resposta == null || pergunta.resposta == undefined ) {
            this._mensagemPerguntaObrigatoria = "Pergunta Obrigatória";
        }
        else  {
            valid = true;
            if (pergunta.dependentes) {
                let validDependentes: boolean = true;
                pergunta.dependentes
                    .filter(pd =>{
                        if (pergunta.tipo == "RADIOBUTTON") {
                            return pd.valor == pergunta.resposta
                        }
                        else if (pergunta.tipo == "CHECKBUTTON") {
                            let respostas: string[] = pergunta.resposta.split(",");
                            return respostas.some(resposta => pd.valor == resposta);
                        }
                    })
                    .forEach(pd => {
                        let validDependente: boolean = this.validarPergunta(pd.pergunta);
                        if (!validDependente) {
                            validDependentes = false;
                        }
                    });
                if (!validDependentes) {
                    this._mensagemPerguntaObrigatoria = "Pergunta Obrigatória";
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.buscarPaginas(1);
    }

    /**
     * Getter totalElements
     * @return {number }
     */
    public get totalElements(): number  {
      return this._totalElements;
    }

    /**
     * Getter pagina
     * @return {number }
     */
    public get pagina(): number  {
      return this._pagina;
    }

    /**
     * Getter secoes
     * @return {Secao[] }
     */
    public get secoes(): Secao[]  {
      return this._secoes;
    }

    /**
     * Getter perguntas
     * @return {Pergunta[] }
     */
    public get perguntas(): Pergunta[]  {
      return this._perguntas;
    }

    /**
     * Getter quantidadeRegistro
     * @return {number }
     */
    public get quantidadeRegistro(): number  {
      return this._quantidadeRegistro;
    }

    /**
     * Getter tituloPagina
     * @return {string }
     */
    public get tituloPagina(): string  {
      return this._tituloPagina;
    }

    /**
     * Getter paginasComErro
     * @return {Pagina[] }
     */
    public get paginasComErro(): Pagina[]  {
      return this._paginasComErro;
    }



        // exibirAnterior(): boolean {
    //     return this._perguntaAtual > 0
    // }

    // exibirProximo(): boolean {
    //     return this._perguntaAtual < this.listarPerguntasSemDependencia().length - 1;
    // }

    // private round(value, precision): number {
    //     var multiplier = Math.pow(10, precision || 0);
    //     return Math.round(value * multiplier) / multiplier;
    // }
        // obterProgressoEmPercentual(): String {
    //     return this.round( (( (this._perguntaAtual + 1) / this.listarPerguntasSemDependencia().length ) * 100), 0) + "%" ;
    // }
}
