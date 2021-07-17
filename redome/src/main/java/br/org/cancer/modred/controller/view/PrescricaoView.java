package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do JsonView.
 * 
 * @author bruno.sousa
 *
 */
public class PrescricaoView {
	
	/**
	 * JsonView para tela de listagem de autorizações de pacientes.
	 * 
	 * @author Fillipe Queiroz
	 *
	 */
	public interface AutorizacaoPacienteListar extends ListarPaginacao {}
	
	public interface DetalheDoador {}
	

}
