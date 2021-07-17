package br.org.cancer.redome.courier.service.impl;

import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.facade.IAuthenticationFacade;
import br.org.cancer.redome.courier.security.Usuario;
import br.org.cancer.redome.courier.service.IUsuarioService;
import br.org.cancer.redome.courier.util.AsymmetricCryptography;

@Transactional(readOnly = true)
@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired 
	private IAuthenticationFacade authenticationFacade;
	
	//@Autowired
	//private IUsuarioRepository usuarioRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Usuario obterUsuarioLogado() {
		Authentication authentication = authenticationFacade.getAuthentication();
		
		if (!authentication.isAuthenticated()) {
			throw new BusinessException("erro.mensagem.usuario.nao.encontrado");
		}		

		final PublicKey chavePublica = AsymmetricCryptography.getPublicKey("modred_public_key.der");
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		LinkedHashMap<String, Object> decodedDetails = (LinkedHashMap<String, Object>) details.getDecodedDetails();
		
		Usuario usuario = Usuario.builder()
				.id(obterLongValue(decodedDetails.get("id"), chavePublica))
				.username(authentication.getName())
				.nome(obterStringValue(decodedDetails.get("nome")))
				.authorities(authentication.getAuthorities()
						.stream()
						.map(authority -> authority.getAuthority())
						.collect(Collectors.toList()))
				.idTransportadora(obterLongValue(decodedDetails.get("transportadora"), chavePublica))
				.build();
		
		System.out.println(usuario);
		
		return usuario; 
	}

	@Override
	public Long obterIdUsuarioLogado() {
		return obterUsuarioLogado().getId();
	}
	
	@Override
	public Long obterIdTransportadora() {
		return obterUsuarioLogado().getIdTransportadora();
	}
	
	private Long obterLongValue(Object value, PublicKey publicKey) {
		if (value == null || value.toString().equals("null")) {
			return null;
		}
		final String stringValue = AsymmetricCryptography.decryptText(value.toString(), publicKey);
		return Long.parseLong(stringValue);				
	}
	
	private String obterStringValue(Object value) {
		if (value == null || value.toString().equals("null")) {
			return null;
		}		
		return value.toString();				
	}

}
