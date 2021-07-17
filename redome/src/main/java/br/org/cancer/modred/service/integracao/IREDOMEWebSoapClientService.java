package br.org.cancer.modred.service.integracao;

import br.org.cancer.modred.webservice.helper.TAbstractResponse;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Classe cliente para envio e consumo de dados SOAP do REDOMEWEB.
 * @author Filipe Paes
 *
 */
public interface IREDOMEWebSoapClientService {

	/**
	 * Conecta com o serviço SOAP REDOME e submete o xml via post.
	 * @param xmlSoap dados xml a serem enviados para o redomeweb.
	 * @param bodyResponse tipo de resposta do servidor.
	 * @return
	 */
	TRetorno doPost(String xmlSoap, TAbstractResponse bodyResponse);
	
}
