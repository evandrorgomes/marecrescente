package br.org.cancer.modred.feign.dto;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.domain.StatusProcesso;

public class ListaTarefaDTO {
	
	private List<Long> perfilResponsavel;
	private Long idUsuarioResponsavel; 
	private List<Long> parceiros;
	private List<Long> idsTiposTarefa; 
	private List<Long> statusTarefa; 
	private Long statusProcesso = StatusProcesso.ANDAMENTO.getId();
	private Long processoId;
	private Boolean inclusivoExclusivo; 
	private Pageable pageable;
	private Long relacaoEntidadeId; 
	private Long rmr;
	private Long idDoador;
	private Long tipoProcesso; 
	private Boolean atribuidoQualquerUsuario = false; 
	private Long tarefaPaiId;
	private Boolean tarefaSemAgendamento = false; 
	private Long idUsuarioLogado;
	/**
	 * @return the perfilResponsavel
	 */
	public List<Long> getPerfilResponsavel() {
		return perfilResponsavel;
	}
	/**
	 * @param perfilResponsavel the perfilResponsavel to set
	 */
	public void setPerfilResponsavel(List<Long> perfilResponsavel) {
		this.perfilResponsavel = perfilResponsavel;
	}
	/**
	 * @return the idUsuarioResponsavel
	 */
	public Long getIdUsuarioResponsavel() {
		return idUsuarioResponsavel;
	}
	/**
	 * @param idUsuarioResponsavel the idUsuarioResponsavel to set
	 */
	public void setIdUsuarioResponsavel(Long idUsuarioResponsavel) {
		this.idUsuarioResponsavel = idUsuarioResponsavel;
	}
	/**
	 * @return the parceiros
	 */
	public List<Long> getParceiros() {
		return parceiros;
	}
	/**
	 * @param parceiros the parceiros to set
	 */
	public void setParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
	}
	/**
	 * @return the idsTiposTarefa
	 */
	public List<Long> getIdsTiposTarefa() {
		return idsTiposTarefa;
	}
	/**
	 * @param idsTiposTarefa the idsTiposTarefa to set
	 */
	public void setIdsTiposTarefa(List<Long> idsTiposTarefa) {
		this.idsTiposTarefa = idsTiposTarefa;
	}
	/**
	 * @return the statusTarefa
	 */
	public List<Long> getStatusTarefa() {
		return statusTarefa;
	}
	/**
	 * @param statusTarefa the statusTarefa to set
	 */
	public void setStatusTarefa(List<Long> statusTarefa) {
		this.statusTarefa = statusTarefa;
	}
	/**
	 * @return the statusProcesso
	 */
	public Long getStatusProcesso() {
		return statusProcesso;
	}
	/**
	 * @param statusProcesso the statusProcesso to set
	 */
	public void setStatusProcesso(Long statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	/**
	 * @return the processoId
	 */
	public Long getProcessoId() {
		return processoId;
	}
	/**
	 * @param processoId the processoId to set
	 */
	public void setProcessoId(Long processoId) {
		this.processoId = processoId;
	}
	/**
	 * @return the inclusivoExclusivo
	 */
	public Boolean getInclusivoExclusivo() {
		return inclusivoExclusivo;
	}
	/**
	 * @param inclusivoExclusivo the inclusivoExclusivo to set
	 */
	public void setInclusivoExclusivo(Boolean inclusivoExclusivo) {
		this.inclusivoExclusivo = inclusivoExclusivo;
	}
	/**
	 * @return the pageable
	 */
	public Pageable getPageable() {
		return pageable;
	}
	/**
	 * @param pageable the pageable to set
	 */
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	/**
	 * @return the relacaoEntidadeId
	 */
	public Long getRelacaoEntidadeId() {
		return relacaoEntidadeId;
	}
	/**
	 * @param relacaoEntidadeId the relacaoEntidadeId to set
	 */
	public void setRelacaoEntidadeId(Long relacaoEntidadeId) {
		this.relacaoEntidadeId = relacaoEntidadeId;
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
	 * @return the tipoProcesso
	 */
	public Long getTipoProcesso() {
		return tipoProcesso;
	}
	/**
	 * @param tipoProcesso the tipoProcesso to set
	 */
	public void setTipoProcesso(Long tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}
	/**
	 * @return the atribuidoQualquerUsuario
	 */
	public Boolean getAtribuidoQualquerUsuario() {
		return atribuidoQualquerUsuario;
	}
	/**
	 * @param atribuidoQualquerUsuario the atribuidoQualquerUsuario to set
	 */
	public void setAtribuidoQualquerUsuario(Boolean atribuidoQualquerUsuario) {
		this.atribuidoQualquerUsuario = atribuidoQualquerUsuario;
	}
	/**
	 * @return the tarefaPaiId
	 */
	public Long getTarefaPaiId() {
		return tarefaPaiId;
	}
	/**
	 * @param tarefaPaiId the tarefaPaiId to set
	 */
	public void setTarefaPaiId(Long tarefaPaiId) {
		this.tarefaPaiId = tarefaPaiId;
	}
	/**
	 * @return the tarefaSemAgendamento
	 */
	public Boolean getTarefaSemAgendamento() {
		return tarefaSemAgendamento;
	}
	/**
	 * @param tarefaSemAgendamento the tarefaSemAgendamento to set
	 */
	public void setTarefaSemAgendamento(Boolean tarefaSemAgendamento) {
		this.tarefaSemAgendamento = tarefaSemAgendamento;
	}
	/**
	 * @return the idUsuarioLogado
	 */
	public Long getIdUsuarioLogado() {
		return idUsuarioLogado;
	}
	/**
	 * @param idUsuarioLogado the idUsuarioLogado to set
	 */
	public void setIdUsuarioLogado(Long idUsuarioLogado) {
		this.idUsuarioLogado = idUsuarioLogado;
	}
	
	public Long getIdDoador() {
		return idDoador;
	}
	
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}
	
	
	
	

}
