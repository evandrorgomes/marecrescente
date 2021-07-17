package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Pizão
 * 
 * Entidade que representa os valores que compõem o genótipo.
 * O genótipo busca preliminar representa o genótipo gerado a partir da consulta preliminar
 * criado a partir de uma busca preliminar que gerou match na base do Redome.
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_VAEP_ID", sequenceName = "SQ_VAEP_ID", allocationSize = 1)
@Table(name = "VALOR_GENOTIPO_EXPAND_PRELIMIN")
public class ValorGenotipoExpandidoPreliminar implements Serializable {
	private static final long serialVersionUID = 5234496152699408206L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VAEP_ID")
	@Column(name = "VAEP_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	private Locus locus; 
	
	@NotNull
	@Column(name = "VAEP_NR_VALOR")
	private Integer valorAlelo;
	
	@NotNull
	@Column(name = "VAEP_NR_POSICAO")
	private Integer posicaoAlelo;
	
	@ManyToOne
	@JoinColumn(name = "BUPR_ID")
	private BuscaPreliminar buscaPreliminar;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locus getLocus() {
		return locus;
	}

	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	public Integer getValorAlelo() {
		return valorAlelo;
	}

	public void setValorAlelo(Integer valorAlelo) {
		this.valorAlelo = valorAlelo;
	}

	public Integer getPosicaoAlelo() {
		return posicaoAlelo;
	}

	public void setPosicaoAlelo(Integer posicaoAlelo) {
		this.posicaoAlelo = posicaoAlelo;
	}

	public BuscaPreliminar getBuscaPreliminar() {
		return buscaPreliminar;
	}

	public void setBuscaPreliminar(BuscaPreliminar buscaPreliminar) {
		this.buscaPreliminar = buscaPreliminar;
	}

	
}
