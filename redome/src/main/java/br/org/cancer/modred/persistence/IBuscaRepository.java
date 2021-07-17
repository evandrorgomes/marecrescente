package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Busca;

/**
 * Interface com métodos de persistencia de Busca.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IBuscaRepository extends IRepository<Busca, Long>, IBuscaRepositoryCustom {

	/**
	 * Método para obter busca por RMR.
	 * 
	 * @param rmr - id do paciente
	 * @param statusBusca - status da busca
	 * @return
	 */
	List<Busca> findByPacienteRmrAndStatusIdIn(Long rmr, List<Long> statusBusca);

	/**
	 * Método que retorna a Busca atual ativa do paciente.
	 * 
	 * @param rmr do paciente que será pesquisado.
	 * @param buscaAtiva se o status da busca do paciente está ativa (TRUE) ou inativa (0).
	 * @return busca relativa ao paciente.
	 */
	Busca findByPacienteRmrAndStatusBuscaAtiva(Long rmr, Boolean buscaAtiva);
	
	
	/**
	 * Método que retorna a Busca atual do paciente.
	 * 
	 * @param rmr do paciente que será pesquisado.
	 * @return busca relativa ao paciente.
	 */
	Busca findByPacienteRmr(Long rmr);
	
	/**
	 * Retorna a busca associada ao match ID fornecido.
	 * 
	 * @param matchId ID do match.
	 * @return busca associada ao match.
	 */
	@Query("select new Busca(b.id) from Match m join m.busca b where m.id = :id")
	Busca obterBuscaPorMatch(@Param("id") Long matchId);
	
	/**
	 * Retorna a busca associada ao pedido workup ID fornecido.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 * @return busca associada ao pedido de workup.
	 */
	@Query("select new Busca(b.id) from PedidoWorkup pw join pw.solicitacao sol join sol.match mat join mat.busca b where pw.id = :id")
	Busca obterBuscaPorPedidoWorkup(@Param("id") Long pedidoWorkupId);
	
	/**
	 * Retorna a busca associada ao pedido contato ID fornecido.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return busca associada ao pedido de contato.
	 */
	@Query("select new Busca(b.id) from PedidoContato pc join pc.solicitacao sol join sol.match mat join mat.busca b where pc.id = :id")
	Busca obterBuscaPorPedidoContato(@Param("id") Long pedidoContatoId);
	
	/**
	 * Retorna a busca associada ao pedido exame ID fornecido.
	 * 
	 * @param pedidoExameId ID do pedido de exame.
	 * @return busca associada ao pedido de exame.
	 */
	@Query("select new Busca(b.id) from PedidoExame pe join pe.solicitacao sol join sol.match mat join mat.busca b where pe.id = :id")
	Busca obterBuscaPorPedidoExame(@Param("id") Long pedidoExameId);
	
	/**
	 * Obtém o ID do centro de transplante associado a busca do paciente.
	 * 
	 * @param id identificador da busca
	 * @return ID do centro de transplante associado.
	 */
	@Query(value="select CETR_ID_CENTRO_TRANSPLANTE from Busca b where b.busc_id = :id", nativeQuery=true )
	Long obterCentroTransplanteIdPorBuscaId(@Param("id") Long id);
}
