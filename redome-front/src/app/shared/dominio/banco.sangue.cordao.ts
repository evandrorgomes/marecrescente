import { BaseEntidade } from "../base.entidade";
import { Pais } from "./pais";
import { ConvertUtil } from "../util/convert.util";

/**
 * Classe para definir os campos de banco de sangue de cordão
 * utilizado como referência a entidade no front-end.
 * 
 * @author Pizão
 */
export class BancoSangueCordao extends BaseEntidade {
    
    private _id: number;
	private _sigla: string;
    private _nome: string;
    private _endereco: string;

	constructor(id?: number, nome?: string) {
		super();
		this._id = id;
		this._nome = nome;
	}


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter sigla
     * @return {string}
     */
	public get sigla(): string {
		return this._sigla;
	}

    /**
     * Setter sigla
     * @param {string} value
     */
	public set sigla(value: string) {
		this._sigla = value;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get nome(): string {
		return this._nome;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set nome(value: string) {
		this._nome = value;
    }

    public get endereco(): string {
		return this._endereco;
	}

	public set endereco(value: string) {
		this._endereco = value;
	}
    
    public jsonToEntity(res:any): BancoSangueCordao {

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.sigla = ConvertUtil.parseJsonParaAtributos(res.sigla, new String());
        this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
    
		return this;
	}
	
}