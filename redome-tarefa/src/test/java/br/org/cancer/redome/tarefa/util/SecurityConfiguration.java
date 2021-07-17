package br.org.cancer.redome.tarefa.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Responsável pela configuração de segurança na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
@Profile("test")
@Configuration
@Order(99)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public SecurityConfiguration() {

	}

	public SecurityConfiguration(boolean disableDefaults) {
		super(disableDefaults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security. config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.requestMatchers()			
				.antMatchers("/h2-console").anyRequest()
				.and()
				.requestMatchers().antMatchers("/", "/index.jsp", 
						"/public/**", "/api/**")
				.and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/**")
				.authenticated()
				.and().anonymous().disable();
		
		http.headers().frameOptions().sameOrigin();
	}
	
	
}
