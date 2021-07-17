import { Cid } from './cid';
import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe Bean utilizada para definir os campos de cid
 * 
 * @author Bruno Sousa
 * @export
 * @class EstagioDoenca
 */
export class EstagioDoenca extends BaseEntidade {

  /**
   * Identificador único da classe
   * 
   * @private
   * @type {number}
   * @memberOf EstagioDoenca
   */
  private _id: number;

  /**
   * Descrição do estágio da doença
   * 
   * @private
   * @type {string}
   * @memberOf EstagioDoenca
   */
  private _descricao: string;

  /**
   * CID associada á esse estágio
   * 
   * @private
   * @type {Cid}
   * @memberOf EstagioDoenca
   */
  private _cid: Cid;

  constructor(id?: number, descricao?: string, cid?: Cid) {
    super();
    this._id = id;
    this._descricao = descricao;
    this._cid = cid;
  }


	/**
     * 
     * 
     * @type {number}
     * @memberOf EstagioDoenca
     */
  public get id(): number {
    return this._id;
  }

  /**
   * 
   * 
   * 
   * @memberOf EstagioDoenca
   */
  public set id(value: number) {
    this._id = value;
  }


	/**
     * 
     * 
     * @type {string}
     * @memberOf EstagioDoenca
     */
  public get descricao(): string {
    return this._descricao;
  }

	/**
     * 
     * 
     * 
     * @memberOf EstagioDoenca
     */
  public set descricao(value: string) {
    this._descricao = value;
  }

	/**
     * 
     * 
     * @type {Cid}
     * @memberOf EstagioDoenca
     */
  public get cid(): Cid {
    return this._cid;
  }

	/**
     * 
     * 
     * 
     * @memberOf EstagioDoenca
     */
  public set cid(value: Cid) {
    this._cid = value;
  }

  public jsonToEntity(res:any): EstagioDoenca{

    if (res.cid) {
      this.cid = new Cid().jsonToEntity(res.cid);
    }

    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		
		return this;
	}

}