package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do contexto de
 * serialização.
 * 
 * @author Fillipe Queiroz
 *
 */
public class AvaliacaoPrescricaoView {

	/**
	 * Filtro mais enxuto para interfaces que retornam coleção de dados.
	 * 
	 * @author Fillipe Queiroz
	 */
	public interface ListarAvaliacao extends ListarPaginacao {}

	/**
	 * Busca os detalhes da avaliação da prescrição.
	 * 
	 * @author Fillipe Queiroz
	 *
	 */
	public interface Detalhe {}
}
