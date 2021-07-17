import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe que representa um status da invoice
 *
 * @author Bruno Sousa
 * @export
 * @class StatusInvoice
 * @extends {BaseEntidade}
 */
export class StatusInvoice extends BaseEntidade {

	public static readonly PAGA: number = 0;
	public static readonly ABERTA: number = 1;
	public static readonly CANCELADA: number = 2;

	private _id: number;
	private _nome: string;


	constructor(id: number = null) {
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
	public get nome(): string {
		return this._nome;
	}

	/**
	 *
	 *
	 *
	 * @memberOf StatusPendencia
	 */
	public set nome(value: string) {
		this._nome = value;
	}



	public jsonToEntity(res:any): StatusInvoice {

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

		return this;
	}

}
