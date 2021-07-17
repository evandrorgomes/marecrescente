package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ContatoTelefonicoCentroTransplante;
import br.org.cancer.modred.persistence.IContatoTelefonicoCentroTransplanteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IContatoTelefonicoCentroTransplanteService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de negócio envolvendo a entidade ContatoTelefonicoCentroTransplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ContatoTelefonicoCentroTransplanteService extends AbstractService<ContatoTelefonicoCentroTransplante, Long> 
	implements IContatoTelefonicoCentroTransplanteService {

	@Autowired
	private IContatoTelefonicoCentroTransplanteRepository contatoTelefonicoCentroTransplanteRepository;

	@Override
	public IRepository<ContatoTelefonicoCentroTransplante, Long> getRepository() {
		return contatoTelefonicoCentroTransplanteRepository;
	}
	
}
