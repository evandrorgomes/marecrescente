import {BaseEntidade} from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe que representa um status de pedido de coleta
 * 
 * @author Bruno Sousa
 * @export
 * @class StatusPedidoColeta
 * @extends {BaseEntidade}
 */
export class StatusPedidoColeta  extends BaseEntidade {

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
	 * @memberOf StatusPedidoColeta
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPedidoColeta
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf StatusPedidoColeta
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPedidoColeta
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}
	public jsonToEntity(res: any): this {

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

        return this;
    }

}