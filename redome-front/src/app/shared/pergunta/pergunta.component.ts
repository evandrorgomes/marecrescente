import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormatoNumerico } from '../classes/formato.numerico';
import { OpcaoPergunta } from '../classes/opcao.pergunta';
import { PerguntaDependente } from '../classes/pergunta.dependente';
import { TiposPergunta } from '../enums/tipo.pergunta';
import { TiposComparacao } from './../enums/tipos.comparacao';
import { TiposAlinhamento } from '../enums/tipos.alinhamento';

/**
 * Classe Component para padronizar as perguntas de um formulário
 * @author bruno.sousa
 */
@Component({
    moduleId: module.id,
    selector:'pergunta',
    templateUrl: './pergunta.component.html'
})
export class PerguntaComponente {

    private _marcador: string;
    private _id: string;
    private _tipoPergunta: TiposPergunta = TiposPergunta.TEXT;
    private _descricao: string = "";
    private _opcoes: OpcaoPergunta[];
    private _valorRespondido: string;
    private _tamanho: number = 0;
    private _comErro: boolean;
    private _alinhamento: string = TiposAlinhamento.VERTICAL;
    private _desabilita: string;
    _selecionados: any[];
    private _dependentes: PerguntaDependente[];
    public _data: Array<string | RegExp>;
 
