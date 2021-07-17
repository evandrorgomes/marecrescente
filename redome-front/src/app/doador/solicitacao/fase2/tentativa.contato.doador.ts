import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from "../../../shared/base.entidade";
import { UsuarioLogado } from "../../../shared/dominio/usuario.logado";
import { ContatoTelefonicoDoador } from '../../contato.telefonico.doador';
import { PedidoContato } from './pedido.contato';

export class TentativaContatoDoador extends BaseEntidade {
	private _id: number;
	private _dataCriacao: Date;
	private _usuario: UsuarioLogado;
	private _pedidoContato: PedidoContato;
	private _horaInicioAgendamento: Date;
	private _horaFimAgendamento: Date;
	private _dataAgendamento: Date;
	private _contatoTelefonicoDoador: ContatoTelefonicoDoador;

	
	public get id(): number {
		return this._id;
	}
	
	public set id(value: number) {
		this._id = value;
	}
	
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}
	
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}
	
	public get usuario(): UsuarioLogado {
		return this._usuario;
	}
	
	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}
		
	public get pedidoContato(): PedidoContato {
		return this._pedidoContato;
	}
	
	public set pedidoContato(value: PedidoContato) {
		this._pedidoContato = value;
	}
	public get horaFimAgendamento(): Date {
		return this._horaFimAgendamento;
	}
	
	public set horaFimAgendamento(value: Date) {
		this._horaFimAgendamento = value;
	}
	
	public get dataAgendamento(): Date {
		return this._dataAgendamento;
	}
	
	public set dataAgendamento(value: Date) {
		this._dataAgendamento = value;
	}
	public get horaInicioAgendamento(): Date {
		return this._horaInicioAgendamento;
	}
	
	public set horaInicioAgendamento(value: Date) {
		this._horaInicioAgendamento = value;
	}

    /**
     * Getter contatoTelefonicoDoador
     * @return {ContatoTelefonicoDoador}
     */
	public get contatoTelefonicoDoador(): ContatoTelefonicoDoador {
		return this._contatoTelefonicoDoador;
	}

    /**
     * Setter contatoTelefonicoDoador
     * @param {ContatoTelefonicoDoador} value
     */
	public set contatoTelefonicoDoador(value: ContatoTelefonicoDoador) {
		this._contatoTelefonicoDoador = value;
	}
	
	public jsonToEntity(res:any): TentativaContatoDoador {

		if (res.usuario) {
			this.usuario = res.usuario;
		}

		if (res.pedidoContato) {
			this.pedidoContato = new PedidoContato().jsonToEntity(res.pedidoContato);
		}

		if (res.contatoTelefonicoDoador) {
			this.contatoTelefonicoDoador = new ContatoTelefonicoDoador().jsonToEntity(res.contatoTelefonicoDoador);
		}

		this.id = 						ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCriacao = 				ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Number());
		this.horaInicioAgendamento =	ConvertUtil.parseJsonParaAtributos(res.horaInicioAgendamento, new Number());
		this.horaFimAgendamento = 		ConvertUtil.parseJsonParaAtributos(res.horaFimAgendamento, new Number());
		this.dataAgendamento = 			ConvertUtil.parseJsonParaAtributos(res.dataAgendamento, new Number());
		
		return this;
	}
	
}