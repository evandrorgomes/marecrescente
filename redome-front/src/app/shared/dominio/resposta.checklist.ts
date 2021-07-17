import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ItensChecklist } from "./item.checklist";

/**
 * Resposta de checklist para serem passadas para o componente que checa cada item.
 * @author Filipe Paes
 */
export class RespostaChecklist extends BaseEntidade {
    private _id: number;
    private _item: ItensChecklist;
    private _resposta: boolean;
    private _pedidoLogistica: number;

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
     * Getter item
     * @return {ItensChecklist}
     */
    public get item(): ItensChecklist {
        return this._item;
    }

    /**
     * Setter item
     * @param {ItensChecklist} value
     */
    public set item(value: ItensChecklist) {
        this._item = value;
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


    /**
     * Getter pedidoLogistica
     * @return {number}
     */
	public get pedidoLogistica(): number {
		return this._pedidoLogistica;
	}

    /**
     * Setter pedidoLogistica
     * @param {number} value
     */
	public set pedidoLogistica(value: number) {
		this._pedidoLogistica = value;
	}


    public jsonToEntity(res: any): RespostaChecklist {
        if(res.item){
            this._item = new ItensChecklist().jsonToEntity(res.item);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.resposta = ConvertUtil.parseJsonParaAtributos(res.resposta, new Boolean());
        this.pedidoLogistica = ConvertUtil.parseJsonParaAtributos(res.pedidoLogistica, new Number());

        return this;
    }
}
