package br.org.cancer.redome.tarefa.integracao.client.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Classe de acesso aos métodos REST disponibilizados pelo WMDA.
 * @author Filipe Paes
 *
 */
@PropertySource(value = "classpath:/rest_servers.properties")
@Component
public class RestWmdaClient extends RestGenericClient {
	
	
	@Value("${wmda_user_id}")
	private String userId;

	@Value("${wmda_client_id}")
	@Override
	void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Value("${wmda_client_secret}")
	@Override
	void setSenha(String senha) {
		this.senha = senha;
	}

	@Value("${wmda_url_base}")
	@Override
	void baseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Value("${wmda_url_token}")
	@Override
	void urlToken(String urlToken) {
		this.urlToken = urlToken;
	}
	
	@Override
	protected HttpHeaders criarCabecalho() {
		HttpHeaders headers = super.criarCabecalho();
		headers.set("UserId", this.userId);
		return headers;
	}
	
	@PostConstruct
	@Override
	void configTokenRequest() {
		
		this.tokenRequest = new TokenRequestImpl(this.usuario, this.senha, this.urlToken) {
						
			@Override
			public void setTokenParams() {
				MultiValueMap<String, String> mapFormParam= new LinkedMultiValueMap<>();
				
				mapFormParam.add("grant_type", "client_credentials");
				mapFormParam.add("client_id", this.usuario);
				mapFormParam.add("client_secret", this.senha);
				
				this.parametros = mapFormParam;
			}
			
			@Override
			public void configUrlBuilder() {
				this.builder = UriComponentsBuilder.fromHttpUrl(this.urlToken);
			}
			
			@Override
			public void configHeader() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				this.header = headers;
			}
		};	
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
