package br.org.cancer.modred.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.cancer.modred.exception.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;

//@Component
public class FeignErrorDecoder implements ErrorDecoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);
	
	private StringDecoder stringDecoder = new StringDecoder();
    
    @Override
    public Exception decode(String methodKey, Response response) {
    	String message = "Null Response Body.";
		if (response.body() != null) {
			try {
				message = stringDecoder.decode(response, String.class).toString();
			} catch (IOException e) {
				LOGGER.error(methodKey + "Error Deserializing response body from failed feign request response.", e);
			}
		}
		return new FeignClientException(response.status(), message, response.headers()); 
    }
    
}
