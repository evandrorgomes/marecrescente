package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe utilizada para acriação de uma nova evolução do doaador.
 * 
 * @author brunosousa
 *
 */
public class EvolucaoDoadorVo implements Serializable {
	
	private static final long serialVersionUID = 2770201712649053803L;
	
	private Long idDoador;
	private String descricao;
	
	/**
	 * @return the idDoador
	 */
	public Long getIdDoador() {
		return idDoador;
	}
	/**
	 * @param idDoador the idDoador to set
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
