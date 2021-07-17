import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ArquivoPrescricaoDTO } from "./arquivo.prescricao.dto";
import { FonteCelulaDTO } from "./fonte.celula.dto";
import { TipoAmostraPrescricaoDTO } from "./tipo.amostra.prescricao.dto";

export class PrescricaoMedulaDTO extends BaseEntidade {

   private _dataColeta1: Date;
   private _dataColeta2: Date;
   private _dataLimiteWorkup1: Date;
   private _dataLimiteWorkup2: Date;
   private _fonteCelulaOpcao1: FonteCelulaDTO;
   private _quantidadeTotalOpcao1: number;
   private _quantidadePorKgOpcao1: number;
   private _quantidadeTotalOpcao2: number;
   private _quantidadePorKgOpcao2: number;
   private _fonteCelulaOpcao2: FonteCelulaDTO;
   private _fazerColeta: boolean;
   private _evolucao: EvolucaoDTO;
   private _amostras: TipoAmostraPrescricaoDTO[] = [];

   get dataColeta1(): Date {
      return this._dataColeta1;
   }

   set dataColeta1(value: Date) {
      this._dataColeta1 = value;
   }

   get dataColeta2(): Date {
      return this._dataColeta2;
   }

   set dataColeta2(value: Date) {
      this._dataColeta2 = value;
   }

   get dataLimiteWorkup1(): Date {
      return this._dataLimiteWorkup1;
   }

   set dataLimiteWorkup1(value: Date) {
      this._dataLimiteWorkup1 = value;
   }

   get dataLimiteWorkup2(): Date {
      return this._dataLimiteWorkup2;
   }

   set dataLimiteWorkup2(value: Date) {
      this._dataLimiteWorkup2 = value;
   }

   get fonteCelulaOpcao1(): FonteCelulaDTO {
      return this._fonteCelulaOpcao1;
   }

   set fonteCelulaOpcao1(value: FonteCelulaDTO) {
      this._fonteCelulaOpcao1 = value;
   }

   get quantidadeTotalOpcao1(): number {
      return this._quantidadeTotalOpcao1;
   }

   set quantidadeTotalOpcao1(value: number) {
      this._quantidadeTotalOpcao1 = value;
   }

   get quantidadePorKgOpcao1(): number {
      return this._quantidadePorKgOpcao1;
   }

   set quantidadePorKgOpcao1(value: number) {
      this._quantidadePorKgOpcao1 = value;
   }

   get fonteCelulaOpcao2(): FonteCelulaDTO {
      return this._fonteCelulaOpcao2;
   }

   set fonteCelulaOpcao2(value: FonteCelulaDTO) {
      this._fonteCelulaOpcao2 = value;
   }

   get quantidadeTotalOpcao2(): number {
      return this._quantidadeTotalOpcao2;
   }

   set quantidadeTotalOpcao2(value: number) {
      this._quantidadeTotalOpcao2 = value;
   }

   get quantidadePorKgOpcao2(): number {
      return this._quantidadePorKgOpcao2;
   }

   set quantidadePorKgOpcao2(value: number) {
      this._quantidadePorKgOpcao2 = value;
   }

   get amostras(): TipoAmostraPrescricaoDTO[] {
      return this._amostras;
   }

   set amostras(value: TipoAmostraPrescricaoDTO[]) {
      this._amostras = value;
   }

   get fazerColeta(): boolean {
      return this._fazerColeta;
   }

   set fazerColeta(value: boolean) {
      this._fazerColeta = value;
   }

	public get evolucao(): EvolucaoDTO {
		return this._evolucao;
	}

	public set evolucao(value: EvolucaoDTO) {
		this._evolucao = value;
	}


   public jsonToEntity(res: any): PrescricaoMedulaDTO {

		if (res.fonteCelulaOpcao1) {
			this.fonteCelulaOpcao1 = new FonteCelulaDTO().jsonToEntity(res.fonteCelulaOpcao1);
		}

		if (res.fonteCelulaOpcao2) {
			this.fonteCelulaOpcao2 = new FonteCelulaDTO().jsonToEntity(res.fonteCelulaOpcao2);
		}

		if(res.amostras){
			res.amostras.forEach(arquivo =>{
				this.amostras.push(new TipoAmostraPrescricaoDTO().jsonToEntity(arquivo));
			})
		}

      if (res.ultimaEvolucao) {
			this.evolucao = new EvolucaoDTO().jsonToEntity(res.ultimaEvolucao);
		}

		this.dataColeta1 = ConvertUtil.parseJsonParaAtributos(res.dataColeta1, new Date());
		this.dataColeta2 = ConvertUtil.parseJsonParaAtributos(res.dataColeta2, new Date());
		this.dataLimiteWorkup1 = ConvertUtil.parseJsonParaAtributos(res.dataLimiteWorkup1, new Date());
		this.dataLimiteWorkup2 = ConvertUtil.parseJsonParaAtributos(res.dataLimiteWorkup2, new Date());
		this.quantidadeTotalOpcao1 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalOpcao1, new Number());
		this.quantidadeTotalOpcao2 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalOpcao2, new Number());
		this.quantidadePorKgOpcao1 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao1, new Number());
		this.quantidadePorKgOpcao2 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao2, new Number());
      this.fazerColeta = ConvertUtil.parseJsonParaAtributos(res.fazerColeta, new Boolean());


		return this;
	}


}
