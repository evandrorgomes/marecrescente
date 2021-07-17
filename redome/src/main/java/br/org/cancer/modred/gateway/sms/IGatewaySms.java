package br.org.cancer.modred.gateway.sms;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Contrato padrão para a implemtação de gateway de sms no modred.
 * 
 * @author brunosousa
 *
 */
public interface IGatewaySms {
	
	/**
	 * Método para enviar o sms.
	 * 
	 * @param numeroTelefone - Número do telefone com ddd no formato 99999999999 
	 * @param mensagem - Texoto a ser eviado
	 * @return Retorno o identifidador e o status.
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	IRetornoSms enviar(String numeroTelefone, String mensagem) throws IOException, URISyntaxException;
	
	/**
	 * Método para verificar o status do envio do sms.
	 * 
	 * @param identificador - Identificador para a consulta.
	 * @return o Status do sms.
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	IRetornoSms status(String identificador) throws IOException, URISyntaxException;

}
