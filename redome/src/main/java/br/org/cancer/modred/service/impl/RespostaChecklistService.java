package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.RespostaChecklist;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IRespostaChecklistRepository;
import br.org.cancer.modred.service.IRespostaChecklistService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação dos métodos de negócio de resposta de checklist. 
 * @author Filipe Paes
 *
 */
@Service
public class RespostaChecklistService  extends AbstractService<RespostaChecklist, Long> implements IRespostaChecklistService {

	@Autowired
	private IRespostaChecklistRepository respostaChecklistRepository;
	
	@Override
	public IRepository<RespostaChecklist, Long> getRepository() {
		return respostaChecklistRepository;
	}

	@Override
	public RespostaChecklist obterRespostaPor(Long idItem, Long idPedidoLogistica) {
		return respostaChecklistRepository.obterRespostaPor(idItem, idPedidoLogistica);
	}

	@Override
	public List<RespostaChecklist> obterRepostasPorIdPedidoLogistica(Long idLogistica) {
		return respostaChecklistRepository.findByPedidoLogisticaId(idLogistica);
	}

}