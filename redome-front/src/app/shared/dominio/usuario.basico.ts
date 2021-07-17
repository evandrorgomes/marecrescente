import { BaseEntidade } from "app/shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * @description Representa os dados básico do usuário, compartilhado com 
 * qualquer especialização que possa ser implementada.
 * Ex.: UsuarioLogado, UsuarioBancoSangueCordao, UsuarioCentroTransplante, etc.
 * @author Pizão
 * @export
 * @class UsuarioBasico
 * @extends {BaseEntidade}
 */
export class UsuarioBasico extends BaseEntidade {
    private _id: number;
    private _username: string;
    private _nome: string;
    private _email: string;

    constructor(username?: string, nome?: string) {
        super();
        this._username = username;
        this._nome = nome;
    }

	public get id(): number {
		return this._id;
	}

    public set id(value: number) {
		this._id = value;
	}

	public get username(): string {
		return this._username;
	}

	public set username(value: string) {
		this._username = value;
	}

	public get nome(): string {
		return this._nome;
    }
    
    public set nome(value: string) {
		this._nome = value;
    }
    
	public get email(): string {
		return this._email;
	}

	public set email(value: string) {
		this._email = value;
	}

    public jsonToEntity(res: any) {
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        if (res.user_name) {
            this.username = ConvertUtil.parseJsonParaAtributos(res.user_name, new String());
        }
        if (res.username) {
            this.username = ConvertUtil.parseJsonParaAtributos(res.username, new String());
        }
        this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        this.email = ConvertUtil.parseJsonParaAtributos(res.email, new String());
        
        return this;
    }
}