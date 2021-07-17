package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.model.ContatoTelefonicoCourier;
import br.org.cancer.redome.courier.persistence.IContatoTelefonicoCourierRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IContatoTelefonicoCourierService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;

/**
 * Classe de implementacao dos métodos de negócio de contato telefonico de courier.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ContatoTelefonicoCourierService  extends AbstractService<ContatoTelefonicoCourier, Long> 
implements IContatoTelefonicoCourierService{
	
	@Autowired
	private IContatoTelefonicoCourierRepository contatoTelefonicoCourierRepository;

	@Override
	public IRepository<ContatoTelefonicoCourier, Long> getRepository() {
		return contatoTelefonicoCourierRepository;
	}

}
