/**
 * Classe que representa uma resposta RespostaPendenciaDTO dto
 * 
 * @author Cintia Oliveira
 * @export
 */
export class RespostaPendenciaDTO {

	private _resposta: string;
	private _usuario:string;
	private _exame: number;
	private _dataFormatadaDialogo:string;
	private _evolucao: number;
	private _link:string;
	private _estiloIcone:string;

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberof RespostaPendenciaDTO
	 */
	public get resposta(): string {
		return this._resposta;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendenciaDTO
	 */
	public set resposta(value: string) {
		this._resposta = value;
	}


	/**
	 * 
	 * 
	 * @type {Usuario}
	 * @memberof RespostaPendenciaDTO
	 */
	public get usuario(): string {
		return this._usuario;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendenciaDTO
	 */
	public set usuario(value: string) {
		this._usuario = value;
	}
	
	/**
	 * 
	 * 
	 * @type {string}
	 * @memberof RespostaPendenciaDTO
	 */
	public get dataFormatadaDialogo(): string {
		return this._dataFormatadaDialogo;
	}

	/**
	 * 
	 * 
	 * @memberof RespostaPendenciaDTO
	 */
	public set dataFormatadaDialogo(value: string) {
		this._dataFormatadaDialogo = value;
	}
	
	public get exame(): number {
		return this._exame;
	}

	public set exame(value: number) {
		this._exame = value;
	}

	public get evolucao(): number {
		return this._evolucao;
	}

	public set evolucao(value: number) {
		this._evolucao = value;
	}

	public get link(): string {
		return this._link;
	}

	public set link(value: string) {
		this._link = value;
	}

	public get estiloIcone(): string {
		return this._estiloIcone;
	}

	public set estiloIcone(value: string) {
		this._estiloIcone = value;
	}
	
}
