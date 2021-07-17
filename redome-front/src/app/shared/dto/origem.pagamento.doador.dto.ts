import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class OrigemPagamentoDoadorDTO extends BaseEntidade{

   private _idRegistroOrigem: number;
   private _idRegistroPagamento: number;
   private _idDoador: number;

    /**
     * Getter idRegistroOrigem
     * @return {number}
     */
	public get idRegistroOrigem(): number {
		return this._idRegistroOrigem;
	}

    /**
     * Getter idRegistroPagamento
     * @return {number}
     */
	public get idRegistroPagamento(): number {
		return this._idRegistroPagamento;
	}

    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Setter idRegistroOrigem
     * @param {number} value
     */
	public set idRegistroOrigem(value: number) {
		this._idRegistroOrigem = value;
	}

    /**
     * Setter idRegistroPagamento
     * @param {number} value
     */
	public set idRegistroPagamento(value: number) {
		this._idRegistroPagamento = value;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

   jsonToEntity(res: any): OrigemPagamentoDoadorDTO {
      this.idRegistroOrigem = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this.idRegistroPagamento = ConvertUtil.parseJsonParaAtributos(res.idRegistroPagamento, new Number());
      this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
      
		return this;
   }
}
