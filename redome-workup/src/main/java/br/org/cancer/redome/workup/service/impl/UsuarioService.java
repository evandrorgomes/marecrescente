package br.org.cancer.redome.workup.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.facade.IAuthenticationFacade;
import br.org.cancer.redome.workup.model.security.Usuario;
import br.org.cancer.redome.workup.persistence.IUsuarioRepository;
import br.org.cancer.redome.workup.service.IUsuarioService;

@Transactional(readOnly = true)
@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired 
	private IAuthenticationFacade authenticationFacade;
	
//	@Autowired
//	private IUsuarioService usuarioService;
//	
//	private IGenericRepository genericRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Usuario obterUsuarioLogado() {		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = null;
		
		if (authentication != null && authentication.isAuthenticated()) {
			
			if (authentication.getPrincipal() instanceof UserDetails) {
				usuario = (Usuario) authentication.getPrincipal();
			}
			else {
				usuario = usuarioRepository.findByUsername(authentication
						.getPrincipal()
						.toString());
				
				if (usuario == null) {
					throw new UsernameNotFoundException("Usuário não encontrado");
				}

				usuario = usuario.toBuilder()
						.authorities(authenticationFacade.getAuthentication().getAuthorities()
								.stream()
								.map(authority -> authority.getAuthority())
								.collect(Collectors.toList()))
						.build();
						
				return usuario;
			}
		}
		
		return null;
	}

	
//	@Override
//	public Usuario obterUsuarioLogado() {		
//Authentication authentication = authenticationFacade.getAuthentication();
//		
//		if (!authentication.isAuthenticated()) {
//			throw new BusinessException("erro.mensagem.usuario.nao.encontrado");
//		}		
//
//		final PublicKey chavePublica = AsymmetricCryptography.getPublicKey("modred_public_key.der");
//		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//		LinkedHashMap<String, Object> decodedDetails = (LinkedHashMap<String, Object>) details.getDecodedDetails();
//		
//		Usuario usuario = Usuario.builder()
//				.id(obterLongValue(decodedDetails.get("id"), chavePublica))
//				.username(authentication.getName())
//				.nome(obterStringValue(decodedDetails.get("nome")))
//				.authorities(authentication.getAuthorities()
//						.stream()
//						.map(authority -> authority.getAuthority())
//						.collect(Collectors.toList()))
//				.idTransportadora(obterLongValue(decodedDetails.get("transportadora"), chavePublica))
//				.build();
//		
//		System.out.println(usuario);
//		
//		return usuario; 
//	}

	@Override
	public Long obterIdUsuarioLogado() {
		return obterUsuarioLogado().getId();
	}
	
//	private Long obterLongValue(Object value, PublicKey publicKey) {
//		if (value == null || value.toString().equals("null")) {
//			return null;
//		}
//		final String stringValue = AsymmetricCryptography.decryptText(value.toString(), publicKey);
//		return Long.parseLong(stringValue);				
//	}
//	
//	private String obterStringValue(Object value) {
//		if (value == null || value.toString().equals("null")) {
//			return null;
//		}		
//		return value.toString();				
//	}
	

}
