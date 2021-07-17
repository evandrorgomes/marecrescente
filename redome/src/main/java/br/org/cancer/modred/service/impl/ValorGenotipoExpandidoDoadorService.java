package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoExpandidoDoador;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoExpandidoDoadorRepository;
import br.org.cancer.modred.service.IValorGenotipoExpandidoDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade GenotipoExpandido.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class ValorGenotipoExpandidoDoadorService extends AbstractService<ValorGenotipoExpandidoDoador, Long> implements IValorGenotipoExpandidoDoadorService {

	@Autowired
	private IValorGenotipoExpandidoDoadorRepository valorGenotipoExpandidoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRepository<ValorGenotipoExpandidoDoador, Long> getRepository() {
		return valorGenotipoExpandidoRepository;
	}

	
	@Override
	public void deletarValoresPorGenotipo(Long idGenotipo) {
		valorGenotipoExpandidoRepository.deletarValoresPorGenotipo(idGenotipo);
	}
}
