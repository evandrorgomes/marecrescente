package br.org.cancer.modred.service.impl;

import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface service de status do paciente.
 * 
 * @author evandro.gomes
 *
 */
public interface IStatusPacienteService extends IService<StatusPaciente, Long>{

	/**
	 * Obtem o status do paciente.
	 * 
	 * @param rmr do paciente.
	 * @return dto do status do paciente.
	 */
	StatusPaciente obterStatusPacientePorRmr(Long rmr);

}
