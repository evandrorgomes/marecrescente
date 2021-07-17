package br.org.cancer.redome.notificacao.util;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.notificacao.configuration.FeignErrorDecoder;
import br.org.cancer.redome.notificacao.feign.IPerfilFeign;
import feign.Feign;
import feign.RequestInterceptor;

@Profile("test")
@Configuration
public class FeignClientFactory {
	
	private final String homePageUrl = "http://localhost:1111/";
	
	private FeignErrorDecoder errorDecoder;
	private ObjectMapper objectMapper;
	private RequestInterceptor requestInterceptor;
	private ObjectFactory<HttpMessageConverters> objectFactory;
	
	@Autowired
	public FeignClientFactory(FeignErrorDecoder errorDecoder, ObjectMapper objectMapper, RequestInterceptor requestInterceptor, ObjectFactory<HttpMessageConverters> objectFactory) {
		this.errorDecoder = errorDecoder;
		this.objectMapper = objectMapper;
		this.requestInterceptor = requestInterceptor;
		this.objectFactory = objectFactory;
	}
	
	@Bean
	public IPerfilFeign perfilFeign() {				
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                //.decoder(decoder)
                 .requestInterceptor(requestInterceptor)
                 .target(IPerfilFeign.class, homePageUrl + "redome-auth" );
	}
	

}
