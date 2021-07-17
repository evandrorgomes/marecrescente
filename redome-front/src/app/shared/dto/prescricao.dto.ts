import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ArquivoPrescricaoDTO } from "./arquivo.prescricao.dto";
import { PrescricaoCordaoDTO } from "./prescricao.cordao.dto";
import { PrescricaoMedulaDTO } from "./prescricao.medula.dto";

export class PrescricaoDTO extends BaseEntidade {

   private _id: number;
   private _idTipoPrescricao: number;
   private _idDoador: number;
   private _idTipoDoador: number;
   private _rmr: number;
   private _idCentroTransplante: number;
   private _medula: PrescricaoMedulaDTO;
   private _cordao: PrescricaoCordaoDTO;
   private _arquivosPrescricao: ArquivoPrescricaoDTO[] = [];
   private _arquivosPrescricaoJustificativa: ArquivoPrescricaoDTO[] = [];

   get id(): number {
      return this._id;
   }

   set id(value: number) {
      this._id = value;
   }

   get idTipoPrescricao(): number {
      return this._idTipoPrescricao;
   }

   set idTipoPrescricao(value: number) {
      this._idTipoPrescricao = value;
   }

   get idDoador(): number {
      return this._idDoador;
   }

   set idDoador(value: number) {
      this._idDoador = value;
   }


   get idTipoDoador(): number {
      return this._idTipoDoador;
   }

   set idTipoDoador(value: number) {
      this._idTipoDoador = value;
   }

   get rmr(): number {
      return this._rmr;
   }

   set rmr(value: number) {
      this._rmr = value;
   }

   get idCentroTransplante(): number {
      return this._idCentroTransplante;
   }

   set idCentroTransplante(value: number) {
      this._idCentroTransplante = value;
   }

   get medula(): PrescricaoMedulaDTO {
      return this._medula;
   }

   set medula(value: PrescricaoMedulaDTO) {
      this._medula = value;
   }

   get cordao(): PrescricaoCordaoDTO {
      return this._cordao;
   }

   set cordao(value: PrescricaoCordaoDTO) {
      this._cordao = value;
   }

   public get arquivosPrescricao(): ArquivoPrescricaoDTO[] {
		return this._arquivosPrescricao;
	}

	public set arquivosPrescricao(value: ArquivoPrescricaoDTO[]) {
		this._arquivosPrescricao = value;
	}

	public get arquivosPrescricaoJustificativa(): ArquivoPrescricaoDTO[] {
		return this._arquivosPrescricaoJustificativa;
	}

	public set arquivosPrescricaoJustificativa(value: ArquivoPrescricaoDTO[]) {
		this._arquivosPrescricaoJustificativa = value;
	}

	public jsonToEntity(res: any): PrescricaoDTO {

		if (res.arquivosPrescricao) {
			this.arquivosPrescricao = [];
			res.arquivosPrescricao.forEach(arquivo => {
				this.arquivosPrescricao.push(new ArquivoPrescricaoDTO().jsonToEntity(arquivo));
			});
		}

		if (res.arquivosPrescricaoJustificativa) {
			this.arquivosPrescricaoJustificativa = [] ;
			res.arquivosPrescricaoJustificativa.forEach(arquivo => {
				this.arquivosPrescricaoJustificativa.push(new ArquivoPrescricaoDTO().jsonToEntity(arquivo));
			});
		}

		if (res.medula) {
			this.medula = new PrescricaoMedulaDTO().jsonToEntity(res.medula);
		}

      if (res.cordao) {
			this.cordao = new PrescricaoCordaoDTO().jsonToEntity(res.cordao);
		}

      this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this.idTipoPrescricao = ConvertUtil.parseJsonParaAtributos(res.idTipoPrescricao, new Number());
      this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
      this.idTipoDoador = ConvertUtil.parseJsonParaAtributos(res.idTipoDoador, new Number());
      this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
      this.idCentroTransplante = ConvertUtil.parseJsonParaAtributos(res.idCentroTransplante, new Number());

		return this;
	}

}
