package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.StatusDoador;

/**
 * Interface para acesso ao banco de dados envolvendo a classe Doador.
 * 
 * @author Pizão
 */
@Repository
public interface IDoadorRepository extends IRepository<Doador, Long> {
	
	/**
	 * Obtém o status do doador.
	 * 
	 * @param idDoador - identificador do doador
	 * @return status do doador
	 */
	@Query("select d.statusDoador from Doador d where d.id= :idDoador")
	StatusDoador obterStatusDoadorPorId(@Param(value = "idDoador") Long idDoador);
	
	
	/**
	 * Obtém a quantidade de processos ativos de um doador.
	 * 
	 * @param idDoador - identificador do doador
	 * @return status do doador
	 */
	@Query("select count(d) from Doador d "
			+ " join d.matchs m "
			+ " join m.busca b "
			+ " join b.status sb "
			+ " where d.id= :idDoador and m.status = true "
			+ " and sb.buscaAtiva = true")
	int obterQuantidadeProcessosAtivosDoador(@Param(value = "idDoador") Long idDoador);
	
	/**
	 * Obtem o doador ativo a partir do pedido de logística.
	 * 
	 * @param pedidoLogisticaId identificação do pedido logistica.
	 * @return id do doador ativo associado ao pedido informado.
	 */
	@Query("select d.id "
		+  "from Solicitacao s join s.match m join m.doador d "
		+  "where s.id = :id")
	Long obterDoadorPorSolicitacao(@Param("id") Long solicitacaoId);

	/**
	 * Obtem o doador ativo a partir da Solicitação.
	 * 
	 * @param solicitacaoId identificação da solicitacao.
	 * @return id do doador ativo associado a solicitacao informada.
	 */
	@Query("select d.dmr "
		+  "from Solicitacao s join s.match m join m.doador d "
		+  "where s.id = :id")
	Long obterDmrDoadorPorSolicitacao(@Param("id") Long solicitacaoId);

	/**
	 * Obtém toda identificação do doador para utilizar no cabeçalho.
	 * 
	 * @param idDoador ID do doador.
	 * @return doador preenchido com todas as informações.
	 */
	@Query("select d from Doador d where d.id = ?1")
	@QueryHints(@javax.persistence.QueryHint(name = "org.hibernate.fetchSize", value = "1"))
	Doador carregarDoadorSomenteComIdentificacao(Long idDoador);

}
