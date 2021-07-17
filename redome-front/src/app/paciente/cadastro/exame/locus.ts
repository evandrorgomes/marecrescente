import { BaseEntidade } from "../../../shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe domínio de Locus
 * @author Filipe Paes
 * @export
 * @class Locus
 * @extends {BaseEntidade}
 */
export class Locus extends BaseEntidade {

  constructor(codigo?: string) {
    super();
    this._codigo = codigo;
  }

  /**
   * Código do Locus
   * 
   * @private
   * @type {string}@memberof Locus
   */
  private _codigo: string;

  /**
   * Ordenacao do locus
   * 
   * @private
   * @type {number}@memberof Locus
   */
  private _ordem: number;

	/**
     * 
     * 
     * @type {string}@memberof Locus
     */
  public get codigo(): string {
    return this._codigo;
  }

	/**
     * 
     * 
     * @memberof Locus
     */
  public set codigo(value: string) {
    this._codigo = value;
  }

	/**
   * 
   * 
   * @type {number}@memberof Locus
   */
  public get ordem(): number {
    return this._ordem;
  }

	/**
   * 
   * 
   * @memberof Locus
   */
  public set ordem(value: number) {
    this._ordem = value;
  }


  public jsonToEntity(res: any): Locus {
    
    this.codigo = ConvertUtil.parseJsonParaAtributos(res.codigo, new String());
    this.ordem = ConvertUtil.parseJsonParaAtributos(res.ordem, new Number());
    
    return this;
  }
}