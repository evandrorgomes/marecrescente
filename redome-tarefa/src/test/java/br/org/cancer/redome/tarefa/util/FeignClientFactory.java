package br.org.cancer.redome.tarefa.util;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import br.org.cancer.redome.tarefa.configuration.FeignErrorDecoder;
import br.org.cancer.redome.tarefa.feign.client.IBuscaFeign;
import br.org.cancer.redome.tarefa.feign.client.IConfiguracaoFeign;
import br.org.cancer.redome.tarefa.feign.client.IExameFeign;
import br.org.cancer.redome.tarefa.feign.client.IMatchFeign;
import br.org.cancer.redome.tarefa.feign.client.IPacienteFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoExameFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaFeign;
import br.org.cancer.redome.tarefa.feign.client.ITentativaContatoDoadorFeign;
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
	@Lazy(true)
	public IBuscaFeign buscaFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IBuscaFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IConfiguracaoFeign configuracaoFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IConfiguracaoFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IExameFeign exameFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IExameFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IMatchFeign matchFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IMatchFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IPacienteFeign pacienteFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IPacienteFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IPedidoContatoSmsFeign pedidoContatoSmsFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IPedidoContatoSmsFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IPedidoExameFeign pedidoExameFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IPedidoExameFeign.class, homePageUrl + "redome" );
	}
	
	@Bean
	@Lazy(true)
	public IPesquisaWmdaFeign pesquisaWmdaFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(IPesquisaWmdaFeign.class, homePageUrl + "redome");
	}
	
	@Bean
	@Lazy(true)
	public ITentativaContatoDoadorFeign tentativaContatoDoadorFeign() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
                .errorDecoder(errorDecoder)
                .contract(new SpringMvcContract())
                .requestInterceptor(requestInterceptor)
                .target(ITentativaContatoDoadorFeign.class, homePageUrl + "redome" );
	}
	

}
