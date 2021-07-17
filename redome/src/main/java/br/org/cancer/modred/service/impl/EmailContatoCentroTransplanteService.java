package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.EmailContatoCentroTransplante;
import br.org.cancer.modred.persistence.IEmailContatoCentroTransplanteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEmailContatoCentroTransplanteService;
import br.org.cancer.modred.service.impl.custom.AbstractService;


/**
 * Implementação dos métodos de negócio de email de contato do centro de transplante.
 * @author bruno.sousa
 *
 */
@Service
public class EmailContatoCentroTransplanteService extends AbstractService<EmailContatoCentroTransplante, Long> 
implements IEmailContatoCentroTransplanteService  {

	@Autowired
	private IEmailContatoCentroTransplanteRepository emailContatoCentroTransplanteRepository;
	
	@Override
	public IRepository<EmailContatoCentroTransplante, Long> getRepository() {
		return emailContatoCentroTransplanteRepository;
	}

	@Override
	public void salvar(EmailContatoCentroTransplante emailCentroTransplante) {
		if (emailCentroTransplante.getCentroTransplante() != null) {
			if (emailCentroTransplante.getCentroTransplante().getEmails().isEmpty() && (emailCentroTransplante.getPrincipal() == null || !emailCentroTransplante.getPrincipal())) {
				emailCentroTransplante.setPrincipal(true);
			}
			else if (!emailCentroTransplante.getCentroTransplante().getEmails().isEmpty() && emailCentroTransplante.getPrincipal() != null && emailCentroTransplante.getPrincipal()) {
				emailCentroTransplante.getCentroTransplante().getEmails().stream().forEach(email-> email.setPrincipal(false));
				saveAll(emailCentroTransplante.getCentroTransplante().getEmails());
			}
		}
		
		save(emailCentroTransplante);		
	}

}
