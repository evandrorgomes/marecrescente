package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoEnriquecimento;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoEnriquecimento.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IPedidoEnriquecimentoRepository extends IRepository<PedidoEnriquecimento, Long> {
	
	/**
	 * Método para listar pedidos por idDoador, rmr e se está aberto.
	 * @param idDoador - id do doador.
	 * @param rmr - id do paciente.
	 * @param aberto - flag se está aberto ou não.
	 * @return lista de pedidos de enriquecimento.
	 */
	@Query("select p from PedidoEnriquecimento p join p.solicitacao s join s.match m join m.doador d "
			+ " join m.busca b join b.paciente paci where d.id = :doador "
			+ " and (paci.rmr = :paciente or :paciente is null) and p.aberto = :aberto")
	List<PedidoEnriquecimento> buscarPor(@Param("doador") Long idDoador
			, @Param("paciente")Long rmr
			, @Param("aberto")boolean aberto);

	/**
	 * 
	 * @param idDoador
	 * @param aberto
	 * @return
	 */
	@Query("select p from PedidoEnriquecimento p join p.solicitacao s join s.match m join m.doador d "
			+ " join m.busca b where d.id = :doador and p.aberto = :aberto")
	PedidoEnriquecimento obterPedidoDeEnriqueciomentoPorIdDoadorEStatus(
			  @Param("doador") Long idDoador, @Param("aberto")boolean aberto);
}
