import { TiposDoador } from 'app/shared/enums/tipos.doador';
import { Prescricao } from '../consulta/workup/prescricao';
import { TipoSolicitacao } from './tipo.solicitacao';
import { Doador } from '../doador';
import {BaseEntidade} from '../../shared/base.entidade';
import { Paciente } from '../../paciente/paciente';
import { PedidoContato } from './fase2/pedido.contato';
import { StatusSolicitacao } from '../../shared/enums/status.solicitacao';
import { PedidoWorkup } from '../consulta/workup/pedido.workup';
import { Match } from '../../paciente/busca/match';
import { Busca } from '../../paciente/busca/busca';
import { ConvertUtil } from '../../shared/util/convert.util';
import { TipoExame } from 'app/laboratorio/tipo.exame';

export class Solicitacao extends BaseEntidade {
    private _id: Number;
	private _doador: Doador;
	private _tipoSolicitacao: TipoSolicitacao;
	private _paciente: Paciente;
	private _prescricao: Prescricao;
	private _status: number;
	private _pedidoContato:PedidoContato;
	private _dataCriacao:Date;
	private _pedidoWorkup:PedidoWorkup;
	private _match:Match;
	private _busca: Busca;
	private _tipoExame: TipoExame;

	/**
	 * @returns Number
	 */
	public get id(): Number {
		return this._id;
	}
	/**
	 * @param  {Number} value
	 */
	public set id(value: Number) {
		this._id = value;
	}
	/**
	 * @returns Doador
	 */
	public get doador(): Doador {
		return this._doador;
	}
	/**
	 * @param  {Doador} value
	 */
	public set doador(value: Doador) {
		this._doador = value;
	}
	/**
	 * @returns TipoSolicitacao
	 */
	public get tipoSolicitacao(): TipoSolicitacao {
		return this._tipoSolicitacao;
	}
	/**
	 * @param  {TipoSolicitacao} value
	 */
	public set tipoSolicitacao(value: TipoSolicitacao) {
		this._tipoSolicitacao = value;
	}
	/**
	 * @returns Paciente
	 */
	public get paciente(): Paciente {
		return this._paciente;
	}
	/**
	 * @param  {Paciente} value
	 */
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	/**
	 * Prescricao utilizada para o pedido de workup.
	 * @returns Prescricao
	 */
	public get prescricao(): Prescricao {
		return this._prescricao;
	}
	/**
	 * Prescricao utilizada para o pedido de workup.
	 * @param  {Prescricao} value
	 */
	public set prescricao(value: Prescricao) {
		this._prescricao = value;
	}
	/**
	 * @returns number
	 */
	public get status(): number {
		return this._status;
	}
	/**
	 * @param  {number} value
	 */
	public set status(value: number) {
		this._status = value;
	}
	/**
	 * @returns PedidoContato
	 */
	public get pedidoContato(): PedidoContato {
		return this._pedidoContato;
	}
	/**
	 * @param  {PedidoContato} value
	 */
	public set pedidoContato(value: PedidoContato) {
		this._pedidoContato = value;
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
	

	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}

	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}

    /**
     * Getter match
     * @return {Match}
     */
	public get match(): Match {
		return this._match;
	}

    /**
     * Setter match
     * @param {Match} value
     */
	public set match(value: Match) {
		this._match = value;
	}


    /**
     * Getter busca
     * @return {Busca}
     */
	public get busca(): Busca {
		return this._busca;
	}

    /**
     * Setter busca
     * @param {Busca} value
     */
	public set busca(value: Busca) {
		this._busca = value;
	}

	public get tipoExame(): TipoExame {
		return this._tipoExame;
	}

	public set tipoExame(value: TipoExame) {
		this._tipoExame = value;
	}


	public jsonToEntity(res: any): Solicitacao {

		if (res.doador) {
			this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
		}

		if (res.tipoSolicitacao) {
			this.tipoSolicitacao = new TipoSolicitacao().jsonToEntity(res.tipoSolicitacao);
		}
		if (res.paciente) {
			this.paciente = new Paciente().jsonToEntity(res.paciente);
		}

		if (res.prescricao) {
			this.prescricao = new Prescricao().jsonToEntity(res.prescricao);
		}

		if (res.pedidoContato) {
			this.pedidoContato = new PedidoContato().jsonToEntity(res.pedidoContato);
		}

		if (res.pedidoWorkup) {
			this.pedidoWorkup = new PedidoWorkup().jsonToEntity(res.pedidoWorkup);
		}

		if (res.match) {
			this.match = new Match().jsonToEntity(res.match);
		}

		if (res.busca) {
			this.busca = new Busca().jsonToEntity(res.busca);
		}

		if (res.tipoExame) {
			this.tipoExame = new TipoExame().jsonToEntity(res.tipoExame);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.status = ConvertUtil.parseJsonParaAtributos(res.status, new Number());
		this.dataCriacao  = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());	

        return this;
    }
	
}