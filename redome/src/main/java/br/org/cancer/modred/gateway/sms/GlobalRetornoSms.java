package br.org.cancer.modred.gateway.sms;

import java.time.LocalDateTime;

/**
 * Classe que representa o retorno do sms da empresa Global.
 * 
 * @author brunosousa
 *
 */
public class GlobalRetornoSms extends RetornoSms {
	
	private LocalDateTime dataEnvio;

	/**
	 * Construtor recevendo os parametros abaixo.
	 * 
	 * @param identificador - Identificador gerado pela api.
	 * @param status - Status do envio
	 * @param dataEnvio - Data do envio do sms.
	 */
	public GlobalRetornoSms(String identificador, StatusSms status, LocalDateTime dataEnvio) {
		super(identificador, status);
		this.dataEnvio = dataEnvio;
	}

	/**
	 * @return the dataEnvio
	 */
	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}
	

}
