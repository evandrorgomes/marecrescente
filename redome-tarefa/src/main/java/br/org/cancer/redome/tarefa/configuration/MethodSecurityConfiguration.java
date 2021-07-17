package br.org.cancer.redome.tarefa.configuration;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import br.org.cancer.redome.tarefa.authorization.CustomPermissionEvaluator;
import br.org.cancer.redome.tarefa.authorization.CustomSecurityExpressionRoot;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new CustomMethodSecurityExpressionHandler();
	}
	
    /**
	 * Handler customizado para utililizacao das annotations do Spring Security.
	 * 
	 * @author Cintia Oliveira
	 *
	 */
	public static class CustomMethodSecurityExpressionHandler extends OAuth2MethodSecurityExpressionHandler  {

		/**
		 * Construtor da classe CustomMethodSecurityExpressionHandler.
		 * 
		 * @param usuarioService serviço para obter dados do usuário logado.
		 * @param genericRepository repositorio generico
		 */
		public CustomMethodSecurityExpressionHandler() {
			super();
		}

		@Override
		protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
				Authentication authentication, MethodInvocation invocation) {
			CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);
			root.setPermissionEvaluator(new CustomPermissionEvaluator());
			root.setTrustResolver(new AuthenticationTrustResolverImpl());
			root.setRoleHierarchy(getRoleHierarchy());
			return root;
		}
	}
    
}
