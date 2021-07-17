import {BaseEntidade} from "../base.entidade";
import {Invoice} from "./invoice";
import {TipoItemInvoice} from "../dominio/tipo.item.invoice";
import {TipoServico} from "../dominio/tipo.servico";
import {ConvertUtil} from "../util/convert.util";

export class ItemInvoice extends BaseEntidade {

   private _id: number;
   private _rmr: number;
   private _indevido: boolean;
   private _valor: number;
   private _tipoServico: TipoServico;
   private _invoice: number;
   private _idPedidoExame: number;
   private _idPedidoColeta: number;
   private _idDoadorInternacional: string;
   private _numeroDoPagamento: number;
   private _idPagamento:number;

   get id(): number {
      return this._id;
   }

   set id(value: number) {
      this._id = value;
   }

   get rmr(): number {
      return this._rmr;
   }

   set rmr(value: number) {
      this._rmr = value;
   }

   get indevido(): boolean {
      return this._indevido;
   }

   set indevido(value: boolean) {
      this._indevido = value;
   }

   get valor(): number {
      return this._valor;
   }

   set valor(value: number) {
      this._valor = value;
   }

   get tipoServico(): TipoServico {
      return this._tipoServico;
   }

   set tipoServico(value: TipoServico) {
      this._tipoServico = value;
   }

	public get invoice(): number {
		return this._invoice;
	}

	public set invoice(value: number) {
		this._invoice = value;
	}
   

   get idPedidoExame(): number {
      return this._idPedidoExame;
   }

   set idPedidoExame(value: number) {
      this._idPedidoExame = value;
   }

   get idPedidoColeta(): number {
      return this._idPedidoColeta;
   }

   set idPedidoColeta(value: number) {
      this._idPedidoColeta = value;
   }

   get idDoadorInternacional(): string {
      return this._idDoadorInternacional;
   }

   set idDoadorInternacional(value: string) {
      this._idDoadorInternacional = value;
   }

   get numeroDoPagamento(): number {
      return this._numeroDoPagamento;
   }

   set numeroDoPagamento(value: number) {
      this._numeroDoPagamento = value;
   }

	public get idPagamento(): number {
		return this._idPagamento;
	}

	public set idPagamento(value: number) {
		this._idPagamento = value;
	}


   jsonToEntity(res: any): ItemInvoice {

      if (res.tipoServico) {
         this.tipoServico = new TipoServico().jsonToEntity(res.tipoServico);
      }

      this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
      this.indevido = ConvertUtil.parseJsonParaAtributos(res.indevido, new Boolean());
      this.valor = ConvertUtil.parseJsonParaAtributos(res.valor, new Number());
      this.idDoadorInternacional = ConvertUtil.parseJsonParaAtributos(res.idDoadorInternacional, new String());
      this.numeroDoPagamento = ConvertUtil.parseJsonParaAtributos(res.numeroDoPagamento, new Number());
      this.idPagamento = ConvertUtil.parseJsonParaAtributos(res.valor, new Number());
      return this;
   }

}
