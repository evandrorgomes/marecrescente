package br.org.cancer.modred.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.Evolucao;


/**
 * Classe de DTO para avaliacao de camara técnica.
 * @author Filipe Paes
 *
 */
public class AvaliacaoCamaraTecnicaDTO {

	@JsonView(AvaliacaoCamaraTecnicaView.Detalhe.class)
	private AvaliacaoCamaraTecnica avaliacaoCamaraTecnica;
	
	@JsonView(AvaliacaoCamaraTecnicaView.Detalhe.class)
	private Evolucao ultimaEvolucao;

	/**
	 * @return the avaliacaoCamaraTecnica
	 */
	public AvaliacaoCamaraTecnica getAvaliacaoCamaraTecnica() {
		return avaliacaoCamaraTecnica;
	}

	/**
	 * @param avaliacaoCamaraTecnica the avaliacaoCamaraTecnica to set
	 */
	public void setAvaliacaoCamaraTecnica(AvaliacaoCamaraTecnica avaliacaoCamaraTecnica) {
		this.avaliacaoCamaraTecnica = avaliacaoCamaraTecnica;
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
