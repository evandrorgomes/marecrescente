package br.org.cancer.redome.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;

/* teste ergomes */

@SpringBootApplication
@EnableConfigServer
public class RedomeCloudApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RedomeCloudApplication.class, args);
		//configureApplication(new SpringApplicationBuilder()).run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder); 
	}
	
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(RedomeCloudApplication.class).web(WebApplicationType.NONE);
	}
	

}
