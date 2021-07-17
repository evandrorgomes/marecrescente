package br.org.cancer.modred.gateway.sms;

/**
 * Classe de retorno do sms.
 * 
 * @author brunosousa
 *
 */
public class RetornoSms implements IRetornoSms {
	
	private String identificador;
	private StatusSms status;
	
	/**
	 * construtor com os parametros abaixo.
	 * 
	 * @param identificador - Identificador do sms.
	 * @param status - Status de retorno.
	 */
	public RetornoSms(String identificador, StatusSms status) {
		super();
		this.identificador = identificador;
		this.status = status;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @return the status
	 */
	public StatusSms getStatus() {
		return status;
	}
	
	

}
