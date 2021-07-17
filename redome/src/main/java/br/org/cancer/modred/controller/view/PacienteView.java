package br.org.cancer.modred.controller.view;

/**
 * Classe necessária para a utilização do JsonView.
 * 
 * @author Bruno Sousa
 *
 */
public class PacienteView {

	/**
	 * Consulta.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface Consulta {}

	/**
	 * IdentificacaoCompleta.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface IdentificacaoCompleta {}

	/**
	 * IdentificacaoParcial.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface IdentificacaoParcial {}

	/**
	 * Detalhe.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface Detalhe {}
	
	/**
	 * Dados Pessoais (Identificação e Dados Pessoais).
	 * 
	 * @author Dev Team
	 *
	 */
	public interface DadosPessoais {}
	
	/**
	 * Diagnostico.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface Diagnostico {}
	
	/**
	 * Rascunho.
	 * 
	 * @author Dev Team
	 *
	 */
	public interface Rascunho {}
}
