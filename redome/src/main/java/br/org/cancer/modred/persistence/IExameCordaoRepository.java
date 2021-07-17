package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ExameCordaoInternacional;

/**
 * Camada de acesso a base dados de Exame de cordao internacional.
 * 
 * @author fillipe.queiroz
 *
 */
@Repository
public interface IExameCordaoRepository extends IExameDoadorBaseRepository<ExameCordaoInternacional>{
    
	/**
	 * Lista todos os exames conferidos (somente IDs) de um determinado paciente.
	 * 
	 * @param rmr RMR do paciente a ser pesquisado.
	 * @return lista de exames do paciente informado.
	 */
	@Query("select new ExameCordaoInternacional(ex.id, ex.dataCriacao) from ExameCordaoInternacional ex "
		+  "where ex.doador.id= :idDoador and ex.statusExame = 1")
	List<ExameCordaoInternacional> listarExamesDoadorInternacional(@Param("idDoador") Long idDoador);
	
}