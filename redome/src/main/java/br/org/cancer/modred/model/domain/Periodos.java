package br.org.cancer.modred.model.domain;

/**
 * Enum para definição de turnos para fila de trabalho.
 * @author Filipe Paes
 *
 */
public enum Periodos {
	
	QUALQUER(0L),
	MANHA(1L),
	TARDE(2L),
	NOITE(3L),
	NAO_MANHA(4L),
	NAO_TARDE(5L),
	NAO_NOITE(6L);
	
	Long id;
	
	Periodos(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
