package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe de DTO de resultado de workup.
 * 
 * @author ergomes
 * 
 */
public class ResultadoWorkupNacionalDTO implements Serializable {

	private static final long serialVersionUID = 5991252479966697681L;

	private Boolean coletaInviavel;

	private String motivoInviabilidade;

	private Long idPedidoWorkup;

	private Long idFonteCelula;

	private LocalDate dataColeta;

	private LocalDate dataGCSF;

	private Boolean adeguadoAferese;

	private String acessoVenosoCentral;

	private Boolean sangueAntologoColetado;

	private String motivoSangueAntologoNaoColetado;

	/**
	 * @return the coletaInviavel
	 */
	public Boolean getColetaInviavel() {
		return coletaInviavel;
	}

	/**
	 * @param coletaInviavel the coletaInviavel to set
	 */
	public void setColetaInviavel(Boolean coletaInviavel) {
		this.coletaInviavel = coletaInviavel;
	}

	/**
	 * @return the motivoInviabilidade
	 */
	public String getMotivoInviabilidade() {
		return motivoInviabilidade;
	}

	/**
	 * @param motivoInviabilidade the motivoInviabilidade to set
	 */
	public void setMotivoInviabilidade(String motivoInviabilidade) {
		this.motivoInviabilidade = motivoInviabilidade;
	}

	public Long getIdPedidoWorkup() {
		return idPedidoWorkup;
	}

	public void setIdPedidoWorkup(Long idPedidoWorkup) {
		this.idPedidoWorkup = idPedidoWorkup;
	}

	public Long getIdFonteCelula() {
		return idFonteCelula;
	}

	public void setIdFonteCelula(Long idFonteCelula) {
		this.idFonteCelula = idFonteCelula;
	}

	public LocalDate getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}

	public LocalDate getDataGCSF() {
		return dataGCSF;
	}

	public void setDataGCSF(LocalDate dataGCSF) {
		this.dataGCSF = dataGCSF;
	}

	public Boolean getAdeguadoAferese() {
		return adeguadoAferese;
	}

	public void setAdeguadoAferese(Boolean adeguadoAferese) {
		this.adeguadoAferese = adeguadoAferese;
	}

	public String getAcessoVenosoCentral() {
		return acessoVenosoCentral;
	}

	public void setAcessoVenosoCentral(String acessoVenosoCentral) {
		this.acessoVenosoCentral = acessoVenosoCentral;
	}

	public Boolean getSangueAntologoColetado() {
		return sangueAntologoColetado;
	}

	public void setSangueAntologoColetado(Boolean sangueAntologoColetado) {
		this.sangueAntologoColetado = sangueAntologoColetado;
	}

	public String getMotivoSangueAntologoNaoColetado() {
		return motivoSangueAntologoNaoColetado;
	}

	public void setMotivoSangueAntologoNaoColetado(String motivoSangueAntologoNaoColetado) {
		this.motivoSangueAntologoNaoColetado = motivoSangueAntologoNaoColetado;
	}

}