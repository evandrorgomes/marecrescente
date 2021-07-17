import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de raça 
 * pode ser utilizada por qualquer classe.
 * @author Fillipe Queiroz
 */
export class Raca extends BaseEntidade {

	private _id: number;
	private _nome: string;

	constructor(id?: number, nome?: string) {
		super();
		this._id = id;
		this._nome = nome;
	}


    /**
   * Método construtor.
   * @param object Objeto do tipor raça
   * @author Fillipe Queiroz
   */
    /*constructor(value: Object = {}){
         Object.assign(this, value);
    }*/


	/**
	 * 
	 * 
	 * @readonly
	 * @type {number}
	 * @memberOf Raca
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * this
	 * 
	 * @memberOf Raca
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Raca
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Raca
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	public jsonToEntity(res:any): Raca{
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new Number());
		
		return this;
	}

}