package br.org.cancer.modred.feign.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe que define os atributos contidos na entidade
 * que representa uma notificação associado a um determinado
 * paciente enviado para um determinado usuário.
 * 
 * @author ergomes
 * 
 */
public class NotificacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1498608486981255830L;

	private Long id;
	private String descricao;
	private Long categoriaId;
	private Long rmr;
	private Boolean lido;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataLeitura;	
	private Long parceiro;
	private String nomeClasseParceiro;
	private Long usuarioId;
	private Long totalNotificacoes;
	private Long idPerfil;

	public NotificacaoDTO() {
		super();
	}

	public NotificacaoDTO(Long id) {
		this.id = id;		
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	 * @return the dataLeitura
	 */
	public LocalDateTime getDataLeitura() {
		return dataLeitura;
	}

	/**
	 * @param dataLeitura the dataLeitura to set
	 */
	public void setDataLeitura(LocalDateTime dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	/**
	 * @return the parceiro
	 */
	public Long getParceiro() {
		return parceiro;
	}

	/**
	 * @param parceiro the parceiro to set
	 */
	public void setParceiro(Long parceiro) {
		this.parceiro = parceiro;
	}
	
	public String getNomeClasseParceiro() {
		return nomeClasseParceiro;
	}

	public void setNomeClasseParceiro(String nomeClasseParceiro) {
		this.nomeClasseParceiro = nomeClasseParceiro;
	}

	/**
	 * @return the usuarioId
	 */
	public Long getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the totalNotificacoes
	 */
	public Long getTotalNotificacoes() {
		return totalNotificacoes;
	}

	/**
	 * @param totalNotificacoes the totalNotificacoes to set
	 */
	public void setTotalNotificacoes(Long totalNotificacoes) {
		this.totalNotificacoes = totalNotificacoes;
	}

	/**
	 * @return the idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the categoriaId
	 */
	public Long getCategoriaId() {
		return categoriaId;
	}

	/**
	 * @param categoriaId the categoriaId to set
	 */
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	/**
	 * @return the lido
	 */
	public Boolean getLido() {
		return lido;
	}

	/**
	 * @param lido the lido to set
	 */
	public void setLido(Boolean lido) {
		this.lido = lido;
	}
	
	public NotificacaoDTO parse(NotificacaoDTO notificacao) {
		return new NotificacaoDTO();
	}
}