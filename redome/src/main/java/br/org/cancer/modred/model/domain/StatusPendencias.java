package br.org.cancer.modred.model.domain;

/**
 * Enum para status pendencia.
 * 
 * @author Filipe Paes
 *
 */
public enum StatusPendencias {
	ABERTA(1L),
	RESPONDIDA(2L),
	CANCELADA(3L),
	FECHADA(4L);

	private Long id;

	StatusPendencias(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
