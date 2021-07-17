package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.MatchPreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade MatchPreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IMatchPreliminarRepository extends IRepository<MatchPreliminar, Long>, IMatchPreliminarRepositoryCustom {
	String MATCH_PRELIMINAR_PROCEDURE = "proc_match_preliminar";
	String MATCH_PROCEDURE = "PROC_MATCH_PRELIMINAR";
	
	/**
	 * Chamada a procedure que gera os matchs fakes preliminares.
	 * 
	 * @param rmr ID do paciente cadastrado para consulta preliminar.
	 * @param idDoador ID do doador nacional.
	 */
	@Procedure(IMatchPreliminarRepository.MATCH_PRELIMINAR_PROCEDURE)
	void executarProcedureMatchPreliminar(@Param("rmr") Long rmr, @Param("idDoador") Long idDoador);

	/**
	 * Busca o total de matchs ativos por busca preliminar (paciente).
	 * 
	 * @param idBuscaPreliminar - id do paciente.
	 * @param tiposDoador - lista de Tipos de doador. 	 
	 * @param status - status do match
	 * 
	 * @return quantidade total de matchs para um paciente.
	 */
	@Query(value = "select count(1) "
			+ "from MatchPreliminar m join m.doador d join m.buscaPreliminar b "
			+ "where b.id = :idBuscaPreliminar and d.tipoDoador in (:tiposDoador)")
	Long obterQuantidadeMatchs(@Param("idBuscaPreliminar") Long idBuscaPreliminar, @Param("tiposDoador") List<Long> tiposDoador);
	
	/**
	 * Lista os matchs preliminares relacionados a busca e tipos de doador informados.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar associada.
	 * @param tiposDoador tipo do doador que deve exibida no resultado, de acordo com o tipo.
	 * @return lista de DTOS com informações de match medula ou cordão, de acordo com o tipo informado.
	 */
	/*@Query(value = "select matPrel "
			+ "from MatchPreliminar matPrel join matPrel.doador doad join matPrel.buscaPreliminar buscPrel "
			+ "where buscPrel.id = :idBuscaPreliminar "
			+ "and doad.tipoDoador in (:tiposDoador) ")
	List<MatchPreliminar> listarMatchsPreliminares(@Param("idBuscaPreliminar") Long idBuscaPreliminar, @Param("tiposDoador") List<Long> tiposDoador);*/

	/**
	 * Executa a procedure para criar um match entre as partes informadas (busca preliminar e doador).
	 * @param idBusca ID da busca preliminar.
	 * 
	 */
	@Procedure(procedureName = IMatchPreliminarRepository.MATCH_PROCEDURE)
	void executarProcedureMatch(@Param("pBUPR_ID") Long idBusca);
	
}
