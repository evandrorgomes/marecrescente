package br.org.cancer.modred.test.util;

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

import br.org.cancer.modred.configuration.FeignErrorDecoder;
import br.org.cancer.modred.feign.client.INotificacaoFeign;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.client.ITipoTarefaFeign;
import feign.Feign;
import feign.RequestInterceptor;

@Profile("test")
@Configuration
public class FeignClientFactory {
	
	private final String homePageUrl = "http://localhost:1111/"; 
	
	private FeignErrorDecoder errorDecoder;
	private RequestInterceptor requestInterceptor;
	private ObjectFactory<HttpMessageConverters> objectFactory;
	
	@Autowired
	public FeignClientFactory(FeignErrorDecoder errorDecoder, RequestInterceptor requestInterceptor, ObjectFactory<HttpMessageConverters> objectFactory) {
		this.errorDecoder = errorDecoder;	
		this.requestInterceptor = requestInterceptor;
		this.objectFactory = objectFactory;
	}
		
	@Bean
	public INotificacaoFeign notificacaoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(INotificacaoFeign.class, homePageUrl + "redome-notificacao" );
	}
	
	@Bean
	public ITarefaFeign tarefaFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(ITarefaFeign.class, homePageUrl + "redome-tarefa");
	}
	
	@Bean
	public IProcessoFeign processoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IProcessoFeign.class, homePageUrl + "redome-tarefa");
	}
	
	@Bean
	public ITipoTarefaFeign tipoTarefaFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(ITipoTarefaFeign.class, homePageUrl + "redome-tarefa");
	}
	

}
