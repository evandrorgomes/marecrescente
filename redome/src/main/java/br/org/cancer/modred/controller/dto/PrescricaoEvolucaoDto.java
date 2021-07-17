package br.org.cancer.modred.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Prescricao;

/**
 * Dto para prescricao e a ultima evolucao do paciente.
 * @author Filipe Paes
 *
 */
public class PrescricaoEvolucaoDto {
	@JsonView(AvaliacaoPrescricaoView.Detalhe.class)
	private Prescricao prescricao;
	@JsonView(AvaliacaoPrescricaoView.Detalhe.class)
	private Evolucao ultimaEvolucao;
	
	
	
	public PrescricaoEvolucaoDto(Prescricao prescricao, Evolucao evolucao) {
		this.prescricao = prescricao;
		this.ultimaEvolucao = evolucao;
	}
	
	public PrescricaoEvolucaoDto() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the prescricao
	 */
	public Prescricao getPrescricao() {
		return prescricao;
	}
	/**
	 * @param prescricao the prescricao to set
	 */
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	/**
	 * @return the ultimaEvolucao
	 */
	public Evolucao getUltimaEvolucao() {
		return ultimaEvolucao;
	}
	/**
	 * @param ultimaEvolucao the ultimaEvolucao to set
	 */
	public void setUltimaEvolucao(Evolucao ultimaEvolucao) {
		this.ultimaEvolucao = ultimaEvolucao;
	}
	
	
	
	
}
