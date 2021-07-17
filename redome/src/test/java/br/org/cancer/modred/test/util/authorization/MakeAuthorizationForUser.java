package br.org.cancer.modred.test.util.authorization;

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
				"        \"modred-validation\"," + 
				"        \"modred-rest-invoice\"" + 
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
		
		
		return json;
	}
	
	@Override
	public String getAccessTokenJson() {
		
		Long exp = Instant.now().plus(15, ChronoUnit.MINUTES) .toEpochMilli(); 
		
		String rec = recursos.stream().map(recurso -> "\"" + recurso + "\"").collect(Collectors.joining(", "));
		String per = perfis.stream().map(perfil -> "\"" + perfil + "\"").collect(Collectors.joining(", "));
		
		String json = "{\n" + 
				"    \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW9kcmVkX3Jlc3RfYXBpIiwibW9kcmVkLXJlc3Qtd29ya3VwIiwicmVkb21lLWF1dGgiLCJtb2RyZWQtdmFsaWRhdGlvbiIsIm1vZHJlZC1yZXN0LWludm9pY2UiLCJtb2RyZWQtcmVzdC1maWxhIiwibW9kcmVkLXJlc3QtdGFyZWZhIiwibW9kcmVkX3Jlc3Rfbm90aWZpY2FjYW8iXSwidXNlcl9uYW1lIjoiTUVESUNPX1JFRE9NRSIsInNjb3BlIjpbInRydXN0IiwidmFsaWRhdGlvbl9obGEiLCJpbnRlZ3JhY2FvIiwicmVhZC1yZWRvbWUtbm90aWZpY2FjYW8iLCJ3cml0ZS1yZWRvbWUtbm90aWZpY2FjYW8iLCJyZWFkLXJlZG9tZS10YXJlZmEiLCJ3cml0ZS1yZWRvbWUtdGFyZWZhIl0sImNlbnRyb3MiOltdLCJhY3RpdmUiOnRydWUsIm5vbWUiOiJNRURJQ08gUkVET01FIiwicmVjdXJzb3MiOlsiQURJQ0lPTkFSX1JFU1NBTFZBX0RPQURPUiIsIkFWQUxJQVJfUEVESURPX0NPTEVUQSIsIkFWQUxJQVJfUFJFU0NSSUNBTyIsIkFWQUxJQVJfV09SS1VQX0RPQURPUiIsIkNBREFTVFJBUl9BTkFMSVNFX01FRElDQV9ET0FET1IiLCJDQU5DRUxBUl9XT1JLVVAiLCJFWENMVUlSX1JFU1NBTFZBX0RPQURPUiIsIklOQVRJVkFSX0RPQURPUl9BVkFMSUFDQU9fV09SS1VQIiwiSU5DTFVJUl9SRVNTQUxWQSIsIlZJU1VBTElaQVJfSURFTlRJRklDQUNBT19DT01QTEVUQSIsIlZJU1VBTElaQVJfSURFTlRJRklDQUNBT19DT01QTEVUQV9ET0FET1IiXSwiZXhwIjoxNTg5MjMzMTUyLCJhdXRob3JpdGllcyI6WyJNRURJQ09fUkVET01FIl0sImp0aSI6ImZmYTQzODZjLTcxYjEtNGZiYy05OGE4LWVkMmMyZmE0ZmNiMSIsImJhbmNvU2FuZ3VlIjpudWxsLCJjbGllbnRfaWQiOiJtb2RyZWQtZnJvbnQtY2xpZW50In0.NK2Pus200_2EdmoKTe1sPI-OkHHKnhrngzYqXJHFH4k\"," + 
				"    \"token_type\": \"bearer\"," + 
				"    \"expires_in\": " + String.valueOf(exp) + "," + 
				"    \"scope\": \"trust validation_hla integracao read-redome-notificacao write-redome-notificacao read-redome-tarefa write-redome-tarefa\"," + 
				"    \"centros\": []," + 
				"    \"active\": true," + 
				"    \"nome\": \"MEDICO REDOME\"," + 
				"    \"recursos\": [" + rec + 
				"    ]," + 
				"    \"bancoSangue\": null," + 
				"    \"jti\": \"ffa4386c-71b1-4fbc-98a8-ed2c2fa4fcb1\""; 
		
		return json;
	}

}
