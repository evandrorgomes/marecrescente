package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe para JSONView de tentativa de contato.
 * @author Filipe Paes
 *
 */
public class TentativaContatoDoadorView {
    /**
     * Filtro mais detalhado para interfaces que retornam um único objeto.
     */
    public interface Consultar{}
    
	/**
	 * View para listagem de tarefas.
	 */
	public interface ListarTarefas extends ListarPaginacao {}
	
}
