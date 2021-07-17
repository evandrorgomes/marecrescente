import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos do Estado Civil. 
 * pode ser utilizada por qualquer classe.
 * @author ergomes
 */
export class EstadoCivil extends BaseEntidade {

	private _id: number;
	private _nome: string;

	constructor(id?: number, nome?: string) {
		super();
		this._id = id;
		this._nome = nome;
	}


    /**
   * Método construtor.
   * @param object Objeto do tipo estado civil
   * @author ergomes
   */
    /*constructor(value: Object = {}){
         Object.assign(this, value);
    }*/


	/**
	 * 
	 * 
	 * @readonly
	 * @type {number}
	 * @memberOf EstadoCivil
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * this
	 * 
	 * @memberOf EstadoCivil
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf EstadoCivil
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf EstadoCivil
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	public jsonToEntity(res:any): EstadoCivil{
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new Number());
		
		return this;
	}

}