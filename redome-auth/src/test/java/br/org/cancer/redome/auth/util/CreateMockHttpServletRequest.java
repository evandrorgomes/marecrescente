/**
 * 
 */
package br.org.cancer.redome.auth.util;

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
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkLXJlc3QtY291cmllciIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInVzZXJfbmFtZSI6IkNPVVJJRVIiLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyIsInJlYWQtcmVkb21lLW5vdGlmaWNhY2FvIiwid3JpdGUtcmVkb21lLW5vdGlmaWNhY2FvIiwicmVhZC1yZWRvbWUtdGFyZWZhIiwid3JpdGUtcmVkb21lLXRhcmVmYSJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjp0cnVlLCJub21lIjoiQ09VUklFUiIsInJlY3Vyc29zIjpbIkFHRU5EQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIkNPTlNVTFRBUl9DT1VSSUVSIiwiRU5UUkVHQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIklOQVRJVkFSX0NPVVJJRVIiLCJSRVRJUkFSX1RSQU5TUE9SVEVfTUFURVJJQUwiLCJTQUxWQVJfQ09VUklFUiJdLCJleHAiOjE1OTcxNjAxODUsImF1dGhvcml0aWVzIjpbIlRSQU5TUE9SVEFET1JBIl0sImp0aSI6IjdjYTUxZDQzLTU3OWEtNDRmYy1hY2Q3LTNjZjIzYWJlNTljMiIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.-hHfix8wsQMga3tetG0xKLEDssylmx9tldbBSL0cLrk");
	}
	
	public static MockHttpServletRequestBuilder makePost(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkLXJlc3QtY291cmllciIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInVzZXJfbmFtZSI6IkNPVVJJRVIiLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyIsInJlYWQtcmVkb21lLW5vdGlmaWNhY2FvIiwid3JpdGUtcmVkb21lLW5vdGlmaWNhY2FvIiwicmVhZC1yZWRvbWUtdGFyZWZhIiwid3JpdGUtcmVkb21lLXRhcmVmYSJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjp0cnVlLCJub21lIjoiQ09VUklFUiIsInJlY3Vyc29zIjpbIkFHRU5EQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIkNPTlNVTFRBUl9DT1VSSUVSIiwiRU5UUkVHQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIklOQVRJVkFSX0NPVVJJRVIiLCJSRVRJUkFSX1RSQU5TUE9SVEVfTUFURVJJQUwiLCJTQUxWQVJfQ09VUklFUiJdLCJleHAiOjE1OTcxNjAxODUsImF1dGhvcml0aWVzIjpbIlRSQU5TUE9SVEFET1JBIl0sImp0aSI6IjdjYTUxZDQzLTU3OWEtNDRmYy1hY2Q3LTNjZjIzYWJlNTljMiIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.-hHfix8wsQMga3tetG0xKLEDssylmx9tldbBSL0cLrk");
	}
	
	public static MockHttpServletRequestBuilder makePut(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkLXJlc3QtY291cmllciIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInVzZXJfbmFtZSI6IkNPVVJJRVIiLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyIsInJlYWQtcmVkb21lLW5vdGlmaWNhY2FvIiwid3JpdGUtcmVkb21lLW5vdGlmaWNhY2FvIiwicmVhZC1yZWRvbWUtdGFyZWZhIiwid3JpdGUtcmVkb21lLXRhcmVmYSJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjp0cnVlLCJub21lIjoiQ09VUklFUiIsInJlY3Vyc29zIjpbIkFHRU5EQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIkNPTlNVTFRBUl9DT1VSSUVSIiwiRU5UUkVHQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIklOQVRJVkFSX0NPVVJJRVIiLCJSRVRJUkFSX1RSQU5TUE9SVEVfTUFURVJJQUwiLCJTQUxWQVJfQ09VUklFUiJdLCJleHAiOjE1OTcxNjAxODUsImF1dGhvcml0aWVzIjpbIlRSQU5TUE9SVEFET1JBIl0sImp0aSI6IjdjYTUxZDQzLTU3OWEtNDRmYy1hY2Q3LTNjZjIzYWJlNTljMiIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.-hHfix8wsQMga3tetG0xKLEDssylmx9tldbBSL0cLrk");
	}
	
	public static MockHttpServletRequestBuilder makeDelete(String urlTemplate) {
		return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(urlTemplate)				
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkLXJlc3QtY291cmllciIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInVzZXJfbmFtZSI6IkNPVVJJRVIiLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyIsInJlYWQtcmVkb21lLW5vdGlmaWNhY2FvIiwid3JpdGUtcmVkb21lLW5vdGlmaWNhY2FvIiwicmVhZC1yZWRvbWUtdGFyZWZhIiwid3JpdGUtcmVkb21lLXRhcmVmYSJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjp0cnVlLCJub21lIjoiQ09VUklFUiIsInJlY3Vyc29zIjpbIkFHRU5EQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIkNPTlNVTFRBUl9DT1VSSUVSIiwiRU5UUkVHQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIklOQVRJVkFSX0NPVVJJRVIiLCJSRVRJUkFSX1RSQU5TUE9SVEVfTUFURVJJQUwiLCJTQUxWQVJfQ09VUklFUiJdLCJleHAiOjE1OTcxNjAxODUsImF1dGhvcml0aWVzIjpbIlRSQU5TUE9SVEFET1JBIl0sImp0aSI6IjdjYTUxZDQzLTU3OWEtNDRmYy1hY2Q3LTNjZjIzYWJlNTljMiIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.-hHfix8wsQMga3tetG0xKLEDssylmx9tldbBSL0cLrk");
	}
	
	public static MockMultipartHttpServletRequestBuilder makeMultipart(String urlTemplate, String method) {
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(urlTemplate);
		builder.with(new RequestPostProcessor() {
						
		    @Override
		    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
		        request.setMethod(method.toUpperCase());
		        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkLXJlc3QtY291cmllciIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInVzZXJfbmFtZSI6IkNPVVJJRVIiLCJzY29wZSI6WyJ0cnVzdCIsInZhbGlkYXRpb25faGxhIiwiaW50ZWdyYWNhbyIsInJlYWQtcmVkb21lLW5vdGlmaWNhY2FvIiwid3JpdGUtcmVkb21lLW5vdGlmaWNhY2FvIiwicmVhZC1yZWRvbWUtdGFyZWZhIiwid3JpdGUtcmVkb21lLXRhcmVmYSJdLCJjZW50cm9zIjpbXSwiYWN0aXZlIjp0cnVlLCJub21lIjoiQ09VUklFUiIsInJlY3Vyc29zIjpbIkFHRU5EQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIkNPTlNVTFRBUl9DT1VSSUVSIiwiRU5UUkVHQVJfVFJBTlNQT1JURV9NQVRFUklBTCIsIklOQVRJVkFSX0NPVVJJRVIiLCJSRVRJUkFSX1RSQU5TUE9SVEVfTUFURVJJQUwiLCJTQUxWQVJfQ09VUklFUiJdLCJleHAiOjE1OTcxNjAxODUsImF1dGhvcml0aWVzIjpbIlRSQU5TUE9SVEFET1JBIl0sImp0aSI6IjdjYTUxZDQzLTU3OWEtNDRmYy1hY2Q3LTNjZjIzYWJlNTljMiIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.-hHfix8wsQMga3tetG0xKLEDssylmx9tldbBSL0cLrk");
		        return request;
		    }
		});
		
		return builder;		
	}

}
