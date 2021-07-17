package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe referente aos parametros necessários para a finalização do agendamento de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public class RessalvaDoadorDTO implements Serializable{

	private static final long serialVersionUID = 1936086889276503402L;
	
	private Long id;
	private Long idDoador;
	private String observacao;
	private Usuario usuarioResponsavel;
	private Boolean excluido = false;
	
	/**
	 * @param idDoador
	 * @param observacao
	 */
	public RessalvaDoadorDTO(Long idDoador, String observacao) {
		super();
		this.idDoador = idDoador;
		this.observacao = observacao;
	}

	/**
	 * 
	 */
	public RessalvaDoadorDTO() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the usuarioResponsavel
	 */
	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * @param usuarioResponsavel the usuarioResponsavel to set
	 */
	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	/**
	 * @return the excluido
	 */
	public Boolean getExcluido() {
		return excluido;
	}

	/**
	 * @param excluido the excluido to set
	 */
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}
	
	
}
