package br.org.cancer.redome.auth.util;

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

import br.org.cancer.redome.auth.configuration.FeignErrorDecoder;
import br.org.cancer.redome.auth.feign.client.ITransportadoraFeign;
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
	public ITransportadoraFeign transportadoraFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(ITransportadoraFeign.class, homePageUrl + "redome-courier" );
	}
/*	
	@Bean
	public IEvolucaoFeign evolucaoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IEvolucaoFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	public ILogEvolucaoFeign logEvolucaoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(ILogEvolucaoFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	public IMedicoFeign medicoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IMedicoFeign.class, homePageUrl + "redome" );
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
	public IPerfilFeign perfilFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IPerfilFeign.class, homePageUrl + "redome-auth");
	}
	
	@Bean
	public IUsuarioFeign usuarioFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IUsuarioFeign.class, homePageUrl + "redome-auth" );
	}
*/
}
