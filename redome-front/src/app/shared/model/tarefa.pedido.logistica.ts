import { TarefaBase } from "../dominio/tarefa.base";
import { PedidoLogistica } from "./pedido.logistica";

/**
 * Especialização da tarefa
 *
 * @export
 * @class TarefaLogistica
 * @extends {TarefaBase}
 */
export class TarefaPedidoLogistica extends TarefaBase {

    private _pedidoLogistica: PedidoLogistica;

	public get pedidoLogistica(): PedidoLogistica {
		return this._pedidoLogistica;
	}

	public set pedidoLogistica(value: PedidoLogistica) {
		this._pedidoLogistica = value;
	}

}
