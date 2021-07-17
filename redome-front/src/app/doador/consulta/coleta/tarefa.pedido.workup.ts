import {PedidoColeta} from './pedido.coleta';
import {PedidoWorkup} from '../workup/pedido.workup';
import {TarefaBase} from '../../../shared/dominio/tarefa.base';

/**
 * Classe de especificação de tarefa para Pedido workup.
 * 
 * @author Fillipe Queiroz
 * @export
 * @class TarefaPedidoWorkup
 * @extends {Tarefa}
 */
export class TarefaPedidoWorkup extends TarefaBase {

	private _pedidoWorkup: PedidoWorkup;
	private _pedidoColeta: PedidoColeta;

	public get pedidoColeta(): PedidoColeta {
		return this._pedidoColeta;
	}

	public set pedidoColeta(value: PedidoColeta) {
		this._pedidoColeta = value;
	}
	
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}

	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}

}