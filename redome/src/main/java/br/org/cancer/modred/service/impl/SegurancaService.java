package br.org.cancer.modred.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.authorization.CustomPermissionEvaluator;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.ISegurancaService;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe para acesso a recursos de segurança do Spring.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class SegurancaService implements ISegurancaService {

	@Autowired
	@Qualifier("tokenServices")
	private RemoteTokenServices tokenServices;

	//@Autowired	
	private TokenStore tokenStore;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	private IGenericRepository genericRepository;

	/**
	 * {@inheritDoc}
	 */
	public OAuth2AccessToken obterToken(HttpServletRequest request) {
		String headerAutenticacao = request.getHeader("Authorization");
		if (headerAutenticacao != null) {
			String token = headerAutenticacao.replace("Bearer", "").trim();
			if (token != null) {
				return tokenStore.readAccessToken(token);
			}
		}
		throw new BusinessException("token.nao.encontrado");
	}

	@CacheEvict(value = "autorizacao", key = "#token.getAdditionalInformation().get('user_name').toUpperCase()",
				condition = "#token.getAdditionalInformation().get('user_name') != null")
	@Deprecated
	public boolean removerToken(OAuth2AccessToken token) {
		return true;
		//return tokenServices.revokeToken(token.getValue());
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean usuarioLogadoPossuiRecurso(Object object, List<String> recursos) {
		CustomPermissionEvaluator permissionEvaluator = new CustomPermissionEvaluator(
				usuarioService, genericRepository);
		Boolean[] possui = {false};
		if (recursos != null && !recursos.isEmpty()) {
			
			recursos.forEach(recurso -> {
				if (permissionEvaluator.hasPermission(SecurityContextHolder.getContext()
						.getAuthentication(), object, recurso)) {
					possui[0] = true;
				}
			});
		}
		
		return possui[0];
	}

	/**
	 * @param genericRepository the genericRepository to set
	 */
	@Autowired
	public void setGenericRepository(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	/**
	 * @param tokenStore the tokenStore to set
	 */
	@Autowired
	@Lazy
	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}
	
	
	
	
	
}
