package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * Classe utilizada para criação da consulta de doador - contato passivo.
 * 
 * @author evandro.gomes
 *
 */
public class PedidosPacienteInvoiceDTO implements Serializable {

	private static final long serialVersionUID = -336290359512408089L;

	private Long rmr;
	private String nomePaciente;
	private String origem;
	private Long idDoador;
	private String idRegistroDoador;
	private LocalDateTime dataCriacao;

	private Long idPedido;
	private String locus;
	private Long idPagamento;
	private Long idStatusPagamento;
	private Long idTipoServico;
	private String descricaoServico;

	private Long QtdRegistros;

	public PedidosPacienteInvoiceDTO() {
	}

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the nomePaciente
	 */
	public String getNomePaciente() {
		return nomePaciente;
	}

	/**
	 * @param nomePaciente the nomePaciente to set
	 */
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	/**
	 * @return the origem
	 */
	public String getOrigem() {
		return origem;
	}

	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
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
	 * @return the qtdRegistros
	 */
	public Long getQtdRegistros() {
		return QtdRegistros;
	}

	/**
	 * @param qtdRegistros the qtdRegistros to set
	 */
	public void setQtdRegistros(Long qtdRegistros) {
		QtdRegistros = qtdRegistros;
	}

	/**
	 * @return the idPedido
	 */
	public Long getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}

	/**
	 * @param locus the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}

	/**
	 * @return the idPagamento
	 */
	public Long getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @param idPagamento the idPagamento to set
	 */
	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	/**
	 * @return the idStatusPagamento
	 */
	public Long getIdStatusPagamento() {
		return idStatusPagamento;
	}

	/**
	 * @param idStatusPagamento the idStatusPagamento to set
	 */
	public void setIdStatusPagamento(Long idStatusPagamento) {
		this.idStatusPagamento = idStatusPagamento;
	}

	/**
	 * @return the idTipoServico
	 */
	public Long getIdTipoServico() {
		return idTipoServico;
	}

	/**
	 * @param idTipoServico the idTipoServico to set
	 */
	public void setIdTipoServico(Long idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	/**
	 * @return the descricaoServico
	 */
	public String getDescricaoServico() {
		return descricaoServico;
	}

	/**
	 * @param descricaoServico the descricaoServico to set
	 */
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}

	/**
	 * @return the idRegistroDoador
	 */
	public String getIdRegistroDoador() {
		return idRegistroDoador;
	}

	/**
	 * @param idRegistroDoador the idRegistroDoador to set
	 */
	public void setIdRegistroDoador(String idRegistroDoador) {
		this.idRegistroDoador = idRegistroDoador;
	}

}
