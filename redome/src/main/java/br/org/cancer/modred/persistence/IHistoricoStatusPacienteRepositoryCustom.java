package br.org.cancer.modred.persistence;

import br.org.cancer.modred.model.HistoricoStatusPaciente;
import br.org.cancer.modred.model.Paciente;

/**
 * Classe de implentação de métodos customizados de histórico 
 * de status de paciente.
 * @author Filipe Paes
 *
 */
public interface IHistoricoStatusPacienteRepositoryCustom {

	/**
	 * Obtém o último status de paciente que define o status atual do mesmo
	 * de acordo com o a data de inclusão de forma decrescente e a ordenação de 
	 * forma crescente.
	 * @param paciente detentor do histórico.
	 * @return objeto de histórico de referido.
	 */
	HistoricoStatusPaciente obterUltimoStatusPaciente(Paciente paciente);
	
}
