import {BaseEntidade} from '../base.entidade';
import {ConvertUtil} from "../util/convert.util";

/**
 * Classe que representa um tipo de item de invoice
 *
 * @author Bruno Sousa
 * @export
 * @class TipoItemInvoice
 * @extends {BaseEntidade}
 */
export class TipoItemInvoice extends BaseEntidade {

   public static readonly AMOSTRA: number = 0;
   public static readonly EXAME: number = 1;
   public static readonly SEGUNDA_FASE: number = 2;

   private _id: number;
   private _descricao: string;

   constructor(id: number = null) {
      super();
      this._id = id;
   }

   /**
    *
    *
    * @type {number}
    * @memberOf TipoPendencia
    */
   public get id(): number {
      return this._id;
   }

   /**
    *
    *
    *
    * @memberOf TipoPendencia
    */
   public set id(value: number) {
      this._id = value;
   }

   /**
    *
    *
    * @type {string}
    * @memberOf TipoPendencia
    */
   public get descricao(): string {
      return this._descricao;
   }

   /**
    *
    *
    *
    * @memberOf TipoPendencia
    */
   public set descricao(value: string) {
      this._descricao = value;
   }


   public jsonToEntity(res: any): TipoItemInvoice {
      this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
      return this;
   }

}
