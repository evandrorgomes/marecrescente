package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Solicitacao;

/**
 * Interface para acesso ao banco de dados envolvendo a classe Solicitacao.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface ISolicitacaoRepository extends IRepository<Solicitacao, Long> {
	
	/**
	 * Método para consultar solicitação por idDoador e por tipo.
	 * @param idDoador id do doador.
	 * @param tipoSolicitacao
	 * @return solicitação localizada.
	 */
	Solicitacao findByMatchDoadorIdAndTipoSolicitacaoId(Long idDoador, Long tipoSolicitacao);
	
	/**
	 * Retorna uma solicitação por idDoador e tipo com status em aberto.
	 * @param idDoador - id do doador.
	 * @param tipo - id do tipo de solicitação
	 * @return solicitação localizada por parametros.
	 */
	@Query("select s from Solicitacao s join s.match m join m.doador d where s.tipoSolicitacao.id = :tipo and d.id = :idDoador and s.status = 1")
	Solicitacao solicitacaoPorTipoEIdDoadorComStatusEmAberto(@Param("idDoador") Long idDoador, @Param("tipo") Long tipo);
	
	/**
	 * Localiza solicitações por idDoador.
	 * @param idDoador - id do doador.
	 * @return lista de solicitações localizadas.
	 */
	@Query("select s from Solicitacao s join s.match m join m.doador d where  d.id = :idDoador and s.status = :idStatus")
	List<Solicitacao> solicitacoesPorIdDoadorEStatus(@Param("idDoador") Long idDoador, @Param("idStatus") Integer idStatus);

	/**
	 * Localiza solicitação por idDoador.
	 * @param idDoador - id do doador.
	 * @return solicitação localizadas.
	 */
	@Query("select s from Solicitacao s join s.match m join m.doador d where  d.id = :idDoador and s.status = :idStatus")
	Solicitacao solicitacaoPorIdDoadorEStatus(@Param("idDoador") Long idDoador, @Param("idStatus") Integer idStatus);

	/**
	 * Localiza solicitação por idDoador e com status concluido e cancelado.
	 * @param idDoador - id do doador.
	 * @return solicitação localizadas.
	 */
	@Query("select s from Solicitacao s join s.match m join m.doador d where  d.id = :idDoador order by s.dataCriacao desc")
	List<Solicitacao> ultimaSolicitacaoCriadaPorIdDoador(@Param("idDoador") Long idDoador);
	
	/**
	 * Retorna uma listagem de solicitacoes de acordo com o id do match e com o status.
	 * @param matchId - id do match a ser localizado.
	 * @param status - id do status da solicitação.
	 * @return lista de solicitações.
	 */
	List<Solicitacao> findByMatchIdAndStatus(Long matchId, Integer status);
	
	/**
	 * Localiza solicitações em aberto por idDoador.
	 * @param idDoador - id do doador.
	 * @return lista de solicitações localizadas.
	 */
	@Query("select s from PedidoExame pe join pe.solicitacao s where pe.id = :pedidoExameId")
	Solicitacao obterSolicitacaoPorPedidoExame(@Param("pedidoExameId") Long pedidoExameId);
	
	/**
	 * Obtém o doador associado a solicitação.
	 * 
	 * @param idSolicitacao - id da solicitação.
	 * @return o doador associado a solicitação.
	 */
	@Query("select doad from Solicitacao s join s.match m join m.doador doad where s.id = :idSolicitacao")
	Doador obterDoador(@Param("idSolicitacao") Long idSolicitacao);
	
	/**
	 * Localiza solicitações em aberto por rmr.
	 * @param rmr - identificador do paciente.
	 * @return lista de solicitações localizadas.
	 */
	@Query("select s from Solicitacao s join s.tipoSolicitacao ts join s.busca b join b.paciente p where p.rmr = :rmr and s.status = 1 and ts.id = :idTipoSolicitacao")
	List<Solicitacao> solicitacoesEmAbertoPorPacienteETipoSolicitacao(@Param("rmr") Long rmr, @Param("idTipoSolicitacao") Long idTipoSolicitacao);

	/**
	 * Recupera o id da solicitacao em aberto pelo id do match.
	 * 
	 * @param idMatch
	 * @return
	 */
	@Query("select s.id "
			+ "from Solicitacao s join s.match m "
			+ "where m.id = :idMatch and s.status = 1 and m.status = true ")	
	Long obterIdSolicitacaoPorIdMatch(@Param("idMatch") Long idMatch);	

	/**
	 * Método para buscar quantidade de solicitações abertos ou atendidos considerando os que estão na posição de histórico. por rmr.
	 * 
	 * @param idBusca - id da busca.
	 * @param tiposDoador - lista de Tipos de doador. 	 
	 * @param status - status do match
	 * @return quantidade total de solicitações por doador.
	 */
	@Query("select count(s.id) "
			+ "from Solicitacao s join s.match m join m.doador d "
			+ "where m.busca.id = :idBusca and d.tipoDoador in ( :tipos ) "
			+ "and s.status != 3 ")
	Long obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(@Param("idBusca") Long idBusca, @Param("tipos") List<Long> tipos);
	
	
	/**
	 * Quantidade de solicitações em aberto dos matchs de uma busca especifica e por tipo de solicitacao.
	 * 
	 * @param idBusca - identificador da busca
	 * @param tiposSolicitcao - identificador dos tipos de solicitacao
	 * @return quantidade total de solicitações em aberto dos matchs de uma busca e por tipo de solicitação
	 */
	@Query("select count(s.id) from Solicitacao s join s.tipoSolicitacao ts join s.match m join m.busca b where b.id = :idBusca and s.status = 1 and ts.id in ( :tipos )")
	Long quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(@Param("idBusca") Long idbusca, @Param("tipos") List<Long> tiposSolicitaao);

	/**
	 * Lista as solicitações em aberto dos matchs de uma busca especifica e por tipo de solicitacao.
	 * 
	 * @param idBusca - identificador da busca
	 * @param tiposSolicitcao - identificador dos tipos de solicitacao
	 * @return lista de solicitações em aberto dos matchs de uma busca e por tipo de solicitação
	 */
	@Query("select s from Solicitacao s join s.tipoSolicitacao ts join s.match m join m.busca b where b.id = :idBusca and s.status = 1 and ts.id in ( :tipos )")
	List<Solicitacao> listarSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(@Param("idBusca")  Long idBusca, @Param("tipos")  List<Long> tipos);

	/**
	 * Lista as solicitações por centro de transplante por tipo de solicitacao e status.
	 * 
	 * @param idCentroTransplante - identificador do centro de transplante
	 * @param tiposSolicitcao - identificador dos tipos de solicitacao
	 * @param statusSolicitcao - identificador dos status da solicitacao
	 * @return lista de solicitações em aberto dos matchs de uma busca e por tipo de solicitação e status
	 */
	@Query("select s "
			+ "from Solicitacao s join s.tipoSolicitacao ts join s.match m join m.busca b join b.centroTransplante ct "
			+ "where ts.id in (:tipos) and s.status in (:status) and ct.id = :idCentroTransplante ")
	PageImpl<Solicitacao> listarSolicitacoesPeloCentroTransplanteETipoEStatusSolicitacao(@Param("idCentroTransplante")  Long idCentroTransplante, 
			@Param("tipos")  List<Long> tipos, @Param("status")  List<Integer> status, @Param("pageable") Pageable pageable);

	
	@Query("select s from Solicitacao s join s.tipoSolicitacao ts "
			+ "where ts.id in (:tipos) and s.status in (:status) ")
	List<Solicitacao> listarSolicitacoesPorTipoSolicitacaoEStatusSolicitacao(@Param("tipos") List<Long> tiposSolicitacao,
			@Param("status") List<Integer> statusSolicitacao);

	
	@Query("select s from Solicitacao s join s.tipoSolicitacao ts "
			+ "where ts.id in (:tipos) and s.status in (:status) and s.faseWorkup in (:fases) ")
	List<Solicitacao> listarSolicitacoesPorTipoSolicitacaoPorStatusSolicitacaoEFasesWorkup(@Param("tipos") List<Long> tiposSolicitacao,
			@Param("status") List<Integer> statusSolicitacao, @Param("fases") List<Long> fasesWorkup);
}
