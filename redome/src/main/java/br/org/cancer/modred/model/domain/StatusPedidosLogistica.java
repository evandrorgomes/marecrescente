package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Enum para status de pedido de workup.
 * 
 * @author Filipe Paes
 *
 */
public enum StatusPedidosLogistica {

	ABERTO(1L)
	, FECHADO(2L)
	, AGUARDANDO_AUTORIZACAO_PACIENTE(3L)
	, CANCELADO(4L);

	Long id;

	StatusPedidosLogistica(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
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
