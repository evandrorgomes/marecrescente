import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from 'app/shared/base.entidade';
import { Solicitacao } from 'app/doador/solicitacao/solicitacao';

export class PedidoEnriquecimento extends BaseEntidade {
	private _id: number;
	private _dataCriacao: Date;
	private _dataEnriquecimento: Date;
	private _aberto: Boolean;
	private _solicitacao: Solicitacao;
	private _tipo: number;

	/**
	 * @returns Number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * @param  {Number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	/**
	 * @returns Date
	 */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}
	/**
	 * @returns Boolean
	 */
	public get aberto(): Boolean {
		return this._aberto;
	}
	/**
	 * @param  {Boolean} value
	 */
	public set aberto(value: Boolean) {
		this._aberto = value;
	}
	/**
	 * @returns Solicitacao
	 */
	public get solicitacao(): Solicitacao {
		return this._solicitacao;
	}
	/**
	 * @param  {Solicitacao} value
	 */
	public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	}

    /**
     * Getter dataEnriquecimento
     * @return {Date}
     */
	public get dataEnriquecimento(): Date {
		return this._dataEnriquecimento;
	}

    /**
     * Setter dataEnriquecimento
     * @param {Date} value
     */
	public set dataEnriquecimento(value: Date) {
		this._dataEnriquecimento = value;
	}

    /**
     * Getter tipo
     * @return {number}
     */
	public get tipo(): number {
		return this._tipo;
	}

    /**
     * Setter tipo
     * @param {number} value
     */
	public set tipo(value: number) {
		this._tipo = value;
	}

	public jsonToEntity(res:any): PedidoEnriquecimento {

		if (res.solicitacao) {
			this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
		}
		
		this.id = 				ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCriacao =		ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataEnriquecimento = ConvertUtil.parseJsonParaAtributos(res.dataEnriquecimento, new Date());
		this.aberto = 			ConvertUtil.parseJsonParaAtributos(res.aberto, new Boolean());
		this.tipo = 			ConvertUtil.parseJsonParaAtributos(res.tipo, new Number());
		

		return this;		
	}
}