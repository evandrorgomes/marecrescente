package br.org.cancer.redome.courier.configuraion;

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

import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.feign.client.IRelatorioFeign;
import br.org.cancer.redome.courier.feign.client.IWorkupFeign;
import br.org.cancer.redome.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.feign.client.ITarefaFeign;
import br.org.cancer.redome.feign.client.IUsuarioFeign;
import br.org.cancer.redome.feign.client.helper.ITarefaHelper;
import br.org.cancer.redome.feign.client.helper.TarefaHelper;
import feign.Feign;
import feign.RequestInterceptor;
import feign.httpclient.ApacheHttpClient;

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
	public IUsuarioFeign usuarioFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-AUTH");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
		    //InstanceInfo info =  discoveryClient.getNextServerFromEureka("REDOME-AUTH", false);		
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IUsuarioFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
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
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ITarefaFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	@Bean
	@Lazy(true)
	public ITarefaHelper tarefaHelper() {
		return new TarefaHelper();
	}
	
	@Bean
	@Lazy(true)
	public IWorkupFeign workupFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME-WORKUP");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IWorkupFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	@Bean
	@Lazy(true)
	public ISolicitacaoFeign solicitacaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ISolicitacaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	@Bean
	@Lazy(true)
	public ILogEvolucaoFeign logEvolucaoFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(ILogEvolucaoFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	@Bean
	@Lazy(true)
	public IRelatorioFeign relatorioFeign() {
		List<ServiceInstance> lista = discoveryClient.getInstances("REDOME");
		if (CollectionUtils.isNotEmpty(lista)) {
			ServiceInstance info = lista.get(0);
	        return Feign.builder()
	                .encoder(new SpringEncoder(objectFactory))
	                .decoder(new ResponseEntityDecoder(new SpringDecoder(objectFactory)))
	                .errorDecoder(errorDecoder)
	                .client(new ApacheHttpClient())
	                .contract(new SpringMvcContract())
	                .requestInterceptor(requestInterceptor)
	                .target(IRelatorioFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno2");		
		
	}
	
	

}
