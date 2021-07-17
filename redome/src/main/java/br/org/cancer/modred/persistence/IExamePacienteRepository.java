package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ExamePaciente;

/**
 * Camada de acesso a base dados de Exame de paciente.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IExamePacienteRepository extends IExameBaseRepository<ExamePaciente>{
    
	/** 
	 * Método para verificar se uma metodologia consta no exame original do paciente.
	 * @param exameId
	 * @param sigla
	 * @return caso a metodologia não seja encontrada, nada será retornado.
	 */
	ExamePaciente findByIdAndMetodologiasSigla(long exameId, String sigla);
	
    /**
     * Busca a lista de exames por paciente (RMR informado).
     * 
     * @param pacienteRmr
     * @return
     */
    List<ExamePaciente> findByPacienteRmrAndStatusExameNotOrderByDataExameDesc(Long pacienteRmr, Integer status);
    
    /**
     * Retorna o exame mais prioritário pela data.
     * @return exame
     */
    @Query("select e from ExamePaciente e where e.statusExame = :status order by e.dataCriacao,get_score(e.paciente.rmr) desc")
    Page<ExamePaciente> findFirstByStatusExameOrderByDataCriacaoDesc(@Param("status") int status, Pageable page );

	
	/**
	 * Lista todos os exames conferidos (somente IDs) de um determinado paciente.
	 * 
	 * @param rmr RMR do paciente a ser pesquisado.
	 * @return lista de exames do paciente informado.
	 */
	@Query("select new ExamePaciente(ex.id, ex.dataCriacao) from ExamePaciente ex "
		+  "where ex.paciente.rmr = :rmr and ex.statusExame = 1")
	List<ExamePaciente> listarExamesConferidos(@Param("rmr") Long rmr);
	
	
	
}