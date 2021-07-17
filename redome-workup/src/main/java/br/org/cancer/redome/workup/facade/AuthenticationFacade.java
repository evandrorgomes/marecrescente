package br.org.cancer.redome.workup.facade;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
	
	public AuthenticationFacade() {
        super();
    }

    @Override
    public final Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
}
	

}