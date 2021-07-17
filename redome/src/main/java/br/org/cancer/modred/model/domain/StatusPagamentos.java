package br.org.cancer.modred.model.domain;

/**
 * Enum para representar os possíveis estados de uma tarefa dentro de um determinado processo de negócio.
 * 
 * @author Thiago Moraes
 *
 */
public enum StatusPagamentos {
	ABERTA(1L),
	CANCELADA(2L),
	PAGO(3L),
	CREDITO(4L);

	private Long id;

	StatusPagamentos(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return StatusPagamento - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static StatusPagamentos valueOf(Long value) {

		StatusPagamentos[] values = StatusPagamentos.values();
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

		StatusPagamentos[] values = StatusPagamentos.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}


}
