package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.TipoAmostra;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITipoAmostraRepository;
import br.org.cancer.modred.service.ITipoAmostraService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação da interface {@link TipoAmostraService}
 * @author Filipe Paes
 *
 */
@Service
public class TipoAmostraService   extends AbstractService<TipoAmostra, Long> implements ITipoAmostraService   {

	
	@Autowired
	private ITipoAmostraRepository repository;
	
	@Override
	public IRepository<TipoAmostra, Long> getRepository() {
		return repository; 
	}

}
