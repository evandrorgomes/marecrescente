package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Classe que representa a tabela de Banco de Sangue de cordão.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "BANCO_SANGUE_CORDAO")
@SequenceGenerator(name = "SQ_BASC_ID", sequenceName = "SQ_BASC_ID", allocationSize = 1)
@Data
public class BancoSangueCordao implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -244771304842110462L;

	@Id
	@Column(name = "BASC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BASC_ID")
	private Long id;

	@Column(name = "BASC_TX_SIGLA")
	private String sigla;

	@Column(name = "BASC_TX_NOME")
	@NotNull
	private String nome;
		
	@Column(name = "BASC_NR_ID_BRASILCORD")
	@NotNull
	private Long idBancoSangueCordao;
	
	public BancoSangueCordao() {
	}
	
	public BancoSangueCordao(Long id) {
		this.id = id;
	}
	
	public BancoSangueCordao(Long id, String nome) {
		this(id);
		this.nome = nome;
	}

	
}