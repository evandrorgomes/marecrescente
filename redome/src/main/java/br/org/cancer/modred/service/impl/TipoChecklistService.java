package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.RespostaChecklist;
import br.org.cancer.modred.model.TipoChecklist;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITipoChecklistRepository;
import br.org.cancer.modred.service.ITipoChecklistService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação dos métodos de negócio de tipo de checklist. 
 * @author Filipe Paes
 *
 */
@Service
public class TipoChecklistService extends AbstractService<TipoChecklist, Long> implements ITipoChecklistService {

	@Autowired
	private ITipoChecklistRepository tipoCheckListRepository;
	
	@Autowired
	private RespostaChecklistService respostaChecklistService;
	
	@Autowired
	private PedidoLogisticaService pedidoLogisticaService;
	
	
	@Override
	public IRepository<TipoChecklist, Long> getRepository() {
		return tipoCheckListRepository;
	}

	@Override
	public TipoChecklist obterTipoChecklist(Long idTipoChecklist) {
		TipoChecklist tipoChecklist = findById(idTipoChecklist);
		if(tipoChecklist != null) {
			tipoChecklist.getCategorias().forEach(item -> {
				item.getItens().sort((l1, l2) -> l1.getNome().compareTo(l2.getNome()));
			});
		}
		return tipoChecklist;
	}

	@Override
	public void salvarChecklist(RespostaChecklist resposta) {
		RespostaChecklist respostaLoad = respostaChecklistService.obterRespostaPor(resposta.getItem().getId(), resposta.getPedidoLogistica().getId());
		if(respostaLoad != null){
			respostaLoad.setResposta(resposta.getResposta());
			respostaChecklistService.save(respostaLoad);
		}
		else{
			if(resposta.getPedidoLogistica() != null){
				resposta.setPedidoLogistica(pedidoLogisticaService.findById(resposta.getPedidoLogistica().getId()));
			}
			respostaChecklistService.save(resposta);
		}
	}
	
}
