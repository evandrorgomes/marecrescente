package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.persistence.IEmailContatoTransportadoraRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IEmailContatoTransportadoraService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;


/**
 * Implementação dos métodos de negócio de email de transportadora.
 * @author Filipe Paes
 *
 */
@Service
public class EmailContatoTransportadoraService extends AbstractService<EmailContatoTransportadora, Long> 
implements IEmailContatoTransportadoraService  {

	@Autowired
	private IEmailContatoTransportadoraRepository emailContatoTransportadoraRepository;
	
	@Override
	public IRepository<EmailContatoTransportadora, Long> getRepository() {
		return emailContatoTransportadoraRepository;
	}

	@Override
	public void atualizar(Long idTransportadora, EmailContatoTransportadora emailContatoTransportadora) {
		EmailContatoTransportadora emailLocalizado = this.findById(emailContatoTransportadora.getId());
		emailContatoTransportadora.setEmail(emailLocalizado.getEmail());
		this.save(emailLocalizado);
	}
}
