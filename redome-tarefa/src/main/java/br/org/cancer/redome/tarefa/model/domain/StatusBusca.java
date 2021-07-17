package br.org.cancer.redome.tarefa.model.domain;

/**
 * Enum para status de busca.
 * @author Filipe Paes
 *
 */
public enum StatusBusca {
	AGUARDANDO_LIBERACAO(1L),
	LIBERADA(2L),
	EM_AVALIACAO(3L),
	CANCELADA(4L);
	private Long id;
	
	private StatusBusca(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}


