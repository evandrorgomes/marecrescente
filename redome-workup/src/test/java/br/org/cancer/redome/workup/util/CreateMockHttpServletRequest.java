/**
 * 
 */
package br.org.cancer.redome.workup.util;

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
	
	public static MockHttpServletRequestBuilder makeGet(String urlTemplate) {
		return get(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
	}
	
	public static MockHttpServletRequestBuilder makePost(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
	}
	
	public static MockHttpServletRequestBuilder makePut(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
	}
	
	public static MockHttpServletRequestBuilder makePatch(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
	}
	
	public static MockHttpServletRequestBuilder makeDelete(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
	}
	
	public static MockMultipartHttpServletRequestBuilder makeMultipart(String urlTemplate, String method) {
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(urlTemplate);
		builder.with(new RequestPostProcessor() {
						
		    @Override
		    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
		        request.setMethod(method.toUpperCase());
		        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIl0sInVzZXJfbmFtZSI6IkZJTkFOQ0VJUk8iLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjoidHJ1ZSIsIm5vbWUiOiJBbmFsaXN0YSBGaW5hbmNlaXJvIiwicmVjdXJzb3MiOlsiQ09OU1VMVEFSX0lOVk9JQ0UiLCJDT05TVUxUQVJfUEVESURPX1BBQ0lFTlRFX0lOVk9JQ0UiXSwiZXhwIjoxNTcwMjI3NDExLCJhdXRob3JpdGllcyI6WyJGSU5BTkNFSVJPIl0sImp0aSI6Ijc0OGZhMWM3LWVkNmMtNDI4YS1iNzVmLTg1MWM0ZTk2OWNkMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.4m-8m0BLwC4LVO-2C7k-a5kHKjf4U1sdriP2DC9mZmk");
		        return request;
		    }
		});
		
		return builder;		
	}

}