    constructor() {
        this._data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/];
    }

    @Input() formatoNumerico: FormatoNumerico;

    @Input()
    public set marcador(value: string) {
        this._marcador = value;
    }

    public get marcador(): string  {
		return this._marcador;
	}

    @Input()
    set id(value: string) {
        this._id = value;
    }

    @Output()
    valorChange = new EventEmitter<string>();
    
    @Input()
    public set descricao(value: string) {
        if (!value) {
            this._descricao = "";
        }
        else {
            this._descricao = value;
        }
    }

	public get descricao(): string  {
		return this._descricao;
	}
    

    @Input()
    set tipo(value: string) {
        if (!value) {
            this._tipoPergunta = TiposPergunta.TEXT;
        }
        else if (value == 'TEXT') {
            this._tipoPergunta = TiposPergunta.TEXT;
        }
        else if (value == 'TEXTAREA') {
            this._tipoPergunta = TiposPergunta.TEXTAREA;
        }
        else if (value == 'SELECT') {
            this._tipoPergunta = TiposPergunta.SELECT;
        }
        else if (value == 'RADIOBUTTON') {
            this._tipoPergunta = TiposPergunta.RADIOBUTTON;
        }
        else if (value == 'CHECKBUTTON') {
            this._tipoPergunta = TiposPergunta.CHECKBUTTON;
        }
        else if (value == 'NUMERIC') {
            this._tipoPergunta = TiposPergunta.NUMERIC;
        }
        else if (value == 'DATE') {
            this._tipoPergunta = TiposPergunta.DATE;
        }
    }

    @Input()
    public set tamanho(value: number) {
        this._tamanho = value;
    }

	public get tamanho(): number  {
		return this._tamanho;
	}
    
    @Input()
    public set comErro(value: boolean) {
        this._comErro = value;
    }

	public get comErro(): boolean  {
		return this._comErro;
	}

    @Input()
    public set alinhamento(value: string) {
        if (value == TiposAlinhamento.HORIZONTAL) {
            this._alinhamento = TiposAlinhamento.HORIZONTAL;
        }
        else {
            this._alinhamento = TiposAlinhamento.VERTICAL;
        }
    }

	public get alinhamento(): string  {
		return this._alinhamento;
	}

    @Input()
    public set opcoes(value: OpcaoPergunta[]) {
        if (value) {            
            this._opcoes = value;
            this._selecionados = [];
            this._opcoes.forEach((obj, index) => {
                this._selecionados[index] = {
                    selected: false,
                    opcao: obj,
                    desabilitar: false
                }
            });            
        }
    }

	public get opcoes(): OpcaoPergunta[] {
		return this._opcoes;
	}

    @Input()
    public get valor(): string {
        return this._valorRespondido;
    }

    public set valor(value: string) {        
        this._valorRespondido = value;
        this.valorChange.emit(this._valorRespondido);
        if (this._tipoPergunta && this._tipoPergunta == TiposPergunta.CHECKBUTTON ) {
            this.verificaValorParaCheckBox();
        }
    }

    @Input()
    public set dependentes(value: PerguntaDependente[]) {
        this._dependentes = value;
    }

	public get dependentes(): PerguntaDependente[] {
        this._dependentes.forEach(element => {
            element.pergunta.comErro = element.comErro;
        });
        return this._dependentes;
    }
    
    @Input()
    public set desabilita(value: string) {
        if(value){
           this._desabilita = value;
        }
    }

	public get desabilita(): string  {
		return this._desabilita;
	}


    /* @Input() 
    public set numericOption(value: any) {
        this._numericOption = value;
    }

    public get numericOption(): any {
        return this._numericOption;
    }     */

    obterCSSPorErro(comErro: boolean): string {
        return comErro? "pergunta-obrigatrorio" : "";
    }

    obterMarcadorText(): string {
        return this._marcador ? this._marcador + " - " + this._descricao : this._descricao;
    }

    obterTipoText(): boolean {
        return this._tipoPergunta == TiposPergunta.TEXT;
    }

    obterTipoNumeric(): boolean {
        return this._tipoPergunta == TiposPergunta.NUMERIC;
    }

    obterTipoDate(): boolean {
        return this._tipoPergunta == TiposPergunta.DATE;
    }

    obterTipoTextArea(): boolean {
        return this._tipoPergunta == TiposPergunta.TEXTAREA;
    }

    obterTipoRadio(): boolean {
        return this._tipoPergunta == TiposPergunta.RADIOBUTTON;
    }

    obterTipoCheck(): boolean {
        return this._tipoPergunta == TiposPergunta.CHECKBUTTON;
    }
    
    obterTipoSelect(): boolean {
        return this._tipoPergunta == TiposPergunta.SELECT;
    }

    private obterMaxLength(): string {
        return this._tamanho + '';
    }

    private valorchange(event) {
    }

    obterTipoAlinhamentoVertical(): boolean {
        return this._alinhamento == TiposAlinhamento.VERTICAL;
    }

    obterTipoAlinhamentoHorizontal(): boolean {
        return this._alinhamento == TiposAlinhamento.HORIZONTAL;
    }

    public checkChange() {
        let val: string = "";
        let separador = "";
        this._selecionados
            .filter( sel => sel.selected == true)
            .forEach(sel => {
                val += separador + sel.opcao.valor;
                separador = ","
            });
        this.valor = val;
    }

    private verificaValorParaCheckBox() {
        if (this.valor && this._selecionados) {
            let valores: string[] = this.valor.split(",");
            let desabilatarOutras: boolean = false;
            valores.forEach(valor => {
                this._selecionados
                    .forEach(sel => {
                        if (sel.opcao.valor == valor) {
                            sel.selected = true;
                            desabilatarOutras = sel.opcao.invalidarOutras ? true : false;
                        }
                        else {
                            sel.desabilitar = desabilatarOutras;
                        }
                    });                    
            });
        }
    }

    obterIdPergunta(): string {
        if (!this._id) {
            const random = Math.floor(Math.random() * (9999999 - 1000000)) + 1000000;
            this._id = "perg_" + random;
        }
        return this._id;

    }

    public verificarResposta(valor: string, tipoComparacao: string): boolean {
        if (this._valorRespondido) {
            if (this._tipoPergunta === TiposPergunta.RADIOBUTTON) {
                return valor === this._valorRespondido;
            }
            if (this._tipoPergunta === TiposPergunta.CHECKBUTTON) {
                let valoresRespondidos: string[] = this._valorRespondido.split(",");
                let valores: string[] = valor.split(',');
                return valoresRespondidos.some(respostaIndividual =>  valores.some(valor =>  respostaIndividual.trim() == valor.trim()));
            }
            if (this._tipoPergunta === TiposPergunta.NUMERIC) {
                return this.validandoTipoComparacao(valor, tipoComparacao);
            }
        }
        return false;
    }

    public validandoTipoComparacao(valor: string, operador: string): boolean {

        let vl: number = parseInt(valor) ;
        let vlResp: number = parseInt(this._valorRespondido) ;

        if (TiposComparacao[operador] === TiposComparacao.IGUAL) {
            return vlResp === vl;
        } 
        else if (TiposComparacao[operador] === TiposComparacao.MAIOR) {
            return vlResp > vl;
        }
        else if (TiposComparacao[operador] === TiposComparacao.MENOR) {
            return vlResp < vl;
        }
        else if (TiposComparacao[operador] === TiposComparacao.DIFERENTE) {
            return vlResp != vl;
        }
        else if (TiposComparacao[operador] === TiposComparacao.MAIORIGUAL) {
            return vlResp >= vl;
        }
        else if (TiposComparacao[operador] === TiposComparacao.MENORIGUAL) {
            return vlResp <= vl;
        }
        return false;
    }

    public desabilitarOutros(opcao: OpcaoPergunta, selecionado: boolean){
        if(opcao.invalidarOutras){
            this._opcoes.forEach((obj, index) => {
                if(this._selecionados[index].opcao.valor != opcao.valor){
                    this._selecionados[index] = {
                        selected: false,
                        opcao: obj,
                        desabilitar: selecionado
                    }
                }
            });      
        }
    }

    public getOptions(): any {
        if (this.formatoNumerico) {
            return { 
                prefix: this.formatoNumerico.prefixo ? this.formatoNumerico.prefixo : '',
                suffix: this.formatoNumerico.sufixo ? this.formatoNumerico.sufixo : '',
                allowNegative : this.formatoNumerico.permitirNegativo, 
                thousands: this.formatoNumerico.separadorMilhares ? this.formatoNumerico.separadorMilhares : '',
                precision: this.formatoNumerico.casasDecimais != undefined ? this.formatoNumerico.casasDecimais: 2
            }
        }
        
        return  {};
 
    }

}