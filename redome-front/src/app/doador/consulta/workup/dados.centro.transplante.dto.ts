import {BaseEntidade} from '../../../shared/base.entidade';
import {ContatoTelefonico} from '../../../shared/classes/contato.telefonico';

export class DadosCentroTransplanteDTO extends BaseEntidade {
	private _rmr:number;
	private _nomePaciente:string;
	private _nomeMedico:string;
	private _nomeCentroTransplante:string;
	private _telefonesCT:ContatoTelefonico;
	private _telefonesMedico:ContatoTelefonico;

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
	public get nomePaciente(): string {
		return this._nomePaciente;
	}
	/**
	 * @param  {string} value
	 */
	public set nomePaciente(value: string) {
		this._nomePaciente = value;
	}
	/**
	 * @returns string
	 */
	public get nomeMedico(): string {
		return this._nomeMedico;
	}
	/**
	 * @param  {string} value
	 */
	public set nomeMedico(value: string) {
		this._nomeMedico = value;
	}
	/**
	 * @returns string
	 */
	public get nomeCentroTransplante(): string {
		return this._nomeCentroTransplante;
	}
	/**
	 * @param  {string} value
	 */
	public set nomeCentroTransplante(value: string) {
		this._nomeCentroTransplante = value;
	}
	/**
	 * @returns ContatoTelefonico
	 */
	public get telefonesCT(): ContatoTelefonico {
		return this._telefonesCT;
	}
	/**
	 * @param  {ContatoTelefonico} value
	 */
	public set telefonesCT(value: ContatoTelefonico) {
		this._telefonesCT = value;
	}
	/**
	 * @returns ContatoTelefonico
	 */
	public get telefonesMedico(): ContatoTelefonico {
		return this._telefonesMedico;
	}
	/**
	 * @param  {ContatoTelefonico} value
	 */
	public set telefonesMedico(value: ContatoTelefonico) {
		this._telefonesMedico = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}
