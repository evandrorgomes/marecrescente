package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO para representar um item do Histórico da Busca.
 * 
 * @author Pizão
 *
 */
public class HistoricoBuscaDTO implements Serializable{
	private static final long serialVersionUID = 2168894176672283109L;
	
	private String justificativa;
	private LocalDateTime dataAtualizacao;
	private String nomeUsuario;
	
	
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
}
