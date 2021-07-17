package br.org.cancer.modred.exception;

/**
 * Classe de exceções relacionadas a comunicação com o MODRED.
 * @author Filipe Paes
 *
 */
public class ComunicacaoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ComunicacaoException(String mensagem) {
		super(mensagem);
	}

}
