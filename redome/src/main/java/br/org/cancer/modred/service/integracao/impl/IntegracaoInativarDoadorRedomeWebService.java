package br.org.cancer.modred.service.integracao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.configuration.IntegracaoRedomeWebConfig;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.service.integracao.IIntegracaoInativarDoadorRedomeWebService;
import br.org.cancer.modred.service.integracao.IREDOMEWebSoapClientService;
import br.org.cancer.modred.webservice.helper.TInativarDoadorResponse;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Implementação da interface {@link IIntegracaoInativarDoadorRedomeWebService}.
 * @author Filipe Paes
 *
 */
@Service
public class IntegracaoInativarDoadorRedomeWebService implements IIntegracaoInativarDoadorRedomeWebService {

	@Autowired
	private IntegracaoRedomeWebConfig integracaoConf;
	
	@Autowired
	private IREDOMEWebSoapClientService redomeWebSoapClient;
	
	@Override
	public void executarInativacao(Long dmr) {
		String xmlSoap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:red=\""+integracaoConf.getHost() +"\">\n" + 
				"   <soapenv:Header/>\n" + 
				"   <soapenv:Body>\n" + 
				"      <red:inativarDoador>\n" + 
				"         <idDoador>" + dmr + "</idDoador>\n" + 
				"      </red:inativarDoador>\n" + 
				"   </soapenv:Body>\n" + 
				"</soapenv:Envelope>";

		TRetorno retorno = redomeWebSoapClient.doPost(xmlSoap, new TInativarDoadorResponse());
		if (retorno.getSucessos() == null) {
			throw new BusinessException("erro.chamadaredomeweb.inativardoador");
		}
	}

}
