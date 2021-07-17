package br.org.cancer.modred.redomelib.vo;

import java.io.Serializable;

/**
 * Classe que representa os valores do nmdp.
 * 
 * @author bruno.sousa
 *
 */
public class ValorNmdpVo implements Serializable {
    
	private static final long serialVersionUID = 2455600086863375044L;

    private String codigo;
    private String subtipo;
    private Boolean agrupado;
    private Long quantidadeSubTipo;
    private Boolean splitCriado;
    
	public ValorNmdpVo(String codigo, String subtipo, Boolean agrupado, Long quantidadeSubTipo, Boolean splitCriado) {
		super();
		this.codigo = codigo;
		this.subtipo = subtipo;
		this.agrupado = agrupado;
		this.quantidadeSubTipo = quantidadeSubTipo;
		this.splitCriado = splitCriado;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return the subtipo
	 */
	public String getSubtipo() {
		return subtipo;
	}

	/**
	 * @return the agrupado
	 */
	public Boolean getAgrupado() {
		return agrupado;
	}

	/**
	 * @return the quantidadeSubTipo
	 */
	public Long getQuantidadeSubTipo() {
		return quantidadeSubTipo;
	}

	/**
	 * @return the splitCriado
	 */
	public Boolean getSplitCriado() {
		return splitCriado;
	}

}
