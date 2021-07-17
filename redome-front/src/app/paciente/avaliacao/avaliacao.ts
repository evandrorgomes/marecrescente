import {BaseEntidade} from '../../shared/base.entidade';
import { Pendencia } from './pendencia';
import { Medico } from '../../shared/dominio/medico';
import { CentroTransplante } from '../../shared/dominio/centro.transplante';
import { Paciente } from '../paciente';

/**
 * Classe que representa uma avaliação
 * 
 * @export
 * @class Avaliacao
 * @extends {BaseEntidade}
 * @author Bruno Sousa
 */
export class Avaliacao  extends BaseEntidade {

	private _id: number;
	private _dataResultado: Date;
	private _paciente: Paciente;
	private _aprovado: boolean;
	private _medicoResponsavel: Medico;
	private _observacao: string;
	private _centroAvaliador: CentroTransplante;
	private _status: number;
	private _pendencias: Pendencia[];
	
	/**
	 * @param {number} id 
	 * 
	 * @memberOf Avaliacao
	 */
	constructor( id: number = null) {
		super();
		this._id = id;
	}
    

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Avaliacao
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {Date}
	 * @memberOf Avaliacao
	 */
	public get dataResultado(): Date {
		return this._dataResultado;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set dataResultado(value: Date) {
		this._dataResultado = value;
	}

	/**
	 * 
	 * 
	 * @type {Paciente}
	 * @memberOf Avaliacao
	 */
	public get paciente(): Paciente {
		return this._paciente;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf Avaliacao
	 */
	public get aprovado(): boolean {
		return this._aprovado;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set aprovado(value: boolean) {
		this._aprovado = value;
	}

	/**
	 * 
	 * 
	 * @type {Medico}
	 * @memberOf Avaliacao
	 */
	public get medicoResponsavel(): Medico {
		return this._medicoResponsavel;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set medicoResponsavel(value: Medico) {
		this._medicoResponsavel = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Avaliacao
	 */
	public get observacao(): string {
		return this._observacao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set observacao(value: string) {
		this._observacao = value;
	}

	/**
	 * 
	 * 
	 * @type {CentroTransplante}
	 * @memberOf Avaliacao
	 */
	public get centroAvaliador(): CentroTransplante {
		return this._centroAvaliador;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set centroAvaliador(value: CentroTransplante) {
		this._centroAvaliador = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Avaliacao
	 */
	public get status(): number {
		return this._status;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set status(value: number) {
		this._status = value;
	}

	/**
	 * 
	 * 
	 * @type {Pendencia[]}
	 * @memberOf Avaliacao
	 */
	public get pendencias(): Pendencia[] {
		return this._pendencias;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Avaliacao
	 */
	public set pendencias(value: Pendencia[]) {
		this._pendencias = value;
	}
    

	public jsonToEntity(res:any):this{
		
		return this;
	}
}