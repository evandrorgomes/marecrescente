package br.org.cancer.modred.configuration.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.configuration.CustomRemoteTokenServices;
import br.org.cancer.modred.configuration.UserFeignClientInterceptor;
import feign.RequestInterceptor;

/**
 * Responsável pela configuração de acesso e autorização dos recursos.
 * 
 * @author Cintia Oliveira
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	//private static final String RESOURCE_ID = "modred_rest_api";
	
	@Value("${resourceId}")
	private String resourceId;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;

	@Value("${security.oauth2.resource.token-info-uri}")
	private String checkTokenEndpointUrl;
	
	@Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;
	
	@Value("${security.oauth2.client.grant-type}")
	private String grantType;
	
	@Value("${security.oauth2.client.scope}")
	private List<String> scope;
	
	@Autowired
	private OAuth2ClientContext oauth2Context;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * ResourceServerConfigurerAdapter#configure(org.springframework.security.
	 * oauth2.config.annotation.web.configurers. ResourceServerSecurityConfigurer)
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices()).resourceId(resourceId).stateless(true);
	}
	
	@Bean
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		ClientCredentialsResourceDetails client = new ClientCredentialsResourceDetails();
		client.setAccessTokenUri(accessTokenUri);
		client.setClientId(clientId);
		client.setClientSecret(passwordEncoder.encode(clientSecret != null ? clientSecret : "" ));
		client.setGrantType(grantType);
		client.setScope(scope);
		
		return client;
	}
	
	@Bean
	public AuthorizationCodeResourceDetails authorizationCodeResourceDetails() {
		AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
		client.setAccessTokenUri(accessTokenUri);
		client.setClientId(clientId);
		client.setClientSecret(passwordEncoder.encode(clientSecret != null ? clientSecret : "" ));
		client.setGrantType(grantType);
		client.setScope(scope);
		return client;
	}
	
	@Bean("oAuth2RestTemplate")
	public OAuth2RestTemplate oAuth2RestTemplate() {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails());
		restTemplate.setMessageConverters(Arrays.asList(jackson2HttpMessageConverter));
		restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
		return restTemplate;
	}
		
	@Bean
	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestInterceptor getUserFeignClientInterceptor(@Qualifier("oAuth2RestTemplate") OAuth2RestTemplate oAuth2RestTemplate) {
        return new UserFeignClientInterceptor(oAuth2RestTemplate);
    }		
	
	public JwtAccessTokenConverter createJwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("$2a$11$u4/lWJswk5xHXfpq0kPEqOn1BftGvys8yPrz1bTIU0y4lX9ZTSxNm");
		converter.setAccessTokenConverter(new JwtConverter());
		return converter;
	}
	
	public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

		@Override
		public void configure(JwtAccessTokenConverter converter) {
			converter.setAccessTokenConverter(this);			
		}

		@Override
		public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
			OAuth2Authentication auth = super.extractAuthentication(map);
			
			auth.setDetails(map); // this will get spring to copy JWT content into Authentication
			return auth;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.
	 * ResourceServerConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()			
			.requestMatchers()
			.antMatchers("/", "/index.jsp", "/public/**", "/api/**")
			.and()
			.authorizeRequests().antMatchers("/api/**").authenticated()
			.and()
			.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
			.and()
			.anonymous().disable();
	}
	
	@Bean
	@Primary
	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public RemoteTokenServices tokenServices() {
		CustomRemoteTokenServices tokenServices = new CustomRemoteTokenServices();
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		//tokenServices.setClientSecret(passwordEncoder.encode(clientSecret != null ? clientSecret : "" ));
		tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
		tokenServices.setAccessTokenConverter(createJwtAccessTokenConverter());
		
		return  tokenServices;
	}
	

}
