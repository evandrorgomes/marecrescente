package br.org.cancer.modred.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * Classe utilizada para criação da consulta de doador - contato passivo.
 * 
 * @author evandro.gomes
 *
 */
public class PedidoExameDoadorInternacionalVo implements Serializable, Cloneable {

	private static final long serialVersionUID = 4860435637249185865L;

	private Long idBusca;
	private Long idSolicitacao;
	private Long idStatusSolicitacao;
	private Long idTipoSolicitacao;
	private Long idTipoExame;
	private String nomeTipoExame;

	private Long idDoador;
	private Long veioDoEmdis;
	private String idOrigem;
	private Long tipoDoador;

	private Long idPedidoExame;
	private Long idExame;
	private String origem;

	private String exame;

	private Long idTarefa;
	private Long idProcesso;
	private Long tipoTarefa;

	private LocalDateTime dataCriacao;

	private Long idTipoServico;
	private String descricaoServico;
	private Long idPagamento;

	private String grid;

	public PedidoExameDoadorInternacionalVo() {
	}

	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}

	/**
	 * @param idBusca the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
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
	 * @return the idStatusSolicitacao
	 */
	public Long getIdStatusSolicitacao() {
		return idStatusSolicitacao;
	}

	/**
	 * @param idStatusSolicitacao the idStatusSolicitacao to set
	 */
	public void setIdStatusSolicitacao(Long idStatusSolicitacao) {
		this.idStatusSolicitacao = idStatusSolicitacao;
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
	 * @return the veioDoEmdis
	 */
	public Long getVeioDoEmdis() {
		return veioDoEmdis;
	}

	/**
	 * @param veioDoEmdis the veioDoEmdis to set
	 */
	public void setVeioDoEmdis(Long veioDoEmdis) {
		this.veioDoEmdis = veioDoEmdis;
	}

	/**
	 * @return the idOrigem
	 */
	public String getIdOrigem() {
		return idOrigem;
	}

	/**
	 * @param idOrigem the idOrigem to set
	 */
	public void setIdOrigem(String idOrigem) {
		this.idOrigem = idOrigem;
	}

	/**
	 * @return the tipoDoador
	 */
	public Long getTipoDoador() {
		return tipoDoador;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(Long tipoDoador) {
		this.tipoDoador = tipoDoador;
	}

	/**
	 * @return the idPedidoExame
	 */
	public Long getIdPedidoExame() {
		return idPedidoExame;
	}

	/**
	 * @param idPedidoExame the idPedidoExame to set
	 */
	public void setIdPedidoExame(Long idPedidoExame) {
		this.idPedidoExame = idPedidoExame;
	}

	/**
	 * @return the idExame
	 */
	public Long getIdExame() {
		return idExame;
	}

	/**
	 * @param idExame the idExame to set
	 */
	public void setIdExame(Long idExame) {
		this.idExame = idExame;
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
	 * @return the exame
	 */
	public String getExame() {
		return exame;
	}

	/**
	 * @param exame the exame to set
	 */
	public void setExame(String exame) {
		this.exame = exame;
	}

	/**
	 * @return the idTarefa
	 */
	public Long getIdTarefa() {
		return idTarefa;
	}

	/**
	 * @param idTarefa the idTarefa to set
	 */
	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}

	/**
	 * @return the idProcesso
	 */
	public Long getIdProcesso() {
		return idProcesso;
	}

	/**
	 * @param idProcesso the idProcesso to set
	 */
	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	/**
	 * @return the tipoTarefa
	 */
	public Long getTipoTarefa() {
		return tipoTarefa;
	}

	/**
	 * @param tipoTarefa the tipoTarefa to set
	 */
	public void setTipoTarefa(Long tipoTarefa) {
		this.tipoTarefa = tipoTarefa;
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
	 * @return the idTipoSolicitacao
	 */
	public Long getIdTipoSolicitacao() {
		return idTipoSolicitacao;
	}

	/**
	 * @param idTipoSolicitacao the idTipoSolicitacao to set
	 */
	public void setIdTipoSolicitacao(Long idTipoSolicitacao) {
		this.idTipoSolicitacao = idTipoSolicitacao;
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
	 * @return the grid
	 */
	public String getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(String grid) {
		this.grid = grid;
	}

	/**
	 * @return the idTipoExame
	 */
	public Long getIdTipoExame() {
		return idTipoExame;
	}

	/**
	 * @param idTipoExame the idTipoExame to set
	 */
	public void setIdTipoExame(Long idTipoExame) {
		this.idTipoExame = idTipoExame;
	}

	/**
	 * @return the nomeTipoExame
	 */
	public String getNomeTipoExame() {
		return nomeTipoExame;
	}

	/**
	 * @param nomeTipoExame the nomeTipoExame to set
	 */
	public void setNomeTipoExame(String nomeTipoExame) {
		this.nomeTipoExame = nomeTipoExame;
	}

	@Override
	public PedidoExameDoadorInternacionalVo clone() {
		PedidoExameDoadorInternacionalVo clone = new PedidoExameDoadorInternacionalVo();
		clone.setIdBusca(this.idBusca);
		clone.setIdSolicitacao(this.idSolicitacao);
		clone.setIdStatusSolicitacao(this.idStatusSolicitacao);
		clone.setIdTipoSolicitacao(this.idTipoSolicitacao);
		clone.setIdDoador(this.idDoador);
		clone.setVeioDoEmdis(this.veioDoEmdis);
		clone.setIdOrigem(this.idOrigem);
		clone.setTipoDoador(this.tipoDoador);
		clone.setOrigem(this.origem);
		clone.setDataCriacao(this.dataCriacao);
		return clone;
	}

}
