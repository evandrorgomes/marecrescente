package br.org.cancer.redome.tarefa.integracao.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.integracao.client.ITokenRequest;
import br.org.cancer.redome.tarefa.integracao.client.exception.ComunicacaoException;

/**
 * Classe para conectar com o servidor rest e 
 * executar métodos básicos como POST, GET, PUT e DELETE.
 * @author Filipe Paes
 *
 */
public abstract class RestGenericClient{

	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper jsonObjectMapper;
	
	
	protected String usuario;
	
	protected String senha;
	
	protected String baseUrl;
	
	protected String urlToken;
	
	private String token;
	
	protected ITokenRequest tokenRequest;

	private boolean tokenConfigurado = false;
	
	
	/**
	 * Realiza solicitações post para o servidor MODRED com a autenticação JWT.
	 * @param urlPost - url a ser executada.
	 * @param entidade - entidade a ser submetida.
	 * @return retorno do servidor.
	 */
	public String post(String urlPost, Object entidade) {
		return realizarSolicitacaoHttp(HttpMethod.POST, urlPost, entidade);
	}
	
	/**
	 * Realiza solicitações put para o servidor MODRED com a autenticação JWT.
	 * @param urlPost - url a ser executada.
	 * @param entidade - entidade a ser submetida.
	 * @return retorno do servidor.
	 */
	public String put(String urlPost, Object entidade) {
		return realizarSolicitacaoHttp(HttpMethod.PUT, urlPost, entidade);
	}
	
	/**
	 * Realiza solicitações delete para o servidor MODRED com a autenticação JWT.
	 * @param urlPost - url a ser executada.
	 * @param entidade - entidade a ser submetida.
	 */
	public void delete(String urlPost, Object entidade) {
		realizarSolicitacaoHttp(HttpMethod.DELETE, urlPost, entidade);
	}
	
	/**
	 * Realiza solicitações get para o servidor MODRED com a autenticação JWT.
	 * @param urlPost - url a ser executada.
	 * @return map contendo resultado generico do get enviado.
	 */
	public String get(String urlPost) {
		return realizarSolicitacaoHttp(HttpMethod.GET, urlPost, null);
	}
	
	@SuppressWarnings("unchecked")
	private String realizarSolicitacaoHttp(HttpMethod metodoHttp, String url, Object entidade) {
		HttpHeaders headers = criarCabecalho();
		try{
			jsonObjectMapper.setSerializationInclusion(Include.NON_NULL);
			HttpEntity<String> entidadeString = new HttpEntity<>(entidade != null? jsonObjectMapper.writeValueAsString(entidade) : null,headers);
			ResponseEntity<String> response = restTemplate.exchange(this.baseUrl + url, metodoHttp, entidadeString, String.class);
			if(response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
				throw new ComunicacaoException("Erro ao tentar submeter entidade");
			}
			
			return response.getBody();
		}
		catch(HttpStatusCodeException e){
			e.printStackTrace();
			throw new ComunicacaoException("Erro ao tentar comunicação com o servidor");
		}
		catch(Exception exception){
			exception.printStackTrace();
			throw new BusinessException("erro.interno");
		}
	}

	protected HttpHeaders criarCabecalho() {
//		if(!tokenConfigurado ) {
			configurarToken();
//		}
		this.token = this.getTokenRequest().getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ this.token);
		return headers;
	}

	private void configurarToken() {
		this.getTokenRequest().configUrlBuilder();
		this.getTokenRequest().setTokenParams();
		this.getTokenRequest().configHeader();
		this.tokenConfigurado = true;
	}
	
	/**
	 * @return the tokenRequest
	 */
	public ITokenRequest getTokenRequest() {
		return tokenRequest;
	}

	/**
	 * Permite passar ao cliente REST o usuário para obter login para o token.
	 * @param usuario usuario a ser setado para obter o token.
	 */
	abstract void setUsuario(String usuario);
	
	/**
	 * Permite passar ao cliente REST a senha para obter login para o token.
	 * @param senha senha a ser setada para obter o token.
	 */
	abstract void setSenha(String senha);
	
	/**
	 * Permite passar ao cliente REST a URL base para os acessos ao sistema servidor.
	 * @param baseUrl url base para outras url's do sistema.
	 */
	abstract void baseUrl(String baseUrl);
	
	/**
	 * Permite passar ao cliente REST a URL para obter token.
	 * @param urlToken url para obter o token.
	 */
	abstract void urlToken(String urlToken);
	
	/**
	 * Configura a obtenção do token.
	 */
	abstract void configTokenRequest();
		
	
}
