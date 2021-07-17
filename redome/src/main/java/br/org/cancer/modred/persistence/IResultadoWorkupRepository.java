package br.org.cancer.modred.persistence;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ResultadoWorkup;

/**
 * Interface de persistencia para resposta de workup.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IResultadoWorkupRepository  extends IRepository<ResultadoWorkup, Long>{

	/**
	 * Método para obter paciente associado ao resultado.
	 * @param dmr - dmr do doador.
	 * @param idCentroColeta - id do centro de coleta do exame.
	 * @param aberto - se o exame já foi cadastrado ou não.
	 * @return paciente associado.
	 */
	@Query("select res from ResultadoWorkup res, PedidoWorkup p join p.solicitacao s join s.match m join m.doador d  "
			+ "where res.pedidoWorkup = p.id and d.dmr = :dmr and p.centroColeta.id in :centros and p.status = 6 and s.status = 1"
			+ "and ((:aberto = true) or (:aberto = false )) ")
	ResultadoWorkup obterDoadorComResultado(@Param("dmr") Long dmr, @Param("centros") ArrayList<Long> centros,
			@Param("aberto") Boolean aberto);
	
	/**
	 * Busca um resultado de workup por id de pedido de workup.
	 * @param idPedido - id do pedido de workup do resultado. 
	 * @return resultado de pedido de workrup.
	 */
	ResultadoWorkup findByPedidoWorkup(Long idPedido);
}
