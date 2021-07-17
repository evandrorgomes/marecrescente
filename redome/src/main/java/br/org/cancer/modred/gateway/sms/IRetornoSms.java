package br.org.cancer.modred.gateway.sms;

/**
 * Contrato padrão de retorno do sms.
 * 
 * @author brunosousa
 *
 */
public interface IRetornoSms {
	
	/**
	 * Retorno o identificador gerado pela api.
	 * 
	 * @return identificador recebido pela api.
	 */
	String getIdentificador();
	
	/**
	 * Retorna o status do envio e de retorno so sms.
	 * 
	 * @return status da api.
	 */
	StatusSms getStatus();
}
