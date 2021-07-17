package br.org.cancer.modred.vo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * Classe utilizada para criação da consulta de doador - contato passivo.
 * 
 * @author evandro.gomes
 *
 */
public class ConsultaDoadorNacionalVo implements Serializable {

	private static final long serialVersionUID = -307578901347149664L;

	 private Long idDoador;
	 private Long idTentativa;
	 private Long idProcesso;
	 private Long idTarefa;
	 private Long idEnriquecimento;
	 private Long idStatusTarefa;
	 private Long dmr;
	 private String nomeDoador;
	 private Long idStatusDoador;
	 private String descricaoStatusDoador;
	 private Long idMotivoDoador;
	 private String descricaoMotivoDoador;
	 private LocalDate dataRetornoInatividade;
	 private Boolean contactado;
	 private Long tipoContato;
	 private Boolean passivo;
	 private Integer tipoFluxo;
	 
	/**
	 * 
	 */
	public ConsultaDoadorNacionalVo() {}
	/**
	 * @param idDoador
	 */
	public ConsultaDoadorNacionalVo(Long idDoador) {
		this();
		this.idDoador = idDoador;
	}

	/**
	 * @param idTentativa
	 * @param tipoContato
	 */
	public ConsultaDoadorNacionalVo(Long idTentativa, Long tipoContato) {
		this.idTentativa = idTentativa;
		this.tipoContato = tipoContato;
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
	 * @return the idTentativa
	 */
	public Long getIdTentativa() {
		return idTentativa;
	}
	/**
	 * @param idTentativa the idTentativa to set
	 */
	public void setIdTentativa(Long idTentativa) {
		this.idTentativa = idTentativa;
	}
	/**
	 * @return the idTarefa
	 */
	public Long getIdTarefa() {
		return idTarefa;
	}
	/**
	 * @param idTarefa the idTarefa to set
	 */
	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}
	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}
	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}
	/**
	 * @return the nomeDoador
	 */
	public String getNomeDoador() {
		return nomeDoador;
	}
	/**
	 * @param nomeDoador the nomeDoador to set
	 */
	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}
	/**
	 * @return the idStatusDoador
	 */
	public Long getIdStatusDoador() {
		return idStatusDoador;
	}
	/**
	 * @param idStatusDoador the idStatusDoador to set
	 */
	public void setIdStatusDoador(Long idStatusDoador) {
		this.idStatusDoador = idStatusDoador;
	}
	/**
	 * @return the descricaoStatusDoador
	 */
	public String getDescricaoStatusDoador() {
		return descricaoStatusDoador;
	}
	/**
	 * @param descricaoStatusDoador the descricaoStatusDoador to set
	 */
	public void setDescricaoStatusDoador(String descricaoStatusDoador) {
		this.descricaoStatusDoador = descricaoStatusDoador;
	}
	/**
	 * @return the idMotivoDoador
	 */
	public Long getIdMotivoDoador() {
		return idMotivoDoador;
	}
	/**
	 * @param idMotivoDoador the idMotivoDoador to set
	 */
	public void setIdMotivoDoador(Long idMotivoDoador) {
		this.idMotivoDoador = idMotivoDoador;
	}
	/**
	 * @return the descricaoMotivoDoador
	 */
	public String getDescricaoMotivoDoador() {
		return descricaoMotivoDoador;
	}
	/**
	 * @param descricaoMotivoDoador the descricaoMotivoDoador to set
	 */
	public void setDescricaoMotivoDoador(String descricaoMotivoDoador) {
		this.descricaoMotivoDoador = descricaoMotivoDoador;
	}
	/**
	 * @return the dataRetornoInatividade
	 */
	public LocalDate getDataRetornoInatividade() {
		return dataRetornoInatividade;
	}
	/**
	 * @param dataRetornoInatividade the dataRetornoInatividade to set
	 */
	public void setDataRetornoInatividade(LocalDate dataRetornoInatividade) {
		this.dataRetornoInatividade = dataRetornoInatividade;
	}
	/**
	 * @return the idStatusTarefa
	 */
	public Long getIdStatusTarefa() {
		return idStatusTarefa;
	}
	/**
	 * @param idStatusTarefa the idStatusTarefa to set
	 */
	public void setIdStatusTarefa(Long idStatusTarefa) {
		this.idStatusTarefa = idStatusTarefa;
	}
	/**
	 * @return the contactado
	 */
	public Boolean isContactado() {
		return contactado;
	}
	/**
	 * @param contactado the contactado to set
	 */
	public void setContactado(Boolean contactado) {
		this.contactado = contactado;
	}
	/**
	 * @return the tipoContato
	 */
	public Long getTipoContato() {
		return tipoContato;
	}
	/**
	 * @param tipoContato the tipoContato to set
	 */
	public void setTipoContato(Long tipoContato) {
		this.tipoContato = tipoContato;
	}
	/**
	 * @return the idProcesso
	 */
	public Long getIdProcesso() {
		return idProcesso;
	}
	/**
	 * @param idProcesso the idProcesso to set
	 */
	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
	/**
	 * @return the contactado
	 */
	public Boolean getContactado() {
		return contactado;
	}
	/**
	 * @return the passivo
	 */
	public Boolean getPassivo() {
		return passivo;
	}
	/**
	 * @param passivo the passivo to set
	 */
	public void setPassivo(Boolean passivo) {
		this.passivo = passivo;
	}
	/**
	 * @return the tipoFluxo
	 */
	public Integer getTipoFluxo() {
		return tipoFluxo;
	}
	/**
	 * @param tipoFluxo the tipoFluxo to set
	 */
	public void setTipoFluxo(Integer tipoFluxo) {
		this.tipoFluxo = tipoFluxo;
	}
	/**
	 * @return the idEnriquecimento
	 */
	public Long getIdEnriquecimento() {
		return idEnriquecimento;
	}
	/**
	 * @param idEnriquecimento the idEnriquecimento to set
	 */
	public void setIdEnriquecimento(Long idEnriquecimento) {
		this.idEnriquecimento = idEnriquecimento;
	}
	
}
