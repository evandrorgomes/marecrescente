package br.org.cancer.modred.configuration.security;

/**
 * Responsável pela configurações de autorização na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
//@Configuration
//@Profile("default || dev || hml || prod")
//@EnableAuthorizationServer
public class AuthorizationServerConfiguration {} //extends AuthorizationServerConfigurerAdapter {
/*
	private static String REALM = "OAUTH_MODRED_REALM";

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	private ClientDetailsService clientDetailsService;
	
	UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
				.withClient("modred-front-client")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials")
				.scopes("trust", "validation_hla", "integracao")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-invoice", "modred-rest-fila", "modred-rest-tarefa, modred-rest-notificacao")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("modred-validation")
				.secret(passwordEncoder.encode("modred-validation"))
				//.secret("modred-validation")
				.authorizedGrantTypes("client_credentials")
				.scopes("validation_hla")
				.resourceIds("modred-validation")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("brasilcord-client-id")
				//.secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("Br@s1lc0rd"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("brasilcord_rest_api", "read", "write")
				.resourceIds("brasilcord-rest-id", "modred_rest_api", "modred-validation")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("redome-invoice")
				//.secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("server")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-invoice")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("redome-tarefa")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("server", "PACIENTES_PARA_PROCESSO_BUSCA", "FINALIZAR_TENTATIVA_PEDIDO_CONTATO")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-tarefa")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("redome-fila")
				//.secret("Br@s1lc0rd")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token")
				.scopes("read-redome-fila", "write-redome-fila")
				.resourceIds("modred_rest_api", "modred-validation", "modred-rest-fila")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600)
				.and()
				.withClient("redome-notificacao")
				.secret(passwordEncoder.encode("123456"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
				.scopes("read-redome-notificacao", "write-redome-notificacao")
				.resourceIds("modred_rest_api", "modred_rest_notificacao")
				// 30 minutos.
				.accessTokenValiditySeconds(3600)
				// 60 minutos.
				.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(this.tokenStore())
				.tokenEnhancer(this.tokenEnhancerChain())
				.accessTokenConverter(this.accessTokenConverter())
				.userApprovalHandler(userApprovalHandler(this.tokenStore()))
				.authenticationManager(this.authenticationManager)
				.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client");
		oauthServer.checkTokenAccess("permitAll()");
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

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

	@Bean
	public TokenEnhancerChain tokenEnhancerChain() {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(),
				accessTokenConverter()));
		return tokenEnhancerChain;
	}

	public static class CustomTokenEnhancer implements TokenEnhancer {

		@Override
		public OAuth2AccessToken enhance(
				OAuth2AccessToken accessToken,
				OAuth2Authentication authentication) {
			if (authentication.getPrincipal() instanceof CustomUser) {
				CustomUser usuario = (CustomUser) authentication.getPrincipal();
				Map<String, Object> additionalInfo = new HashMap<>();
				additionalInfo.put("active", Boolean.TRUE);
				additionalInfo.put("nome", usuario.getNome());
				additionalInfo.put("recursos", usuario.getRecursos());
				additionalInfo.put("centros", usuario.getCentros());
				additionalInfo.put("bancoSangue", usuario.getBancoSangue());
				( (DefaultOAuth2AccessToken) accessToken ).setAdditionalInformation(additionalInfo);
			}
			return accessToken;
		}
		
		
	}

	@Autowired
	public void setUserDetailsService(@Qualifier("userDetailsServiceBean") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}
	
}
*/
