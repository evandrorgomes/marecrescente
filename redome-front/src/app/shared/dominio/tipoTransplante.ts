import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe utilizada para representar a entidade tipo transplante, destinada a fornecer 
 * qual foi o transplante prévio.
 *  
 * @author Bruno Sousa
 */
export class TipoTransplante extends BaseEntidade {

	private _id:Number;
	private _descricao: String;
	
	constructor(id?: number, descricao?: string) {
    	super();
    	this._id = id;
    	this._descricao = descricao;
  	}
	
	public get id(): Number {
		return this._id;
	}

	public set id(value: Number) {
		this._id = value;
	}

	public get descricao(): String {
		return this._descricao;
	}
    
	public set descricao(descricao: String) {
		this._descricao = descricao;
	}

	public jsonToEntity(res:any): TipoTransplante{

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		
		return this;
	}
}