package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
public class PesquisaWmdaDto {

	private Long id;
	private Long buscaId;
	private Long searchId;
	private Long searchResultId;
	private String searchType;
	private String algorithm;
	private LocalDateTime dtEnvio;
	private LocalDateTime dtResultado;
	private List<PesquisaWmdaDoadorDto> pesquisaWmdaDoadores;
	
	public PesquisaWmdaDto() {
		super();
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
	public PesquisaWmdaDto comOId(Long id) {
		this.id = id;
		return this;
	}
	/**
	 * @return the buscaId
	 */
	public Long getBuscaId() {
		return buscaId;
	}
	/**
	 * @param buscaId the buscaId to set
	 */
	public PesquisaWmdaDto comABuscaId(Long buscaId) {
		this.buscaId = buscaId;
		return this;
	}
	/**
	 * @return the searchId
	 */
	public Long getSearchId() {
		return searchId;
	}
	/**
	 * @param searchId the searchId to set
	 */
	public PesquisaWmdaDto comOSearchId(Long searchId) {
		this.searchId = searchId;
		return this;
	}
	/**
	 * @return the searchResultId
	 */
	public Long getSearchResultId() {
		return searchResultId;
	}
	/**
	 * @param searchResultId the searchResultId to set
	 */
	public PesquisaWmdaDto comOSearchResultId(Long searchResultId) {
		this.searchResultId = searchResultId;
		return this;
	}
	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public PesquisaWmdaDto comOSearchType(String searchType) {
		this.searchType = searchType;
		return this;
	}
	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}
	/**
	 * @param algorithm the algorithm to set
	 */
	public PesquisaWmdaDto comOAlgorithm(String algorithm) {
		this.algorithm = algorithm;
		return this;
	}
	/**
	 * @return the dtEnvio
	 */
	public LocalDateTime getDtEnvio() {
		return dtEnvio;
	}
	/**
	 * @param dtEnvio the dtEnvio to set
	 */
	public PesquisaWmdaDto comADtEnvio(LocalDateTime dtEnvio) {
		this.dtEnvio = dtEnvio;
		return this;
	}
	/**
	 * @return the dtResultado
	 */
	public LocalDateTime getDtResultado() {
		return dtResultado;
	}
	/**
	 * @param dtResultado the dtResultado to set
	 */
	public PesquisaWmdaDto comADtResultado(LocalDateTime dtResultado) {
		this.dtResultado = dtResultado;
		return this;
	}
	/**
	 * @return the pesquisaWmdaDoadores
	 */
	public List<PesquisaWmdaDoadorDto> getPesquisaWmdaDoadores() {
		return pesquisaWmdaDoadores;
	}
	/**
	 * @param pesquisaWmdaDoadores the pesquisaWmdaDoadores to set
	 */
	public void setPesquisaWmdaDoadores(List<PesquisaWmdaDoadorDto> pesquisaWmdaDoadores) {
		this.pesquisaWmdaDoadores = pesquisaWmdaDoadores;
	}

}