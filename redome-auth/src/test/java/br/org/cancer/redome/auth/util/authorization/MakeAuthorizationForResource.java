package br.org.cancer.redome.auth.util.authorization;

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

}
