import { BaseEntidade } from "../base.entidade";
import { BuscaPreliminar } from "app/shared/model/busca.preliminar";
import { ConvertUtil } from "app/shared/util/convert.util";

export class LocusExamePreliminar extends BaseEntidade {

    private _id: number;
    private _locus: string;
    private _primeiroAlelo: string;
    private _segundoAlelo: string;
    private _buscaPreliminar: BuscaPreliminar;

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
     * Getter locus
     * @return {string}
     */
	public get locus(): string {
		return this._locus;
	}

    /**
     * Setter locus
     * @param {string} value
     */
	public set locus(value: string) {
		this._locus = value;
	}

    /**
     * Getter primeiroAlelo
     * @return {string}
     */
	public get primeiroAlelo(): string {
		return this._primeiroAlelo;
	}

    /**
     * Setter primeiroAlelo
     * @param {string} value
     */
	public set primeiroAlelo(value: string) {
		this._primeiroAlelo = value;
	}

    /**
     * Getter segundoAlelo
     * @return {string}
     */
	public get segundoAlelo(): string {
		return this._segundoAlelo;
	}

    /**
     * Setter segundoAlelo
     * @param {string} value
     */
	public set segundoAlelo(value: string) {
		this._segundoAlelo = value;
	}

    /**
     * Getter buscaPreliminar
     * @return {BuscaPreliminar}
     */
	public get buscaPreliminar(): BuscaPreliminar {
		return this._buscaPreliminar;
	}

    /**
     * Setter buscaPreliminar
     * @param {BuscaPreliminar} value
     */
	public set buscaPreliminar(value: BuscaPreliminar) {
		this._buscaPreliminar = value;
	}

    public jsonToEntity(res: any): LocusExamePreliminar {

        if (res.buscaPreliminar) {
            this.buscaPreliminar = new BuscaPreliminar().jsonToEntity(res.buscaPreliminar);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.locus = ConvertUtil.parseJsonParaAtributos(res.locus, new String());
        this.primeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.primeiroAlelo, new String());
        this.segundoAlelo = ConvertUtil.parseJsonParaAtributos(res.segundoAlelo, new String());

        return this;
    }

}