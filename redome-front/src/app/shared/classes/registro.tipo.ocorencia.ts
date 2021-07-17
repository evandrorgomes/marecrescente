import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class RegistroTipoOcorrencia extends BaseEntidade{

    private _id:number;
    private _nome:string;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get nome(): string {
		return this._nome;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set nome(value: string) {
		this._nome = value;
	}

    public jsonToEntity(res: any) :this{
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        return this;
    }
}
