import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de UF 
 * pode ser utilizada por qualquer classe.
 * @author Fillipe Queiroz
 */
export class UF extends BaseEntidade {

	private _sigla: string;
	private _nome: string;

    /**
   * Método construtor.
   * @param object Objeto do tipo UF
   * @author Fillipe Queiroz
   */
	constructor(sigla?: string, nome?: string) {
		super();
		this._sigla = sigla;
		this._nome = nome;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf UF
	 */
	public get sigla(): string {
		return this._sigla;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf UF
	 */
	public set sigla(value: string) {
		this._sigla = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf UF
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf UF
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	public jsonToEntity(res:any):this{

		this.sigla = ConvertUtil.parseJsonParaAtributos(res.sigla, new String());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		return this;
	}

}