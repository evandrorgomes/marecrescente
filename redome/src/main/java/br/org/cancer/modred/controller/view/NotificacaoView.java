package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do contexto de
 * serialização.
 * 
 * @author Fillipe Queiroz
 *
 */
public class NotificacaoView {

	/**
	 * Filtro mais enxuto para interfaces que retornam coleção de dados.
	 * 
	 * @author Fillipe Queiroz
	 */
	public interface ListarNotificacao extends ListarPaginacao {}

}
