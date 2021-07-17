import {BaseEntidade} from '../base.entidade';

/**
 * Classe que representa um tipo de pendencia
 * 
 * @author Bruno Sousa
 * @export
 * @class TipoPendencia
 * @extends {BaseEntidade}
 */
export class TipoPendencia  extends BaseEntidade {

	private _id: number;
    private _descricao: string;
	
	constructor(id:number = null){
		super();
		this._id = id;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf TipoPendencia
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf TipoPendencia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf TipoPendencia
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf TipoPendencia
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}
    

	public jsonToEntity(res:any):this{
		
		return this;
	}

}