package br.org.cancer.modred.persistence;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Turno;

/**
 * Interface de persistencia de turnos.
 * @author Filipe Paes
 *
 */
@Repository
public interface ITurnoRepository  extends JpaRepository<Turno, Long>{

	@Query("select t from Turno t where to_char(:hora, 'hh24:mi') between to_char(t.horaInicio, 'hh24:mi') and to_char(t.horaFim, 'hh24:mi') and t.inclusivoExclusivo = false ")
	Turno buscarTurnoPorHora(@Param("hora") LocalDateTime hora);

	Turno findByHoraInicioAndHoraFimAndInclusivoExclusivo(LocalDateTime horaInicio, LocalDateTime horaFim, Boolean inclusivoExclusivo );
}
