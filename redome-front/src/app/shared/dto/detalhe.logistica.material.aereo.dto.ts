import { BaseEntidade } from "app/shared/base.entidade";
import { Courier } from "app/transportadora/courier";
import { ConvertUtil } from "../util/convert.util";


/**
 * Classe que representa o detalhe do material que consiste em:
 * Informações de coleta, previsão de retirada e a transportadora responsável.
 *
 * @author Pizão
 *
 */
export class DetalheLogisticaMaterialAereoDTO extends BaseEntidade {

	private _idPedidoTransporte:number;
  private _idTransportadora: number;
  private _courier:Courier;
	private _dadosVoo:string;
  private _horaPrevistaRetirada: Date;


    /**
     * Getter idPedidoTransporte
     * @return {number}
     */
	public get idPedidoTransporte(): number {
		return this._idPedidoTransporte;
	}

    /**
     * Setter idPedidoTransporte
     * @param {number} value
     */
	public set idPedidoTransporte(value: number) {
		this._idPedidoTransporte = value;
	}


    /**
     * Getter idTransportadora
     * @return {number}
     */
	public get idTransportadora(): number {
		return this._idTransportadora;
	}

    /**
     * Setter idTransportadora
     * @param {number} value
     */
	public set idTransportadora(value: number) {
		this._idTransportadora = value;
	}


    /**
     * Getter courier
     * @return {Courier}
     */
	public get courier(): Courier {
		return this._courier;
	}

    /**
     * Setter courier
     * @param {Courier} value
     */
	public set courier(value: Courier) {
		this._courier = value;
	}


    /**
     * Getter dadosVoo
     * @return {string}
     */
	public get dadosVoo(): string {
		return this._dadosVoo;
	}

    /**
     * Setter dadosVoo
     * @param {string} value
     */
	public set dadosVoo(value: string) {
		this._dadosVoo = value;
	}


    /**
     * Getter horaPrevistaRetirada
     * @return {Date}
     */
	public get horaPrevistaRetirada(): Date {
		return this._horaPrevistaRetirada;
	}

    /**
     * Setter horaPrevistaRetirada
     * @param {Date} value
     */
	public set horaPrevistaRetirada(value: Date) {
		this._horaPrevistaRetirada = value;
	}

	public jsonToEntity(res: any) : DetalheLogisticaMaterialAereoDTO {

    if(res.courier){
      this.courier = new Courier().jsonToEntity(res.courier);
    }

    this.idPedidoTransporte = ConvertUtil.parseJsonParaAtributos(res.idPedidoTransporte, new Number());
    this.idTransportadora = ConvertUtil.parseJsonParaAtributos(res.idTransportadora, new Number());
    this.dadosVoo = ConvertUtil.parseJsonParaAtributos(res.dadosVoo, new String());
    this.horaPrevistaRetirada = ConvertUtil.parseJsonParaAtributos(res.horaPrevistaRetirada, new Date());

    return this;
	}
}
