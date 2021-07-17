import { BaseEntidade } from "../../../shared/base.entidade";

/**
 * Classe domínio de Locus
 * @author Fillipe Queiroz
 * @export
 * @class Locus
 * @extends {BaseEntidade}
 */
export class LocusVO extends BaseEntidade {

  public static LOCI_A : string = "A";
  public static LOCI_B : string = "B";
  public static LOCI_C : string = "C";  
  public static LOCI_DRB1 : string = "DRB1";
  public static LOCI_DQB1 : string = "DQB1";

    private _codigo: String;
    private _primeiroAlelo: String;
    private _segundoAlelo: String;

	/**
     * 
     * 
     * @type {String}
     * @memberof LocusVO
     */
    public get codigo(): String {
      return this._codigo;
    }

	/**
     * 
     * 
     * 
     * @memberof LocusVO
     */
    public set codigo(value: String) {
      this._codigo = value;
    }

	/**
     * Retorna o valor do primeiro alelo.
     * 
     * @type {String}
     * @memberof LocusVO
     */
    public get primeiroAlelo(): String {
      return this._primeiroAlelo;
    }

	/**
     * Seta o valor do primeiro alelo.
     * 
     * @memberof LocusVO
     */
    public set primeiroAlelo(value: String) {
      this._primeiroAlelo = value;
    }

  	/**
     * Retorna o valor do segundo alelo.
     * 
     * @type {String}
     * @memberof LocusVO
     */
    public get segundoAlelo(): String {
      return this._segundoAlelo;
    }

	/**
     * Seta o valor do segundo alelo.
     * 
     * @memberof LocusVO
     */
    public set segundoAlelo(value: String) {
      this._segundoAlelo = value;
    }

    public jsonToEntity(res: any) {
      throw new Error("Method not implemented.");
  }

}