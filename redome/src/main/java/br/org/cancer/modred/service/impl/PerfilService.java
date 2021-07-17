package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.persistence.IPerfilRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IPerfilService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação da interface que descreve os métodos de negócio que operam sobre a entidade Perfil.
 * 
 * @author Thiago Moraes
 *
 */
@Service
@Transactional
public class PerfilService extends AbstractService<Perfil, Long> implements IPerfilService{

    @Autowired
    private IPerfilRepository perfilRepository;
    
    @Override
	public IRepository<Perfil, Long> getRepository() {
		return perfilRepository;
	}

	@Override
	public List<Perfil> listarDisponiveisParaRedome() {
		return perfilRepository.listarDisponiveisParaRedome();
	}
    
}
