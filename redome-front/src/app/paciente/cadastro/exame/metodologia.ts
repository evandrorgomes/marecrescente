import { BaseEntidade } from "../../../shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe de domínio para as várias metodologias utilizadas em exames de HLA 
 * @author Filipe Paes
 * @export
 * @class Metodologia
 * @extends {BaseEntidade}
 */
export class Metodologia extends BaseEntidade {

  /**
   * Id de metodologia
   * @private
   * @type {number}@memberof Metodologia
   */
  private _id: number;

  /**
   * Sigla de metodologia
   * @private
   * @type {string}@memberof Metodologia
   */
  private _sigla: string;

  /**
   * Descrição de metodologia
   * @private
   * @type {string}@memberof Metodologia
   */
  private _descricao: string;


	/**
     * 
     * @type {number}@memberof Metodologia
     */
  public get id(): number {
    return this._id;
  }

	/**
     * 
     * 
     * @memberof Metodologia
     */
  public set id(value: number) {
    this._id = value;
  }

	/**
     * 
     * 
     * @type {string}@memberof Metodologia
     */
  public get sigla(): string {
    return this._sigla;
  }

	/**
     * 
     * @memberof Metodologia
     */
  public set sigla(value: string) {
    this._sigla = value;
  }

	/**
     * 
     * 
     * @type {string}@memberof Metodologia
     */
  public get descricao(): string {
    return this._descricao;
  }

	/**
     * 
     * 
     * @memberof Metodologia
     */
  public set descricao(value: string) {
    this._descricao = value;
  }

  public jsonToEntity(res: any): Metodologia {
    
    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.sigla = ConvertUtil.parseJsonParaAtributos(res.sigla, new String());
    this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

		return this;
	}

}