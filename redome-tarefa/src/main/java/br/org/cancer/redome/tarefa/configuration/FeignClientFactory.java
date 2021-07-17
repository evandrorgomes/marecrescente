package br.org.cancer.redome.tarefa.configuration;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IBuscaFeign;
import br.org.cancer.redome.tarefa.feign.client.IConfiguracaoFeign;
import br.org.cancer.redome.tarefa.feign.client.IExameFeign;
import br.org.cancer.redome.tarefa.feign.client.IMatchFeign;
import br.org.cancer.redome.tarefa.feign.client.IPacienteFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoExameFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaDoadorFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaFeign;
import br.org.cancer.redome.tarefa.feign.client.ITentativaContatoDoadorFeign;
import feign.Feign;
import feign.RequestInterceptor;

@Profile("!test")
@Configuration
public class FeignClientFactory {
	
	private FeignErrorDecoder errorDecoder;
	private DiscoveryClient discoveryClient;
	private RequestInterceptor requestInterceptor;
	private ObjectFactory<HttpMessageConverters> objectFactory;
	
	@Autowired
	public FeignClientFactory(FeignErrorDecoder errorDecoder, DiscoveryClient discoveryClient, RequestInterceptor requestInterceptor, ObjectFactory<HttpMessageConverters> objectFactory) {
		this.errorDecoder = errorDecoder;
		this.discoveryClient = discoveryClient;
		this.requestInterceptor = requestInterceptor;
		this.objectFactory = objectFactory;
	}
	
	@Bean
	@Lazy(true)
	public IBuscaFeign buscaFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IBuscaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IConfiguracaoFeign configuracaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IConfiguracaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IExameFeign exameFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IExameFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IMatchFeign matchFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IMatchFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IPacienteFeign pacienteFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPacienteFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IPedidoContatoSmsFeign pedidoContatoSmsFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPedidoContatoSmsFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IPedidoExameFeign pedidoExameFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPedidoExameFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IPesquisaWmdaFeign pesquisaWmdaFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPesquisaWmdaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}

	@Bean
	@Lazy(true)
	public IPesquisaWmdaDoadorFeign pesquisaWmdaDoadorFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPesquisaWmdaDoadorFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ITentativaContatoDoadorFeign tentativaContatoDoadorFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(ITentativaContatoDoadorFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}

}
