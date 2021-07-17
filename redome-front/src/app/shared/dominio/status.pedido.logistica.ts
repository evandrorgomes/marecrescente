import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe que representa um status de pedido de logistica
 *
 * @author Bruno Sousa
 * @export
 * @class TipoPendencia
 * @extends {BaseEntidade}
 */
export class StatusPedidoLogistica  extends BaseEntidade {

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

	public jsonToEntity(res: any): StatusPedidoLogistica {

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

    return this;
  }

}
