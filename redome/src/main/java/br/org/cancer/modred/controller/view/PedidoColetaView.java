package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do JsonView.
 * 
 * @author bruno.sousa
 *
 */
public class PedidoColetaView {


	/**
	 * Detalhe da coleta.
	 * 
	 */
	public interface DetalheColeta {}
	
	/**
	 * JsonView para tela de agendar coleta.
	 * 
	 * @author bruno.sousa
	 *
	 */
	public interface AgendamentoColeta extends ListarPaginacao {}
	
	/**
	 * Interface para o medico transplantador sugerir novas datas.
	 * 
	 * @author Queiroz
	 *
	 */
	public interface SugerirDataColeta extends ListarPaginacao {} 
}
