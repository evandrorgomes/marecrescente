package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.HistoricoStatusPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.model.domain.StatusPacientes;
import br.org.cancer.modred.persistence.IHistoricoStatusPacienteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IHistoricoStatusPacienteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação da classe de negócios de Historico de Status de Paciente.
 * @author Filipe Paes
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HistoricoStatusPacienteService extends AbstractService<HistoricoStatusPaciente, Long>  implements IHistoricoStatusPacienteService {

	@Autowired
	private IHistoricoStatusPacienteRepository historicoStatusPacienteRepository;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Override
	public IRepository<HistoricoStatusPaciente, Long> getRepository() {
		return historicoStatusPacienteRepository;
	}

	@Override
	public void adicionarStatusPaciente(StatusPacientes status, Paciente paciente) {
		HistoricoStatusPaciente historico = new HistoricoStatusPaciente();
		historico.setDataAlteracao(LocalDateTime.now());
		historico.setPaciente(paciente);
		historico.setStatus(new StatusPaciente(status.getId()));
		this.save(historico);
		salvarStatusPaciente(paciente);
	}

	private void salvarStatusPaciente(Paciente paciente) {
		HistoricoStatusPaciente statusAtual = this.historicoStatusPacienteRepository.obterUltimoStatusPaciente(paciente);
		Paciente pacienteObtido = pacienteService.findById(paciente.getRmr());
		pacienteObtido.setStatus(statusAtual.getStatus());
		pacienteService.save(pacienteObtido);
	}
	
	
	
	

}
