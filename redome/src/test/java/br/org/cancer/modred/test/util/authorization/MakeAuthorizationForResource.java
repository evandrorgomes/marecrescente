package br.org.cancer.modred.test.util.authorization;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MakeAuthorizationForResource implements IAuthorizationForResource {
	
	public String clientId;
	public List<String> resources = new ArrayList<>();
	public List<String> scopes = new ArrayList<>();
	
	
	public MakeAuthorizationForResource(String clientId) {
		this.clientId = clientId;
	}
	
	public IAuthorizationForResource addResource(String...resource) {
		Collections.addAll(this.resources, resource);
		return this;
	}
	
	public IAuthorizationForResource addScope(String...scope) {
		Collections.addAll(this.scopes, scope);
		return this;
	}
	
	public void clear() {
		clientId = null;
		resources.clear();
		scopes.clear();
	}
	
	public String getJson() {
		
		Long exp = Instant.now().plus(15, ChronoUnit.MINUTES) .toEpochMilli(); 
		
		String res = resources.stream().map(resource ->  "\"" + resource + "\"").collect(Collectors.joining(", "));
		String scp = scopes.stream().map(scope -> "\"" + scope + "\"").collect(Collectors.joining(", "));		
				
		String json = "{" +
			    "\"aud\": [" + res +			          
			    "    ], " +
			    "    \"scope\": [ " + scp + 			            
			    "    ]," +
			    "    \"active\": true, " +
			    "    \"exp\": " + String.valueOf(exp) + "," +
			    "    \"jti\": \"fa30aefb-98ac-4fa6-a56f-4a7d3a87caae\"," + 
			    "    \"client_id\": \"" + clientId + "\"" +
			    "}";
		
		System.out.println(json);
		
		return json;
	}
	
	@Override
	public String getAccessTokenJson() {
		Long exp = Instant.now().plus(15, ChronoUnit.MINUTES) .toEpochMilli();
		
		String json = "{" + 
				"    \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwibW9kcmVkLXZhbGlkYXRpb24iLCJtb2RyZWQtcmVzdC1pbnZvaWNlIiwibW9kcmVkLXJlc3QtZmlsYSIsIm1vZHJlZC1yZXN0LXRhcmVmYSIsIm1vZHJlZF9yZXN0X25vdGlmaWNhY2FvIl0sInNjb3BlIjpbInRydXN0IiwidmFsaWRhdGlvbl9obGEiLCJpbnRlZ3JhY2FvIiwicmVhZC1yZWRvbWUtbm90aWZpY2FjYW8iLCJ3cml0ZS1yZWRvbWUtbm90aWZpY2FjYW8iLCJyZWFkLXJlZG9tZS10YXJlZmEiLCJ3cml0ZS1yZWRvbWUtdGFyZWZhIl0sImV4cCI6MTU4NzEzOTk4NywianRpIjoiZDRhOWMxNzAtNjg4Yi00NjNiLThlNjYtNGM5MTYwZGRhNzcwIiwiY2xpZW50X2lkIjoibW9kcmVkLWZyb250LWNsaWVudCJ9.frmwRqrf-VjQBCYnRRoqzSmlpiOl6BuPPRg-dXanBso\"," + 
				"    \"token_type\": \"bearer\"," + 
				"    \"expires_in\": " + String.valueOf(exp) + "," + 
				"    \"scope\": \"trust validation_hla integracao read-redome-notificacao write-redome-notificacao read-redome-tarefa write-redome-tarefa\"," + 
				"    \"jti\": \"d4a9c170-688b-463b-8e66-4c9160dda770\"" + 
				"}";
	
		return json;
	}

}
