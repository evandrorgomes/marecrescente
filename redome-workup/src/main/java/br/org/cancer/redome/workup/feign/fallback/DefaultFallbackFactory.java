package br.org.cancer.redome.workup.feign.fallback;

import org.springframework.web.server.ResponseStatusException;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

public class DefaultFallbackFactory<T> implements FallbackFactory<T> {

	@Override
	public T create(Throwable cause) {
				
		Integer httpStatus = null;
		String message = "";
		if (cause instanceof FeignException) {
			httpStatus = ((FeignException) cause).status();
			message =  ((FeignException) cause).getMessage();
		}
		else if (cause instanceof ResponseStatusException) {
			httpStatus = ((ResponseStatusException) cause).getStatus().value();
			message = ((ResponseStatusException) cause).getMessage();
		}
		
		if (httpStatus != null && httpStatus.equals(422)) {
			System.out.println(message);
		}
		
		return null;
		
	}

}
