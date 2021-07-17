package br.org.cancer.redome.workup.util.authorization;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.redome.workup.util.AsymmetricCryptography;


public class MakeAuthorizationForUser implements IAuthorizationForUser {
	
	private Long id = null;
	public String usuario = "";
	private String nomeUsuario = "";
	public List<String> recursos = new ArrayList<>();
	public List<String> perfis = new ArrayList<>();
	public List<String> resources  = Arrays.asList("modred_rest_api", "modred-rest-tarefa", "modred-rest-notificacao", "modred-rest-workup");
	public Long transportadora;
	
	public MakeAuthorizationForUser(Long id, String username, String nome) {
		this.id = id;
		this.usuario = username;
		this.nomeUsuario = nome;
	}
	
	public MakeAuthorizationForUser(Long id, String username) {
		this(id, username, username);
	}
	
	public IAuthorizationForUser addRecurso(String...recurso) {
		Collections.addAll(this.recursos, recurso);
		return this;
	}
	
	public IAuthorizationForUser addPerfil(String...perfil) {
		Collections.addAll(this.perfis, perfil);
		return this;
	}
	
	public IAuthorizationForUser transportadora(Long id) {
		transportadora = id;
		return this;
	}
	
	public void clear() {
		id = null;
		usuario = null;
		nomeUsuario = null;
		perfis.clear();
		recursos.clear();
		transportadora = null;
	}
	
	public String getJson() {
		
		final PrivateKey chavePrivada = AsymmetricCryptography.getPrivateKey("modred_private_key.der");
		
		Long exp = Instant.now().plus(15, ChronoUnit.MINUTES) .toEpochMilli(); 
		
		String rec = recursos.stream().map(recurso -> "\"" + recurso + "\"").collect(Collectors.joining(", "));
		String per = perfis.stream().map(perfil -> "\"" + perfil + "\"").collect(Collectors.joining(", "));
		String aud = resources.stream().map(resource -> "\"" + resource + "\"").collect(Collectors.joining(", "));		
		String transp = transportadora == null ? null :
			AsymmetricCryptography.encryptText(transportadora.toString(), chavePrivada);
		String idUsuario = id == null ? null : 
			AsymmetricCryptography.encryptText(id.toString(), chavePrivada);
		
		String json = "{" + 
				"    \"aud\": [" + aud + 
				"    ]," + 
				"    \"id\": \"" + idUsuario + "\"," +
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
				"    \"client_id\": \"modred-front-client\"," +
				"    \"transportadora\":\"" + transp + "\"" +
				"}";
		
		/*String x = "{" + 
				"    \"expires_in\": 3599, " + 
				"    \"scope\": \"server\"," + 
				"    \"jti\": \"85e4666c-8e71-4ba7-8f3b-719c0855ecd0\"" + 
				"}";*/
		
		
		return json;
	}

}
