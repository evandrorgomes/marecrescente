import { TipoAmostra } from "./tipo.amostra";
import { Prescricao } from "app/doador/consulta/workup/prescricao";
import { BaseEntidade } from "app/shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe associativa entre tipo de amostra e prescrição.
 */
export class TipoAmostraPrescricao   extends BaseEntidade{
    
    private _id :number;
	private _ml: number;
	private _tipoAmostra: TipoAmostra;
    private _prescricao: Prescricao ;
    private _descricaoOutrosExames: String;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter ml
     * @return {number}
     */
	public get ml(): number {
		return this._ml;
	}

    /**
     * Getter tipoAmostra
     * @return {TipoAmostra}
     */
	public get tipoAmostra(): TipoAmostra {
		return this._tipoAmostra;
	}

    /**
     * Getter prescricao
     * @return {Prescricao }
     */
	public get prescricao(): Prescricao  {
		return this._prescricao;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter ml
     * @param {number} value
     */
	public set ml(value: number) {
		this._ml = value;
	}

    /**
     * Setter tipoAmostra
     * @param {TipoAmostra} value
     */
	public set tipoAmostra(value: TipoAmostra) {
		this._tipoAmostra = value;
	}

    /**
     * Setter prescricao
     * @param {Prescricao } value
     */
	public set prescricao(value: Prescricao ) {
		this._prescricao = value;
    }

    /**
     * Getter descricaoOutrosExames
     * @return {String}
     */
	public get descricaoOutrosExames(): String {
		return this._descricaoOutrosExames;
	}

    /**
     * Setter descricaoOutrosExames
     * @param {String} value
     */
	public set descricaoOutrosExames(value: String) {
		this._descricaoOutrosExames = value;
	}

    
    public jsonToEntity(res: any) : this{
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.ml = ConvertUtil.parseJsonParaAtributos(res.descricao,new Number());
        this.tipoAmostra = new TipoAmostra().jsonToEntity(res.tipoAmostra);
        this.prescricao = new Prescricao().jsonToEntity(res.prescricao);
        this.descricaoOutrosExames = ConvertUtil.parseJsonParaAtributos(res.descricao,new String());
		return this;
    }

}