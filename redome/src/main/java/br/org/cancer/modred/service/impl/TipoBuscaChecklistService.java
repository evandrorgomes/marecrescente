package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.TipoBuscaChecklist;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITipoBuscaChecklistRepository;
import br.org.cancer.modred.service.ITipoBuscaChecklistService;
import br.org.cancer.modred.service.impl.custom.AbstractService;


/**
 * Classe de implementação de métodos de negócio de TipoBuscaChecklist.
 * @author Filipe Paes
 *
 */
@Service
public class TipoBuscaChecklistService  extends AbstractService<TipoBuscaChecklist, Long> implements ITipoBuscaChecklistService {

	@Autowired
	private ITipoBuscaChecklistRepository tipoBuscaChecklistRepository;
	
	
	@Override
	public IRepository<TipoBuscaChecklist, Long> getRepository() {
		return tipoBuscaChecklistRepository;
	}

}
