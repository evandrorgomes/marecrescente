package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * JSON View para as serializações envolvendo a transportadora e o transporte de material.
 * 
 * @author Pizão
 *
 */
public class TransportadoraView {
	
	
	/**
	 * Interface para lista de logísticas de material
	 * definidos para a transportadora.
	 * 
	 * @author Pizão
	 *
	 */
	public interface Listar extends ListarPaginacao {}
	
	/**
	 * Interface para lista de logísticas de material
	 * definidos para a transportadora.
	 * 
	 * @author Pizão
	 *
	 */
	public interface ListarPendentes extends ListarPaginacao {}
	
	/**
	 * 
	 * Detalhe de transporte.
	 *
	 */
	public interface Detalhe{}

	
	/**
	 * Interface confirmar o agendamento de transporte.
	 * 
	 * @author Queiroz
	 *
	 */
	public interface AgendarTransporte extends ListarPaginacao{}

}
