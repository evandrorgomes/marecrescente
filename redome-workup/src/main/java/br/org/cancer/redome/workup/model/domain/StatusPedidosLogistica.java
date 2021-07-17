package br.org.cancer.redome.workup.model.domain;

import br.org.cancer.redome.workup.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para status de pedido de workup.
 * 
 * @author Filipe Paes
 *
 */
@AllArgsConstructor
@Getter
public enum StatusPedidosLogistica {

	ABERTO(1L),
	FECHADO(2L),
	AGUARDANDO_AUTORIZACAO_PACIENTE(3L),
	CANCELADO(4L);

	Long id;

	
	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param codigo código a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static StatusPedidosLogistica get(Long codigo) {
		for (StatusPedidosLogistica status : values()) {
			if (status.id.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}
	
	
}
