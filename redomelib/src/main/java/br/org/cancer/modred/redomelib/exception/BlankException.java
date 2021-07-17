package br.org.cancer.modred.redomelib.exception;

/**
 * Classe customizada de exceção para encapsular erros de validação na camada
 * de negócio da aplicação.
 * 
 * @author bruno.sousa
 *
 */
public class BlankException extends AleloException {

	private static final long serialVersionUID = -7147368745533638444L;

	/**
     * @param message m
     * @param erros lista de erros
     */
    public BlankException(String message) {
        super(message);
    }

    public BlankException() {
        super();
    }

    
}
