package br.org.cancer.redome.tarefa.integracao.client.exception;

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
