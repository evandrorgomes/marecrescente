package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ReservaDoadorInternacional;

/**
 * Interface para acesso ao banco de dados envolvendo a classe ReservaDoadorInternacional.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IReservaDoadorInternacionalRepository extends IRepository<ReservaDoadorInternacional, Long> {
	
	ReservaDoadorInternacional findByDoadorId(Long idDoador);
	
	/**
	 * Obtém o paciente que possui o doador informado como reservado.
	 * 
	 * @param idDoador ID do doador.
	 * @return paciente associado.
	 */
	@Query("select paci from ReservaDoadorInternacional res join res.busca busc join busc.paciente paci join res.doador doad "
		+ " where doad.id = :idDoador")
	Paciente obterPacienteAssociado(@Param("idDoador") Long idDoador);
}
