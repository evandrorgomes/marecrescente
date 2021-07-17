package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Json view para obter pedido de transporte.
 * @author Filipe Paes
 *
 */
public class PedidoTransporteView {
	/**
	 * Filtro mais enxuto para interfaces que retornam coleção de dados.
	 * 
	 * @author Filipe Paes
	 */
	public interface ListarPedidos extends ListarPaginacao {}

	/**
	 * Busca os detalhes de pedido de transporte.
	 * 
	 * @author Filipe Paes
	 *
	 */
	public interface Detalhe {}

}
