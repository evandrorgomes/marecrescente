import {BaseEntidade} from '../base.entidade';
import {ConvertUtil} from "../util/convert.util";
import {TipoItemInvoice} from "./tipo.item.invoice";

/**
* Classe que representa um tipo de servico
*
* @author Bruno Sousa
* @export
* @class TipoServico
* @extends {BaseEntidade}
*/
export class TipoServico extends BaseEntidade {

   private _id: number;
   private _sigla: string;
   private _descricao: string;
   private _tipo: TipoItemInvoice;

   constructor(id: number = null) {
      super();
      this._id = id;
   }

   /**
    *
    * @returns {number}
    */
   public get id(): number {
      return this._id;
   }


   /**
    *
    * @param value
    */
   public set id(value: number) {
      this._id = value;
   }


   /**
    *
    * @returns {string}
    */
   get sigla(): string {
      return this._sigla;
   }

   /**
    *
    * @param value
    */
   set sigla(value: string) {
      this._sigla = value;
   }

   /**
    *
    * @type {string}
    */
   public get descricao(): string {
      return this._descricao;
   }

   /**
    *
    * @memberOf TipoPendencia
    */
   public set descricao(value: string) {
      this._descricao = value;
   }

   /**
    *
    * @returns {TipoItemInvoice}
    */
   get tipo(): TipoItemInvoice {
      return this._tipo;
   }

   /**
    *
    * @param value
    */
   set tipo(value: TipoItemInvoice) {
      this._tipo = value;
   }

   public jsonToEntity(res: any): TipoServico {

      if (res.tipo) {
         this._tipo = new TipoItemInvoice().jsonToEntity(res.tipo);
      }

      this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this._sigla = ConvertUtil.parseJsonParaAtributos(res.sigla, new String());
      this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
      return this;
   }

}
