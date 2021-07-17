package br.org.cancer.modred.configuration;

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

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.INotificacaoFeign;
import br.org.cancer.modred.feign.client.IPedidoWorkupFeign;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.IResultadoWorkupFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.client.ITipoTarefaFeign;
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
	public INotificacaoFeign notificacaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-NOTIFICACAO");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(INotificacaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IProcessoFeign processoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-TAREFA");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IProcessoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ITipoTarefaFeign tipoTarefaFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-TAREFA");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(ITipoTarefaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ITarefaFeign tarefaFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-TAREFA");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(ITarefaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}

	@Bean
	@Lazy(true)
	public IPedidoWorkupFeign pedidoWorkupFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-WORKUP");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IPedidoWorkupFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}

	@Bean
	@Lazy(true)
	public IResultadoWorkupFeign resultadoWorkupFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-WORKUP");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
			return Feign.builder()
			        .encoder(new SpringEncoder(this.objectFactory))
			        .decoder(new ResponseEntityDecoder(new SpringDecoder(this.objectFactory)))
			        .errorDecoder(errorDecoder)
			        .contract(new SpringMvcContract())
			        //.decoder(decoder)
			         .requestInterceptor(requestInterceptor)
			         .target(IResultadoWorkupFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}

}
