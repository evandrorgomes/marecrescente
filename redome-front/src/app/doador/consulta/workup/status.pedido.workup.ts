import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { Prescricao } from './prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { ConvertUtil } from '../../../shared/util/convert.util';

export class StatusPedidoWorkup extends BaseEntidade {
	
	private _id: number;
	private _descricao: string;

	/**
	 * id do pedido de workup
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * id do pedido de workup
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	/**
	 * @returns string
	 */
	public get descricao(): string {
		return this._descricao;
	}
	/**
	 * @param  {string} value
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}

	public jsonToEntity(res: any): this {

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());		
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());		
	
		return this;
    }

}