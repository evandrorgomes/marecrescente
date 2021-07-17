import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de etnia
 * pode ser utilizada por qualquer classe.
 * @author Fillipe Queiroz
 */
export class Etnia extends BaseEntidade {
    
    private _id: number;
    private _nome: string;

  /**
   * Método construtor.
   * @param object Objeto do tipo etnia
   * @author Fillipe Queiroz
   */
	constructor(id?: number, nome?: string) {
		super();
		this._id = id;
		this._nome = nome;
	}

	/**
     * 
     * 
     * @type {number}
     * @memberOf Cid
     */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Etnia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Etnia
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Etnia
	 */
	public set nome(value: string) {
		this._nome = value;
	}
 
	public jsonToEntity(res:any): Etnia{
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new Number());
		
		return this;
	}
    
}