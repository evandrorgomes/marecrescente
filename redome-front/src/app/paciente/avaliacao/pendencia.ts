import { PacienteUtil } from '../../shared/paciente.util';
import { RespostaPendencia } from './resposta.pendencia';
import {BaseEntidade} from '../../shared/base.entidade';
import { TipoPendencia } from '../../shared/dominio/tipo.pendencia';
import { StatusPendencia } from '../../shared/dominio/status.pendencia';
import { Avaliacao } from './avaliacao';

/**
 * Classe que representa uma pendência
 * 
 * @author Bruno Sousa
 * @export
 * @class Pendencia
 * @extends {BaseEntidade}
 */
export class Pendencia  extends BaseEntidade {
	static STATUS_ABERTO:StatusPendencia = new StatusPendencia(1);

    private _id: number;
    private _dataCriacao: Date;
    private _descricao: string;
    private _avaliacao: Avaliacao;
    private _statusPendencia: StatusPendencia = Pendencia.STATUS_ABERTO;
	private _tipoPendencia: TipoPendencia;
	private _tempoCriacao: string;
	private _respostas: RespostaPendencia[];
	private _dataFormatadaDialogo:string;



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

	public get descricao(): string {
		return this._descricao;
	}

	public set descricao(value: string) {
		this._descricao = value;
	}

	public get avaliacao(): Avaliacao {
		return this._avaliacao;
	}

	public set avaliacao(value: Avaliacao) {
		this._avaliacao = value;
	}

	public get statusPendencia(): StatusPendencia {
		return this._statusPendencia;
	}

	public set statusPendencia(value: StatusPendencia) {
		this._statusPendencia = value;
	}

	public get tipoPendencia(): TipoPendencia {
		return this._tipoPendencia;
	}

	public set tipoPendencia(value: TipoPendencia) {
		this._tipoPendencia = value;
	}

	public get tempoCriacao(): string {
		return this._tempoCriacao;
	}

	public set tempoCriacao(value: string) {
		this._tempoCriacao = value;
	}

	/**
	 * 
	 * 
	 * @type {RespostaPendencia[]}
	 * @memberof Pendencia
	 */
	public get respostas(): RespostaPendencia[] {
		return this._respostas;
	}

	/**
	 * 
	 * 
	 * @memberof Pendencia
	 */
	public set respostaPendencia(value: RespostaPendencia[]) {
		this._respostas = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberof Pendencia
	 */
	public get dataFormatadaDialogo(): string {
		return this._dataFormatadaDialogo;
	}

	/**
	 * 
	 * 
	 * @memberof Pendencia
	 */
	public set dataFormatadaDialogo(value: string) {
		this._dataFormatadaDialogo = value;
	}

	public jsonToEntity(res:any):this{
		
		return this;
	}

}