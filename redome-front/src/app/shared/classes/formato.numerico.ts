import { BaseEntidade } from 'app/shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class FormatoNumerico extends BaseEntidade {
    
    private _alinhamento: string;
	private _permitirNegativo: boolean;
	private _separadorDecimal: string;
	private _casasDecimais: number;
	private _prefixo: string;
	private _sufixo: string;
    private _separadorMilhares: string;
    

    /**
     * Getter alinhamento
     * @return {string}
     */
	public get alinhamento(): string {
		return this._alinhamento;
	}

    /**
     * Setter alinhamento
     * @param {string} value
     */
	public set alinhamento(value: string) {
		this._alinhamento = value;
	}

    /**
     * Getter permitirNegativo
     * @return {boolean}
     */
	public get permitirNegativo(): boolean {
		return this._permitirNegativo;
	}

    /**
     * Setter permitirNegativo
     * @param {boolean} value
     */
	public set permitirNegativo(value: boolean) {
		this._permitirNegativo = value;
	}
    
    /**
     * Getter separadorDecimal
     * @return {string}
     */
	public get separadorDecimal(): string {
		return this._separadorDecimal;
	}

    /**
     * Setter separadorDecimal
     * @param {string} value
     */
	public set separadorDecimal(value: string) {
		this._separadorDecimal = value;
    }

    /**
     * Getter casasDecimais
     * @return {number}
     */
	public get casasDecimais(): number {
		return this._casasDecimais;
	}

    /**
     * Setter casasDecimais
     * @param {number} value
     */
	public set casasDecimais(value: number) {
		this._casasDecimais = value;
	}

    /**
     * Getter prefixo
     * @return {string}
     */
	public get prefixo(): string {
		return this._prefixo;
	}

    /**
     * Setter prefixo
     * @param {string} value
     */
	public set prefixo(value: string) {
		this._prefixo = value;
	}

    /**
     * Getter sufixo
     * @return {string}
     */
	public get sufixo(): string {
		return this._sufixo;
	}

    /**
     * Setter sufixo
     * @param {string} value
     */
	public set sufixo(value: string) {
		this._sufixo = value;
	}

    /**
     * Getter separadorMilhares
     * @return {string}
     */
	public get separadorMilhares(): string {
		return this._separadorMilhares;
	}

    /**
     * Setter separadorMilhares
     * @param {string} value
     */
	public set separadorMilhares(value: string) {
		this._separadorMilhares = value;
    }
    
    public jsonToEntity(res: any): FormatoNumerico {

        this.alinhamento        = ConvertUtil.parseJsonParaAtributos(res.alinhamento, new String());
        this.permitirNegativo   = ConvertUtil.parseJsonParaAtributos(res.permitirNegativo, new Boolean());
	    this.separadorDecimal   = ConvertUtil.parseJsonParaAtributos(res.separadorDecimal, new String());
	    this.casasDecimais      = ConvertUtil.parseJsonParaAtributos(res.casasDecimais, new Number());
	    this.prefixo            = ConvertUtil.parseJsonParaAtributos(res.prefixo, new String());
	    this.sufixo             = ConvertUtil.parseJsonParaAtributos(res.sufixo, new String());
        this.separadorMilhares  = ConvertUtil.parseJsonParaAtributos(res.separadorMilhares, new String());

        return this;
    }
    

}