package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;


/**
 * Enum para status de um pedido de exame, criado durante o contato com o doador.
 * 
 * @author Pizão
 *
 */
public enum StatusPedidosIdm {
	AGUARDANDO_RESULTADO(0L),
	RESULTADO_CADASTRADO(1L),
	CANCELADO(2L);
	
	private Long id;

	StatusPedidosIdm(Long id) {
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
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static StatusPedidosIdm get(Long id) {
		for (StatusPedidosIdm status : values()) {
			if (status.equals(id)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

	private boolean equals(Long id) {
		return this.id.equals(id);
	}
}
