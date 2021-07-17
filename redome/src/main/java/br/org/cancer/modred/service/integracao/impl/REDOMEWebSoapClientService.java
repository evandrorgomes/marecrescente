package br.org.cancer.modred.service.integracao.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.org.cancer.modred.configuration.IntegracaoRedomeWebConfig;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.service.integracao.IREDOMEWebSoapClientService;
import br.org.cancer.modred.webservice.helper.TAbstractResponse;
import br.org.cancer.modred.webservice.helper.TEnvelope;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Implementação da interface IRedomeWebSoapClientService.
 * 
 * @author Filipe Paes
 *
 */
@Service
public class REDOMEWebSoapClientService implements IREDOMEWebSoapClientService {

	private static final Logger LOG = LoggerFactory.getLogger(REDOMEWebSoapClientService.class);

	@Autowired
	private IntegracaoRedomeWebConfig integracaoConf;

	@Override
	public TRetorno doPost(String xmlSoap, TAbstractResponse bodyResponse) {
		TRetorno retorno = null;
		try (
			CloseableHttpClient httpclient = HttpClients.createDefault();
		) {
			HttpPost post = new HttpPost(integracaoConf.obterHostRedomenetWsdl());
			StringEntity data = new StringEntity(xmlSoap, ContentType.TEXT_XML);
			post.setEntity(data);

			CloseableHttpResponse response = httpclient.execute(post);
			ObjectMapper xmlMapper = new XmlMapper();

			final TEnvelope envelope = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), TEnvelope.class);
			if (envelope.getBody() != null) {
				if (envelope.getBody().getResponse(bodyResponse) != null && envelope.getBody().getResponse(bodyResponse).getDados() != null) {
					retorno = xmlMapper.readValue(envelope.getBody().getResponse(bodyResponse).getDados(),TRetorno.class);
				}
				else if (envelope.getBody().getFault() != null) {
					LOG.error(envelope.getBody().getFault().getFaultstring());
					throw new BusinessException("erro.soap.fault.redomeweb");
				}
			}
			response.close();		
		}
		catch (Exception e) {
			throw new BusinessException("erro.interno", e);
		}
		return retorno;
	}

}
