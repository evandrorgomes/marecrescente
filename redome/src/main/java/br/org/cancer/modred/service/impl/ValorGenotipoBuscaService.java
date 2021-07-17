package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoBuscaPaciente;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoBuscaRepository;
import br.org.cancer.modred.service.IValorGenotipoBuscaService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class ValorGenotipoBuscaService extends AbstractService<ValorGenotipoBuscaPaciente, Long> implements IValorGenotipoBuscaService {

	@Autowired
	private IValorGenotipoBuscaRepository valorGenotipoBuscaRepository;
	
	@Override
	public IRepository<ValorGenotipoBuscaPaciente, Long> getRepository() {
		return valorGenotipoBuscaRepository;
	}

	@Override
	public void deletarValoresPorGenotipo(Long genotipoId) {
		valorGenotipoBuscaRepository.deletarValoresPorGenotipo(genotipoId);
	}

}
