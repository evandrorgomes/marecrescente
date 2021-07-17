import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { PedidoColeta } from './pedido.coleta';
import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { Prescricao } from '../workup/prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';


/**
 * Classe de especificação de tarefa para Pedido coleta.
 * 
 * @author Bruno Sousa
 * @export
 * @class TarefaPedidoColeta
 * @extends {Tarefa}
 */
export class TarefaPedidoColeta extends TarefaBase {

	private _pedidoColeta: PedidoColeta;

	public get pedidoColeta(): PedidoColeta {
		return this._pedidoColeta;
	}

	public set pedidoColeta(value: PedidoColeta) {
		this._pedidoColeta = value;
	}

}