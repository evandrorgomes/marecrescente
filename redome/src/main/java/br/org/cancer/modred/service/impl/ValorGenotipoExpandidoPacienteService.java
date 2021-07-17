package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoExpandidoPaciente;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoExpandidoRepository;
import br.org.cancer.modred.service.IValorGenotipoExpandidoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade GenotipoExpandido.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class ValorGenotipoExpandidoPacienteService extends AbstractService<ValorGenotipoExpandidoPaciente, Long> implements IValorGenotipoExpandidoService {

	@Autowired
	private IValorGenotipoExpandidoRepository valorGenotipoExpandidoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRepository<ValorGenotipoExpandidoPaciente, Long> getRepository() {
		return valorGenotipoExpandidoRepository;
	}

	@Override
	public void deletarValoresPorPaciente(Long rmr) {
		valorGenotipoExpandidoRepository.deletarValoresPorPaciente(rmr);
	}
	
	@Override
	public void deletarValoresPorGenotipo(Long idGenotipo) {
		valorGenotipoExpandidoRepository.deletarValoresPorGenotipo(idGenotipo);
	}
}
