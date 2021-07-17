package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Enum para status de pedido de coleta.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum StatusPedidosTransporte {

	EM_ANALISE(1L),
	AGUARDANDO_DOCUMENTACAO(2L),
	AGUARDANDO_CONFIRMACAO(3L),
	AGUARDANDO_RETIRADA(4L),
	AGUARDANDO_ENTREGA(5L),
	ENTREGUE(6L),
	CANCELADO(7l);

	Long id;

	StatusPedidosTransporte(Long id) {
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
	public static StatusPedidosTransporte get(Long codigo) {
		for (StatusPedidosTransporte status : values()) {
			if (status.id.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

}
