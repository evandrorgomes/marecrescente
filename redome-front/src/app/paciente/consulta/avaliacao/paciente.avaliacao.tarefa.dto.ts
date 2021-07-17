import { Cid } from '../../../shared/dominio/cid';
/**
 * Classe que representa um PacienteAvaliacaoTarefaDTO dto
 * 
 * @author Fillipe Queiroz
 * @export
 */
export class PacienteAvaliacaoTarefaDTO {

	private _rmr: number;
	private _nome:string;
	private _idade: number;
	private _tempoCadastro:string;
	private _cid:Cid;
	private _descricaoCid:string;

	/**
	 * Retorna o rmr
	 */
	public get rmr(): number {
		return this._rmr;
	}

	/**
	 * Seta o rmr
	 */
	public set rmr(value: number) {
		this._rmr = value;
	}

	/**
	 * Recupera o nome
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * Seta o nome
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	/**
	 * Recupera a idade
	 */
	public get idade(): number {
		return this._idade;
	}

	/**
	 * Seta a idade
	 */
	public set idade(value: number) {
		this._idade = value;
	}

	/**
	 * Recupera o tempo de cadastro
	 */
	public get tempoCadastro(): string {
		return this._tempoCadastro;
	}

	/**
	 * Seta o tempo de cadastro
	 */
	public set tempoCadastro(value: string) {
		this._tempoCadastro = value;
	}

	/**
	 * Recupera a cid.
	 */
	public get cid(): Cid {
		return this._cid;
	}

	/**
	 * Seta a cid.
	 */
	public set cid(value: Cid) {
		this._cid = value;
	}


}
