import { BaseEntidade } from "app/shared/base.entidade";
import { EnderecoContatoCentroTransplante } from "../model/endereco.contato.centro.transplante";

/**
 * Classe que representa o detalhe do material que consiste em:
 * Informações de coleta, previsão de retirada e a transportadora responsável.
 *
 * @author Pizão
 *
 */
export class DetalheLogisticaColetaInternacionalDTO extends BaseEntidade {
	private _rmr:number;
	private _idPedidoLogistica:number;
  private _idTipoDoador: number;
  private _idCentroTransplante: number;
	private _nomeCentroTransplante: string;
	private _enderecoCentroTransplante: EnderecoContatoCentroTransplante;
	private _contatosCentroTransplante: string[];

  private _retiradaLocal: string;
	private _retiradaIdDoador:string;
  private _retiradaHawb:string;
	private _nomeCourier: string;
  private _passaporteCourier: string;
  private _dataEmbarque: Date;
  private _dataChegada: Date;

  private _dataColeta: Date;

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Getter idPedidoLogistica
     * @return {number}
     */
	public get idPedidoLogistica(): number {
		return this._idPedidoLogistica;
	}

    /**
     * Getter idTipoDoador
     * @return {number}
     */
	public get idTipoDoador(): number {
		return this._idTipoDoador;
	}

    /**
     * Getter nomeCentroTransplante
     * @return {string}
     */
	public get nomeCentroTransplante(): string {
		return this._nomeCentroTransplante;
	}

    /**
     * Getter contatosCentroTransplante
     * @return {string[]}
     */
	public get contatosCentroTransplante(): string[] {
		return this._contatosCentroTransplante;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Setter idPedidoLogistica
     * @param {number} value
     */
	public set idPedidoLogistica(value: number) {
		this._idPedidoLogistica = value;
	}

    /**
     * Setter idTipoDoador
     * @param {number} value
     */
	public set idTipoDoador(value: number) {
		this._idTipoDoador = value;
	}

    /**
     * Setter nomeCentroTransplante
     * @param {string} value
     */
	public set nomeCentroTransplante(value: string) {
		this._nomeCentroTransplante = value;
	}

    /**
     * Setter contatosCentroTransplante
     * @param {string[]} value
     */
	public set contatosCentroTransplante(value: string[]) {
		this._contatosCentroTransplante = value;
	}

    /**
     * Getter retiradaLocal
     * @return {string}
     */
	public get retiradaLocal(): string {
		return this._retiradaLocal;
	}

    /**
     * Getter retiradaIdDoador
     * @return {string}
     */
	public get retiradaIdDoador(): string {
		return this._retiradaIdDoador;
	}

    /**
     * Getter retiradaHawb
     * @return {string}
     */
	public get retiradaHawb(): string {
		return this._retiradaHawb;
	}

    /**
     * Setter retiradaLocal
     * @param {string} value
     */
	public set retiradaLocal(value: string) {
		this._retiradaLocal = value;
	}

    /**
     * Setter retiradaIdDoador
     * @param {string} value
     */
	public set retiradaIdDoador(value: string) {
		this._retiradaIdDoador = value;
	}

    /**
     * Setter retiradaHawb
     * @param {string} value
     */
	public set retiradaHawb(value: string) {
		this._retiradaHawb = value;
	}

    /**
     * Getter idCentroTransplante
     * @return {number}
     */
	public get idCentroTransplante(): number {
		return this._idCentroTransplante;
	}

    /**
     * Setter idCentroTransplante
     * @param {number} value
     */
	public set idCentroTransplante(value: number) {
		this._idCentroTransplante = value;
	}


    /**
     * Getter enderecoCentroTransplante
     * @return {EnderecoContatoCentroTransplante}
     */
	public get enderecoCentroTransplante(): EnderecoContatoCentroTransplante {
		return this._enderecoCentroTransplante;
	}

    /**
     * Setter enderecoCentroTransplante
     * @param {EnderecoContatoCentroTransplante} value
     */
	public set enderecoCentroTransplante(value: EnderecoContatoCentroTransplante) {
		this._enderecoCentroTransplante = value;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}


    /**
     * Getter nomeCourier
     * @return {string}
     */
	public get nomeCourier(): string {
		return this._nomeCourier;
	}

    /**
     * Getter passaporteCourier
     * @return {string}
     */
	public get passaporteCourier(): string {
		return this._passaporteCourier;
	}

    /**
     * Getter dataEmbarque
     * @return {Date}
     */
	public get dataEmbarque(): Date {
		return this._dataEmbarque;
	}

    /**
     * Getter dataChegada
     * @return {Date}
     */
	public get dataChegada(): Date {
		return this._dataChegada;
	}

    /**
     * Setter nomeCourier
     * @param {string} value
     */
	public set nomeCourier(value: string) {
		this._nomeCourier = value;
	}

    /**
     * Setter passaporteCourier
     * @param {string} value
     */
	public set passaporteCourier(value: string) {
		this._passaporteCourier = value;
	}

    /**
     * Setter dataEmbarque
     * @param {Date} value
     */
	public set dataEmbarque(value: Date) {
		this._dataEmbarque = value;
	}

    /**
     * Setter dataChegada
     * @param {Date} value
     */
	public set dataChegada(value: Date) {
		this._dataChegada = value;
	}



	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}
}
