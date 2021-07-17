package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.notificacao.IConfiguracaoCategoriaNotificacao;

/**
 * Classe de persistencia de categoria de notificacao.
 * 
 * @author Fillipe Queiroz
 * 
 */
@Entity
@Table(name = "CATEGORIA_NOTIFICACAO")
public class CategoriaNotificacao implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5101070961572548179L;

	@Id
	@Column(name = "CANO_ID")
	private Long id;

	@Column(name = "CANO_TX_DESCRICAO")
	private String descricao;
	

	/**
	 * Construtor default.
	 * @param id
	 */
	public CategoriaNotificacao() {
		super();
	}
	

	/**
	 * Construtor com parametro id.
	 * @param id
	 */
	public CategoriaNotificacao(Long id) {
		super();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CategoriaNotificacao other = (CategoriaNotificacao) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
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