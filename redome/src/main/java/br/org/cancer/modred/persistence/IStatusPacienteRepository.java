package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.StatusPaciente;

/**
 * Interface de status do paciente.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IStatusPacienteRepository extends IRepository<StatusPaciente, Long> {
	
	/**
	 * Obtém o status do paciente por rmr.
	 * 
	 * @param rmr id do paciente.
	 * @return status do paciente.
	 */
	@Query("select sp from Paciente p join p.status sp where p.rmr = :id")
	StatusPaciente obterStatusPacientePorRmr(@Param("id") Long rmr);
}