import {BaseEntidade} from "../base.entidade";
import {StatusInvoice} from "../dominio/status.invoice";
import {ItemInvoice} from "./item.invoice";
import {ConvertUtil} from "../util/convert.util";

export class Invoice extends BaseEntidade {

   private _id: number;
   private _numero: number;
   private _dataVencimento: Date;
   private _dataFaturamento: Date;
   private _status: StatusInvoice;
   private _itens: ItemInvoice[];

   get id(): number {
      return this._id;
   }

   set id(value: number) {
      this._id = value;
   }

   get numero(): number {
      return this._numero;
   }

   set numero(value: number) {
      this._numero = value;
   }

   get dataVencimento(): Date {
      return this._dataVencimento;
   }

   set dataVencimento(value: Date) {
      this._dataVencimento = value;
   }

   get dataFaturamento(): Date {
      return this._dataFaturamento;
   }

   set dataFaturamento(value: Date) {
      this._dataFaturamento = value;
   }

   get status(): StatusInvoice {
      return this._status;
   }

   set status(value: StatusInvoice) {
      this._status = value;
   }

   get itens(): ItemInvoice[] {
      return this._itens;
   }

   set itens(value: ItemInvoice[]) {
      this._itens = value;
   }

   jsonToEntity(res: any): Invoice {

      if (res.status) {
         this.status = new StatusInvoice().jsonToEntity(res.status);
      }
      if (res.itens) {
         this.itens = [];
         res.itens.forEach(itemInvoice => {
            this.itens.push(new ItemInvoice().jsonToEntity(itemInvoice));
         })
      }

      this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this.numero = ConvertUtil.parseJsonParaAtributos(res.numero, new Number());
      this.dataVencimento = ConvertUtil.parseJsonParaAtributos(res.dataVencimento, new Date());
      this.dataFaturamento = ConvertUtil.parseJsonParaAtributos(res.dataFaturamento, new Date());

      return this;
   }

}
