package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe VO utiliz\ada na criação do match preliminar.
 * 
 * @author brunosousa
 *
 */
public class GenotipoDoadorVO implements Serializable {
		
	private static final long serialVersionUID = -5012630033270611072L;
	
	private String codigoLocus;
	
	private Long tipo;
	
	private Long posicao;
	
	private String qualificacao;
	
	private String genotipo;
	
	/**
	 * Construtor sobrecarregado com as propriedades.
	 * 
	 * @param codigoLocus - codigo do  locus
	 * @param tipo - tipo
	 * @param posicao - posição do alelo
	 * @param qualificacao - Qualificação 
	 * @param genotipo - valor do genótipo
	 */
	public GenotipoDoadorVO(String codigoLocus, Long tipo, Long posicao, String qualificacao, String genotipo) {
		super();
		this.codigoLocus = codigoLocus;
		this.tipo = tipo;
		this.posicao = posicao;
		this.qualificacao = qualificacao;
		this.genotipo = genotipo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the codigoLocus
	 */
	public String getCodigoLocus() {
		return codigoLocus;
	}

	/**
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * @return the posicao
	 */
	public Long getPosicao() {
		return posicao;
	}

	/**
	 * @return the qualificacao
	 */
	public String getQualificacao() {
		return qualificacao;
	}

	/**
	 * @return the genotipo
	 */
	public String getGenotipo() {
		return genotipo;
	}

	
	

}
