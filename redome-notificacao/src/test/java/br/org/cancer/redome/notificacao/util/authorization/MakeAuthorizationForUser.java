package br.org.cancer.redome.notificacao.util.authorization;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MakeAuthorizationForUser implements IAuthorizationForUser {
	
	public String usuario = "";
	public List<String> recursos = new ArrayList<>();
	public List<String> perfis = new ArrayList<>();
	
	public MakeAuthorizationForUser(String usuario) {
		this.usuario = usuario;
	}
	
	public IAuthorizationForUser addRecurso(String...recurso) {
		Collections.addAll(this.recursos, recurso);
		return this;
	}
	
	public IAuthorizationForUser addPerfil(String...perfil) {
		Collections.addAll(this.perfis, perfil);
		return this;
	}
	
	public void clear() {
		usuario = null;
		perfis.clear();
		recursos.clear();
	}
	
	public String getJson() {
		
		Long exp = Instant.now().plus(15, ChronoUnit.MINUTES) .toEpochMilli(); 
		
		String rec = recursos.stream().map(recurso -> "\"" + recurso + "\"").collect(Collectors.joining(", "));
		String per = perfis.stream().map(perfil -> "\"" + perfil + "\"").collect(Collectors.joining(", "));
		
		String json = "{" + 
				"    \"aud\": [" + 
				"        \"modred_rest_api\"," + 
				"        \"modred-rest-notificacao\"" +
				"    ]," + 
				"    \"user_name\": \"" + usuario + "\"," + 
				"    \"scope\": [" + 
				"        \"trust\"," + 
				"        \"validation_hla\"," + 
				"        \"integracao\"," + 
				"		 \"read-redome-fila\", " +
				"		 \"write-redome-fila\"" +
				"    ]," + 
				"    \"active\": true," + 
				"    \"nome\": \"Analista Financeiro\"," + 
				"    \"recursos\": [" + rec + 
				"    ]," + 
				"    \"exp\": " + String.valueOf(exp) + "," + 
				"    \"authorities\": [" + per + 
				"    ]," + 
				"    \"jti\": \"630cef54-4e6e-40d2-bc71-7cbc50da5c09\"," + 
				"    \"client_id\": \"modred-front-client\"" + 
				"}";
		
		/*String x = "{" + 
				"    \"expires_in\": 3599, " + 
				"    \"scope\": \"server\"," + 
				"    \"jti\": \"85e4666c-8e71-4ba7-8f3b-719c0855ecd0\"" + 
				"}";*/
		
		
		return json;
	}

}
