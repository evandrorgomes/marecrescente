package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Classe que representa a escolha do do analista de busca sobre o genotipo divergente.
 * 
 * @author brunosousa
 *
 */
public class ResultadoDivergenciaDTO implements Serializable {

	private static final long serialVersionUID = -1014600036891116560L;
	
	private Boolean divergencia;
	private List<Long> idDemaisExames;
	private List<Long> idExamesSelecionados;
	private String locus;
	private Long idBusca;
	
	/**
	 * @return the divergencia
	 */
	public Boolean getDivergencia() {
		return divergencia;
	}
	/**
	 * @param divergencia the divergencia to set
	 */
	public void setDivergencia(Boolean divergencia) {
		this.divergencia = divergencia;
	}
	/**
	 * @return the idDemaisExames
	 */
	public List<Long> getIdDemaisExames() {
		return idDemaisExames;
	}
	/**
	 * @param idDemaisExames the idDemaisExames to set
	 */
	public void setIdDemaisExames(List<Long> idDemaisExames) {
		this.idDemaisExames = idDemaisExames;
	}
	/**
	 * @return the idExamesSelecionados
	 */
	public List<Long> getIdExamesSelecionados() {
		return idExamesSelecionados;
	}
	/**
	 * @param idExamesSelecionados the idExamesSelecionados to set
	 */
	public void setIdExamesSelecionados(List<Long> idExamesSelecionados) {
		this.idExamesSelecionados = idExamesSelecionados;
	}
	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}
	/**
	 * @param locus the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}
	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}
	/**
	 * @param idBusca the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
	}
	
}
