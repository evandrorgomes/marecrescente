package br.org.cancer.modred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IStatusPacienteRepository;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Classe de negócios para status do Paciente.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StatusPacienteService extends AbstractLoggingService<StatusPaciente, Long> implements IStatusPacienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusPacienteService.class);

	@Autowired
	private IStatusPacienteRepository statusPacienteRepository;


	@Override
	public StatusPaciente obterStatusPacientePorRmr(Long rmr) {
		return statusPacienteRepository.obterStatusPacientePorRmr(rmr);
	}


	@Override
	public String[] obterParametros(StatusPaciente entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IRepository<StatusPaciente, Long> getRepository() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Paciente obterPaciente(StatusPaciente entity) {
		// TODO Auto-generated method stub
		return null;
	}


}
