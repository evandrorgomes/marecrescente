import { BaseEntidade } from '../../../shared/base.entidade';
import { Processo } from '../../../shared/dominio/processo';
import { Perfil } from '../../../shared/dominio/perfil';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';

/*
*Classe que representa uma tarefa para um usuário
*/
export class Tarefa extends BaseEntidade {
	
	private _id: number;
    private _rmr: number;
	private _nome: string;
	private _totalTarefas: number;
    private _agingAvaliacao: string;
    private _agingTarefa: string;
    private _dataCriacaoPendencia: Date;
	private _dataCriacaoAvaliacao: Date;
	private _processo:Processo;
	private _perfilResponsavel: Perfil;
	private _usuarioResponsavel: UsuarioLogado;
    
	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}
    
	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
	}

	public get totalTarefas(): number {
		return this._totalTarefas;
	}

	public set totalTarefas(value: number) {
		this._totalTarefas = value;
	}

	public get dataCriacaoPendencia(): Date {
		return this._dataCriacaoPendencia;
	}

	public set dataCriacaoPendencia(value: Date) {
		this._dataCriacaoPendencia = value;
	}

	public get dataCriacaoAvaliacao(): Date {
		return this._dataCriacaoAvaliacao;
	}

	public set dataCriacaoAvaliacao(value: Date) {
		this._dataCriacaoAvaliacao = value;
    }

	public get agingAvaliacao(): string {
		return this._agingAvaliacao;
	}

	public set agingAvaliacao(value: string) {
		this._agingAvaliacao = value;
	}

	public get agingTarefa(): string {
		return this._agingTarefa;
	}

	public set agingTarefa(value: string) {
		this._agingTarefa = value;
	}

	/**
	 * @returns Processo
	 */
	public get processo(): Processo {
		return this._processo;
	}
	/**
	 * @param  {Processo} value
	 */
	public set processo(value: Processo) {
		this._processo = value;
	}
	/**
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	
	public get perfilResponsavel(): Perfil {
		return this._perfilResponsavel;
	}

	public set perfilResponsavel(value: Perfil) {
		this._perfilResponsavel = value;
	}

	public get usuarioResponsavel(): UsuarioLogado {
		return this._usuarioResponsavel;
	}

	public set usuarioResponsavel(value: UsuarioLogado) {
		this._usuarioResponsavel = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
	
}