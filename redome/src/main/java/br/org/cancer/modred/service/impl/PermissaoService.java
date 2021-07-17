package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.security.Permissao;
import br.org.cancer.modred.model.security.PermissaoId;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPermissaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IPermissaoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação da interface que descreve os métodos de negócio que operam sobre a entidade Permissao.
 * 
 * @author Thiago Moraes
 *
 */
@Service
@Transactional
public class PermissaoService extends AbstractService<Permissao, PermissaoId> implements IPermissaoService {

    @Autowired
    private IPermissaoRepository permissaoRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService; 
    
	@Override
	public IRepository<Permissao, PermissaoId> getRepository() {
		return permissaoRepository;
	}
	
	
	@Override
	public Boolean usuarioLogadoPossuiPermissao(String recurso) {
		Usuario usuarioLogado = customUserDetailsService.obterUsuarioLogado();
		return usuarioLogado.getPerfis().stream().anyMatch(perfil -> {
			return perfil.getRecursos().stream().anyMatch(permissao -> recurso.equals(permissao.getSigla()));
		});		
	}

}
