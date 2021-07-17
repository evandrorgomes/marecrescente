package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;


/**
 * Enum para status de um pedido de exame, criado durante o contato com o doador.
 * 
 * @author Pizão
 *
 */
public enum StatusPedidosExame {
	AGUARDANDO_AMOSTRA(0L, 10L),
	AMOSTRA_RECEBIDA(1L, null),
	RESULTADO_CADASTRADO(2L, 1L),
	CANCELADO(3L, 2L),
	NAO_ATENDIDA_SEM_HLA_IDM(4L, 0L),
	NAO_ATENDIDA_COM_HLA_IDM(5L, 3L),
	ENCAMINHADA(6L, 5L),
	ENCAMINHADA_RECUSADA(7L, 6L),
	ENCAMINHADA_ATENDIDA(8L, 7L),
	AGUARDANDO_ACEITE(9L, 11L),
	ATENDIDA_PARCIALMENTE(10L, 13L),
	ENCAMINHADA_ATENDIDA_PARCIALMENTE(11L, 14L),
	ENCAMINHADA_DEVOLVIDA(12L, 15L),
	AGUARDANDO_REDOME(13L, 16L);	
	
	private Long id;
	private Long idRedomeWeb;

	StatusPedidosExame(Long id, Long idRedomeWeb) {
		this.id = id;
		this.idRedomeWeb = idRedomeWeb;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the idRedomeWeb
	 */
	public Long getIdRedomeWeb() {
		return idRedomeWeb;
	}

	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static StatusPedidosExame get(Long id) {
		for (StatusPedidosExame status : values()) {
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
