package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.HistoricoStatusPaciente;


/**
 * Interface de persistencia de HistoricoStatusPaciente.
 * @author Filipe Paes
 *
 */
@Repository
public interface IHistoricoStatusPacienteRepository  extends IRepository<HistoricoStatusPaciente, Long>, IHistoricoStatusPacienteRepositoryCustom{

}
