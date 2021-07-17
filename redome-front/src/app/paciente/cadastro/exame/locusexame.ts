import { LocusExamePk } from './locusexamepk';
import { Locus } from './locus';
import { BaseEntidade } from "../../../shared/base.entidade";
import { Exame } from './exame.';
import { ConvertUtil } from '../../../shared/util/convert.util';

/**
 * Classe de dominio de LocusExame
 * @author Filipe Paes
 * @export
 * @class LocusExame
 * @extends {BaseEntidade}
 */
export class LocusExame extends BaseEntidade {

  public static LOCUS_BLANK: string = "-";

  /**
   * Referencia para chave estrangeira de locusexamepk
   * 
   * @private
   * @type {LocusExamePk}@memberof LocusExame
   */
  private _id: LocusExamePk;

  /**
   * Exame ao qual estão vinculados os resultados de HLA 
   * @private
   * @type {Exame}@memberof LocusExame
   */
  private _exame: Exame;

  /**
   * Resultado do primeiro Alelo
   * @private
   * @type {string}@memberof LocusExame
   */
  private _primeiroAlelo: string;

  /**
   * Resultado do segundo Alelo
   * @private
   * @type {string}@memberof LocusExame
   */
  private _segundoAlelo: string;

  private _primeiroAleloDivergente: boolean;

  private _segundoAleloDivergente: boolean;

  private _inativo: boolean;

  private _primeiroAleloComposicao: string;

  private _segundoAleloComposicao: string;

  
  constructor(codigo?: string, primeiroAlelo?: string,  segundoAlelo?: string) {
    super();
    this.id = new LocusExamePk();
    this.id.locus = new Locus();
    this.id.locus.codigo = codigo;
    this._primeiroAlelo = primeiroAlelo;
    this._segundoAlelo = segundoAlelo;
  }

  

	/**
   * 
   * 
   * @type {LocusExamePk}@memberof LocusExame
   */
  public get id(): LocusExamePk {
    return this._id;
  }

	/**
   * 
   * 
   * @memberof LocusExame
   */
  public set id(value: LocusExamePk) {
    this._id = value;
  }

	/**
     * 
     * 
     * @type {Exame}@memberof LocusExame
     */
  public get exame(): Exame {
    return this._exame;
  }

	/**
     * 
     * 
     * @memberof LocusExame
     */
  public set exame(value: Exame) {
    this._exame = value;
  }

	/**
     * 
     * 
     * @type {string}@memberof LocusExame
     */
  public get primeiroAlelo(): string {
    return this._primeiroAlelo;
  }

	/**
     * 
     * 
     * @memberof LocusExame
     */
  public set primeiroAlelo(value: string) {
    this._primeiroAlelo = value;
  }

	/**
     * 
     * 
     * @type {string}@memberof LocusExame
     */
  public get segundoAlelo(): string {
    return this._segundoAlelo;
  }

	/**
     * 
     * 
     * @memberof LocusExame
     */
  public set segundoAlelo(value: string) {
    this._segundoAlelo = value;
  }

    /**
     * Getter primeiroAleloDivergente
     * @return {boolean}
     */
	public get primeiroAleloDivergente(): boolean {
		return this._primeiroAleloDivergente;
	}

    /**
     * Setter primeiroAleloDivergente
     * @param {boolean} value
     */
	public set primeiroAleloDivergente(value: boolean) {
		this._primeiroAleloDivergente = value;
	}

    /**
     * Getter segundoAleloDivergente
     * @return {boolean}
     */
	public get segundoAleloDivergente(): boolean {
		return this._segundoAleloDivergente;
	}

    /**
     * Setter segundoAleloDivergente
     * @param {boolean} value
     */
	public set segundoAleloDivergente(value: boolean) {
		this._segundoAleloDivergente = value;
  }

    /**
     * Getter inativo
     * @return {boolean}
     */
	public get inativo(): boolean {
		return this._inativo;
	}

    /**
     * Setter inativo
     * @param {boolean} value
     */
	public set inativo(value: boolean) {
		this._inativo = value;
  }

    /**
     * Getter primeiroAleloComposicao
     * @return {string}
     */
	public get primeiroAleloComposicao(): string {
		return this._primeiroAleloComposicao;
	}

    /**
     * Setter primeiroAleloComposicao
     * @param {string} value
     */
	public set primeiroAleloComposicao(value: string) {
		this._primeiroAleloComposicao = value;
	}

    /**
     * Getter segundoAleloComposicao
     * @return {string}
     */
	public get segundoAleloComposicao(): string {
		return this._segundoAleloComposicao;
	}

    /**
     * Setter segundoAleloComposicao
     * @param {string} value
     */
	public set segundoAleloComposicao(value: string) {
		this._segundoAleloComposicao = value;
	}
  
  
  public jsonToEntity(res: any):this {
    this.id = new LocusExamePk().jsonToEntity(res.id);
    this.primeiroAlelo = ConvertUtil.parseJsonParaAtributos(res.primeiroAlelo, new String());
    this.segundoAlelo = ConvertUtil.parseJsonParaAtributos(res.segundoAlelo, new String());
    this.primeiroAleloDivergente = ConvertUtil.parseJsonParaAtributos(res.primeiroAleloDivergente, new Boolean());
    this.segundoAleloDivergente = ConvertUtil.parseJsonParaAtributos(res.segundoAleloDivergente, new Boolean());
    this.inativo = ConvertUtil.parseJsonParaAtributos(res.inativo, new Boolean());
    
    this.primeiroAleloComposicao = ConvertUtil.parseJsonParaAtributos(res.primeiroAleloComposicao, new String());
    this.segundoAleloComposicao = ConvertUtil.parseJsonParaAtributos(res.segundoAleloComposicao, new String());


    return this;
  }
}