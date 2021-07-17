import {BaseEntidade} from '../base.entidade';

/**
 * Classe que representa um status da pendencia
 * 
 * @author Bruno Sousa
 * @export
 * @class StatusPendencia
 * @extends {BaseEntidade}
 */
export class StatusPendencia  extends BaseEntidade {

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
	 * @memberOf StatusPendencia
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPendencia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf StatusPendencia
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPendencia
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}
    

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
}