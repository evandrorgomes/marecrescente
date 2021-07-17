package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do contexto de
 * serialização.
 * 
 * @author bruno.sousa
 *
 */
public class SolicitacaoView {

	/**
	 * @author bruno.sousa
	 */
	public interface detalhe {}

	/**
	 * @author ergomes
	 */
	public interface listar extends ListarPaginacao {}
	
	/**
	 * @author bruno.sousa
	 */
	public interface detalheWorkup extends detalhe {}

}
