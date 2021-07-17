package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para representar os possíveis estados de uma Avaliação .
 * 
 * @author Fillipe Queiroz
 *
 */
public enum StatusAvaliacao implements EnumType<Integer>{

	FECHADA(0),
	ABERTA(1),
	CANCELADA(2);

	private int id;

	StatusAvaliacao(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return StatusTarefa - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static StatusAvaliacao valueOf(int value) {

		StatusAvaliacao[] values = StatusAvaliacao.values();
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

		StatusAvaliacao[] values = StatusAvaliacao.values();
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
				return "dominio.status.avaliacao.aberta";
			case FECHADA:
				return "dominio.status.avaliacao.fechada";
			case CANCELADA:
				return "dominio.status.avaliacao.cancelada";

			default:
				return null;
		}
	}
}
