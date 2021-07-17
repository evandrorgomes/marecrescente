package br.org.cancer.modred.feign.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PedidoWorkupDTO implements Serializable {

	private static final long serialVersionUID = 3841109119515428856L;
	
	private Long id;
	private LocalDate dataPrevistaLiberacaoDoador;
	private LocalDate dataPrevistaDisponibilidadeDoador;
	private LocalDate dataColeta;
	private LocalDate dataLimiteWorkup;
	private LocalDate dataInicioWorkup;
	private LocalDate dataFinalWorkup;
	private LocalDateTime dataUltimoStatus;
	private LocalDateTime dataCriacao;
	private Integer tentativasAgendamento;
	private Integer tipoUtilizado;
	private Long idStatus;
	private Long idSolicitacao;
	private Long idCentroTransplante;
	private Long idCentroColeta;
	private Long idUsuarioResponsavel;
	private Boolean necessitaLogistica;
	private Long idFonteCelula;
	
	public PedidoWorkupDTO() {
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
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the dataPrevistaLiberacaoDoador
	 */
	public LocalDate getDataPrevistaLiberacaoDoador() {
		return dataPrevistaLiberacaoDoador;
	}
	/**
	 * @param dataPrevistaLiberacaoDoador the dataPrevistaLiberacaoDoador to set
	 */
	public void setDataPrevistaLiberacaoDoador(LocalDate dataPrevistaLiberacaoDoador) {
		this.dataPrevistaLiberacaoDoador = dataPrevistaLiberacaoDoador;
	}
	/**
	 * @return the dataPrevistaDisponibilidadeDoador
	 */
	public LocalDate getDataPrevistaDisponibilidadeDoador() {
		return dataPrevistaDisponibilidadeDoador;
	}
	/**
	 * @param dataPrevistaDisponibilidadeDoador the dataPrevistaDisponibilidadeDoador to set
	 */
	public void setDataPrevistaDisponibilidadeDoador(LocalDate dataPrevistaDisponibilidadeDoador) {
		this.dataPrevistaDisponibilidadeDoador = dataPrevistaDisponibilidadeDoador;
	}
	/**
	 * @return the dataColeta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}
	/**
	 * @param dataColeta the dataColeta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}
	/**
	 * @return the dataLimiteWorkup
	 */
	public LocalDate getDataLimiteWorkup() {
		return dataLimiteWorkup;
	}
	/**
	 * @param dataLimiteWorkup the dataLimiteWorkup to set
	 */
	public void setDataLimiteWorkup(LocalDate dataLimiteWorkup) {
		this.dataLimiteWorkup = dataLimiteWorkup;
	}
	/**
	 * @return the dataInicioWorkup
	 */
	public LocalDate getDataInicioWorkup() {
		return dataInicioWorkup;
	}
	/**
	 * @param dataInicioWorkup the dataInicioWorkup to set
	 */
	public void setDataInicioWorkup(LocalDate dataInicioWorkup) {
		this.dataInicioWorkup = dataInicioWorkup;
	}
	/**
	 * @return the dataFinalWorkup
	 */
	public LocalDate getDataFinalWorkup() {
		return dataFinalWorkup;
	}
	/**
	 * @param dataFinalWorkup the dataFinalWorkup to set
	 */
	public void setDataFinalWorkup(LocalDate dataFinalWorkup) {
		this.dataFinalWorkup = dataFinalWorkup;
	}
	/**
	 * @return the dataUltimoStatus
	 */
	public LocalDateTime getDataUltimoStatus() {
		return dataUltimoStatus;
	}
	/**
	 * @param dataUltimoStatus the dataUltimoStatus to set
	 */
	public void setDataUltimoStatus(LocalDateTime dataUltimoStatus) {
		this.dataUltimoStatus = dataUltimoStatus;
	}
	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	/**
	 * @return the tentativasAgendamento
	 */
	public Integer getTentativasAgendamento() {
		return tentativasAgendamento;
	}
	/**
	 * @param tentativasAgendamento the tentativasAgendamento to set
	 */
	public void setTentativasAgendamento(Integer tentativasAgendamento) {
		this.tentativasAgendamento = tentativasAgendamento;
	}
	/**
	 * @return the tipoUtilizado
	 */
	public Integer getTipoUtilizado() {
		return tipoUtilizado;
	}
	/**
	 * @param tipoUtilizado the tipoUtilizado to set
	 */
	public void setTipoUtilizado(Integer tipoUtilizado) {
		this.tipoUtilizado = tipoUtilizado;
	}
	/**
	 * @return the idStatus
	 */
	public Long getIdStatus() {
		return idStatus;
	}
	/**
	 * @param idStatus the idStatus to set
	 */
	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}
	/**
	 * @return the idSolicitacao
	 */
	public Long getIdSolicitacao() {
		return idSolicitacao;
	}
	/**
	 * @param idSolicitacao the idSolicitacao to set
	 */
	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	/**
	 * @return the idCentroColeta
	 */
	public Long getIdCentroColeta() {
		return idCentroColeta;
	}
	/**
	 * @param idCentroColeta the idCentroColeta to set
	 */
	public void setIdCentroColeta(Long idCentroColeta) {
		this.idCentroColeta = idCentroColeta;
	}
	/**
	 * @return the idUsuarioResponsavel
	 */
	public Long getIdUsuarioResponsavel() {
		return idUsuarioResponsavel;
	}
	/**
	 * @param idUsuarioResponsavel the idUsuarioResponsavel to set
	 */
	public void setIdUsuarioResponsavel(Long idUsuarioResponsavel) {
		this.idUsuarioResponsavel = idUsuarioResponsavel;
	}
	/**
	 * @return the necessitaLogistica
	 */
	public Boolean getNecessitaLogistica() {
		return necessitaLogistica;
	}
	/**
	 * @param necessitaLogistica the necessitaLogistica to set
	 */
	public void setNecessitaLogistica(Boolean necessitaLogistica) {
		this.necessitaLogistica = necessitaLogistica;
	}
	/**
	 * @return the idFonteCelula
	 */
	public Long getIdFonteCelula() {
		return idFonteCelula;
	}
	/**
	 * @param idFonteCelula the idFonteCelula to set
	 */
	public void setIdFonteCelula(Long idFonteCelula) {
		this.idFonteCelula = idFonteCelula;
	}

	/**
	 * @return the idCentroTransplante
	 */
	public Long getIdCentroTransplante() {
		return idCentroTransplante;
	}

	/**
	 * @param idCentroTransplante the idCentroTransplante to set
	 */
	public void setIdCentroTransplante(Long idCentroTransplante) {
		this.idCentroTransplante = idCentroTransplante;
	}
	
}
