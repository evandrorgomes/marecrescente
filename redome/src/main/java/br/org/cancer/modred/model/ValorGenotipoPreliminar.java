package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
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
@Table(name = "VALOR_GENOTIPO_PRELIMINAR")
public class ValorGenotipoPreliminar implements Serializable {
	
	private static final long serialVersionUID = -5862667216741618262L;

	@EmbeddedId
	@Valid
	@NotNull
	private ValorGenotipoPreliminarPK id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCU_ID", insertable = false, updatable = false)
	private Locus locus;
	
	@NotNull
	@Column(name = "VGPR_TX_ALELO")
	private String valorAlelo;
	
	@NotNull
	@Column(name = "VGPR_NR_POSICAO_ALELO", insertable = false, updatable = false)
	private Integer posicaoAlelo;
	
	@ManyToOne
	@JoinColumn(name = "BUPR_ID")
	private BuscaPreliminar buscaPreliminar;
	
	@EnumValues(ComposicaoAlelo.class)
	@Column(name = "VGPR_NR_TIPO")
	private Integer tipo;

	/**
	 * @return the id
	 */
	public ValorGenotipoPreliminarPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ValorGenotipoPreliminarPK id) {
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

	/**
	 * @return the valorAlelo
	 */
	public String getValorAlelo() {
		return valorAlelo;
	}

	/**
	 * @param valorAlelo the valorAlelo to set
	 */
	public void setValorAlelo(String valorAlelo) {
		this.valorAlelo = valorAlelo;
	}

	/**
	 * @return the posicaoAlelo
	 */
	public Integer getPosicaoAlelo() {
		return posicaoAlelo;
	}

	/**
	 * @param posicaoAlelo the posicaoAlelo to set
	 */
	public void setPosicaoAlelo(Integer posicaoAlelo) {
		this.posicaoAlelo = posicaoAlelo;
	}

	/**
	 * @return the buscaPreliminar
	 */
	public BuscaPreliminar getBuscaPreliminar() {
		return buscaPreliminar;
	}

	/**
	 * @param buscaPreliminar the buscaPreliminar to set
	 */
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
