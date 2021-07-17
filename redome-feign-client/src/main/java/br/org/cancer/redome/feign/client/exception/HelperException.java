package br.org.cancer.redome.feign.client.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe customizada de exceção para encapsular exceções recebidas na camada
 * de negócio da aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
public class HelperException extends RuntimeException {

    private static final long serialVersionUID = -4976338448059830735L;
    
    private String[] messageParameters;
    private HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    
    /**
     * @param message
     */
    public HelperException(String message, String...messageParameters) {
        super(message);
        this.messageParameters = messageParameters;
    }
    
    /**
     * Construtor com sobrecarga recebendo HTTP Status.
     * 
     * @param httpStatus status do HTTP.
     * @param message mensagem de erro.
     * @param messageParameters parâmetros da mensagem, caso existam.
     */
    public HelperException(HttpStatus httpStatus, String message, String...messageParameters) {
        super(message);
        this.messageParameters = messageParameters;
        this.httpStatus = httpStatus;
    }

    
    /**
     * @param message
     * @param cause
     */
    public HelperException(String message, Throwable cause, String...messageParameters) {
        super(message, cause);
        this.messageParameters = messageParameters;
    }
    

    /**
     * Retorna a lista de parâmetros da mensagem de erro informada na exceção.
     * @return
     */
    public String[] getMessageParameters() {
        return messageParameters;
    }

	
	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
    
    

}
