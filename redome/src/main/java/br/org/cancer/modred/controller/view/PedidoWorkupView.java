package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do JsonView.
 * 
 * @author Fillipe Queiroz
 *
 */
public class PedidoWorkupView {


	/**
	 * Detalhe de workup.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface DetalheWorkup {}
	
	/**
	 * JsonView para tela de agendar workup.
	 * 
	 * @author Fillipe Queiroz
	 *
	 */
	public interface AgendamentoWorkup extends ListarPaginacao {}
	
	/**
	 * Retorna campos para o cadastro do resultado.
	 */
	public interface CadastrarResultado extends ListarPaginacao {}

	/**
	 * Retorna campos de resultado para cadastro do resultado.
	 */
	public interface ResultadoParaCadastroInternacional {}
	
	/**
	 * Interface para o medico transplantador sugerir novas datas.
	 */
	public interface SugerirDataWorkup extends ListarPaginacao {} 
}
