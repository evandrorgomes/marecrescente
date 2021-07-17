import { ExamePaciente } from '../../cadastro/exame/exame.paciente';

/**
 * Classe que representa a tela de consulta de exames 
 * (paciente, lista de exames e exame selecionado).
 * @author Fillipe Queiroz
 */
export class ExameDTO {

	private _exames: ExamePaciente[];
    private _exameSelecionado: ExamePaciente;
	private _usernameMedicoResponsavel:String;
	private _pacienteEmObito:boolean;
	private _buscaEmMatch: boolean;
	private _idTipoExame: number;
	private _idBusca: number;

	public get exameSelecionado(): ExamePaciente {
		return this._exameSelecionado;
	}

	public set exameSelecionado(value: ExamePaciente) {
		this._exameSelecionado = value;
	}	

	public get exames(): ExamePaciente[] {
		return this._exames;
	}

	public set exames(value: ExamePaciente[]) {
		this._exames = value;
	}
	

	/**
	 * Recupera username do medico responsavel
	 * 
	 * @type {String}
	 * @memberof ExameDTO
	 */
	public get usernameMedicoResponsavel(): String {
		return this._usernameMedicoResponsavel;
	}

	/**
	 * Seta username do medico responsavel
	 * 
	 * @memberof ExameDTO
	 */
	public set usernameMedicoResponsavel(value: String) {
		this._usernameMedicoResponsavel = value;
	}

	/**
	 * Recupera se paciente esta em obito
	 * 
	 * @type {boolean}
	 * @memberof ExameDTO
	 */
	public get pacienteEmObito(): boolean {
		return this._pacienteEmObito;
	}

	/**
	 * Seta se paciente esta em obito
	 * 
	 * @memberof ExameDTO
	 */
	public set pacienteEmObito(value: boolean) {
		this._pacienteEmObito = value;
	}

    /**
     * Indica se o paciente pode receber novos exames.
	 * 
     * @return {boolean}
     */
	public get buscaEmMatch(): boolean {
		return this._buscaEmMatch;
	}

	public set buscaEmMatch(value: boolean) {
		this._buscaEmMatch = value;
	}

    /**
     * Getter idBusca
     * @return {number}
     */
	public get idBusca(): number {
		return this._idBusca;
	}

    /**
     * Setter idBusca
     * @param {number} value
     */
	public set idBusca(value: number) {
		this._idBusca = value;
	}

    /**
     * Getter idTipoExame
     * @return {number}
     */
	public get idTipoExame(): number {
		return this._idTipoExame;
	}

    /**
     * Setter idTipoExame
     * @param {number} value
     */
	public set idTipoExame(value: number) {
		this._idTipoExame = value;
	}
 
	
}

