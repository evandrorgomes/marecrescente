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

import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.model.annotations.EnumValues;

/**
 * @author Pizão
 * 
 * Entidade que representa os valores que compõem o genótipo.
 * O genótipo busca preliminar representa o genótipo gerado a partir da consulta preliminar
 * criado a partir de uma busca preliminar que gerou match na base do Redome.
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_VABP_ID", sequenceName = "SQ_VABP_ID", allocationSize = 1)
@Table(name = "VALOR_GENOTIPO_BUSCA_PRELIMINA")
public class ValorGenotipoBuscaPreliminar implements Serializable {
	private static final long serialVersionUID = -5862667216741618262L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VABP_ID")
	@Column(name = "VABP_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="LOCU_ID")
	private Locus locus;
	
	@NotNull
	@Column(name = "VABP_TX_VALOR")
	private String valorAlelo;
	
	@NotNull
	@Column(name = "VABP_NR_POSICAO")
	private Integer posicaoAlelo;
	
	@ManyToOne
	@JoinColumn(name = "BUPR_ID")
	private BuscaPreliminar buscaPreliminar;

	@EnumValues(ComposicaoAlelo.class)
	@Column(name = "VABP_NR_TIPO")
	private Integer tipo;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the locus
	 */
	public Locus getLocus() {
		return locus;
	}

	/**
	 * @param locus the locus to set
	 */
	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	public String getValorAlelo() {
		return valorAlelo;
	}

	public void setValorAlelo(String valorAlelo) {
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

	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
