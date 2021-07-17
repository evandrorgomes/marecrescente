package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o tipo de tarefa.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "TIPO_TAREFA")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class TipoTarefa implements Serializable {

	private static final long serialVersionUID = 4557443107551121549L;

	@Id
	@Column(name = "TITA_ID")
	private Long id;

	@Column(name = "TITA_TX_DESCRICAO")
	private String descricao;

	@Column(name = "TITA_IN_AUTOMATICA")
	private Boolean automatica;

	@Column(name = "TITA_NR_TEMPO_EXECUCAO")
	private Long tempoExecucao;
	
	public TipoTarefa(Long id) {
		this.id = id;
	}

}