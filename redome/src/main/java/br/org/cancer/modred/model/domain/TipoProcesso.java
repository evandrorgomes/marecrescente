package br.org.cancer.modred.model.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para representar os tipos de processos dentro da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
public enum TipoProcesso implements EnumType<Long>{
	AVALIACAO_PACIENTE_PARA_BUSCA(10L),
	BUSCA(5L),
	CONFERENCIA_EXAMES(15L),
	TRANSFERENCIA_CENTRO(20L),
	AVALIACAO_CAMARA_TECNICA(25L),
	AVALIACAO_PACIENTE_PARA_NOVA_BUSCA(30L);

	private Long id;

	TipoProcesso(Long id) {
		this.id = id;
	}

	@JsonValue
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return TipoProcesso - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TipoProcesso valueOf(Long value) {

		TipoProcesso[] values = TipoProcesso.values();
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

		TipoProcesso[] values = TipoProcesso.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Representação textual do tipo de processo que pode ser apresentada ao usuário final.
	 * 
	 * @return String - representação textual da tarefa.
	 */
	public String getDescriptionKey() {

		switch (this) {

			case AVALIACAO_PACIENTE_PARA_BUSCA:
				return "dominio.tipo.processo.cadastramento_doador";
			case CONFERENCIA_EXAMES:
				return "dominio.tipo.processo.conferencia_exames";

			default:
				return null;
		}
	}

}
