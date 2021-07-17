import { constructor } from 'events';
import { BaseEntidade } from '../shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class StatusPedidoExame extends BaseEntidade {

    private _id: number;
    private _descricao: string;

    constructor(id:number, descricao?: string);
    constructor(id?:number, descricao?: string) {
        super();
        this._id = id;
        this._descricao = descricao;
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
     * Getter nome
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    public jsonToEntity(res: any): StatusPedidoExame {

        this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao  = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

        return this;
    }
}
