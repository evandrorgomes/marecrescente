import { StatusPedidoTransporte } from './status.pedido.transporte';
import { UsuarioLogado } from "../../shared/dominio/usuario.logado";
import { BaseEntidade } from "../../shared/base.entidade";
import { Transportadora } from "./transportadora";
import { PedidoLogistica } from 'app/shared/model/pedido.logistica';


export class PedidoTransporte extends BaseEntidade {

	private _id: number;
	private _dataAtualizacao: Date;
	private _horaPrevistaRetirada: Date;
	private _usuarioResponsavel: UsuarioLogado;
	private _transportadora:Transportadora;
    private _pedidoLogistica:PedidoLogistica;
    private _dataRetirada: Date;
    private _dataEntrega: Date;
    private _status: StatusPedidoTransporte;


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter dataAtualizacao
     * @return {Date}
     */
	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

    /**
     * Setter dataAtualizacao
     * @param {Date} value
     */
	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
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

    /**
     * Getter usuarioResponsavel
     * @return {UsuarioLogado}
     */
	public get usuarioResponsavel(): UsuarioLogado {
		return this._usuarioResponsavel;
	}

    /**
     * Setter usuarioResponsavel
     * @param {UsuarioLogado} value
     */
	public set usuarioResponsavel(value: UsuarioLogado) {
		this._usuarioResponsavel = value;
	}

    /**
     * Getter transportadora
     * @return {Transportadora}
     */
	public get transportadora(): Transportadora {
		return this._transportadora;
	}

    /**
     * Setter transportadora
     * @param {Transportadora} value
     */
	public set transportadora(value: Transportadora) {
		this._transportadora = value;
	}

    /**
     * Getter pedidoLogistica
     * @return {PedidoLogistica}
     */
	public get pedidoLogistica(): PedidoLogistica {
		return this._pedidoLogistica;
	}

    /**
     * Setter pedidoLogistica
     * @param {PedidoLogistica} value
     */
	public set pedidoLogistica(value: PedidoLogistica) {
		this._pedidoLogistica = value;
    }

    /**
     * Getter dataRetirada
     * @return {Date}
     */
	public get dataRetirada(): Date {
		return this._dataRetirada;
	}

    /**
     * Setter dataRetirada
     * @param {Date} value
     */
	public set dataRetirada(value: Date) {
		this._dataRetirada = value;
	}

    /**
     * Getter dataEntrega
     * @return {Date}
     */
	public get dataEntrega(): Date {
		return this._dataEntrega;
	}

    /**
     * Setter dataEntrega
     * @param {Date} value
     */
	public set dataEntrega(value: Date) {
		this._dataEntrega = value;
	}


    /**
     * Getter status
     * @return {StatusPedidoTransporte}
     */
	public get status(): StatusPedidoTransporte {
		return this._status;
	}

    /**
     * Setter status
     * @param {StatusPedidoTransporte} value
     */
	public set status(value: StatusPedidoTransporte) {
		this._status = value;
    }

    public jsonToEntity(res:any):this{

		return this;
	}

}
