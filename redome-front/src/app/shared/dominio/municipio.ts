import { BaseEntidade } from 'app/shared/base.entidade';
import { UF } from './uf';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 *
 *
 * @author Bruno Sousa
 * @export
 * @class Municipio
 * @extends {BaseEntidade}
 */
export class Municipio extends BaseEntidade {

    private _id: number;
    private _descricao: string;
    private _codigoIbge: string;
    private _codigoDne: string;
    private _uf: UF;


	constructor(id?: number, siglaUf?: string) {
        super();
        this._id = id;
        if (siglaUf) {
            this._uf = new UF(siglaUf);
        }
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

    /**
     * Getter codigoIbge
     * @return {string}
     */
	public get codigoIbge(): string {
		return this._codigoIbge;
	}

    /**
     * Setter codigoIbge
     * @param {string} value
     */
	public set codigoIbge(value: string) {
		this._codigoIbge = value;
	}

    /**
     * Getter codigoDne
     * @return {string}
     */
	public get codigoDne(): string {
		return this._codigoDne;
	}

    /**
     * Setter codigoDne
     * @param {string} value
     */
	public set codigoDne(value: string) {
		this._codigoDne = value;
	}

    /**
     * Getter $uf
     * @return {UF}
     */
	public get uf(): UF {
		return this._uf;
	}

    /**
     * Setter $uf
     * @param {UF} value
     */
	public set uf(value: UF) {
		this._uf = value;
	}



    /**
     *
     *
     * @author Bruno Sousa
     * @param {*} res
     * @returns {Municipio}
     * @memberof Municipio
     */
    public jsonToEntity(res: any): Municipio {

        if (res.uf) {
            this.uf = new UF().jsonToEntity(res.uf);
        }

        this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao  = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
        this.codigoIbge  = ConvertUtil.parseJsonParaAtributos(res.codigoIbge, new String());
        this.codigoDne = ConvertUtil.parseJsonParaAtributos(res.codigoDne, new String());

        return this;
    }
}
