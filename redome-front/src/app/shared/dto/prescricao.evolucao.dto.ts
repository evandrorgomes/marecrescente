import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ArquivoPrescricaoDTO } from "./arquivo.prescricao.dto";
import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { PrescricaoDTO } from "./prescricao.dto";

export class PrescricaoEvolucaoDTO extends BaseEntidade {

   private _prescricao: PrescricaoDTO;
   private _evolucao: EvolucaoDTO;

   get prescricao(): PrescricaoDTO {
      return this._prescricao;
   }

   set prescricao(value: PrescricaoDTO) {
      this._prescricao = value;
   }

   get evolucao(): EvolucaoDTO {
      return this._evolucao;
   }

   set evolucao(value: EvolucaoDTO) {
      this._evolucao = value;
   }

	public jsonToEntity(res: any): PrescricaoEvolucaoDTO {

		if (res.prescricao) {
			this.prescricao = new PrescricaoDTO().jsonToEntity(res.prescricao);
		}

      if (res.ultimaEvolucao) {
			this.evolucao = new EvolucaoDTO().jsonToEntity(res.ultimaEvolucao);
		}

		return this;
	}

}
