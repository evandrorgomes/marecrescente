package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * JsonView para Avaliacao de Pedido de Workup.
 * 
 * @author Filipe Paes
 *
 */
public class AvaliacaoResultadoWorkupView {
	/**
	 * Filtro mais enxuto para interfaces que retornam coleção de dados.
	 * 
	 * @author Filipe Paes
	 */
	public interface ListarAvaliacao extends ListarPaginacao {}

	/**
	 * Busca os detalhes da avaliação da prescrição.
	 * 
	 * @author Filipe Paes
	 *
	 */
	public interface Detalhe {}
}
