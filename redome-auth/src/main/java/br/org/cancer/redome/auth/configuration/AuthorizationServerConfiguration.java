package br.org.cancer.redome.auth.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.org.cancer.redome.auth.model.CustomUser;

/**
 * Responsável pela configurações de autorização na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
//@Profile("!test")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private static String REALM = "OAUTH_MODRED_REALM";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	@Qualifier("userDetailsServiceBean")
	UserDetailsService userDetailsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * AuthorizationServerConfigurerAdapter#configure(org.springframework.
	 * security.oauth2.config.annotation.configurers.
	 * ClientDetailsServiceConfigurer)
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient("redome-auth")
			.secret(passwordEncoder.encode("123456"))
			.authorizedGrantTypes("client_credentials").scopes("read-redome-auth").resourceIds("redome-auth")
		// 30 minutos.
			.accessTokenValiditySeconds(3600)
		// 60 minutos.
			.refreshTokenValiditySeconds(3600).and().withClient("modred-front-client").secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "authorization_code")
				.scopes("trust", "validation_hla", "integracao", "read-redome-notificacao", "write-redome-notificacao", "read-redome-tarefa", "write-redome-tarefa")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-invoice", "modred-rest-fila",
						"modred-rest-tarefa", "modred_rest_notificacao", "modred-rest-workup", "redome-auth", "modred-rest-courier")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("modred-validation")
				.secret(passwordEncoder.encode("modred-validation"))
				// .secret("modred-validation")
				.authorizedGrantTypes("client_credentials").scopes("validation_hla").resourceIds("modred-validation", "redome-auth")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("brasilcord-client-id")
				// .secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("Br@s1lc0rd"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("brasilcord_rest_api", "read", "write")
				.resourceIds("brasilcord-rest-id", "modred_rest_api", "modred-validation")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-invoice")
				// .secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token").scopes("server")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-invoice", "redome-auth")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-tarefa")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("server", "PACIENTES_PARA_PROCESSO_BUSCA", "FINALIZAR_TENTATIVA_PEDIDO_CONTATO", "read-redome-tarefa", "write-redome-tarefa")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-tarefa", "redome-auth")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-fila")
				// .secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("read-redome-fila", "write-redome-fila")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-fila", "redome-auth")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-notificacao")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
				.scopes("read-redome-notificacao", "write-redome-notificacao")
				.resourceIds("modred_rest_api", "modred_rest_notificacao", "redome-auth")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-workup")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
				.scopes("read-redome-notificacao", "write-redome-notificacao", "read-redome-tarefa", "write-redome-tarefa", "read", "write")
				.resourceIds("modred_rest_api", "modred_rest_notificacao", "modred_rest_tarefa", "modred_rest_workup", "redome-auth", "modred-rest-courier")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600).and().withClient("redome-courier")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
				.scopes("read-redome-notificacao", "write-redome-notificacao", "read-redome-tarefa", "write-redome-tarefa", "read", "write")
				.resourceIds("modred_rest_api", "modred_rest_notificacao", "modred_rest_tarefa", "modred_rest_workup", "redome-auth", "modred-rest-courier")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * AuthorizationServerConfigurerAdapter#configure(org.springframework.
	 * security.oauth2.config.annotation.web.configurers.
	 * AuthorizationServerEndpointsConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(this.tokenStore()).tokenEnhancer(this.tokenEnhancerChain())
				.accessTokenConverter(this.accessTokenConverter())
				.userApprovalHandler(userApprovalHandler(this.tokenStore()))
				.authenticationManager(this.authenticationManager).userDetailsService(userDetailsService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * AuthorizationServerConfigurerAdapter#configure(org.springframework.
	 * security.oauth2.config.annotation.web.configurers.
	 * AuthorizationServerSecurityConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client");
		oauthServer.checkTokenAccess("permitAll()");
	}

	/**
	 * Define o JWT como formato de token.
	 * 
	 * @return token store
	 */
	@Bean
	public TokenStore tokenStore() {
		JwtTokenStore store = new JwtTokenStore(accessTokenConverter());		
		return store;
	}

	/**
	 * Configura o handler que verifica a validade e aprovação dos tokens já
	 * gerados.
	 * 
	 * @param tokenStore token store
	 * @return TokenStoreUserApprovalHandler
	 */
	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
				
		return handler;
	}

	/**
	 * Configura a interface que fará a aprovação do usuário.
	 * 
	 * @param tokenStore token store
	 * @return ApprovalStore
	 * @throws Exception em caso de erro.
	 */
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		((JwtTokenStore) tokenStore).setApprovalStore(store);
		return store;
	}

	/**
	 * Configura o conversor responsável em traduzir o token em informações de
	 * autenticação e vice-versa.
	 * 
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("$2a$11$u4/lWJswk5xHXfpq0kPEqOn1BftGvys8yPrz1bTIU0y4lX9ZTSxNm");		

		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();

		userTokenConverter.setUserDetailsService(userDetailsService);
		accessTokenConverter.setUserTokenConverter(userTokenConverter);

		converter.setAccessTokenConverter(accessTokenConverter);
		return converter;
	}

	/**
	 * Configura os responsáveis pela adição de informações ao token.
	 * 
	 * @return TokenEnhancerChain
	 */
	@Bean
	public TokenEnhancerChain tokenEnhancerChain() {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(), accessTokenConverter()));
		return tokenEnhancerChain;
	}

	/**
	 * Responsável pela adição de informações adicionais ao token.
	 * 
	 * @author Cintia Oliveira
	 *
	 */
	public static class CustomTokenEnhancer implements TokenEnhancer {

		@Override
		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
			if (authentication.getPrincipal() instanceof CustomUser) {
				CustomUser usuario = (CustomUser) authentication.getPrincipal();
				Map<String, Object> additionalInfo = new HashMap<>();
				additionalInfo.put("active", Boolean.TRUE);
				additionalInfo.put("id", usuario.getEncryptedId());
				additionalInfo.put("nome", usuario.getNome());
				additionalInfo.put("recursos", usuario.getRecursos());
				additionalInfo.put("centros", usuario.getCentros());
				additionalInfo.put("bancoSangue", usuario.getBancoSangue());
				if (usuario.getTransportadora() != null) {
					additionalInfo.put("transportadora", usuario.getTransportadora());
				}
				
				
				( (DefaultOAuth2AccessToken) accessToken ).setAdditionalInformation(additionalInfo);
			}
			return accessToken;
		}
	}

}
