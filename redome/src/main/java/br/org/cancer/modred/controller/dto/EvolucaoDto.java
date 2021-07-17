package br.org.cancer.modred.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.model.Evolucao;

/**
 * Classe Vo para dados da tela de Evolução. s
 * 
 * @author Filipe Paes
 *
 */
public class EvolucaoDto {

	/**
	 * Lista de evoluções.
	 */
	@JsonView(EvolucaoView.ListaEvolucao.class)
	private List<EvolucaoListaDto> evolucoes;
	/**
	 * Última evolução.
	 */
	@JsonView(EvolucaoView.ListaEvolucao.class)
	private Evolucao evolucaoSelecionada;
	
	@JsonView(EvolucaoView.ListaEvolucao.class)
	private boolean pacienteEmObito;

	/**
	 * @return the evolucoes
	 */
	public List<EvolucaoListaDto> getEvolucoes() {
		return evolucoes;
	}

	/**
	 * @param evolucoes the evolucoes to set
	 */
	public void setEvolucoes(List<EvolucaoListaDto> evolucoes) {
		this.evolucoes = evolucoes;
	}

	/**
	 * @return the evolucaoSelecionada
	 */
	public Evolucao getEvolucaoSelecionada() {
		return evolucaoSelecionada;
	}

	/**
	 * @param evolucaoSelecionada the evolucaoSelecionada to set
	 */
	public void setEvolucaoSelecionada(Evolucao evolucaoSelecionada) {
		this.evolucaoSelecionada = evolucaoSelecionada;
	}
	
	/**
	 * Retorna true se paciente está em óbito.
	 * 
	 * @return
	 */
	public boolean isPacienteEmObito() {
		return pacienteEmObito;
	}

	/**
	 * Seta se o paciente está em óbito.
	 * 
	 * @param pacienteEmObito
	 */
	public void setPacienteEmObito(boolean pacienteEmObito) {
		this.pacienteEmObito = pacienteEmObito;
	}
	
}
