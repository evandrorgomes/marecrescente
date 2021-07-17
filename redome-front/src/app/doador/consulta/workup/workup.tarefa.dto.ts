import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { Prescricao } from './prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';

export class WorkupTarefaDTO extends BaseEntidade {
	private _rmr: number;
	private _nomePaciente: string;
	private _centroTransplante: string;
	private _score: number;
	private _agingPedidoWorkup: string;
	private _dtEntregaExame: Date;

	/**
	 * @returns number
	 */
	public get rmr(): number {
		return this._rmr;
	}
	/**
	 * @param  {number} value
	 */
	public set rmr(value: number) {
		this._rmr = value;
	}
	/**
	 * @returns string
	 */
	public get centroTransplante(): string {
		return this._centroTransplante;
	}
	/**
	 * @param  {string} value
	 */
	public set centroTransplante(value: string) {
		this._centroTransplante = value;
	}
	/**
	 * @returns number
	 */
	public get score(): number {
		return this._score;
	}
	/**
	 * @param  {number} value
	 */
	public set score(value: number) {
		this._score = value;
	}
	/**
	 * @returns string
	 */
	public get agingPedidoWorkup(): string {
		return this._agingPedidoWorkup;
	}
	/**
	 * @param  {string} value
	 */
	public set agingPedidoWorkup(value: string) {
		this._agingPedidoWorkup = value;
	}
	/**
	 * @returns Date
	 */
	public get dtEntregaExame(): Date {
		return this._dtEntregaExame;
	}
	/**
	 * @param  {Date} value
	 */
	public set dtEntregaExame(value: Date) {
		this._dtEntregaExame = value;
	}
	
	/**
	 * @returns string
	 */
	public get nomePaciente(): string {
		return this._nomePaciente;
	}
	/**
	 * @param  {string} value
	 */
	public set nomePaciente(value: string) {
		this._nomePaciente = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}