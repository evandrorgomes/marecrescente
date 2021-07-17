package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.modred.model.PesquisaWmda;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
public class PesquisaWmdaDTO implements Serializable {

	private static final long serialVersionUID = 217684077181130503L;

	private Long id;
	private Long buscaId;
	private Long searchId;
	private Long searchResultId;
	private String wmdaId;
	private String tipo;
	private String algorithm;
	private LocalDateTime dtEnvio;
	private LocalDateTime dtResultado;
	private Integer status;
	private Integer qtdMatchWmda;
	private Integer qtdMatchWmdaImportado;
	private String logPesquisaWmda;
	private String jsonPesquisaWmda;
	private Integer qtdValorBlank;
	
	private PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto;

	public PesquisaWmdaDTO() {}
	
	/**
	 * @param id
	 * @param buscaId
	 * @param searchId
	 * @param searchResultId
	 * @param wmdaId
	 * @param tipo
	 * @param algorithm
	 * @param dtEnvio
	 * @param dtResultado
	 * @param status
	 * @param qtdMatchWmda
	 * @param qtdMatchImportado
	 * @param pesquisaWmdaDoadorDto
	 */
	public PesquisaWmdaDTO(Long id, Long buscaId, Long searchId, Long searchResultId, String wmdaId, String tipo,
			String algorithm, LocalDateTime dtEnvio, LocalDateTime dtResultado, Integer status, Integer qtdMatchWmda,
			Integer qtdMatchWmdaImportado, PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto, String logPesquisaWmda, 
			String jsonPesquisaWmda, Integer qtdValorBlank) {
		this.id = id;
		this.buscaId = buscaId;
		this.searchId = searchId;
		this.searchResultId = searchResultId;
		this.wmdaId = wmdaId;
		this.tipo = tipo;
		this.algorithm = algorithm;
		this.dtEnvio = dtEnvio;
		this.dtResultado = dtResultado;
		this.status = status;
		this.qtdMatchWmda = qtdMatchWmda;
		this.qtdMatchWmdaImportado = qtdMatchWmdaImportado;
		this.pesquisaWmdaDoadorDto = pesquisaWmdaDoadorDto;
		this.logPesquisaWmda = logPesquisaWmda;
		this.jsonPesquisaWmda = jsonPesquisaWmda;
		this.qtdValorBlank = qtdValorBlank;
	}


