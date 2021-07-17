import { Raca } from '../../../../shared/dominio/raca';
import { Etnia } from '../../../../shared/dominio/etnia';
import { UF } from '../../../../shared/dominio/uf';
import { RessalvaDoador } from "../../../../doador/ressalva.doador";
import { MotivoStatusDoador } from '../../../../doador/inativacao/motivo.status.doador';
import { PedidoWorkup } from '../../../../doador/consulta/workup/pedido.workup';
import { BaseEntidade } from '../../../../shared/base.entidade';
import { Prescricao } from '../../../../doador/consulta/workup/prescricao';
import { PrescricaoEvolucaoDTO } from 'app/shared/dto/prescricao.evolucao.dto';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class AvaliacaoPrescricaoDTO extends BaseEntidade {

	private _idAvaliacaoPrescricao: number;
	private _prescricaoEvolucao: PrescricaoEvolucaoDTO;
	private _idFonteCelulaDescartada: number;
	private _justificativaDescarteFonteCelula: string;


	public get idFonteCelulaDescartada(): number {
		return this._idFonteCelulaDescartada;
	}

	public set idFonteCelulaDescartada(value: number) {
		this._idFonteCelulaDescartada = value;
	}

	public get justificativaDescarteFonteCelula(): string {
		return this._justificativaDescarteFonteCelula;
	}

	public set justificativaDescarteFonteCelula(value: string) {
		this._justificativaDescarteFonteCelula = value;
	}
	get idAvaliacaoPrescricao(): number {
		return this._idAvaliacaoPrescricao;
	 }
  
	 set idAvaliacaoPrescricao(value: number) {
		this._idAvaliacaoPrescricao = value;
	 }
  
	 get prescricaoEvolucao(): PrescricaoEvolucaoDTO {
		return this._prescricaoEvolucao;
	 }
  
	 set prescricaoEvolucao(value: PrescricaoEvolucaoDTO) {
		this._prescricaoEvolucao = value;
	 }
  
	public jsonToEntity(res: any): AvaliacaoPrescricaoDTO {
  
	  	if (res.prescricaoEvolucaoDTO) {
		  this.prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res.prescricaoEvolucaoDTO);
		}
  
		this.idAvaliacaoPrescricao = ConvertUtil.parseJsonParaAtributos(res.idAvaliacaoPrescricao, new Number());
		this.idFonteCelulaDescartada = ConvertUtil.parseJsonParaAtributos(res.idFonteCelulaDescartada, new Number());
		this.justificativaDescarteFonteCelula = ConvertUtil.parseJsonParaAtributos(res.justificativaDescarteFonteCelula, new String());

		return this;
	}
}