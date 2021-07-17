package br.org.cancer.modred.exception;


/**
 * Classe de exceção que deve ser executada quando há falha no login no MODRED.
 * @author Filipe Paes
 *
 */
public class LoginException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2393787373654939696L;

	public LoginException(String mensagem) {
		super(mensagem);
	}

}
