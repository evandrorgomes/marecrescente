package br.org.cancer.redome.tarefa.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.tarefa.model.Tarefa;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da plataforma redome.
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade Tarefa.
 * 
 * @author Thiago Moraes
 *
 */
@Repository
public interface ITarefaRepository extends JpaRepository<Tarefa, Long>, ITarefaRepositoryCustom {

	@Query("select t from Tarefa t where t.status = 1 and t.tipoTarefa.automatica = true "
			+ "OR ((t.inclusivoExclusivo = 1 AND t.dataInicio IS NULL AND  t.dataFim IS NULL AND SYSDATE BETWEEN t.horaInicio AND t.horaFim) "
			+ "OR (t.inclusivoExclusivo = 0 AND t.dataInicio IS NULL AND t.dataFim IS NULL AND SYSDATE NOT BETWEEN t.horaInicio AND t.horaFim ) "
			+ "OR (t.inclusivoExclusivo = 1 AND t.horaInicio IS NULL AND t.horaFim IS NULL AND SYSDATE BETWEEN CAST(t.dataInicio AS time) AND CAST(t.dataFim AS time)) "
			+ "OR (t.inclusivoExclusivo = 0 AND t.horaInicio IS NULL AND t.horaFim IS NULL AND SYSDATE NOT BETWEEN  CAST(t.dataInicio AS time) AND CAST(t.dataFim AS time))) "
			+ "order by t.agendado, t.dataCriacao")
	List<Tarefa> listarTarefasAutomaticasEmAberto();
	
	@Query("select t from Tarefa t where t.status = 4 and t.tipoTarefa.automatica = true AND :data >=  t.dataInicio order by t.dataInicio")
	List<Tarefa> listarTarefasNotificacoesEmAguardandoAgendamento(@Param(value="data") LocalDateTime data);
	
	
	
	/**
	 * Método para atribuir a tarefa para um usuário responsável por executar a tarefa.
	 * 
	 * @param tarefaId - identificação da tarefa.
	 * @param usuarioId - identificação do usuário que executará a tarefa.
	 * @param now - data corrente.
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Tarefa SET "+
	                "usuarioResponsavel = :usuarioId, "+
	                "dataAtualizacao= :now, "+
	                "status= :status "+
	                "where id = :tarefaId")
	int atribuirTarefaUsuario(@Param("tarefaId") Long tarefaId, @Param("usuarioId") Long usuarioId,
			@Param("status") Long status, @Param("now") LocalDateTime now);
	
	
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Tarefa SET "+            
            "dataAtualizacao= :now, "+
            "status= :status "+
            "where id = :tarefaId AND status not in (3, 5)")
	int atualizarStatusTarefa(@Param("tarefaId") Long tarefaId, @Param("status") Long status, @Param("now") LocalDateTime now);
	

		
	/**
	 * Método que lista as tarefas de acordo com os parametros abaixo.
	 * 
	 * @param idTipoTarefa - Id do tipo de tarefa
	 * @param idTarefaPai - id da tarefa pai
	 * @return lista de tarefas filhas
	 */
	List<Tarefa> findByTipoTarefaIdAndTarefaPaiIdOrderByDataCriacao(Long idTipoTarefa, Long idTarefaPai);

	Boolean existsByProcessoIdAndTipoTarefaAutomaticaAndStatus(Long idProcesso, boolean tipoTarefaAutomatica, Long idStatusTarefa);

	List<Tarefa> findByTarefaPaiIdAndTipoTarefaIdAndStatus(Long idTarefaPai, Long idTipoTarefa, Long id);
	
	List<Tarefa> findByTipoTarefaIdAndStatusOrderById(Long tipoTarefaId, Long statusId);
	
}
