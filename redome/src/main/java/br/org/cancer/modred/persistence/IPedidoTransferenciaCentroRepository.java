package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.model.domain.TiposTransferenciaCentro;

/**
 * Interface para percistencia de Pedido de Transferencia de Centro.
 * 
 * @author brunosousa
 *
 */
@Repository
public interface IPedidoTransferenciaCentroRepository extends IRepository<PedidoTransferenciaCentro, Long> {

	/**
	 * Método para obter o pedido de transferência de centro.
	 * 
	 * @param rmr - identificador do paciente.
	 * @param tipoTransferenciaCentro - Tipo de Transferência.
	 * @return PedidoTransferenciaCentro se existir.
	 */
	PedidoTransferenciaCentro findByPacienteRmrAndAprovadoIsNullAndTipoTransferenciaCentro(Long rmr,
			TiposTransferenciaCentro tipoTransferenciaCentro);
	
	/**
	 * Obtem o pedido de transferência de centro avaliador que esteja em
	 * aberto para o paciente informado.
	 * 
	 * @param rmr identificador do paciente.
	 * @return pedido de transferência associado ao paciente.
	 */
	@Query("select ped from PedidoTransferenciaCentro ped join ped.paciente pac "
		+  "where ped.aprovado is null and pac.rmr = :rmr")
	PedidoTransferenciaCentro obterPedidoAberto(@Param("rmr") Long rmr);
	
	/**
	 * Obtém o último pedido de transferencia de centro para o paciente informado.
	 * 
	 * @param rmr - identificador do paciente
	 * @param tipoTransferenciaCentro - Tipo de Transferencia
	 * @return pedido de transferência associado ao paciente.
	 */
	PedidoTransferenciaCentro findFirstByPacienteRmrAndTipoTransferenciaCentroOrderByIdDesc(Long rmr, TiposTransferenciaCentro tipoTransferenciaCentro);
	
}
