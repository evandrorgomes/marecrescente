import { BaseEntidade } from 'app/shared/base.entidade';
import { Locus } from 'app/paciente/cadastro/exame/locus';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class MismatchDTO extends BaseEntidade {

    private _rmr: number;
    private _aceiteMismatch: number;
    private _locusMismatch: Locus[] = [];

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Getter aceiteMismatch
     * @return {number}
     */
	public get aceiteMismatch(): number {
		return this._aceiteMismatch;
	}

    /**
     * Setter aceiteMismatch
     * @param {number} value
     */
	public set aceiteMismatch(value: number) {
		this._aceiteMismatch = value;
	}

    /**
     * Getter locusMismatch
     * @return {Locus[] }
     */
	public get locusMismatch(): Locus[]  {
		return this._locusMismatch;
	}

    /**
     * Setter locusMismatch
     * @param {Locus[] } value
     */
	public set locusMismatch(value: Locus[] ) {
		this._locusMismatch = value;
	}

    public jsonToEntity(res: any): MismatchDTO {

        if (res.locusMismatch) {
			this.locusMismatch = [];
			res.locusMismatch.forEach(locus => {
				this.locusMismatch.push(new Locus().jsonToEntity(locus));
			});
		}

        this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
        this.aceiteMismatch = ConvertUtil.parseJsonParaAtributos(res.aceiteMismatch, new Number());

        return this;
    }

}