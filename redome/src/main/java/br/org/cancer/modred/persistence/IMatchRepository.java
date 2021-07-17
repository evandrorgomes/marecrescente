package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Match;

/**
 * Interface para de métodos de banco para match.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IMatchRepository extends IRepository<Match, Long>, IMatchRepositoryCustom {

	/**
	 * Método para buscar match por rmr e idDoador.
	 * 
	 * @param rmr - id do paciente.
	 * @param idDoador - id do doador.
	 * @return objeto de match entre paciente e doador.
	 */
	Match findByBuscaPacienteRmrAndDoadorId(Long rmr, Long idDoador);
	
	/**
	 * Retorna o match associado ao pedido workup ID fornecido.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 * @return match associado ao pedido de workup.
	 */
	@Query("select new Match(mat.id) from PedidoWorkup pw join pw.solicitacao sol join sol.match mat where pw.id = :id")
	Match obterMatchPorPedidoWorkup(@Param("id") Long pedidoWorkupId);
	
	/**
	 * Retorna o match associado ao pedido contato ID fornecido.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return match associado ao pedido de contato.
	 */
	@Query("select new Match(mat.id) from PedidoContato pc join pc.solicitacao sol join sol.match mat where pc.id = :id")
	Match obterMatchPorPedidoContato(@Param("id") Long pedidoContatoId);
	
	
	
	/**
	 * Retorna o match associado ao pedido IDM ID fornecido.
	 * 
	 * @param pedidoIdmId ID do pedido de contato.
	 * @return match associado ao pedido de contato.
	 */
	@Query("select mat from PedidoIdm pi join pi.solicitacao sol join sol.match mat where pi.id = :id")
	Match obterMatchPorPedidoIdm(@Param("id") Long pedidoIdmId);
	
	/**
	 * Retorna o match associado ao pedido exame ID fornecido.
	 * 
	 * @param pedidoExameId ID do pedido de exame associado.
	 * @return busca associada.
	 */
	@Query("select mat from PedidoExame pe join pe.solicitacao sol join sol.match mat where pe.id = :id")
	Match obterMatchPorPedidoExame(@Param("id") Long pedidoExameId);
	
	/**
	 * Executa procedure de criação do match associado ao doador.
	 * 
	 * @param idDoador ID do doador associado.
	 */
	@Procedure(procedureName = "proc_match_doador")
	void executarProcedureMatchDoador(@Param("pDOAD_ID") Long idDoador);

	/**
	 * Executa procedure de criação do match associado ao paciente.
	 * 
	 * @param rmr identificação do paciente associado.
	 */
	@Procedure(procedureName = "proc_match_paciente")
	void executarProcedureMatchPaciente(@Param("pPACI_NR_RMR") Long rmr);
	
	/**
	 * Obtém o match ativo entre paciente e doador.
	 * 
	 * @param rmr identificação do paciente.
	 * @param idDoador identificação do doador.
	 * @return match ativo.
	 */
	@Query("select mat "
		+ "from Match mat join mat.doador doad join mat.busca bus join bus.paciente pac "
		+ "where doad.id = :idDoador and pac.rmr = :rmr and mat.status = true")
	Match obterMatchAtivo(@Param("rmr") Long rmr, @Param("idDoador") Long idDoador);
	
	/**
	 * Lista todos os matchs ativos para o doador.
	 * 
	 * @param rmr identificação do doador.
	 * @param idDoador ID do doador.
	 * @return lista de matchs ativos.
	 */
	@Query("select mat "
		+ "from Match mat join mat.doador doad "
		+ "where doad.id = :idDoador and mat.status = true")
	List<Match> listarMatchsAtivos(@Param("idDoador") Long idDoador);
	
	
	/**
	 * Método para buscar quantidade de matchs ativos por rmr.
	 * 
	 * @param rmr - id do paciente.
	 * @param tiposDoador - lista de Tipos de doador. 	 
	 * @param status - status do match
	 * @return quantidade total de matchs para um paciente.
	 */
	Long countByBuscaPacienteRmrAndDoadorTipoDoadorIsInAndStatus(Long rmr, List<Long> tiposDoador, boolean status);

	
	/**
	 * Método para buscar quantidade de matchs ativos por id da busca.
	 * 
	 * @param buscaId - id da busca.
	 * @param tiposDoador - lista de Tipos de doador. 	 
	 * @param status - status do match
	 * @return quantidade total de matchs para um paciente.
	 */
	Integer countByBuscaIdAndDoadorTipoDoadorIsInAndStatus(Long buscaId, List<Long> tiposDoador, boolean status);

	
	/**
	 * Lista os matchs ativos e disponibilizados para uma determinada busca. 
	 * 
	 * @param idBusca identificador da busca
	 * @return lista de matchs ativos e disponibilizados se existir. 
	 */
	@Query("select mat from Match mat join mat.busca busc "
			+ "where mat.disponibilizado = true and mat.status = true and busc.id = :idBusca")
	List<Match> listarMatchsDisponibilizadosPorIdBusca(@Param("idBusca") Long idBusca);
	
	
}
