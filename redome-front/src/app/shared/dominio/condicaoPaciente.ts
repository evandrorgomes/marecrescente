import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe Bean utilizada para definir os campos de CondicaoPaciente
 * 
 * @author Bruno Sousa
 * @export
 * @class CondicaoPaciente
 */
export class CondicaoPaciente extends BaseEntidade {

  /**
   * Identificador único da classe
   * 
   * @private
   * @type {number}
   * @memberOf CondicaoPaciente
   */
  private _id: number;

  /**
   * Descrição da Condição do Paciente
   * 
   * @private
   * @type {string}
   * @memberOf CondicaoPaciente
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
     * @memberOf CondicaoPaciente
     */
  public get id(): number {
    return this._id;
  }

	/**
     * 
     * 
     * 
     * @memberOf CondicaoPaciente
     */
  public set id(value: number) {
    this._id = value;
  }

	/**
     * 
     * 
     * @type {string}
     * @memberOf CondicaoPaciente
     */
  public get descricao(): string {
    return this._descricao;
  }

	/**
     * 
     * 
     * 
     * @memberOf CondicaoPaciente
     */
  public set descricao(value: string) {
    this._descricao = value;
  }

  public jsonToEntity(res:any): CondicaoPaciente {
    
    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
    
		return this;
	}

}