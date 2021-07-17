package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.controller.dto.PesquisaWmdaDTO;

/**
 * Classe que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
@Entity
@Table(name = "PESQUISA_WMDA")
@SequenceGenerator(name = "SQ_PEWM_ID", sequenceName = "SQ_PEWM_ID", allocationSize = 1)
public class PesquisaWmda implements Serializable {

	private static final long serialVersionUID = -7651251207655497433L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEWM_ID")
	@Column(name = "PEWM_ID")
	private Long id;

	@Column(name = "BUSC_ID")
	private Long busca;

	@NotNull
	@Column(name = "PEWM_NR_SEARCH")	
	private Long searchId;
	
	@Column(name = "PEWM_NR_SEARCH_RESULT")	
	private Long searchResultId;

	@Column(name = "PEWM_TX_ALGORITHM")	
	private String algorithm;

	@NotNull
	@Column(name = "PEWM_DT_ENVIO")	
	private LocalDateTime dtEnvio;

	@Column(name = "PEWM_DT_RESULTADO")	
	private LocalDateTime dtResultado;

	@Column(name = "PEWM_TX_WMDA_ID")	
	private String wmdaId;

	@Column(name = "PEWM_TX_SEARCH_TYPE")	
	private String tipo;

	@Column(name = "PEWM_NR_STATUS")	
	private Integer status;

	@Column(name = "PEWM_NR_MATCH_WMDA")	
	private Integer qtdMatchWmda;

	@Column(name = "PEWM_NR_MATCH_IMPORTADO")	
	private Integer qtdMatchWmdaImportado;

	@Lob
	@Column(name = "PEWM_TX_LOG")
	private String logPesquisaWmda;

	@Lob
	@Column(name = "PEWM_TX_JSON")	
	private byte[] jsonPesquisaWmda;

	@Column(name = "PEWM_NR_VALOR_BLANK")	
	private Integer qtdValorBlank;
	
	public PesquisaWmda() {
		super();
	}

	/**
	 * @param id
	 */
	public PesquisaWmda(Long id) {
		super();
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
	 * @return the busca
	 */
	public Long getBusca() {
		return busca;
	}

	/**
	 * @param busca the busca to set
	 */
	public void setBusca(Long busca) {
		this.busca = busca;
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
	public byte[] getJsonPesquisaWmda() {
		return jsonPesquisaWmda;
	}

	/**
	 * @param jsonPesquisaWmda the jsonPesquisaWmda to set
	 */
	public void setJsonPesquisaWmda(byte[] jsonPesquisaWmda) {
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

	public PesquisaWmda parse(PesquisaWmdaDTO pesquisaWmdaDto) {
		PesquisaWmda pesquisaRetorno = new PesquisaWmda();
		pesquisaRetorno.setId(pesquisaWmdaDto.getId());
		pesquisaRetorno.setStatus(pesquisaWmdaDto.getStatus());
		pesquisaRetorno.setSearchId(pesquisaWmdaDto.getSearchId());
		pesquisaRetorno.setSearchResultId(pesquisaWmdaDto.getSearchResultId());
		pesquisaRetorno.setWmdaId(pesquisaWmdaDto.getWmdaId());
		pesquisaRetorno.setTipo(pesquisaWmdaDto.getTipo());
		pesquisaRetorno.setAlgorithm(pesquisaWmdaDto.getAlgorithm());
		pesquisaRetorno.setDtEnvio(pesquisaWmdaDto.getDtEnvio());
		pesquisaRetorno.setDtResultado(pesquisaWmdaDto.getDtResultado());
		pesquisaRetorno.setBusca(pesquisaWmdaDto.getBuscaId());
		pesquisaRetorno.setQtdMatchWmda(
				pesquisaWmdaDto.getQtdMatchWmda() != null ? pesquisaWmdaDto.getQtdMatchWmda() : 0);
		pesquisaRetorno.setQtdMatchWmdaImportado(
				pesquisaWmdaDto.getQtdMatchWmdaImportado() != null ? pesquisaWmdaDto.getQtdMatchWmdaImportado() : 0);
		pesquisaRetorno.setLogPesquisaWmda(pesquisaWmdaDto.getLogPesquisaWmda());
		pesquisaRetorno.setQtdValorBlank(pesquisaWmdaDto.getQtdValorBlank());
//		pesquisaRetorno.setJsonPesquisaWmda(AppUtil.conversorStringParaBytes(pesquisaWmdaDto.getJsonPesquisaWmda()));
		
		return pesquisaRetorno;
	}

}