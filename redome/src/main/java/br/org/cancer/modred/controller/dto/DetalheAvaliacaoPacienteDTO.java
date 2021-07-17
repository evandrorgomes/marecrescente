package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.CentroTransplante;

/**
 * Classe que representa o detalhe da avaliação do paciente que consiste em:
 * Centro avaliador, médico responsável, centro transplantador sugerido e 
 * a lista de centros transplantadores possíveis.
 * 
 * @author Pizão
 *
 */
public class DetalheAvaliacaoPacienteDTO {
	
	private Long rmr;
	private String nomeCentroAvaliador;
	private CentroTransplante centroTransplantador;
	private String nomeMedicoResponsavel;
	private List<ContatoCentroTransplantadorDTO> contatosPorCentroTransplante;
	private Boolean centroTransplantadorConfirmado;
	
	
	public Long getRmr() {
		return rmr;
	}
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	public String getNomeCentroAvaliador() {
		return nomeCentroAvaliador;
	}
	public void setNomeCentroAvaliador(String nomeCentroAvaliador) {
		this.nomeCentroAvaliador = nomeCentroAvaliador;
	}
	public String getNomeMedicoResponsavel() {
		return nomeMedicoResponsavel;
	}
	public void setNomeMedicoResponsavel(String nomeMedicoResponsavel) {
		this.nomeMedicoResponsavel = nomeMedicoResponsavel;
	}
	public List<ContatoCentroTransplantadorDTO> getContatosPorCentroTransplante() {
		return contatosPorCentroTransplante;
	}
	public void setContatosPorCentroTransplante(List<ContatoCentroTransplantadorDTO> contatosPorCentroTransplante) {
		this.contatosPorCentroTransplante = contatosPorCentroTransplante;
	}
	public CentroTransplante getCentroTransplantador() {
		return centroTransplantador;
	}
	public void setCentroTransplantador(CentroTransplante centroTransplantador) {
		this.centroTransplantador = centroTransplantador;
	}
	public Boolean getCentroTransplantadorConfirmado() {
		return centroTransplantadorConfirmado;
	}
	public void setCentroTransplantadorConfirmado(Boolean centroTransplatadorConfirmado) {
		this.centroTransplantadorConfirmado = centroTransplatadorConfirmado;
	}
	
}
