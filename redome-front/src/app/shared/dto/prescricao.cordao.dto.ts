import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ArquivoPrescricaoDTO } from "./arquivo.prescricao.dto";

export class PrescricaoCordaoDTO extends BaseEntidade {

   private _dataInfusao: Date;
   private _dataReceber1: Date;
   private _dataReceber2: Date;
   private _dataReceber3: Date;
   private _armazenaCordao: boolean;
   private _nomeContatoParaReceber: string;
   private _nomeContatoUrgente: string;
   private _codigoAreaUrgente: string;
   private _telefoneUrgente: number;
   private _quantidadePorKgOpcao1: number;
   private _quantidadePorKgOpcao2: number;

   get dataInfusao(): Date {
      return this._dataInfusao;
   }

   set dataInfusao(value: Date) {
      this._dataInfusao = value;
   }

   get dataReceber1(): Date {
      return this._dataReceber1;
   }

   set dataReceber1(value: Date) {
      this._dataReceber1 = value;
   }

   get dataReceber2(): Date {
      return this._dataReceber2;
   }

   set dataReceber2(value: Date) {
      this._dataReceber2 = value;
   }

   get dataReceber3(): Date {
      return this._dataReceber3;
   }

   set dataReceber3(value: Date) {
      this._dataReceber3 = value;
   }

   get armazenaCordao(): boolean {
      return this._armazenaCordao;
   }

   set armazenaCordao(value: boolean) {
      this._armazenaCordao = value;
   }

   get nomeContatoParaReceber(): string {
      return this._nomeContatoParaReceber;
   }

   set nomeContatoParaReceber(value: string) {
      this._nomeContatoParaReceber = value;
   }

   get nomeContatoUrgente(): string {
      return this._nomeContatoUrgente;
   }

   set nomeContatoUrgente(value: string) {
      this._nomeContatoUrgente = value;
   }

   get codigoAreaUrgente(): string {
      return this._codigoAreaUrgente;
   }

   set codigoAreaUrgente(value: string) {
      this._codigoAreaUrgente = value;
   }

   get telefoneUrgente(): number {
      return this._telefoneUrgente;
   }

   set telefoneUrgente(value: number) {
      this._telefoneUrgente = value;
   }

    /**
     * Getter quantidadePorKgOpcao1
     * @return {number}
     */
    public get quantidadePorKgOpcao1(): number {
		return this._quantidadePorKgOpcao1;
	}

    /**
     * Setter quantidadePorKgOpcao1
     * @param {number} value
     */
	public set quantidadePorKgOpcao1(value: number) {
		this._quantidadePorKgOpcao1 = value;
	}

/**
     * Getter quantidadePorKgOpcao2
     * @return {number}
     */
	public get quantidadePorKgOpcao2(): number {
		return this._quantidadePorKgOpcao2;
	}

    /**
     * Setter quantidadePorKgOpcao2
     * @param {number} value
     */
	public set quantidadePorKgOpcao2(value: number) {
		this._quantidadePorKgOpcao2 = value;
	}

	public jsonToEntity(res: any): PrescricaoCordaoDTO {

		this.dataInfusao = ConvertUtil.parseJsonParaAtributos(res.dataInfusao, new Date());
		this.dataReceber1 = ConvertUtil.parseJsonParaAtributos(res.dataReceber1, new Date());
		this.dataReceber2 = ConvertUtil.parseJsonParaAtributos(res.dataReceber2, new Date());
		this.dataReceber3 = ConvertUtil.parseJsonParaAtributos(res.dataReceber3, new Date());
		this.armazenaCordao = ConvertUtil.parseJsonParaAtributos(res.armazenaCordao, new Boolean());
		this.nomeContatoParaReceber = ConvertUtil.parseJsonParaAtributos(res.nomeContatoParaReceber, new String());
		this.nomeContatoUrgente = ConvertUtil.parseJsonParaAtributos(res.nomeContatoUrgente, new String());
		this.codigoAreaUrgente = ConvertUtil.parseJsonParaAtributos(res.codigoAreaUrgente, new Number());
		this.telefoneUrgente = ConvertUtil.parseJsonParaAtributos(res.telefoneUrgente, new Number());

      this.quantidadePorKgOpcao1 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao1, new Number());
      this.quantidadePorKgOpcao2 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao2, new Number());

		return this;
	}

}
