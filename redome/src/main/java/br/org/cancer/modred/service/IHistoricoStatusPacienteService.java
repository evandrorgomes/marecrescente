package br.org.cancer.modred.service;

import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.StatusPacientes;

/**
 * Classe para métodos de troca de status e armazenamento de histórico
 * de status.
 * @author Filipe Paes
 *
 */
public interface IHistoricoStatusPacienteService {
	
	/**
	 * Adicionar um novo status para um paciente.
	 * @param status a ser adicionado.
	 * @param paciente pacientea a ter seu status adicionado.
	 */
	void adicionarStatusPaciente(StatusPacientes status, Paciente paciente);

}
