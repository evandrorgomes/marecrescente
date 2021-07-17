import { Evolucao } from '../cadastro/evolucao/evolucao';
import { Pendencia } from './pendencia';
import { UsuarioLogado } from '../../shared/dominio/usuario.logado';
import {BaseEntidade} from '../../shared/base.entidade';
import { TipoPendencia } from '../../shared/dominio/tipo.pendencia';
import { StatusPendencia } from '../../shared/dominio/status.pendencia';
import { Avaliacao } from './avaliacao';
import { ExamePaciente } from '../cadastro/exame/exame.paciente';

/**
 * Classe que representa uma resposta pendencia
 * 
 * @author Fillipe Queiroz
 * @export
 * @class Pendencia
 * @extends {BaseEntidade}
 */
export class RespostaPendencia  extends BaseEntidade {

	private _id: number;
	private _dataCriacao: Date;
	private _resposta: string;
	private _usuario:UsuarioLogado;
	private _pendencias: Pendencia[];
	private _exame: ExamePaciente;
	private _dataFormatadaDialogo:string;
	private _evolucao: Evolucao;
	private _respondePendencia: boolean;


	/**
	 * 
	 * 
	 * @type {number}
	 * @memberof RespostaPendencia
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendencia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberof RespostaPendencia
	 */
	public get resposta(): string {
		return this._resposta;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendencia
	 */
	public set resposta(value: string) {
		this._resposta = value;
	}


	/**
	 * 
	 * 
	 * @type {UsuarioLogado}
	 * @memberof RespostaPendencia
	 */
	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendencia
	 */
	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}
	
	/**
	 * 
	 * 
	 * @type {Pendencia[]}
	 * @memberof RespostaPendencia
	 */
	public get pendencias(): Pendencia[] {
		return this._pendencias;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendencia
	 */
	public set pendencias(value: Pendencia[]) {
		this._pendencias = value;
	}

	/**
	 * 
	 * 
	 * @type {Date}
	 * @memberof RespostaPendencia
	 */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendencia
	 */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
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
	
	public get exame(): ExamePaciente {
		return this._exame;
	}

	public set exame(value: ExamePaciente) {
		this._exame = value;
	}

	public get evolucao(): Evolucao {
		return this._evolucao;
	}

	public set evolucao(value: Evolucao) {
		this._evolucao = value;
	}


	/**
	 * Verifica se responde a pendencia
	 * 
	 * @type {boolean}
	 * @memberof RespostaPendencia
	 */
	public get respondePendencia(): boolean {
		return this._respondePendencia;
	}

	/**
	 * Setar se responde a pendencia
	 * 
	 * @memberof RespostaPendencia
	 */
	public set respondePendencia(value: boolean) {
		this._respondePendencia = value;
	}
	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }	
}
