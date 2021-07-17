package br.org.cancer.modred.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Interface para autenticar o usuário.
 * 
 * @author Bruno Sousa
 *
 */
public interface ISegurancaService {

	/**
	 * Obtém o token no request passado como parametro.
	 * 
	 * @param request
	 * @return
	 */
	OAuth2AccessToken obterToken(HttpServletRequest request);

	/**
	 * Remove o token informado no parametro.
	 * 
	 * @param token
	 * @return
	 */
	boolean removerToken(OAuth2AccessToken token);
	
	
	/**
	 * retorna true se o usuário logado possuir pelo menos um recurso da lista.
	 * 
	 * @param object
	 * @param recursos
	 * @return Boolean
	 */
	Boolean usuarioLogadoPossuiRecurso(Object object, List<String> recursos);

}
