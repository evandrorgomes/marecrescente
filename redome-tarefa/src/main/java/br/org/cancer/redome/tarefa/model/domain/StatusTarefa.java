package br.org.cancer.redome.tarefa.model.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum para representar os possíveis estados de uma tarefa dentro de um determinado processo de negócio.
 * 
 * @author Thiago Moraes
 *
 */
public enum StatusTarefa {
	ABERTA(1L),
	ATRIBUIDA(2L),
	FEITA(3L),
	AGUARDANDO(4L),
	CANCELADA(5L);

	private Long id;

	StatusTarefa(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@JsonValue
	public Long getId() {
		return id;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return StatusTarefa - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static StatusTarefa valueOf(Long value) {

		StatusTarefa[] values = StatusTarefa.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}

	/**
	 * Método para verificar se o id está dentro do range previsto.
	 * 
	 * @return boolean - returna <b>true</b> se o id corresponde a um valor válido da enum, caso contrário, retorna <b>false</b>.
	 */
	public boolean validate() {

		StatusTarefa[] values = StatusTarefa.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Representação textual da tarefa que pode ser apresentada ao usuário final.
	 * 
	 * @return String - representação textual da tarefa.
	 */
	public String getDescriptionKey() {

		switch (this) {

			case ABERTA:
				return "dominio.status.tarefa.aberta";
			case ATRIBUIDA:
				return "dominio.status.tarefa.atribuida";
			case FEITA:
				return "dominio.status.tarefa.feita";

			default:
				return null;
		}
	}
}
