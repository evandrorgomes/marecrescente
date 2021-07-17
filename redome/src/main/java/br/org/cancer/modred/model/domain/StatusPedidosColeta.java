package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Enum para status de pedido de coleta.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum StatusPedidosColeta {

	INICIAL(1L),
	AGENDADO(2L),
	REALIZADO(3L),
	CANCELADO(4L),
	AGUARDANDO_CT(5L),
	EM_ANALISE(6L),
	CONFIRMADO_CT(7L);

	Long id;

	StatusPedidosColeta(Long id) {
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
	public static StatusPedidosColeta get(Long codigo) {
		for (StatusPedidosColeta status : values()) {
			if (status.id.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

}
