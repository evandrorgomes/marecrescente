package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.vo.PedidoExameDoadorInternacionalVo;
import br.org.cancer.modred.vo.PedidoExameDoadorNacionalVo;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoExame.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IPedidoExameRepositoryCustom {

	List<PedidoExame> listarPedidosExameParaResolverDivergencia(@Param("idDoador") Long idDoador);
	
	List<PedidoExameDoadorNacionalVo> listarPedidosDeExameNacional(Long idDoador, Long idPaciente, Long idBusca,
			List<Integer> filtrarSolicitacaoPelosStatus, List<Long> filtrarSolicitacaoPelosTipos, List<Long> filtrarTarefaPelosTipos, Pageable pageable);
	
	List<PedidoExameDoadorInternacionalVo> listarPedidosDeExameInternacional(Long idBusca,
			List<Integer> filtrarSolicitacaoPelosStatus, List<Long> filtrarSolicitacaoPelosTipos, List<Long> filtrarTarefaPelosTipos, 
			Pageable pageable);
	
	
	/**
	 * Método que obtém o id do registro do doador internacional pelo pedido de exame.
	 * 
	 * @param idPedidoExame
	 * @return
	 */
	String obterIdRegistroDoadorInternacionalPorPedidoExameId(Long idPedidoExame);
	
	/**
	 * Método que obtém o id do registro do doador internacional pelo pedido de idm.
	 * 
	 * @param idPedidoIdm
	 * @return
	 */
	String obterIdRegistroDoadorInternacionalPorPedidoIdmId(Long idPedidoIdm);

}
