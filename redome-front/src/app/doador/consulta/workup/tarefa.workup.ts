import { PedidoWorkup } from './pedido.workup';
import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { Prescricao } from './prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';
import { TarefaBase } from '../../../shared/dominio/tarefa.base';

export class TarefaPedidoWorkup extends TarefaBase {

    private _pedidoWorkup: PedidoWorkup;
    private _score:number;

	/**
	 * @returns PedidoWorkup
	 */
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}
	/**
	 * @param  {PedidoWorkup} value
	 */
	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}
	/**
	 * @returns number
	 */
	public get score(): number {
		return this._score;
	}
	/**
	 * @param  {number} value
	 */
	public set score(value: number) {
		this._score = value;
	}
	
}