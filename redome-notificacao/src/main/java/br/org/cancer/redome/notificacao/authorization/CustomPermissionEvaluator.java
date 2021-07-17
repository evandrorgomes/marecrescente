package br.org.cancer.redome.notificacao.authorization;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	
    @Override
    public boolean hasPermission(
      Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || !(permission instanceof String)){
            return false;
        }
         
        return hasPrivilege(auth, permission.toString().toUpperCase());
    }
 
    @Override
    public boolean hasPermission(
      Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth,  permission.toString().toUpperCase());
    }
    
    @SuppressWarnings("unchecked")
	private boolean hasPrivilege(Authentication auth, String permission) {
    	
    	final OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails) auth.getDetails();
    	if (detail != null) {
    		Map<String, Object> decodedDetails = (Map<String, Object>)detail.getDecodedDetails();
    		if (decodedDetails != null && !decodedDetails.isEmpty() && decodedDetails.containsKey("recursos")) {
		    	
		    	List<String> recursosToken = (List<String>) decodedDetails.get("recursos");
		    	return recursosToken.contains(permission);
    		}
    	}
    	
        return false;
    }
    
    
}
