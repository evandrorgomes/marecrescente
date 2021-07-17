import { constructor } from 'events';
import { Locus } from '../paciente/cadastro/exame/locus';
import { BaseEntidade } from '../shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class TipoExame extends BaseEntidade {

    private _id: number;
    private _descricao: string;
    private _tipoExameFase2: number;
    private _locus: Locus[];

    constructor(id?: number) {
        super();
        this._id = id;
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
     * @return {String}
     */
    public get descricao(): string {
        return this._descricao;
    }

    /**
     * Setter nome
     * @param {String} value
     */
    public set descricao(value: string) {
        this._descricao = value;
    }


    /**
     * Getter locus
     * @return {Locus[]}
     */
    public get locus(): Locus[] {
        return this._locus;
    }

    /**
     * Setter locus
     * @param {Locus[]} value
     */
    public set locus(value: Locus[]) {
        this._locus = value;

    }

    public jsonToEntity(res: any): TipoExame {
        if (res.locus) {
            this.locus = [];
            res.locus.foreach(loc => {
                this.locus.push(new Locus().jsonToEntity(loc));
            });
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

        return this;
    }

    /**
     * Getter tipoExameFase2
     * @return {number}
     */
	public get tipoExameFase2(): number {
		return this._tipoExameFase2;
	}

    /**
     * Setter tipoExameFase2
     * @param {number} value
     */
	public set tipoExameFase2(value: number) {
		this._tipoExameFase2 = value;
	}

}
