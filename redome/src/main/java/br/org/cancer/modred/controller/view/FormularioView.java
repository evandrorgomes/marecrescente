package br.org.cancer.modred.controller.view;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do contexto de
 * serialização.
 * 
 * @author ergomes
 *
 */
public class FormularioView {

	/**
	 * Dados do formulário.
	 * 
	 * @author ergomes
	 *
	 */
	public interface Formulario{}
	
	/**
	 * Dados do formulário respondido.
	 * 
	 * @author ergomes
	 *
	 */
	public interface FormularioRespondido{}

	/**
	 * Dados do formulário.
	 * 
	 * @author ergomes
	 *
	 */
	public interface DetalheDoador{}
	
}
