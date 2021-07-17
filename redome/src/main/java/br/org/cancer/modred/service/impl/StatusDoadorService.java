package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.persistence.IStatusDoadorRepository;
import br.org.cancer.modred.service.IStatusDoadorService;

/**
 * Classe de implementação dos métodos de negócio de status doador.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class StatusDoadorService implements IStatusDoadorService {

	@Autowired
	private IStatusDoadorRepository statusDoadorRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatusDoador obterStatusPorId(Long idStatus) {
		return statusDoadorRepository.findById(idStatus).get();
	}

}
