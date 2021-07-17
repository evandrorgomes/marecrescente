package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.CancelamentoBusca;
import br.org.cancer.modred.persistence.ICancelamentoBuscaRepository;
import br.org.cancer.modred.service.ICancelamentoBuscaService;

/**
 * Implementacao da classe de negócios de Cancelamento de Busca.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class CancelamentoBuscaService implements ICancelamentoBuscaService {

	@Autowired
	private ICancelamentoBuscaRepository cancelamentoBuscaRepository;

	@Override
	public CancelamentoBusca obterUltimoCancelamentoBuscaPeloIdDaBusca(Long idBusca) {
		return cancelamentoBuscaRepository.findFirstByBuscaIdOrderByDataCriacaoDesc(idBusca);
	}

}
