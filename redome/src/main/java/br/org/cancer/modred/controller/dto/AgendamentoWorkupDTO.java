package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe referente aos parametros necessários para a finalização do agendamento de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public class AgendamentoWorkupDTO implements Serializable{
	private static final long serialVersionUID = -9184497700040395558L;

	private LocalDate dataPrevistaDisponibilidadeDoador;
	private LocalDate dataPrevistaLiberacaoDoador;
	private LocalDate dataColeta;
	private LocalDate dataInicioResultado;
	private LocalDate dataFinalResultado;
	
	private LocalDate dataLimiteWorkup;
	
	private Long idCentroColeta;
	private Boolean necessitaLogistica;
	private Long idFonteCelula;

	
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
	public LocalDate getDataInicioResultado() {
		return dataInicioResultado;
	}
	public void setDataInicioResultado(LocalDate dataInicioResultado) {
		this.dataInicioResultado = dataInicioResultado;
	}
	public LocalDate getDataFinalResultado() {
		return dataFinalResultado;
	}
	public void setDataFinalResultado(LocalDate dataFinalResultado) {
		this.dataFinalResultado = dataFinalResultado;
	}
	public Long getIdCentroColeta() {
		return idCentroColeta;
	}
	public void setIdCentroColeta(Long idCentroColeta) {
		this.idCentroColeta = idCentroColeta;
	}
	public Boolean getNecessitaLogistica() {
		return necessitaLogistica;
	}
	public void setNecessitaLogistica(Boolean necessitaLogistica) {
		this.necessitaLogistica = necessitaLogistica;
	}
	public LocalDate getDataLimiteWorkup() {
		return dataLimiteWorkup;
	}
	public void setDataLimiteWorkup(LocalDate dataLimiteWorkup) {
		this.dataLimiteWorkup = dataLimiteWorkup;
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
	
	
	
}
