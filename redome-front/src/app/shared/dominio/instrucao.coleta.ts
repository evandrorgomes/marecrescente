import { BaseEntidade } from '../base.entidade';
import { TiposInstrucaoColeta } from '../enums/tipos.instrucao.coleta';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class InstrucaoColeta extends BaseEntidade {

    private _id: number;
    private _descricao: string;
    private _tipo: TiposInstrucaoColeta;

    constructor();
    constructor(id?: number);
    constructor(id?: number, descricao?: string) {
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

    /**
     * Getter tipo
     * @return {TiposInstrucaoColeta}
     */
	public get tipo(): TiposInstrucaoColeta {
		return this._tipo;
	}

    /**
     * Setter tipo
     * @param {TiposInstrucaoColeta} value
     */
	public set tipo(value: TiposInstrucaoColeta) {
		this._tipo = value;
	}

    public jsonToEntity(res: any): InstrucaoColeta {

        if (res.tipo) {
            this.tipo = res.tipo === TiposInstrucaoColeta.EXAME.toString() ? TiposInstrucaoColeta.EXAME : TiposInstrucaoColeta.SWAB;
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());


        return this;
    }
}
