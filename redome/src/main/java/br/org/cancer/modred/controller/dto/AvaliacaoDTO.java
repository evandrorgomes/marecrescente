package br.org.cancer.modred.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Evolucao;

/**
 * Classe VO para dados da tela de Avaliacao.
 * 
 * @author bruno.sousa
 *
 */
public class AvaliacaoDTO {

	/**
	 * Avaliação atual do paciente.
	 */
	@JsonView({ AvaliacaoView.Avaliacao.class })
	private Avaliacao avaliacaoAtual;

	/**
	 * Ultima evolução do paciente.
	 */
	@JsonView({ AvaliacaoView.Avaliacao.class })
	private Evolucao ultimaEvolucao;

	/**
	 * @return the avaliacaoAtual
	 */
	public Avaliacao getAvaliacaoAtual() {
		return avaliacaoAtual;
	}

	/**
	 * @param avaliacaoAtual the avaliacaoAtual to set
	 */
	public void setAvaliacaoAtual(Avaliacao avaliacaoAtual) {
		this.avaliacaoAtual = avaliacaoAtual;
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
