package br.org.cancer.modred.feign.dto;

import java.io.Serializable;

import javax.persistence.Column;

import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.notificacao.IConfiguracaoCategoriaNotificacao;

/**
 * Classe DTO de categoria de notificacao.
 * 
 * @author ergomes
 * 
 */
public class CategoriaNotificacaoDTO implements Serializable {

	private static final long serialVersionUID = 2558791654833559745L;

	@Column(name = "CANO_ID")
	private Long id;

	@Column(name = "CANO_TX_DESCRICAO")
	private String descricao;

	/**
	 * Construtor default.
	 * @param id
	 */
	public CategoriaNotificacaoDTO() {
		super();
	}

	/**
	 * @param id
	 */
	public CategoriaNotificacaoDTO(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param descricao
	 */
	public CategoriaNotificacaoDTO(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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
	 * Obtém a configuração.
	 * 
	 * @return Configuração.
	 */
	public IConfiguracaoCategoriaNotificacao getConfiguracao() {
		if (this.id != null) {
			return CategoriasNotificacao.get(this.id).getConfiguracao();
		}
		return null;		
	}


}