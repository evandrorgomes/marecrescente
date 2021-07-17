package br.org.cancer.redome.workup.configuration;

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

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ICentroTransplanteFeign;
import br.org.cancer.redome.workup.feign.client.IEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.IMedicoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.IPedidoTransporteFeign;
import br.org.cancer.redome.workup.feign.client.IPerfilFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.feign.client.ITarefaFeign;
import br.org.cancer.redome.workup.feign.client.IUsuarioFeign;
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
	public FeignClientFactory(FeignErrorDecoder errorDecoder, DiscoveryClient discoveryClient, 
			RequestInterceptor requestInterceptor, 
			ObjectFactory<HttpMessageConverters> objectFactory) {
		this.errorDecoder = errorDecoder;
		this.discoveryClient = discoveryClient;
		this.requestInterceptor = requestInterceptor;
		this.objectFactory = objectFactory;
	}
	
	@Bean
	@Lazy(true)
	public ISolicitacaoFeign solicitacaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ISolicitacaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IEvolucaoFeign evolucaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IEvolucaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ILogEvolucaoFeign logEvolucaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ILogEvolucaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public IMedicoFeign medicoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IMedicoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public INotificacaoFeign notificacaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-NOTIFICACAO");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-NOTIFICACAO", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(INotificacaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ITarefaFeign tarefaFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-TAREFA");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-TAREFA", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ITarefaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	@Bean
	@Lazy(true)
	public IPerfilFeign perfilFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-AUTH");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-AUTH", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IPerfilFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");		
	}
	
	@Bean
	@Lazy(true)
	public IUsuarioFeign usuarioFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-AUTH");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-AUTH", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IUsuarioFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	
	@Bean
	@Lazy(true)
	public ICentroTransplanteFeign centroTransplanteFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ICentroTransplanteFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	

	@Bean
	@Lazy(true)
	public IPedidoTransporteFeign pedidoTransporteFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-COURIER");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-AUTH", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IPedidoTransporteFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");		
	}

}
