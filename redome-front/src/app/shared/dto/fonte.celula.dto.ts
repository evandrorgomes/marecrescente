import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de Fonte de Celular
 * @author ergomes
 */
export class FonteCelulaDTO extends BaseEntidade {

  private _id: number;
  private _sigla: string;
  private _descricao: string;

  /**
   * Método construtor.
   * @param object Objeto do tipo etnia
   * @author Fillipe Queiroz
   */
  constructor(id?: number, sigla?: string, descricao?: string) {
    super();
    this._id = id;
    this._sigla = sigla;
    this._descricao = descricao;
  }

	/**
     * 
     * 
     * @type {number}
     * @memberOf Cid
     */
  public get id(): number {
    return this._id;
  }

	/**
	 * 
	 * @memberOf Etnia
	 */
  public set id(value: number) {
    this._id = value;
  }

  /**
   * 
   * 
   * @type {string}
   * @memberof FonteCelula
  */
  public get sigla(): string {
    return this._sigla;
  }

  /**
   * 
   * 
   * @memberof FonteCelula
   */
  public set sigla(value: string) {
    this._sigla = value;
  }

  /**
   * 
   * 
   * @type {string}
   * @memberof FonteCelula
   */
  public get descricao(): string {
    return this._descricao;
  }

  /**
   * 
   * 
   * @memberof FonteCelula
   */
  public set descricao(value: string) {
    this._descricao = value;
  }
  public jsonToEntity(res: any): this {

    this.id         = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.sigla      = ConvertUtil.parseJsonParaAtributos(res.sigla, new String());
    this.descricao  = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

    return this;
  }

}