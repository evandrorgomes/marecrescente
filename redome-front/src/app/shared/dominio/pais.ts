import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de País 
 * pode ser utilizada por qualquer classe.
 * @author Fillipe Queiroz
 */
export class Pais extends BaseEntidade {

	public static BRASIL: number = 1;
    
    private _id: number;
    private _nome: string;
  /**
   * Método construtor.
   * @param object Objeto do tipo País
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
	 * @memberOf Pais
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Pais
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Pais
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Pais
	 */
	public set nome(value: string) {
		this._nome = value;
	}    

	public jsonToEntity(res:any):this{

		this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome  = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		
		return this;
	}
}