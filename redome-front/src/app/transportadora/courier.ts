import { Transportadora } from "app/shared/model/transportadora";
import { BaseEntidade } from "../shared/base.entidade";
import { ConvertUtil } from "../shared/util/convert.util";
import { ContatoTelefonicoCourier } from "./contato.telefonico.courier";
import { EmailContatoCourier } from "./email.contato.courier";

/**
 * @author Queiroz
 *
 */
export class Courier extends BaseEntidade{

    private _id: number;
    private _nome: string;
    private _transportadora:Transportadora;
    private _cpf:string;
    private _rg:string;
    private _contatosTelefonicos:ContatoTelefonicoCourier[];
    private _emails:EmailContatoCourier[];

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


    /**
     * Getter transportadora
     * @return {Transportadora}
     */
	public get transportadora(): Transportadora {
		return this._transportadora;
	}

    /**
     * Setter transportadora
     * @param {Transportadora} value
     */
	public set transportadora(value: Transportadora) {
		this._transportadora = value;
	}

    /**
     * Getter cpf
     * @return {string}
     */
	public get cpf(): string {
		return this._cpf;
	}

    /**
     * Setter cpf
     * @param {string} value
     */
	public set cpf(value: string) {
		this._cpf = value;
	}

    /**
     * Getter rg
     * @return {string}
     */
	public get rg(): string {
		return this._rg;
	}

    /**
     * Setter rg
     * @param {string} value
     */
	public set rg(value: string) {
		this._rg = value;
	}


    /**
     * Getter contatosTelefonicos
     * @return {ContatoTelefonicoCourier[]}
     */
	public get contatosTelefonicos(): ContatoTelefonicoCourier[] {
		return this._contatosTelefonicos;
	}

    /**
     * Setter contatosTelefonicos
     * @param {ContatoTelefonicoCourier[]} value
     */
	public set contatosTelefonicos(value: ContatoTelefonicoCourier[]) {
		this._contatosTelefonicos = value;
	}


    /**
     * Getter emails
     * @return {EmailContatoCourier[]}
     */
	public get emails(): EmailContatoCourier[] {
		return this._emails;
	}

    /**
     * Setter emails
     * @param {EmailContatoCourier[]} value
     */
	public set emails(value: EmailContatoCourier[]) {
		this._emails = value;
	}


    public jsonToEntity(res: any): Courier {
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        this._rg = ConvertUtil.parseJsonParaAtributos(res.rg, new String());
        this._cpf = ConvertUtil.parseJsonParaAtributos(res.cpf, new String());

        if(res.transportadora){
            this.transportadora = new Transportadora().jsonToEntity(res.transportadora);
        }

        if(res.contatosTelefonicos){
            this._contatosTelefonicos = [];
            res.contatosTelefonicos.forEach(contatoJSON => {
                this._contatosTelefonicos.push(new ContatoTelefonicoCourier().jsonToEntity(contatoJSON))
            });
        }
        if(res.emails){
            this._emails = [];
            res.emails.forEach(email => {
                this._emails.push(new EmailContatoCourier().jsonToEntity(email));
            });
        }
        return this;
    }

}
