package br.org.cancer.modred.configuration;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.net.ssl.SSLContext;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.core.transport.Config;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe responsável por estabeler a conexão com o storage.
 * 
 * @author Cintia Oliveira
 *
 */

public class StorageConnection implements IStorageConnection<OSClientV3> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageConnection.class);
	
	private JsonNode credenciais;
	private Token token;
	private Boolean testMode;

	/**
	 * Construtor.
	 * 
	 * @param cloudConfig instancia do cloudconfig por injeção de dependencia
	 * @param testMode Identifica se o bean foi criado para rodar os tests
	 */
	public StorageConnection(Boolean testMode) {		
		this.testMode = testMode;

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	}
	
	/**
	 * Construtor.
	 * 
	 * @param cloudConfig instancia do cloudconfig por injeção de dependencia
	 */	
	public StorageConnection() {
		this(false);

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	}

	private OSClientV3 conectar() {

		try {
			popularCredenciais();

			String authUrl = credenciais.get("auth_url").asText() + "/v3";
			Identifier domainIdent = Identifier.byName(credenciais.get("domainName").asText());
			Identifier projectIdent = Identifier.byName(credenciais.get("project").asText());
			
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(null, null, null);

			OSClientV3 cliente = OSFactory.builderV3()
					.endpoint(authUrl)
					.credentials(credenciais.get("userId").asText(), credenciais.get("password").asText())
					.scopeToProject(projectIdent, domainIdent)
					.withConfig(Config.newConfig()
							.withSSLContext(sslContext))
					.authenticate();

			this.token = cliente.getToken();			

			return cliente;

		}
		catch (AuthenticationException | NoSuchAlgorithmException | KeyManagementException e) {
			LOGGER.error("Erro ao tentar conectar ao storage", e);
		}
		catch (IOException e) {
			LOGGER.error("Erro ao tentar ler o arquivo de configuração {}",
					"", e);
		}		

		return null;
	}

	private void popularCredenciais() throws IOException, JsonProcessingException {
		String vcapServices = null; //cloudConfig.getValue(CloudConfig.VCAP_SERVICES);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(vcapServices);
		JsonNode storageConfiguration = root.findValue("Object-Storage");

		credenciais = storageConfiguration.findValue("credentials");
	}
	
	private volatile OSClientV3 cliente = null;

	/**
	 * @return cliente para conexão com o storage
	 * @throws NoSuchAlgorithmException 
	 */
	public synchronized OSClientV3 getCliente() {
		
		if (this.token == null || this.token.getExpires().before(new Date())) { 
			cliente = conectar();
		}
		else {
			if (!this.testMode) {
				return  OSFactory.clientFromToken(this.token);
			}
		}

		return cliente;
	}

}
