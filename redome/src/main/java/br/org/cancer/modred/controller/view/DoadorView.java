package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;
import br.org.cancer.modred.controller.view.TarefaView.Consultar;

/**
 * Classe necessária para a utilização do JsonView.
 * 
 * @author Bruno Sousa
 *
 */
public class DoadorView {

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
	 * Dados de contato fase 2.
	 * 
	 * @author Fillipe Queiroz
	 *
	 */
	public interface ContatoFase2 extends Consultar{}
	
	/**
	 * Dados de doador para atualização na fase 2.
	 * 
	 * @author Fillipe Queiroz
	 *
	 */
	public interface AtualizacaoFase2 extends ListarPaginacao{}
	
	/**
	 * Contato passivo.
	 * 
	 * @author fillipe.queiroz
	 *
	 */
	public interface ContatoPassivo extends ListarPaginacao{}
	
	/**
	 * Ressalvas.
	 * 
	 * @author fillipe.queiroz
	 *
	 */
	public interface Ressalva {}
	
	/**
	 * Filtro para os atributos utilizados na consulta por doador internacional.
	 * 
	 * @author Pizão
	 *
	 */
	public interface Internacional extends ListarPaginacao{}


	/**
	 * Filtro para a evolução do doador.
	 * 
	 * @author brunosousa
	 *
	 */
	public interface Evolucao {}

	/**
	 * Filtro para os atributos utilizados na consulta por doador nacional.
	 * 
	 * @author ERGOMES
	 *
	 */
	public interface DoadorNacional{};


	/**
	 * Filtro para os atributos utilizados na consulta por logistica doador nacional.
	 * 
	 * @author ERGOMES
	 *
	 */
	public interface LogisticaDoador{};

}
