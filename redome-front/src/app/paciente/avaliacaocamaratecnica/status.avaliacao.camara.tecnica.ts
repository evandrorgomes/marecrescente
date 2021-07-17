import { BaseEntidade } from "../../shared/base.entidade";
import { ConvertUtil } from "../../shared/util/convert.util";

/**
 * Classe de status de Avaliação da Camara Técnica.
 * @author Filipe Paes
 */
export class StatusAvaliacaoCamaraTecnica extends BaseEntidade {
	private _id:number;
    private _descricao: String;

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
     * @return {String}
     */
	public get descricao(): String {
		return this._descricao;
	}

    /**
     * Setter nome
     * @param {String} value
     */
	public set descricao(value: String) {
		this._descricao = value;
	}


    public jsonToEntity(res: any) :this{
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
        return this;
    }

}
