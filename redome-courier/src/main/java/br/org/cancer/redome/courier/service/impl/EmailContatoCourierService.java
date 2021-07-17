package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.courier.model.EmailContatoCourier;
import br.org.cancer.redome.courier.persistence.IEmailContatoCourierRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IEmailContatoCourierService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;

/**
 * Implementação de métodos de negócio de Email de Courier.
 * @author Filipe Paes
 *
 */
@Service
public class EmailContatoCourierService extends AbstractService<EmailContatoCourier, Long> implements IEmailContatoCourierService  {
	
	@Autowired
	private IEmailContatoCourierRepository emailContatoCourierRepository;

	@Override
	public IRepository<EmailContatoCourier, Long> getRepository() {
		return emailContatoCourierRepository;
	}


}
