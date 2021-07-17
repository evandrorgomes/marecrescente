package br.org.cancer.redome.auth.endpoint;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class RevokeTokenEndpoint {
	
	@Resource
    ConsumerTokenServices tokenServices;
	
	@Resource
    private TokenStore tokenStore;
 
    @DeleteMapping(value = "/logout")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")){
            String tokenId = authorization.substring("Bearer".length()+1);
            tokenServices.revokeToken(tokenId);
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenId);            
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            if (refreshToken != null && refreshToken.getValue() != null) {
            	tokenStore.removeRefreshToken(refreshToken);
            }
            tokenStore.removeAccessToken(accessToken);
            
        }
    }

}