	/**
	 * @param id
	 */
	public PesquisaWmdaDTO(Long id) {
		this.id = id;
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
	 * @return the buscaId
	 */
	public Long getBuscaId() {
		return buscaId;
	}

	/**
	 * @param buscaId the buscaId to set
	 */
	public void setBuscaId(Long buscaId) {
		this.buscaId = buscaId;
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
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
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
	public void setDtEnvio(LocalDateTime dtEnvio) {
		this.dtEnvio = dtEnvio;
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
	public void setDtResultado(LocalDateTime dtResultado) {
		this.dtResultado = dtResultado;
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
	public void setSearchId(Long searchId) {
		this.searchId = searchId;
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
	public void setSearchResultId(Long searchResultId) {
		this.searchResultId = searchResultId;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * @return the wmdaId
	 */
	public String getWmdaId() {
		return wmdaId;
	}

	/**
	 * @param wmdaId the wmdaId to set
	 */
	public void setWmdaId(String wmdaId) {
		this.wmdaId = wmdaId;
	}
	/**
	 * @return the pesquisaWmdaDoadorDto
	 */
	public PesquisaWmdaDoadorDTO getPesquisaWmdaDoadorDto() {
		return pesquisaWmdaDoadorDto;
	}

	/**
	 * @param pesquisaWmdaDoadorDto the pesquisaWmdaDoadorDto to set
	 */
	public void setPesquisaWmdaDoadorDto(PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto) {
		this.pesquisaWmdaDoadorDto = pesquisaWmdaDoadorDto;
	}
	
	/**
	 * @return the qtdMatchWmda
	 */
	public Integer getQtdMatchWmda() {
		return qtdMatchWmda;
	}

	/**
	 * @param qtdMatchWmda the qtdMatchWmda to set
	 */
	public void setQtdMatchWmda(Integer qtdMatchWmda) {
		this.qtdMatchWmda = qtdMatchWmda;
	}

	/**
	 * @return the qtdMatchWmdaImportado
	 */
	public Integer getQtdMatchWmdaImportado() {
		return qtdMatchWmdaImportado;
	}

	/**
	 * @param qtdMatchWmdaImportado the qtdMatchWmdaImportado to set
	 */
	public void setQtdMatchWmdaImportado(Integer qtdMatchWmdaImportado) {
		this.qtdMatchWmdaImportado = qtdMatchWmdaImportado;
	}

	/**
	 * @return the logPesquisaWmda
	 */
	public String getLogPesquisaWmda() {
		return logPesquisaWmda;
	}

	/**
	 * @param logPesquisaWmda the logPesquisaWmda to set
	 */
	public void setLogPesquisaWmda(String logPesquisaWmda) {
		this.logPesquisaWmda = logPesquisaWmda;
	}
	
	/**
	 * @return the jsonPesquisaWmda
	 */
	public String getJsonPesquisaWmda() {
		return jsonPesquisaWmda;
	}

	/**
	 * @param jsonPesquisaWmda the jsonPesquisaWmda to set
	 */
	public void setJsonPesquisaWmda(String jsonPesquisaWmda) {
		this.jsonPesquisaWmda = jsonPesquisaWmda;
	}

	/**
	 * @return the qtdValorBlank
	 */
	public Integer getQtdValorBlank() {
		return qtdValorBlank;
	}

	/**
	 * @param qtdValorBlank the qtdValorBlank to set
	 */
	public void setQtdValorBlank(Integer qtdValorBlank) {
		this.qtdValorBlank = qtdValorBlank;
	}

	public static PesquisaWmdaDTO parse(PesquisaWmda pesquisaWmda) {
		PesquisaWmdaDTO pesquisaRetorno = new PesquisaWmdaDTO();
		pesquisaRetorno.setId(pesquisaWmda.getId());
		pesquisaRetorno.setStatus(pesquisaWmda.getStatus());
		pesquisaRetorno.setSearchId(pesquisaWmda.getSearchId());
		pesquisaRetorno.setSearchResultId(pesquisaWmda.getSearchResultId());
		pesquisaRetorno.setWmdaId(pesquisaWmda.getWmdaId());
		pesquisaRetorno.setTipo(pesquisaWmda.getTipo());
		pesquisaRetorno.setAlgorithm(pesquisaWmda.getAlgorithm());
		pesquisaRetorno.setDtEnvio(pesquisaWmda.getDtEnvio());
		pesquisaRetorno.setDtResultado(pesquisaWmda.getDtResultado());
		pesquisaRetorno.setBuscaId(pesquisaWmda.getBusca());
		pesquisaRetorno.setQtdMatchWmda(
				pesquisaWmda.getQtdMatchWmda() != null ? pesquisaWmda.getQtdMatchWmda() : 0);
		pesquisaRetorno.setQtdMatchWmdaImportado(
				pesquisaWmda.getQtdMatchWmdaImportado() != null ? pesquisaWmda.getQtdMatchWmdaImportado() : 0);
		pesquisaRetorno.setLogPesquisaWmda(pesquisaWmda.getLogPesquisaWmda());
		pesquisaRetorno.setQtdValorBlank(pesquisaWmda.getQtdValorBlank());
	//	pesquisaRetorno.setJsonPesquisaWmda(AppUtil.conversorBytesParaString(pesquisaWmda.getJsonPesquisaWmda()));
		
		return pesquisaRetorno;
	}

}