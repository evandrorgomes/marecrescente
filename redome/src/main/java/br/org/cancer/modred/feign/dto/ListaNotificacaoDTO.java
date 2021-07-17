package br.org.cancer.modred.feign.dto;

import java.util.List;

import org.springframework.data.domain.Pageable;

public class ListaNotificacaoDTO {

	private Long idNotificacao;
	private Long idCategoriaNotificacao; 
	private List<Long> parceiros; 
	private Boolean lidas; 
	private Long rmr; 
	private Long idUsuarioLogado; 
	private Boolean somentePacientesDoUsuario; 
	private Pageable pageable;
	/**
	 * @return the idNotificacao
	 */
	public Long getIdNotificacao() {
		return idNotificacao;
	}
	/**
	 * @param idNotificacao the idNotificacao to set
	 */
	public void setIdNotificacao(Long idNotificacao) {
		this.idNotificacao = idNotificacao;
	}
	/**
	 * @return the idCategoriaNotificacao
	 */
	public Long getIdCategoriaNotificacao() {
		return idCategoriaNotificacao;
	}
	/**
	 * @param idCategoriaNotificacao the idCategoriaNotificacao to set
	 */
	public void setIdCategoriaNotificacao(Long idCategoriaNotificacao) {
		this.idCategoriaNotificacao = idCategoriaNotificacao;
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
	 * @return the lidas
	 */
	public Boolean getLidas() {
		return lidas;
	}
	/**
	 * @param lidas the lidas to set
	 */
	public void setLidas(Boolean lidas) {
		this.lidas = lidas;
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
	/**
	 * @return the somentePacientesDoUsuario
	 */
	public Boolean getSomentePacientesDoUsuario() {
		return somentePacientesDoUsuario;
	}
	/**
	 * @param somentePacientesDoUsuario the somentePacientesDoUsuario to set
	 */
	public void setSomentePacientesDoUsuario(Boolean somentePacientesDoUsuario) {
		this.somentePacientesDoUsuario = somentePacientesDoUsuario;
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
	
	
}
