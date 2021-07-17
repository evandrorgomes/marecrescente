import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de motivo
 * 
 * @author Bruno Sousa
 * @export
 * @class Motivo
 */
export class Motivo extends BaseEntidade {

  /**
   * Identificador único da classe
   * 
   * @private
   * @type {number}
   * @memberOf Motivo
   */
  private _id: number;

  /**
   * Descrição do motivo
   * 
   * @private
   * @type {string}
   * @memberOf Motivo
   */
  private _descricao: string;


  constructor(id?: number, descricao?: string) {
    super();
    this._id = id;
    this._descricao = descricao;
  }


	/**
     * 
     * 
     * @type {number}
     * @memberOf Motivo
     */
  public get id(): number {
    return this._id;
  }

	/**
     * 
     * 
     * 
     * @memberOf Motivo
     */
  public set id(value: number) {
    this._id = value;
  }

	/**
     * 
     * 
     * @type {string}
     * @memberOf Motivo
     */
  public get descricao(): string {
    return this._descricao;
  }

	/**
     * 
     * 
     * 
     * @memberOf Motivo
     */
  public set descricao(value: string) {
    this._descricao = value;
  }


  public jsonToEntity(res:any): Motivo{

    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());		
    
    return this;
	}
}