package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoContato;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoContato.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPedidoContatoRepository extends IRepository<PedidoContato, Long> {

	/**
	 * Realiza a busca do pedido contato, a partir do idDoador informado.
	 * 
	 * @param idDoador identificação do doador.
	 * @return o pedido contato relacionado.
	 */
	@Query("select p from PedidoContato p join p.solicitacao s join s.match.doador d "
		 + "where p.aberto is true and d.id = :idDoador")
	PedidoContato obterPedido(@Param("idDoador") Long idDoador);
	
	/**
	 * Realiza a busca do pedido contato, a partir do ID da solicitação
	 * informado.
	 * 
	 * @param solicitacaoId ID da solicitação.
	 * @return o pedido contato relacionado.
	 */
	@Query("select p from PedidoContato p where p.aberto is true and p.solicitacao.id = :solicitacaoId")
	PedidoContato obterPedidoPorSolicitacao(@Param("solicitacaoId") Long solicitacaoId);
	
	
	/**
	 * Método para listar pedidos por idDoador, rmr e se está aberto.
	 * @param idDoador - id do doador.
	 * @param rmr - id do paciente.
	 * @param aberto - flag se está aberto ou não.
	 * @return lista de pedidos de enriquecimento.
	 */
	@Query("select p from PedidoContato p join p.solicitacao s join s.tipoSolicitacao t "
			+ " join s.match m join m.doador d join m.busca b join b.paciente paci "
			+ " where t.id = :tiposolicitacao "
			+ " and d.id = :idDoador "
			+ " and (paci.rmr = :paciente or :paciente is null) "
			+ " and p.aberto = :aberto")
	List<PedidoContato> buscarPedidosPor(@Param("tiposolicitacao") Long tipoSolicitacao
			, @Param("idDoador") Long idDoador
			, @Param("paciente")Long rmr
			, @Param("aberto")boolean aberto);

	
	/**
	 * Realiza a busca do pedido contato, a partir do idDoador informado.
	 * 
	 * @param idDoador identificação do doador.
	 * @return o pedido contato relacionado.
	 */
	@Query("select p from PedidoContato p join p.doador d "
		 + "where p.aberto is true and d.id = :idDoador")
	PedidoContato obterPedidoEmAberto(@Param("idDoador") Long idDoador);

}
