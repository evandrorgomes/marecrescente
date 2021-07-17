package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoBuscaDoador;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoBuscaDoadorRepository;
import br.org.cancer.modred.service.IValorGenotipoBuscaDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class ValorGenotipoBuscaDoadorService extends AbstractService<ValorGenotipoBuscaDoador, Long> implements IValorGenotipoBuscaDoadorService {

	@Autowired
	private IValorGenotipoBuscaDoadorRepository valorGenotipoBuscaRepository;
	
	@Override
	public IRepository<ValorGenotipoBuscaDoador, Long> getRepository() {
		return valorGenotipoBuscaRepository;
	}

	@Override
	public void deletarValoresPorGenotipo(Long genotipoId) {
		valorGenotipoBuscaRepository.deletarValoresPorGenotipo(genotipoId);
	}

}
