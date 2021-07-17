package br.org.cancer.modred.redomelib.exception;

/**
 * Classe customizada de exceção para encapsular erros de validação na camada
 * de negócio da aplicação.
 * 
 * @author bruno.sousa 
 *
 */
public class AleloException extends RuntimeException {

	private static final long serialVersionUID = -7147368745533638444L;
	
	private boolean alelo1Valido = false;
	private boolean alelo2Valido = false;

	/**
     * @param message m
     * @param erros lista de erros
     */
    public AleloException(String message) {
        super(message);
    }

    public AleloException() {
        super();
    }

	/**
	 * @return the alelo1Valido
	 */
	public boolean isAlelo1Valido() {
		return alelo1Valido;
	}

	/**
	 * @param alelo1Valido the alelo1Valido to set
	 */
	public void setAlelo1Valido(boolean alelo1Valido) {
		this.alelo1Valido = alelo1Valido;
	}

	/**
	 * @return the alelo2Valido
	 */
	public boolean isAlelo2Valido() {
		return alelo2Valido;
	}

	/**
	 * @param alelo2Valido the alelo2Valido to set
	 */
	public void setAlelo2Valido(boolean alelo2Valido) {
		this.alelo2Valido = alelo2Valido;
	}

    
}
