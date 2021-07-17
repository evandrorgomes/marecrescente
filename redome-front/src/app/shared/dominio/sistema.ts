import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe utilizada para representar a entidade sistema, destina a 
 * separar os perfis por qualquer perfil externo.
 * 
 * @author Pizão
 */
export class Sistema extends BaseEntidade {
	public static BSCUP: Number = 7;

    private _id:Number;
	private _nome: String;
	private _disponivelRedome: boolean;
	

    /**
     * Construtor da classe
     */
    constructor(id?: number) {
		super();
		this._id = id;
    }
    

	public get id(): Number {
		return this._id;
	}

	public set id(value: Number) {
		this._id = value;
	}

	public get nome(): String {
		return this._nome;
	}

	public set nome(nome: String) {
		this._nome = nome;
	}

	public get disponivelRedome(): boolean {
		return this._disponivelRedome;
	}

	public set disponivelRedome(value: boolean) {
		this._disponivelRedome = value;
	}

	public jsonToEntity(res:any):this{
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = 
			ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.disponivelRedome = 
			ConvertUtil.parseJsonParaAtributos(res.disponivelRedome, new Boolean());
		return this;
	}
}
