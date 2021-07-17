import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

/**
 * DTO que representa uma tarefa de logística (workup, doador ou material).
 * @author ergomes
 */
export class TarefaLogisticaMaterialDTO extends BaseEntidade {

    private _idPedidoLogistica: number;
	  private _tipoTarefaLogisticaMaterial: number;
    private _tipoLogistica: number;
    private _rmr: number;
    private _identificacaoDoador: string;
    private _nomeDoador: string;
    private _nomeResponsavel: string;
    private _dataLimite: Date;
    private _dataDistribuicao: Date;
    private _dataColeta: Date;
    private _tipoAereo: boolean;


    /**
     * Getter tipoTarefaLogisticaMaterial
     * @return {number}
     */
	public get tipoTarefaLogisticaMaterial(): number {
		return this._tipoTarefaLogisticaMaterial;
	}

    /**
     * Getter tipoLogistica
     * @return {number}
     */
	public get tipoLogistica(): number {
		return this._tipoLogistica;
	}

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Getter identificacaoDoador
     * @return {string}
     */
	public get identificacaoDoador(): string {
		return this._identificacaoDoador;
	}

    /**
     * Getter nomeDoador
     * @return {string}
     */
	public get nomeDoador(): string {
		return this._nomeDoador;
	}

    /**
     * Getter nomeResponsavel
     * @return {string}
     */
	public get nomeResponsavel(): string {
		return this._nomeResponsavel;
	}

    /**
     * Getter dataLimite
     * @return {Date}
     */
	public get dataLimite(): Date {
		return this._dataLimite;
	}

    /**
     * Getter dataDistribuicao
     * @return {Date}
     */
	public get dataDistribuicao(): Date {
		return this._dataDistribuicao;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Setter tipoTarefaLogisticaMaterial
     * @param {number} value
     */
	public set tipoTarefaLogisticaMaterial(value: number) {
		this._tipoTarefaLogisticaMaterial = value;
	}

    /**
     * Setter tipoLogistica
     * @param {number} value
     */
	public set tipoLogistica(value: number) {
		this._tipoLogistica = value;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Setter identificacaoDoador
     * @param {string} value
     */
	public set identificacaoDoador(value: string) {
		this._identificacaoDoador = value;
	}

    /**
     * Setter nomeDoador
     * @param {string} value
     */
	public set nomeDoador(value: string) {
		this._nomeDoador = value;
	}

    /**
     * Setter nomeResponsavel
     * @param {string} value
     */
	public set nomeResponsavel(value: string) {
		this._nomeResponsavel = value;
	}

    /**
     * Setter dataLimite
     * @param {Date} value
     */
	public set dataLimite(value: Date) {
		this._dataLimite = value;
	}

    /**
     * Setter dataDistribuicao
     * @param {Date} value
     */
	public set dataDistribuicao(value: Date) {
		this._dataDistribuicao = value;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}


    /**
     * Getter idPedidoLogistica
     * @return {number}
     */
	public get idPedidoLogistica(): number {
		return this._idPedidoLogistica;
	}

    /**
     * Setter idPedidoLogistica
     * @param {number} value
     */
	public set idPedidoLogistica(value: number) {
		this._idPedidoLogistica = value;
	}


    /**
     * Getter tipoAereo
     * @return {boolean}
     */
	public get tipoAereo(): boolean {
		return this._tipoAereo;
	}

    /**
     * Setter tipoAereo
     * @param {boolean} value
     */
	public set tipoAereo(value: boolean) {
		this._tipoAereo = value;
	}


  public jsonToEntity(res: any) : TarefaLogisticaMaterialDTO {

    this.idPedidoLogistica = ConvertUtil.parseJsonParaAtributos(res.idPedidoLogistica, new Number());
    this.tipoTarefaLogisticaMaterial = ConvertUtil.parseJsonParaAtributos(res.tipoTarefaLogisticaMaterial, new Number());
    this.tipoLogistica = ConvertUtil.parseJsonParaAtributos(res.tipoLogistica, new Number());
    this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
    this.identificacaoDoador = ConvertUtil.parseJsonParaAtributos(res.identificacaoDoador, new String());
    this.nomeDoador = ConvertUtil.parseJsonParaAtributos(res.nomeDoador, new String());
    this.nomeResponsavel = ConvertUtil.parseJsonParaAtributos(res.nomeResponsavel, new String());

    this.dataLimite = ConvertUtil.parseJsonParaAtributos(res.dataLimite, new Date());
    this.dataDistribuicao = ConvertUtil.parseJsonParaAtributos(res.dataDistribuicao, new Date());
    this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());
    this.tipoAereo = ConvertUtil.parseJsonParaAtributos(res.tipoAereo, new Boolean());

    this.dataLimite = this.dataColeta;

    return this;
  }


}
