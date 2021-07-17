package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.TarefaView.Consultar;
import br.org.cancer.modred.controller.view.TarefaView.Listar;

/**
 * View para analise médica.
 * @author Filipe Paes
 *
 */
public class AnaliseMedicaView {

	/**
	 * Detalhemento de analise médoca.
	 * @author Filipe Paes
	 *
	 */
	public interface Detalhe extends Consultar {}

	
	/**
	 * View para listagem de tarefas. 
	 * 
	 * @author Filipe Paes
	 */
	public interface ListarTarefas extends Listar {}
}
