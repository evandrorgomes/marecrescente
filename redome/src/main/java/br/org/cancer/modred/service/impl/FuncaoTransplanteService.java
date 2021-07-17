package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.persistence.IFuncaoTransplanteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IFuncaoTransplanteService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para metodos de negocio de função transplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class FuncaoTransplanteService extends AbstractService<FuncaoTransplante, Long> implements IFuncaoTransplanteService {

	@Autowired
	private IFuncaoTransplanteRepository funcaoTransplanteRepository;
	
	@Override
	public IRepository<FuncaoTransplante, Long> getRepository() {
		return funcaoTransplanteRepository;
	}

}
