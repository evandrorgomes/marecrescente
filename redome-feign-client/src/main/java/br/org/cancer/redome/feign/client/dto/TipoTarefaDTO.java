package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

/**
 * Classe que representa o tipo de tarefa.
 * 
 * @author bruno.sousa
 */
@Getter
public class TipoTarefaDTO implements Serializable {

	private static final long serialVersionUID = 4557443107551121549L;

	private Long id;

	private String descricao;

	private Boolean automatica;

	private Long tempoExecucao;
	
	private Boolean somenteAgrupador;
	

	/**
	 * Construtor padrão.
	 */
	@Builder
	public TipoTarefaDTO() {}

	/**
	 * Construtor passsando o id.
	 * 
	 * @param id
	 */
	@Builder
	public TipoTarefaDTO(Long id) {
		this();
		this.id = id;
	}


}