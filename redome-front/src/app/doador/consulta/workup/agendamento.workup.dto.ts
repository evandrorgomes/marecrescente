import {BaseEntidade} from '../../../shared/base.entidade';

export class AgendamentoWorkupDTO extends BaseEntidade {
	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

	private _dataPrevistaDisponibilidadeDoador: Date;
	private _dataPrevistaLiberacaoDoador: Date;
	private _dataColeta: Date;
	private _dataInicioResultado: Date;
	private _dataFinalResultado: Date;
	private _dataLimiteWorkup: Date;

	private _idCentroColeta:number;
	private _necessitaLogistica:boolean;

	private _idFonteCelula: number;


	/**
	 * @returns Date
	 */
	public get dataFinalResultado(): Date {
		return this._dataFinalResultado;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataFinalResultado(value: Date) {
		this._dataFinalResultado = value;
	}
	/**
	 * @returns number
	 */
	public get idCentroColeta(): number {
		return this._idCentroColeta;
	}
	/**
	 * @param  {number} value
	 */
	public set idCentroColeta(value: number) {
		this._idCentroColeta = value;
	}

	public get dataPrevistaDisponibilidadeDoador(): Date {
		return this._dataPrevistaDisponibilidadeDoador;
	}

	public set dataPrevistaDisponibilidadeDoador(value: Date) {
		this._dataPrevistaDisponibilidadeDoador = value;
	}

	public get dataColeta(): Date {
		return this._dataColeta;
	}

	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}

	public get dataInicioResultado(): Date {
		return this._dataInicioResultado;
	}

	public set dataInicioResultado(value: Date) {
		this._dataInicioResultado = value;
	}

	public get dataLimiteWorkup(): Date {
		return this._dataLimiteWorkup;
	}

	public set dataLimiteWorkup(value: Date) {
		this._dataLimiteWorkup = value;
	}

	public get necessitaLogistica(): boolean {
		return this._necessitaLogistica;
	}

	public set necessitaLogistica(value: boolean) {
		this._necessitaLogistica = value;
	}

	public get dataPrevistaLiberacaoDoador(): Date {
		return this._dataPrevistaLiberacaoDoador;
	}

	public set dataPrevistaLiberacaoDoador(value: Date) {
		this._dataPrevistaLiberacaoDoador = value;
	}

    /**
     * Getter idFonteCelula
     * @return {number}
     */
	public get idFonteCelula(): number {
		return this._idFonteCelula;
	}

    /**
     * Setter idFonteCelula
     * @param {number} value
     */
	public set idFonteCelula(value: number) {
		this._idFonteCelula = value;
	}



}
