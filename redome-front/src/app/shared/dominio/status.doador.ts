import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe que representa um status do doador
 * 
 * @author Bruno Sousa
 * @export
 * @class StatusDoador
 * @extends {BaseEntidade}
 */
export class StatusDoador extends BaseEntidade {

	public static ATIVO = 1;
	public static ATIVO_RESSALVA = 2;
	public static INATIVO_TEMPORARIO = 3;
	public static INATIVO_PERMANENTE = 4;

	private _id: number;
	private _descricao: string;
	private _tempoObrigatorio: boolean;


	constructor(id: number = null) {
		super();
		this._id = id;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf StatusPendencia
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPendencia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf StatusPendencia
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf StatusPendencia
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}

	public get tempoObrigatorio(): boolean {
		return this._tempoObrigatorio;
	}

	public set tempoObrigatorio(value: boolean) {
		this._tempoObrigatorio = value;
	}

	public jsonToEntity(res:any): StatusDoador{

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.tempoObrigatorio = ConvertUtil.parseJsonParaAtributos(res.tempoObrigatorio, new Boolean());
	
		return this;
	}

}