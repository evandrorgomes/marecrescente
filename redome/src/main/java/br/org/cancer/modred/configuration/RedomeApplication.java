package br.org.cancer.modred.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//@SpringBootApplication
@SpringBootApplication( scanBasePackages= {"br.org.cancer.modred", "br.org.cancer.modred.redomelib"}) 
//	exclude = { WebMvcAutoConfiguration.class})
//@EnableAutoConfiguration(exclude = {DispatcherServletAutoConfiguration.class})
//@ComponentScan(basePackages = {"br.org.cancer.modred", "br.org.cancer.modred.redomelib"})
//@Import({ApplicationConfiguration.class})
public class RedomeApplication extends SpringBootServletInitializer {
	
	
	public static void main(String[] args) {
		SpringApplication.run(RedomeApplication.class, args);
	}
	
	/**
     * Used when run as WAR
     */
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}
    
    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(RedomeApplication.class).web(WebApplicationType.NONE);
	}
    

    @Bean
	@Primary
	public UserDetailsService userDetailsServiceBean(@Qualifier("customUserDetailsService")  UserDetailsService userDetailsService) throws Exception {
		return userDetailsService; //new CustomUserDetailsService();
	}
    
    /**
	 * Encoder para criptografia das senhas.
	 * 
	 * @return encoder para criptografia
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
    
}
