package br.org.cancer.redome.auth.configuration;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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

import br.org.cancer.redome.auth.exception.BusinessException;
import br.org.cancer.redome.auth.feign.client.ITransportadoraFeign;
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
	public ITransportadoraFeign trasportadoraFeign() {
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
	                .target(ITransportadoraFeign.class, info.getUri() + "/" + info.getServiceId().toLowerCase() );
		}
		throw new BusinessException("erro.interno");
	}
	

}
