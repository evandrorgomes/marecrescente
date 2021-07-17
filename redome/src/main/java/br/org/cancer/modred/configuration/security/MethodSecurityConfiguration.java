package br.org.cancer.modred.configuration.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import br.org.cancer.modred.authorization.CustomPermissionEvaluator;
import br.org.cancer.modred.authorization.CustomSecurityExpressionRoot;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe de configuração necessária para a utilização das annotations do Spring Security.
 * 
 * @author Cintia Oliveira
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	private IUsuarioService usuarioService;

	private IGenericRepository genericRepository;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new CustomMethodSecurityExpressionHandler(usuarioService, genericRepository);
	}

	/**
	 * Handler customizado para utililizacao das annotations do Spring Security.
	 * 
	 * @author Cintia Oliveira
	 *
	 */
	public static class CustomMethodSecurityExpressionHandler extends OAuth2MethodSecurityExpressionHandler  {

		private IGenericRepository genericRepository;
		private IUsuarioService usuarioService;

		/**
		 * Construtor da classe CustomMethodSecurityExpressionHandler.
		 * 
		 * @param usuarioService serviço para obter dados do usuário logado.
		 * @param genericRepository repositorio generico
		 */
		public CustomMethodSecurityExpressionHandler(IUsuarioService usuarioService,
				IGenericRepository genericRepository) {
			super();
			this.usuarioService = usuarioService;
			this.genericRepository = genericRepository;
		}

		@Override
		protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
				Authentication authentication, MethodInvocation invocation) {
			CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);
			root.setPermissionEvaluator(new CustomPermissionEvaluator(usuarioService,
					genericRepository));
			root.setTrustResolver(new AuthenticationTrustResolverImpl());
			root.setRoleHierarchy(getRoleHierarchy());
			return root;
		}
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param genericRepository the genericRepository to set
	 */
	@Autowired
	@Lazy
	public void setGenericRepository(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}
	

}
