package br.org.cancer.redome.courier.configuraion;

import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.org.cancer.redome.courier.service.IStorageService;
import br.org.cancer.redome.courier.service.impl.BluemixStorageService;

@SpringBootApplication(scanBasePackages = "br.org.cancer.redome.courier")
@EnableDiscoveryClient
@EnableOAuth2Sso
@EnableHystrix
@EnableWebMvc
@EnableScheduling
//@EnableFeignClients(basePackages = {"br.org.cancer.redome.workup.feign.client"})
@EnableOAuth2Client
public class RedomeCourierApplication extends SpringBootServletInitializer {
	
	public static final Locale BRASIL_LOCALE = new Locale("pt", "BR");
	public static final String TIME_ZONE = "America/Sao_Paulo";


	public static void main(String[] args) {
		SpringApplication.run(RedomeCourierApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}
	
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(RedomeCourierApplication.class).web(WebApplicationType.SERVLET);
	}
	
	/**
	 * Define o messsage source responsável pela internacionalização das mensagens.
	 * 
	 * @return message source
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	protected static ObjectMapper getObjectMapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
		builder.serializers(LocalDateSerializer.INSTANCE);

		return builder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
		
	@Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
	
	/**
	 * Configura um provedor do contexto do spring.
	 * 
	 * @return
	 */
	@Bean
	public ApplicationContextProvider applicationContextProvider() {
		return new ApplicationContextProvider();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	@ApplicationScope
	public IStorageConnection storageConnection() {
		return new BluemixStorageConnection();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public IStorageService storageService(IStorageConnection storageConnection) {
		return new BluemixStorageService(storageConnection);
	}

}
