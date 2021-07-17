package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.TipoPermissividade;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITipoPermissividadeRepository;
import br.org.cancer.modred.service.ITipoPermissividadeService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para metodos de negocio do tipo de permissividade.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class TipoPermissividadeService extends AbstractService<TipoPermissividade, Long> implements ITipoPermissividadeService {

	@Autowired
	private ITipoPermissividadeRepository tipoPermissividadeRepository;
	
	@Override
	public IRepository<TipoPermissividade, Long> getRepository() {
		return tipoPermissividadeRepository;
	}

}
