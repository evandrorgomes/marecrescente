package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.security.Sistema;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ISistemaRepository;
import br.org.cancer.modred.service.ISistemaService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação da interface que descreve os métodos de negócio que operam sobre a entidade Sistema.
 * 
 * @author Thiago Moraes
 *
 */
@Service
@Transactional
public class SistemaService extends AbstractService<Sistema, Long> implements ISistemaService{

    @Autowired
    private ISistemaRepository sistemaRepository;
    
    @Override
	public IRepository<Sistema, Long> getRepository() {
		return sistemaRepository;
	}

	@Override
	public List<Sistema> listarDisponiveisParaRedome() {
		return sistemaRepository.listarDisponiveisParaRedome();
	}
    
}
