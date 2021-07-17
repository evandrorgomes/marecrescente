package br.org.cancer.modred.model.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum para representar os possíveis estados de um processo de busca de doador compatível.
 * 
 * @author Thiago Moraes
 *
 */
public enum StatusProcesso {
	ANDAMENTO(1L),
	PARADO(2L),
	TERMINADO(3L),
	CANCELADO(4L);

	private Long id;

	StatusProcesso(Long id) {
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
	 * @return StatusProcesso - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static StatusProcesso valueOf(Long value) {

		StatusProcesso[] values = StatusProcesso.values();
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

		StatusProcesso[] values = StatusProcesso.values();
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

			case ANDAMENTO:
				return "dominio.status.processo.andamento";
			case PARADO:
				return "dominio.status.processo.parado";
			case TERMINADO:
				return "dominio.status.processo.terminado";

			default:
				return null;
		}
	}

}
