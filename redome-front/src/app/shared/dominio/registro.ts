import { BaseEntidade } from "../base.entidade";
import { Pais } from "./pais";
import { ConvertUtil } from "../util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de registro
 * pode ser utilizada por qualquer classe.
 * @author Bruno Sousa
 */
export class Registro extends BaseEntidade {
    
    private _id: number;
	private _nome: string;
	private _pais: Pais;

  /**
   * Método construtor.
   * @param object Objeto do tipo etnia
   * @author Fillipe Queiroz
   */
	constructor(id?: number, nome?: string) {
		super();
		this._id = id;
		this._nome = nome;
	}

	/**
     * 
     * 
     * @type {number}
     * @memberOf Cid
     */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Etnia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Etnia
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Etnia
	 */
	public set nome(value: string) {
		this._nome = value;
	}

    /**
     * Getter pais
     * @return {Pais}
     */
	public get pais(): Pais {
		return this._pais;
	}

    /**
     * Setter pais
     * @param {Pais} value
     */
	public set pais(value: Pais) {
		this._pais = value;
	}
 
	public jsonToEntity(res: any): Registro {

		if (res.pais) {
			this.pais = new Pais().jsonToEntity(res.pais);;
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());

        return this;
    }
    
}