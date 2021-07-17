import { BaseEntidade } from '../../../shared/base.entidade';
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { FonteCelula } from '../../../shared/dominio/fonte.celula';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { Disponibilidade } from './disponibilidade';
import { StatusPedidoWorkup } from './status.pedido.workup';
import { ConvertUtil } from '../../../shared/util/convert.util';

export class PedidoWorkup extends BaseEntidade {
	private _id: number;
	private _dataPrevistaLiberacaoDoador: Date;
	private _dataPrevistaDisponibilidadeDoador: Date;
	private _dataColeta: Date;
	private _dataUltimoStatus: Date;
	private _informacaoCorrente: number;
	private _tipoUtilizado: number;
	private _solicitacao: Solicitacao;
	private _statusPedidoWorkup: StatusPedidoWorkup;
	private _ultimaDisponibilidade: Disponibilidade;
	private _centroColeta: CentroTransplante;
	private _dataLimiteWorkup:Date;
	private _dataCriacao: Date;

	private _dataInicioWorkup: Date;
	private _dataFinalWorkup: Date;
	private _fonteCelula: FonteCelula;

	private _isDoadorInternacional: boolean;

	/**
	 * id do pedido de workup
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * id do pedido de workup
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}

	public get dataPrevistaLiberacaoDoador(): Date {
		return this._dataPrevistaLiberacaoDoador;
	}

	public set dataPrevistaLiberacaoDoador(value: Date) {
		this._dataPrevistaLiberacaoDoador = value;
	}

	public get dataPrevistaDisponibilidadeDoador(): Date {
		return this._dataPrevistaDisponibilidadeDoador;
	}

	public set dataPrevistaDisponibilidadeDoador(value: Date) {
		this._dataPrevistaDisponibilidadeDoador = value;
	}

	/**
	 * Data de coleta do pedido de workup
	 * @returns Date
	 */
	public get dataColeta(): Date {
		return this._dataColeta;
	}
	/**
	 * Data de coleta do pedido de workup
	 * @param  {Date} value
	 */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}

	/**
	 * Data do resultado de pedido de workup
	 * @returns Date
	 */
	public get dataUltimoStatus(): Date {
		return this._dataUltimoStatus;
	}
	/**
	 * Data ultimo status do pedido de workup
	 * @param  {Date} value
	 */
	public set dataUltimoStatus(value: Date) {
		this._dataUltimoStatus = value;
	}
	/**
	 * Informação corrente, numero do agrupador das possíveis datas sugeridas pelo
	 * centro de transplante na tentativa atual.
	 * @returns number
	 */
	public get informacaoCorrente(): number {
		return this._informacaoCorrente;
	}
	/**
	 * Informação corrente, numero do agrupador das possíveis datas sugeridas pelo
	 * centro de transplante na tentativa atual.
	 * @param  {number} value
	 */
	public set informacaoCorrente(value: number) {
		this._informacaoCorrente = value;
	}
	/**
	 * Tipo de pedido de workup utilizado
	 * @returns number
	 */
	public get tipoUtilizado(): number {
		return this._tipoUtilizado;
	}
	/**
	 * Tipo de pedido de workup utilizado
	 * @param  {number} value
	 */
	public set tipoUtilizado(value: number) {
		this._tipoUtilizado = value;
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
	 * @returns StatusPedidoWorkup
	 */
	public get statusPedidoWorkup(): StatusPedidoWorkup {
		return this._statusPedidoWorkup;
	}
	/**
	 * @param  {StatusPedidoWorkup} value
	 */
	public set statusPedidoWorkup(value: StatusPedidoWorkup) {
		this._statusPedidoWorkup = value;
	}

	public get ultimaDisponibilidade(): Disponibilidade {
		return this._ultimaDisponibilidade;
	}

	public set ultimaDisponibilidade(value: Disponibilidade) {
		this._ultimaDisponibilidade = value;
	}

	public get centroColeta(): CentroTransplante {
		return this._centroColeta;
	}

	public set centroColeta(value: CentroTransplante) {
		this._centroColeta = value;
	}

	public get dataLimiteWorkup(): Date {
		return this._dataLimiteWorkup;
	}

	public set dataLimiteWorkup(value: Date) {
		this._dataLimiteWorkup = value;
	}


	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get dataInicioWorkup(): Date {
		return this._dataInicioWorkup;
	}

	public set dataInicioWorkup(value: Date) {
		this._dataInicioWorkup = value;
	}

	public get dataFinalWorkup(): Date {
		return this._dataFinalWorkup;
	}

	public set dataFinalWorkup(value: Date) {
		this._dataFinalWorkup = value;
	}

    /**
     * Getter fonteCelula
     * @return {FonteCelula}
     */
	public get fonteCelula(): FonteCelula {
		return this._fonteCelula;
	}

    /**
     * Setter fonteCelula
     * @param {FonteCelula} value
     */
	public set fonteCelula(value: FonteCelula) {
		this._fonteCelula = value;
	}

    /**
     * Retorna TRUE, se doador associado ao pedido de workup é internacional.
     * @return {boolean}
     */
	public get isDoadorInternacional(): boolean {
		return this._isDoadorInternacional;
	}

    /**
     * Seta o valor indicando se é internacional ou não.
	 * Valor recuperado do back-end somente.
     * @param {boolean} value
     */
	public set isDoadorInternacional(value: boolean) {
		this._isDoadorInternacional = value;
	}

	public jsonToEntity(res:any): PedidoWorkup {

		if (res.fonteCelula) {
			this.fonteCelula = new FonteCelula().jsonToEntity(res.fonteCelula);
		}

		if (res.centroColeta) {
			this.centroColeta = new CentroTransplante().jsonToEntity(res.centroColeta);
		}

		if (res.statusPedidoWorkup) {
			this.statusPedidoWorkup =  new StatusPedidoWorkup().jsonToEntity(res.statusPedidoWorkup);
		}

		if (res.ultimaDisponibilidade) {
			this.ultimaDisponibilidade = new Disponibilidade().jsonToEntity(res.ultimaDisponibilidade);
		}

		if (res.solicitacao) {
			this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataPrevistaLiberacaoDoador = ConvertUtil.parseJsonParaAtributos(res.dataPrevistaLiberacaoDoador, new Date());
		this.dataPrevistaDisponibilidadeDoador = ConvertUtil.parseJsonParaAtributos(res.dataPrevistaDisponibilidadeDoador, new Date());
		this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());
		this.dataUltimoStatus = ConvertUtil.parseJsonParaAtributos(res.dataUltimoStatus, new Date());
		this.informacaoCorrente = ConvertUtil.parseJsonParaAtributos(res.informacaoCorrente, new Number());
		this.tipoUtilizado = ConvertUtil.parseJsonParaAtributos(res.tipoUtilizado, new Number());
		this.dataLimiteWorkup = ConvertUtil.parseJsonParaAtributos(res.dataLimiteWorkup, new Date());
		this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataInicioWorkup = ConvertUtil.parseJsonParaAtributos(res.dataInicioWorkup, new Date());
		this.dataFinalWorkup = ConvertUtil.parseJsonParaAtributos(res.dataFinalWorkup, new Date());
		this.isDoadorInternacional  = ConvertUtil.parseJsonParaAtributos(res.isDoadorInternacional, new Boolean());

		return this;
	}
}
