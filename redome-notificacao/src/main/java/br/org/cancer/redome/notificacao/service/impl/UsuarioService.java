package br.org.cancer.redome.notificacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.notificacao.exception.BusinessException;
import br.org.cancer.redome.notificacao.facade.IAuthenticationFacade;
import br.org.cancer.redome.notificacao.model.Usuario;
import br.org.cancer.redome.notificacao.persistence.IUsuarioRepository;
import br.org.cancer.redome.notificacao.service.IUsuarioService;

@Transactional(readOnly = true)
@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired 
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Usuario obterUsuarioLogado() {		
		Usuario usuario = usuarioRepository.findByUsername(authenticationFacade.getAuthentication().getName());
		if (usuario == null) {
			throw new BusinessException("");
		}
		return usuario; 
	}

	@Override
	public Long obterIdUsuarioLogado() {
		return obterUsuarioLogado().getId();
	}
	
	

}
