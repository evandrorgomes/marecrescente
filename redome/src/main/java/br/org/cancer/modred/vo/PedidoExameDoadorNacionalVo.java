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
public class PedidoExameDoadorNacionalVo implements Serializable {

	private static final long serialVersionUID = 4860435637249185865L;
	
	private Long idTarefa;
	private Long tipoTarefa;
	
	private Long idBusca;
	
	private Long rmr;
	private String nomePaciente;
	
	private Long idSolicitacao;
	private String tipoSolicitacaoDescricao;
	private String statusSolicitacaoDescricao;
	
	private Long dmr;
	private Long idDoador;
	private Long tipoDoador;
	private String nomeDoador;
	private String nomeLaboratorio;

	private Long idPedidoExame;
	private String tipoExameDescricao;
	
	private Long idPedidoEnriquecimento;
	private Long idPedidoContato;
	private Long idPedidoContatoSms;
	
	private String statusDescricao;
	private LocalDateTime dataCriacao;

	public PedidoExameDoadorNacionalVo() {}

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
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the nomeDoador
	 */
	public String getNomeDoador() {
		return nomeDoador;
	}

	/**
	 * @param nomeDoador the nomeDoador to set
	 */
	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}

	/**
	 * @return the nomeLaboratorio
	 */
	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}

	/**
	 * @param nomeLaboratorio the nomeLaboratorio to set
	 */
	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
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
	 * @return the tipoExameDescricao
	 */
	public String getTipoExameDescricao() {
		return tipoExameDescricao;
	}

	/**
	 * @param tipoExameDescricao the tipoExameDescricao to set
	 */
	public void setTipoExameDescricao(String tipoExameDescricao) {
		this.tipoExameDescricao = tipoExameDescricao;
	}

	/**
	 * @return the statusDescricao
	 */
	public String getStatusDescricao() {
		return statusDescricao;
	}

	/**
	 * @param statusDescricao the statusDescricao to set
	 */
	public void setStatusDescricao(String statusDescricao) {
		this.statusDescricao = statusDescricao;
	}

	/**
	 * @return the idPedidoEnriquecimento
	 */
	public Long getIdPedidoEnriquecimento() {
		return idPedidoEnriquecimento;
	}

	/**
	 * @param idPedidoEnriquecimento the idPedidoEnriquecimento to set
	 */
	public void setIdPedidoEnriquecimento(Long idPedidoEnriquecimento) {
		this.idPedidoEnriquecimento = idPedidoEnriquecimento;
	}

	/**
	 * @return the idPedidoContato
	 */
	public Long getIdPedidoContato() {
		return idPedidoContato;
	}

	/**
	 * @param idPedidoContato the idPedidoContato to set
	 */
	public void setIdPedidoContato(Long idPedidoContato) {
		this.idPedidoContato = idPedidoContato;
	}

	/**
	 * @return the tipoSolicitacaoDescricao
	 */
	public String getTipoSolicitacaoDescricao() {
		return tipoSolicitacaoDescricao;
	}

	/**
	 * @param tipoSolicitacaoDescricao the tipoSolicitacaoDescricao to set
	 */
	public void setTipoSolicitacaoDescricao(String tipoSolicitacaoDescricao) {
		this.tipoSolicitacaoDescricao = tipoSolicitacaoDescricao;
	}

	/**
	 * @return the statusSolicitacaoDescricao
	 */
	public String getStatusSolicitacaoDescricao() {
		return statusSolicitacaoDescricao;
	}

	/**
	 * @param statusSolicitacaoDescricao the statusSolicitacaoDescricao to set
	 */
	public void setStatusSolicitacaoDescricao(String statusSolicitacaoDescricao) {
		this.statusSolicitacaoDescricao = statusSolicitacaoDescricao;
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
	 * @return the idPedidoContatoSms
	 */
	public Long getIdPedidoContatoSms() {
		return idPedidoContatoSms;
	}

	/**
	 * @param idPedidoContatoSms the idPedidoContatoSms to set
	 */
	public void setIdPedidoContatoSms(Long idPedidoContatoSms) {
		this.idPedidoContatoSms = idPedidoContatoSms;
	}
	
	
}
