package br.org.cancer.redome.workup.configuration;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;

/**
 * Classe responsável por estabeler a conexão com o storage.
 * 
 * @author brunosousa
 *
 */
public class BluemixStorageConnection implements IStorageConnection<AmazonS3> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BluemixStorageConnection.class);
	
	@Value("${storage.api_key}")
	private String apiKey;
	
	@Value("${storage.service_instance_id}")
	private String serviceInstanceId;
	
	@Value("${storage.endpoint_url}")
	private String endpointUrl;
	
	@Value("${storage.IAM_ENDPOINT}")
	private String iamEndpoint;

	/**
	 * Construtor.
	 * 
	 * @param cloudConfig instancia do cloudconfig por injeção de dependencia
	 * 
	 */
	public BluemixStorageConnection() {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	}
	
	private AmazonS3 conectar() {
		
		SDKGlobalConfiguration.IAM_ENDPOINT = iamEndpoint;

    	AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(apiKey, serviceInstanceId);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(15000);
        clientConfig.setUseTcpKeepAlive(false);

        try {
        	return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration (new EndpointConfiguration( endpointUrl, null)) .withPathStyleAccessEnabled (true)                
                .withClientConfiguration(clientConfig).build();
        } 
        catch (SdkClientException e) {
        	LOGGER.error("Erro no storage ", e);
        }
        
        return null;
	}

	private volatile AmazonS3 cliente = null;

	/**
	 * @return cliente para conexão com o storage
	 * @throws NoSuchAlgorithmException 
	 */
	public synchronized AmazonS3 getCliente() {
		
		if (this.cliente == null) { 
			cliente = conectar();
		}
		
		return cliente;
	}

}
