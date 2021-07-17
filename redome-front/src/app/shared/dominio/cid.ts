import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de cid
 * 
 * @author Bruno Sousa
 * @export
 * @class Cid
 */
export class Cid extends BaseEntidade {

    /**
     * Identificador único da classe
     * 
     * @private
     * @type {number}
     * @memberOf Cid
     */
    private _id: number;

    /**
     * Identifica se a cid está contemplada pela portária
     * 
     * @private
     * @type {boolean}
     * @memberOf Cid
     */
    private _transplante: boolean;

    /**
     * Código da cid
     * 
     * @private
     * @type {String}
     * @memberOf Cid
     */
    private _codigo: string;

    /**
     * descrição da cid
     * 
     * @private
     * @type {string}
     * @memberOf Cid
     */
    private _descricao: string;

    /**
     * Idade mínima de contemplação da CID.
     */
    private _idadeMinima: number;

    /**
     * Idade máxima de contemplação da CID.
     */
    private _idadeMaxima: number;


    constructor(id?: number, codigo?: string, descricao?: string, transplante?: boolean, idadeMinima?:number, idadeMaxima?:number) {
        super();
        this._id = id;
        this._codigo = codigo;
        this._descricao = descricao;
        this._transplante = transplante;
        this._idadeMinima = idadeMinima;
        this._idadeMaxima = idadeMaxima;
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
     * @memberOf Cid
     */
    public set id(value: number) {
        this._id = value;
    }

    /**
     * 
     * 
     * @type {boolean}
     * @memberOf Cid
     */
    public get transplante(): boolean {
        return this._transplante;
    }

    /**
     * 
     * 
     * 
     * @memberOf Cid
     */
    public set transplante(value: boolean) {
        this._transplante = value;
    }

    /**
     * 
     * 
     * @type {string}
     * @memberOf Cid
     */
    public get codigo(): string {
        return this._codigo;
    }

    /**
     * 
     * 
     * 
     * @memberOf Cid
     */
    public set codigo(value: string) {
        this._codigo = value;
    }

    /**
     * 
     * 
     * @type {string}
     * @memberOf Cid
     */
    public get descricao(): string {
        return this._descricao;
    }

    /**
     * 
     * 
     * 
     * @memberOf Cid
     */
    public set descricao(value: string) {
        this._descricao = value;
    }

    public get descricaoFormatada(): string {
        return this._codigo + " - " + this._descricao;
    }



    /**
     * Getter idadeMinima
     * @return {number}
     */
	public get idadeMinima(): number {
		return this._idadeMinima;
	}

    /**
     * Setter idadeMinima
     * @param {number} value
     */
	public set idadeMinima(value: number) {
		this._idadeMinima = value;
	}



    /**
     * Getter idadeMaxima
     * @return {number}
     */
	public get idadeMaxima(): number {
		return this._idadeMaxima;
	}

    /**
     * Setter idadeMaxima
     * @param {number} value
     */
	public set idadeMaxima(value: number) {
		this._idadeMaxima = value;
	}
    



    public jsonToEntity(res:any): Cid {
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.codigo = ConvertUtil.parseJsonParaAtributos(res.codigo, new String());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
        this.transplante = ConvertUtil.parseJsonParaAtributos(res.transplante, new Boolean());
        this.idadeMinima = ConvertUtil.parseJsonParaAtributos(res.idadeMinima, new Number());
        this.idadeMaxima = ConvertUtil.parseJsonParaAtributos(res.idadeMaxima, new Number());
		return this;
	}

}