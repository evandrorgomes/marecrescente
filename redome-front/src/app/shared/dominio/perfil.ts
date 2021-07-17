import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";
import { Sistema } from "./sistema";

/**
 * Classe utilizada para representar a entidade perfil de acesso do sistema.
 * 
 * @author Thiago Moraes
 */
export class Perfil extends BaseEntidade {

    private _id:Number;
	private _descricao: String;
	private _qtUsuarios:Number;

	private _sistema: Sistema;
	

    /**
     * Construtor da classe
     */
    constructor(id?: number) {
		super();
		this._id = id;
    }
    

    /**
	 * Método de acesso para obter o identificador único do perfil.
	 * 
	 * @type {number}
	 * @memberOf Perfil
	 */
	public get id(): Number {
		return this._id;
	}

	/**
	 * 
	 * Método de acesso para definir o identificador único do perfil.
	 * 
	 * @memberOf Perfil
	 */	
	public set id(value: Number) {
		this._id = value;
	}


   /**
	 * Método de acesso para obter a descrição do perfil.
	 * 
	 * @type {string}
	 * @memberOf Perfil
	 */	
	public get descricao(): String {
		return this._descricao;
	}

	/**
	 * 
     * Método de acesso para definir a descrição do perfil.
	 * 
	 * @memberOf Perfil
	 */	
	public set descricao(descricao: String) {
		this._descricao = descricao;
	}


    /**
	 * Método de acesso para obter a quantidade de usuários que possue este perfil
	 * 
	 * @type {number}
	 * @memberOf Perfil
	 */
	public get qtUsuarios(): Number {
		return this._qtUsuarios;
	}

	/**
	 * 
	 * Método de acesso para definir a quantidade de usuários que possue este perfil
	 * 
	 * @memberOf Perfil
	 */	
	public set qtUsuarios(value: Number) {
		this._qtUsuarios = value;
	}


	public jsonToEntity(res:any):this{
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = 
			ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

		if (res.sistema) {
			this.sistema = new Sistema().jsonToEntity(res.sistema);
        }

		return this;
	}

	public get sistema(): Sistema {
		return this._sistema;
	}

	public set sistema(value: Sistema) {
		this._sistema = value;
	}


}
