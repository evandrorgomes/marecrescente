import { ConvertUtil } from "app/shared/util/convert.util";
import { BaseEntidade } from "../shared/base.entidade";


/**
 * Classe que representa um genotipo, associada ao paciente.
 * @author Fillipe Queiroz
 */
export class GenotipoDTO extends BaseEntidade {

	private _locus: string;
	private _primeiroAlelo: string;
	private _segundoAlelo: string;
	private _examePrimeiroAlelo: string;
	private _exameSegundoAlelo: string;
	private _tipoPrimeiroAlelo: number;
	private _tipoSegundoAlelo: number;
	private _qualificacaoAlelo: string;
	private _ordem: number;
	private _divergentePrimeiroAlelo: boolean;
  private _divergenteSegundoAlelo: boolean;
  private _probabilidade: string;

	/**
	 * Recupera o codigo do locus
	 */
	public get locus(): string {
		return this._locus;
	}

	/**
	 * Seta o codigo do locus
	 */
	public set locus(value: string) {
		this._locus = value;
	}

	/**
	 * Primeiro alelo
	 */
	public get primeiroAlelo(): string {
		return this._primeiroAlelo;
	}

	/**
	 * Primeiro alelo
	 */
	public set primeiroAlelo(value: string) {
		this._primeiroAlelo = value;
	}

	/**
	 * Segundo alelo
	 */
	public get segundoAlelo(): string {
		return this._segundoAlelo;
	}

	/**
	 * Segundo alelo
	 */
	public set segundoAlelo(value: string) {
		this._segundoAlelo = value;
	}


	/**
	 * Recupera o exame do primeiro alelo em string.
	 */
	public get examePrimeiroAlelo(): string {
		return this._examePrimeiroAlelo;
	}

	/**
	 * Seta o exame do primeiro alelo em string.
	 */
	public set examePrimeiroAlelo(value: string) {
		this._examePrimeiroAlelo = value;
	}

	/**
	 * Recupera o exame do segundo alelo em string.
	 */
	public get exameSegundoAlelo(): string {
		return this._exameSegundoAlelo;
	}

	/**
	 * Seta o exame do segundo alelo em string.
	 */
	public set exameSegundoAlelo(value: string) {
		this._exameSegundoAlelo = value;
	}


    /**
     * Getter tipoPrimeiroAlelo
     * @return {number}
     */
	public get tipoPrimeiroAlelo(): number {
		return this._tipoPrimeiroAlelo;
	}

    /**
     * Setter tipoPrimeiroAlelo
     * @param {number} value
     */
	public set tipoPrimeiroAlelo(value: number) {
		this._tipoPrimeiroAlelo = value;
	}

    /**
     * Getter tipoSegundoAlelo
     * @return {number}
     */
	public get tipoSegundoAlelo(): number {
		return this._tipoSegundoAlelo;
	}

    /**
     * Setter tipoSegundoAlelo
     * @param {number} value
     */
	public set tipoSegundoAlelo(value: number) {
		this._tipoSegundoAlelo = value;
	}

    /**
     * Getter ordem
     * @return {number}
     */
	public get ordem(): number {
		return this._ordem;
	}

    /**
     * Setter ordem
     * @param {number} value
     */
	public set ordem(value: number) {
		this._ordem = value;
	}


    /**
     * Getter divergentePrimeiroAlelo
     * @return {boolean}
     */
	public get divergentePrimeiroAlelo(): boolean {
		return this._divergentePrimeiroAlelo;
	}

    /**
     * Setter divergentePrimeiroAlelo
     * @param {boolean} value
     */
	public set divergentePrimeiroAlelo(value: boolean) {
		this._divergentePrimeiroAlelo = value;
	}

    /**
     * Getter divergenteSegundoAlelo
     * @return {boolean}
     */
	public get divergenteSegundoAlelo(): boolean {
		return this._divergenteSegundoAlelo;
	}

    /**
     * Setter divergenteSegundoAlelo
     * @param {boolean} value
     */
	public set divergenteSegundoAlelo(value: boolean) {
		this._divergenteSegundoAlelo = value;
	}


    /**
     * Getter qualificacaoAlelo
     * @return {string}
     */
	public get qualificacaoAlelo(): string {
		return this._qualificacaoAlelo;
	}

    /**
     * Setter qualificacaoAlelo
     * @param {string} value
     */
	public set qualificacaoAlelo(value: string) {
		this._qualificacaoAlelo = value;
  }


    /**
     * Getter probabilidade
     * @return {string}
     */
	public get probabilidade(): string {
		return this._probabilidade;
	}

    /**
     * Setter probabilidade
     * @param {string} value
     */
	public set probabilidade(value: string) {
		this._probabilidade = value;
	}


	public jsonToEntity(res: any): GenotipoDTO {

		this.locus = ConvertUtil.parseJsonParaAtributos(res.locus, new String());
		this.primeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.primeiroAlelo, new String());
		this.segundoAlelo = ConvertUtil.parseJsonParaAtributos(res.segundoAlelo, new String());
		this.examePrimeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.examePrimeiroAlelo, new String());
		this.exameSegundoAlelo = ConvertUtil.parseJsonParaAtributos(res.exameSegundoAlelo, new String());
		this.tipoPrimeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.tipoPrimeiroAlelo, new Number());
		this.tipoSegundoAlelo = ConvertUtil.parseJsonParaAtributos(res.tipoSegundoAlelo, new Number());
		this.qualificacaoAlelo = ConvertUtil.parseJsonParaAtributos(res.qualificacaoAlelo, new String());
		this.ordem = ConvertUtil.parseJsonParaAtributos(res.ordem, new Number());
		this.divergentePrimeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.divergentePrimeiroAlelo, new Boolean());
		this.divergenteSegundoAlelo = ConvertUtil.parseJsonParaAtributos(res.divergenteSegundoAlelo, new Boolean());
    this.probabilidade = ConvertUtil.parseJsonParaAtributos(res.probabilidade, new String());

		return this;

    }

}

