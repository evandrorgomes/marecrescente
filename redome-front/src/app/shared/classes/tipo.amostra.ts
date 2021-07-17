import { BaseEntidade } from "app/shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

export class TipoAmostra  extends BaseEntidade {
    
	private _id: number;
    private _descricao: string;


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter descricao
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter descricao
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    
    public jsonToEntity(res: any) : this{
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao,new String());
		return this;
    }
}