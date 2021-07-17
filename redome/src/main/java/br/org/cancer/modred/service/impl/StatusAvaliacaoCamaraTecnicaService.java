package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IStatusAvaliacaoCamaraTecnicaRepository;
import br.org.cancer.modred.service.IStatusAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de negócios de status de avaliação de câmara técnica.
 * @author Filipe Paes
 *
 */
@Service
public class StatusAvaliacaoCamaraTecnicaService extends AbstractService<StatusAvaliacaoCamaraTecnica, Long> implements IStatusAvaliacaoCamaraTecnicaService {

	@Autowired
	private IStatusAvaliacaoCamaraTecnicaRepository statusAvaliacaoCamaraTecnicaRepository;
	
	@Override
	public IRepository<StatusAvaliacaoCamaraTecnica, Long> getRepository() {
		return statusAvaliacaoCamaraTecnicaRepository;
	}
	

}
