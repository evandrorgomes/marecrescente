package br.org.cancer.redome.tarefa.integracao.client.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.tarefa.integracao.client.ITokenRequest;
import br.org.cancer.redome.tarefa.integracao.client.exception.LoginException;

/**
 * Implementacao da interface {@link ITokenRequest}
 * @author Filipe Paes
 *
 */
public abstract class TokenRequestImpl implements ITokenRequest {
	
	protected RestTemplate restTemplate;
	
	protected ObjectMapper jsonObjectMapper;

	protected HttpHeaders header;
	
	protected MultiValueMap<String, String> parametros;
	
	private String token;
	
	protected UriComponentsBuilder builder; 
	
	protected String usuario;
	
	protected String senha;
	
	protected String urlToken;
	
	
	
	public TokenRequestImpl(String usuario, String senha, String urlToken) {
		super();
		this.usuario = usuario;
		this.senha = senha;
		this.urlToken = urlToken;
		this.restTemplate = new RestTemplate();
		this.jsonObjectMapper = new ObjectMapper();
	}

	@Override
	public String getToken() {
//		if(tokenEValido()) {
//			return null;
//		}
		try{
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parametros, this.header);
			ResponseEntity<String> response = restTemplate.postForEntity( builder.toUriString(), request , String.class );
			
			@SuppressWarnings("unchecked")
			Map<String, String> map =jsonObjectMapper.readValue(response.getBody(), HashMap.class);
			
			this.token = map.get("access_token");
			
			return this.token;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new LoginException("Erro ao tentar realizar login no servidor REST");
		}
	}
	
	private boolean tokenEValido() {
//		if(token == null) {
//			return false;
//		}
		DecodedJWT jwt = JWT.decode(token);
		Date dataExpiracao = jwt.getExpiresAt();
		return dataExpiracao.after(new Date());
	}

}
