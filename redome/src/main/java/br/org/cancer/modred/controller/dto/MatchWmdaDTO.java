package br.org.cancer.modred.controller.dto;

import java.util.List;

/**
 * Classe de DTO para informações do match.
 * 
 * @author ergomes
 */
public class MatchWmdaDTO {

	private Long id;
	private Long idBusca;
	private Long idDoador;
	private Long rmr;
	private String matchGrade; 
	private Integer ordenacaoWmdaMatch;
	private String probabilidade0;
	private String probabilidade1;
	private String probabilidade2;
	private boolean atualizarMatch;
	private Long tipoDoador;
	
	private List<QualificacaoMatchDTO> qualificacoes;
	
	/**
	 * 
	 */
	public MatchWmdaDTO() {}
	
	/**
	 * @param id
	 * @param idBusca
	 * @param idDoador
	 * @param rmr
	 * @param matchGrade
	 * @param ordenacaoWmdaMatch
	 * @param probabilidade0
	 * @param probabilidade1
	 * @param probabilidade2
	 * @param atualizarMatch
	 * @param qualificacoes
	 */
	public MatchWmdaDTO(Long id, Long idBusca, Long idDoador, Long rmr, String matchGrade, Integer ordenacaoWmdaMatch,
			String probabilidade0, String probabilidade1, String probabilidade2, boolean atualizarMatch,
			List<QualificacaoMatchDTO> qualificacoes, Long tipoDoador) {
		this.id = id;
		this.idBusca = idBusca;
		this.idDoador = idDoador;
		this.rmr = rmr;
		this.matchGrade = matchGrade;
		this.ordenacaoWmdaMatch = ordenacaoWmdaMatch;
		this.probabilidade0 = probabilidade0;
		this.probabilidade1 = probabilidade1;
		this.probabilidade2 = probabilidade2;
		this.atualizarMatch = atualizarMatch;
		this.qualificacoes = qualificacoes;
		this.tipoDoador = tipoDoador;
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
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}
	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	/**
	 * @return the matchGrade
	 */
	public String getMatchGrade() {
		return matchGrade;
	}
	/**
	 * @param matchGrade the matchGrade to set
	 */
	public void setMatchGrade(String matchGrade) {
		this.matchGrade = matchGrade;
	}
	
	/**
	 * @return the ordenacaoWmdaMatch
	 */
	public Integer getOrdenacaoWmdaMatch() {
		return ordenacaoWmdaMatch;
	}
	/**
	 * @param ordenacaoWmdaMatch the ordenacaoWmdaMatch to set
	 */
	public void setOrdenacaoWmdaMatch(Integer ordenacaoWmdaMatch) {
		this.ordenacaoWmdaMatch = ordenacaoWmdaMatch;
	}
	/**
	 * @return the probabilidade0
	 */
	public String getProbabilidade0() {
		return probabilidade0;
	}
	/**
	 * @param probabilidade0 the probabilidade0 to set
	 */
	public void setProbabilidade0(String probabilidade0) {
		this.probabilidade0 = probabilidade0;
	}
	/**
	 * @return the probabilidade1
	 */
	public String getProbabilidade1() {
		return probabilidade1;
	}
	/**
	 * @param probabilidade1 the probabilidade1 to set
	 */
	public void setProbabilidade1(String probabilidade1) {
		this.probabilidade1 = probabilidade1;
	}
	/**
	 * @return the probabilidade2
	 */
	public String getProbabilidade2() {
		return probabilidade2;
	}
	/**
	 * @param probabilidade2 the probabilidade2 to set
	 */
	public void setProbabilidade2(String probabilidade2) {
		this.probabilidade2 = probabilidade2;
	}
	/**
	 * @return the qualificacoes
	 */
	public List<QualificacaoMatchDTO> getQualificacoes() {
		return qualificacoes;
	}
	/**
	 * @param qualificacoes the qualificacoes to set
	 */
	public void setQualificacoes(List<QualificacaoMatchDTO> qualificacoes) {
		this.qualificacoes = qualificacoes;
	}
	/**
	 * @return the atualizarMatch
	 */
	public boolean isAtualizarMatch() {
		return atualizarMatch;
	}
	/**
	 * @param atualizarMatch the atualizarMatch to set
	 */
	public void setAtualizarMatch(boolean atualizarMatch) {
		this.atualizarMatch = atualizarMatch;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipoDoador
	 */
	public Long getTipoDoador() {
		return tipoDoador;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(Long tipoDoador) {
		this.tipoDoador = tipoDoador;
	}	
	
	
	
}
