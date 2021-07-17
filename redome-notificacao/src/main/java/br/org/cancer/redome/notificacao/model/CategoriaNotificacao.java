package br.org.cancer.redome.notificacao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

/**
 * Classe de persistencia de categoria de notificacao.
 * 
 * @author ergomes
 * 
 */
@Entity
@Table(name = "CATEGORIA_NOTIFICACAO")
@Data
@Builder
public class CategoriaNotificacao implements Serializable {

	private static final long serialVersionUID = 2558791654833559745L;

	@Id
	@Column(name = "CANO_ID")
	private Long id;

	@Column(name = "CANO_TX_DESCRICAO")
	private String descricao;
	

	/**
	 * Construtor default.
	 * @param id
	 */
	public CategoriaNotificacao() {
		super();
	}

	/**
	 * @param id
	 * @param descricao
	 */
	public CategoriaNotificacao(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @param id
	 */
	public CategoriaNotificacao(Long id) {
		super();
		this.id = id;
	}
	

}