import { BaseEntidade } from "../base.entidade";
import { ItensChecklist } from "./item.checklist";
import { ConvertUtil } from "../util/convert.util";

/**
 * Categorias para agrupamento de itens de checklist.
 * @author Filipe Paes
 */
export class CategoriaChecklist extends BaseEntidade {
    private _id:number;
    private _nome:String;
    private _itens:ItensChecklist[];

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
     * Getter itens
     * @return {ItensChecklist[]}
     */
	public get itens(): ItensChecklist[] {
		return this._itens;
	}

    /**
     * Setter itens
     * @param {ItensChecklist[]} value
     */
	public set itens(value: ItensChecklist[]) {
		this._itens = value;
	}

    public jsonToEntity(res: any):this{
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        if(res.itens){
            this._itens = [];
            res.itens.forEach(item => {
                this._itens.push(new ItensChecklist().jsonToEntity(item));
            });
        }
        return this;
    }
}
