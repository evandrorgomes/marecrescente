package br.org.cancer.redome.workup.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

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
	private PasswordEncoder passwordEncoder;

	/**
	 * Bean para conexão como client de servidores REST.
	 * 
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();		
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		//tokenServices.setClientSecret(passwordEncoder.encode(clientSecret));
		tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
		tokenServices.setAccessTokenConverter(createJwtAccessTokenConverter());
		resources.tokenServices(tokenServices).resourceId(resourceId).stateless(true);
	}

	@Bean
	public JwtAccessTokenConverter createJwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("$2a$11$u4/lWJswk5xHXfpq0kPEqOn1BftGvys8yPrz1bTIU0y4lX9ZTSxNm");
		converter.setAccessTokenConverter(new JwtConverter());
		return converter;
	}
	
	@Bean
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		ClientCredentialsResourceDetails client = new ClientCredentialsResourceDetails();
		client.setAccessTokenUri(accessTokenUri);
		client.setClientId(clientId);
		client.setClientSecret(clientSecret);
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
	
		
/*	@Bean("oAuth2RestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate(UserInfoRestTemplateFactory templateFactory, MappingJackson2HttpMessageConverter jackson2HttpMessageConverter) {
		//MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        final OAuth2RestTemplate oAuth2RestTemplate = templateFactory.getUserInfoRestTemplate();
        oAuth2RestTemplate.setMessageConverters(Arrays.asList(jackson2HttpMessageConverter));
        
        
        		//new OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2Context);
        oAuth2RestTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        return oAuth2RestTemplate;
    }
	
	
	@Bean
    public RequestInterceptor getUserFeignClientInterceptor(@Qualifier("oAuth2RestTemplate") OAuth2RestTemplate oAuth2RestTemplate) {
        return new UserFeignClientInterceptor(oAuth2RestTemplate);
    }
	*/
	
	@Bean
    public RequestInterceptor userFeignClientInterceptor() {
        return new UserFeignClientInterceptor(oAuth2RestTemplate());
    }

	public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

		@Override
		public void configure(JwtAccessTokenConverter converter) {
			converter.setAccessTokenConverter(this);
		}

		@Override
		public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
			OAuth2Authentication auth = super.extractAuthentication(map);
			auth.setDetails(map);
			return auth;
		}
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().requestMatchers().antMatchers("/", "/api/**").and().authorizeRequests()
				.antMatchers("/api/**").authenticated().and().exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler()).and().anonymous().disable();

	}

}
