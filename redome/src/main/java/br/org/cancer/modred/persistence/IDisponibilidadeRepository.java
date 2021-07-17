package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Disponibilidade;

/**
 * Interface para percistencia de Disponibilidade.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IDisponibilidadeRepository extends IRepository<Disponibilidade, Long> {
	
	Disponibilidade findFirstByPedidoWorkupIdOrderByDataAceiteDesc(Long pedidoWorkupId);

	Disponibilidade findFirstByPedidoColetaIdOrderByDataAceiteDesc(Long idPedidoColeta);

}
