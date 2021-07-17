export class ConsultaPrescricaoDTO {

    private _rmr: number;
    private _nomePaciente: string;
    private _status: string;
    private _agingDaTarefa: string;
	private _idPrescricao: number;
	private _idDoador: number;
	private _identificadorDoador: string;
	private _idTipoDoador: number;
	private _peso: number;

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Getter nomePaciente
     * @return {string}
     */
	public get nomePaciente(): string {
		return this._nomePaciente;
	}

    /**
     * Setter nomePaciente
     * @param {string} value
     */
	public set nomePaciente(value: string) {
		this._nomePaciente = value;
	}

    /**
     * Getter status
     * @return {string}
     */
	public get status(): string {
		return this._status;
	}

    /**
     * Setter status
     * @param {string} value
     */
	public set status(value: string) {
		this._status = value;
	}

	public get agingDaTarefa(): string {
		return this._agingDaTarefa;
	}

	public set agingDaTarefa(value: string) {
		this._agingDaTarefa = value;
	}

	get idPrescricao(): number {
		return this._idPrescricao;
	}

	set idPrescricao(value: number) {
		this._idPrescricao = value;
	}


    /**
     * Getter identificadorDoador
     * @return {string}
     */
	public get identificadorDoador(): string {
		return this._identificadorDoador;
	}

    /**
     * Setter identificadorDoador
     * @param {string} value
     */
	public set identificadorDoador(value: string) {
		this._identificadorDoador = value;
	}

    /**
     * Getter idTipoDoador
     * @return {number}
     */
	public get idTipoDoador(): number {
		return this._idTipoDoador;
	}

    /**
     * Setter idTipoDoador
     * @param {number} value
     */
	public set idTipoDoador(value: number) {
		this._idTipoDoador = value;
	}
	
	   /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

		   /**
     * Getter peso
     * @return {number}
     */
	public get peso(): number {
		return this._peso;
	}

    /**
     * Setter peso
     * @param {number} value
     */
	public set peso(value: number) {
		this._peso = value;
	}
}
