package br.org.cancer.redome.notificacao.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class UserFeignClientInterceptor implements RequestInterceptor {
	
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private OAuth2RestTemplate oAuth2RestTemplate;
    
    public UserFeignClientInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
    	this.oAuth2RestTemplate = oAuth2RestTemplate;
	}
    
    
    @Override
    public void apply(RequestTemplate template) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        String token = "";
        
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            token = details.getTokenValue();
        }
        else if (oAuth2RestTemplate != null) {
        	token = oAuth2RestTemplate.getAccessToken().getValue();
        }
        
        template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, token));
        
        
    }

}
