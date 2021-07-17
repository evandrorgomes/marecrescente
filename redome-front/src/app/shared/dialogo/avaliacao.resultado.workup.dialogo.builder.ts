import { DialogoBuilder } from "./dialogo.builder";

/**
 * Classe que Constroi a lista de Comentarios
 * @author Fillipe Queiroz
 */
export class AvaliacaoResultadoWorkupDialogoBuilder extends DialogoBuilder {


	constructor() {
		super();
	}

	buildComentarios(): AvaliacaoResultadoWorkupDialogoBuilder {
    return null;
	}


	// buildComentarios(pedidos: PedidoAdicionalDTO[]): AvaliacaoResultadoWorkupDialogoBuilder {
	// 	this._comentarios = [];
	// 	pedidos.forEach(pedido => {
	// 		let comentario: Comentario = new Comentario();
	// 		comentario.texto = pedido.texto;
	// 		comentario.username = pedido.username
	// 		comentario.dataFormatadaDialogo = pedido.dataFormatadaDialogo;
	// 		this._comentarios.push(comentario);
	// 	})
	// 	return this;

	// }




}

