/**
 * 
 */
package br.org.cancer.redome.courier.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * @author ergomes
 *
 */
public class CreateMockHttpServletRequest {
	
	private static final String bearertoken =  "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJDT1VSSUVSIiwiY2VudHJvcyI6W10sImFjdGl2ZSI6dHJ1ZSwibm9tZSI6IkNPVVJJRVIiLCJhdXRob3JpdGllcyI6WyJUUkFOU1BPUlRBRE9SQSJdLCJiYW5jb1Nhbmd1ZSI6bnVsbCwidHJhbnNwb3J0YWRvcmEiOiJ577-9Ji5CJu-_ve-_vV7vv70977-977-9du-_ve-_ve-_ve-_vSpcdTAwMUPvv71cdTAwMULvv714bVx1MDAwNVx1MDAwMe-_ve-_vVxuPFfvv71cdTAwMDbvv71cdTAwMUUo77-92Ks877-977-9Ju-_ve-_vUdcdTAwMDHvv71m77-9U--_vXhcdTAwMTZXXHUwMDE2XHUwMDFDRe-_vVx1MDAwMUtcdTAwMTjvv71yP1x1MDAwNFx1MDAwM1x1MDAxN1Xvv71xXHUwMDE377-977-9X1xc77-9QElcdTAwMUPvv73vv71B77-9XWjvv73vv71R77-9XHUwMDFEWu-_ve-_ve-_ve-_vUdp77-977-9XHUwMDA1Skzvv71ccu-_ve-_vSnvv71cdTAwMUPvv71cdTAwMDRcdTAwMEZcdTAwMTHvv71cdTAwMTPvv71IQe-_vT7vv73vv73rppFO77-977-9yYnvv71F2ZpcdTAwMDdcdTAwMTHvv705We-_vWR3N2vvv73Kh1Xvv71cdTAwMDRcXNKt77-977-977-977-977-977-977-9O--_ve-_vVBcdTAwMDFt77-977-9UVx0fO-_ve-_vVx1MDAxN--_vVx1MDAxQnjvv706XHUwMDEzdGZcdTAwMEXvv71KXHUwMDAwecy5Mu-_vUrvv73vv71cdTAwMUUkfO-_ve-_vUXvv71EXHUwMDA177-977-9XHUwMDAw77-9XHUwMDFEXHUwMDE177-9RO-_vVx1MDAwNe-_vVbvv73fi3Pvv70-77-977-9dWfvv70o7JCTRO-_ve-_vVXvv73vv71W77-977-9RXd3IiwiY2xpZW50X2lkIjoibW9kcmVkLWZyb250LWNsaWVudCIsImF1ZCI6WyJtb2RyZWRfcmVzdF9hcGkiLCJtb2RyZWQtcmVzdC13b3JrdXAiLCJyZWRvbWUtYXV0aCIsIm1vZHJlZC12YWxpZGF0aW9uIiwibW9kcmVkLXJlc3QtaW52b2ljZSIsIm1vZHJlZC1yZXN0LWZpbGEiLCJtb2RyZWQtcmVzdC10YXJlZmEiLCJtb2RyZWQtcmVzdC1jb3VyaWVyIiwibW9kcmVkX3Jlc3Rfbm90aWZpY2FjYW8iXSwic2NvcGUiOlsidHJ1c3QiLCJ2YWxpZGF0aW9uX2hsYSIsImludGVncmFjYW8iLCJyZWFkLXJlZG9tZS1ub3RpZmljYWNhbyIsIndyaXRlLXJlZG9tZS1ub3RpZmljYWNhbyIsInJlYWQtcmVkb21lLXRhcmVmYSIsIndyaXRlLXJlZG9tZS10YXJlZmEiXSwicmVjdXJzb3MiOlsiQUdFTkRBUl9UUkFOU1BPUlRFX01BVEVSSUFMIiwiQ09OU1VMVEFSX0NPVVJJRVIiLCJFTlRSRUdBUl9UUkFOU1BPUlRFX01BVEVSSUFMIiwiSU5BVElWQVJfQ09VUklFUiIsIlJFVElSQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIlNBTFZBUl9DT1VSSUVSIl0sImV4cCI6MTU5Nzc2NDY2NSwianRpIjoiZTQ4ZjI5ZGMtOGI4Mi00MjI5LWFiM2MtMmJmMWFlM2M1YjdkIn0.K6z_xvYYlzXujO5pbrcEVXPK-dfCBrFw36K7DyjyDVU";
	
	
	public static MockHttpServletRequestBuilder makeGet(String urlTemplate) {
		return get(urlTemplate)				
				.header("Authorization", bearertoken);
	}
	
	public static MockHttpServletRequestBuilder makePost(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(urlTemplate)				
				.header("Authorization", bearertoken);
	}
	
	public static MockHttpServletRequestBuilder makePut(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(urlTemplate)				
				.header("Authorization", bearertoken);
	}
	
	public static MockHttpServletRequestBuilder makeDelete(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(urlTemplate)				
				.header("Authorization", bearertoken);
	}
	
	public static MockMultipartHttpServletRequestBuilder makeMultipart(String urlTemplate, String method) {
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(urlTemplate);
		builder.with(new RequestPostProcessor() {
						
		    @Override
		    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
		        request.setMethod(method.toUpperCase());
		        request.addHeader("Authorization", bearertoken);
		        return request;
		    }
		});
		
		return builder;		
	}

}
