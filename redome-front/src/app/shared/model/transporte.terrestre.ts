import { PedidoLogistica } from "./pedido.logistica";
import { BaseEntidade } from "../base.entidade";

/**
 * Classe que representa um item do transporte terrestre necessário a
 * logística do doador.
 *
 * @author Pizão
 */
export class TransporteTerrestre extends BaseEntidade{

    private _id: number;
	private _dataRealizacao: Date;
	private _origem: string;
	private _destino: string;
	private _objetoTransportado: string;
    private _dataCriacao: Date;
	private _excluido: boolean;
	private _pedidoLogistica: PedidoLogistica;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get dataRealizacao(): Date {
		return this._dataRealizacao;
	}

	public set dataRealizacao(value: Date) {
		this._dataRealizacao = value;
	}

	public get origem(): string {
		return this._origem;
	}

	public set origem(value: string) {
		this._origem = value;
	}

	public get destino(): string {
		return this._destino;
	}

	public set destino(value: string) {
		this._destino = value;
	}

	public get objetoTransportado(): string {
		return this._objetoTransportado;
	}

	public set objetoTransportado(value: string) {
		this._objetoTransportado = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get excluido(): boolean {
		return this._excluido;
	}

	public set excluido(value: boolean) {
		this._excluido = value;
	}

	public get pedidoLogistica(): PedidoLogistica {
		return this._pedidoLogistica;
	}

	public set pedidoLogistica(value: PedidoLogistica) {
		this._pedidoLogistica = value;
	}

	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}


}
