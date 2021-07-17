package br.org.cancer.modred.service.integracao.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.configuration.IntegracaoRedomeWebConfig;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.domain.StatusDoadorRedomeweb;
import br.org.cancer.modred.service.integracao.IIntegracaoAlterarStatusDoadorRedomeWebService;
import br.org.cancer.modred.service.integracao.IREDOMEWebSoapClientService;
import br.org.cancer.modred.webservice.helper.TAlterarStatusDeDoadorResponse;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Implementa as chamadas para alteração de status do doador no REDOMEWEB.
 * @author Filipe Paes
 *
 */
@Service
public class IntegracaoAlterarStatusDoadorRedomeWebService implements IIntegracaoAlterarStatusDoadorRedomeWebService{

	
	@Autowired
	private IntegracaoRedomeWebConfig integracaoConf;
	
	
	@Autowired
	private IREDOMEWebSoapClientService redomeWebSoapClient;
	
	
	@Override
	public void inativar(Long idDoador) {
		alterarStatus(idDoador, StatusDoadorRedomeweb.INDISPONIVEL);
	}

	@Override
	public void ativar(Long idDoador) {
		alterarStatus(idDoador, StatusDoadorRedomeweb.ATIVO);	
	}
	
	private void alterarStatus(Long idDoador, StatusDoadorRedomeweb status) {
		String xmlSoap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:red=\"{0}\">\n" +
		   "<soapenv:Header/>\n" +
		   "<soapenv:Body>\n" +
		   "   <red:alterarStatusDeDoador>\n" +
		   "      <cdDoador>{1}</cdDoador>\n" +
		   "      <cdStatus>{2}</cdStatus>\n" +
		   "   </red:alterarStatusDeDoador>\n" +
		   "</soapenv:Body>\n" +
		   "</soapenv:Envelope>";

		TRetorno retorno = redomeWebSoapClient.doPost(MessageFormat.format(xmlSoap,integracaoConf.getHost(),idDoador.toString(), status.getId())
				, new TAlterarStatusDeDoadorResponse());
		if (retorno.getSucessos() == null) {
			throw new BusinessException("erro.chamadaredomeweb.alterarstatusdoador");
		}
	}

}
