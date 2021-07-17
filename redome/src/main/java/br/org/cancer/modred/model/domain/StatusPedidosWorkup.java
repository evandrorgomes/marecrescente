package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Enum para status de pedido de workup.
 * 
 * @author Filipe Paes
 *
 */
public enum StatusPedidosWorkup {

	INICIAL(1L)
	, EM_ANALISE(2L)
	, AGUARDANDO_CT(3L)
	, CANCELADO(4L)
	, REALIZADO(5L)
	, AGENDADO(6L)
	, CONFIRMADO_CT(7L);

	Long id;

	StatusPedidosWorkup(Long id) {
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
	public static StatusPedidosWorkup get(Long codigo) {
		for (StatusPedidosWorkup status : values()) {
			if (status.id.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}
	
	
}
