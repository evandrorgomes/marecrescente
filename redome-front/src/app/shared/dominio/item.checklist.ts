import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";


/**
 *Itens que compõe as categorias de tipo de checklist.
 @author Filipe Paes
 */
export class ItensChecklist extends BaseEntidade {
    private _id:number;
    private _nome:String;
    private _resposta:boolean;

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
	public get nome(): String {
		return this._nome;
	}

    /**
     * Setter nome
     * @param {String} value
     */
	public set nome(value: String) {
		this._nome = value;
    }


    /**
     * Getter resposta
     * @return {boolean}
     */
	public get resposta(): boolean {
		return this._resposta;
	}

    /**
     * Setter resposta
     * @param {boolean} value
     */
	public set resposta(value: boolean) {
		this._resposta = value;
	}

    public jsonToEntity(res: any) : this{
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        return this;
    }

}
