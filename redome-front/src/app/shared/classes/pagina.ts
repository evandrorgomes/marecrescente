import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { Pergunta } from './pergunta';
import { Secao } from './secao';

export class Pagina extends BaseEntidade {

	private _identificador: number = 0;
	private _titulo: string;
	private _secoes: Secao[];
	private _comErro: boolean;
	private _qtdPerguntaComErro: number = 0;


    /**
     * Getter titulo
     * @return {string}
     */
	public get titulo(): string {
		return this._titulo;
	}

    /**
     * Setter titulo
     * @param {string} value
     */
	public set titulo(value: string) {
		this._titulo = value;
	}


    /**
     * Getter secoes
     * @return {Secao[]}
     */
	public get secoes(): Secao[] {
		return this._secoes;
	}

    /**
     * Setter secoes
     * @param {Secao[]} value
     */
	public set secoes(value: Secao[]) {
		this._secoes = value;
	}


    /**
     * Getter comErro
     * @return {boolean}
     */
	public get comErro(): boolean {
		return this._comErro;
	}

    /**
     * Setter comErro
     * @param {boolean} value
     */
	public set comErro(value: boolean) {
		this._comErro = value;
	}


    /**
     * Getter qtdPerguntaComErro
     * @return {number}
     */
	public get qtdPerguntaComErro(): number {
		return this._qtdPerguntaComErro;
	}

    /**
     * Setter qtdPerguntaComErro
     * @param {number} value
     */
	public set qtdPerguntaComErro(value: number) {
		this._qtdPerguntaComErro = value;
	}

    /**
     * Getter identificador
     * @return {number}
     */
	public get identificador(): number {
		return this._identificador;
	}

    /**
     * Setter identificador
     * @param {number} value
     */
	public set identificador(value: number) {
		this._identificador = value;
	}


	public jsonToEntity(res:any): Pagina {

		if (res.secoes) {
			this.secoes = [];
			res.secoes.forEach(secao => {
				this.secoes.push(new Secao().jsonToEntity(secao));
			});
		}

		this.identificador = ConvertUtil.parseJsonParaAtributos(res.identificador, new Number());
		this.titulo = ConvertUtil.parseJsonParaAtributos(res.titulo, new String());
		this.comErro = ConvertUtil.parseJsonParaAtributos(res.comErro, new Boolean());
		
		return this;
	}
    
}