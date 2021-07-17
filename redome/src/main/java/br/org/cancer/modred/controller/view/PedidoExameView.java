package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe de view para PedidoExame.
 * @author Filipe Paes
 *
 */
public class PedidoExameView {
	
	/**
	 * Detalhemento de pedido de exame.
	 * @author Filipe Paes
	 *
	 */
	public interface Detalhe{}

	
	/**
	 * View para listagem de tarefas. 
	 * 
	 * @author Filipe Paes
	 */
	public interface ListarTarefas extends ListarPaginacao {}
	
	/**
	 * Edição de pedido de exame de doador internacional para edição.
	 * @author Queiroz
	 *
	 */
	public interface ObterParaEditar{}
	
	
	
}
