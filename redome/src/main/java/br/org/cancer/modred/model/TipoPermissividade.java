package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa o tipo de permissividade.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "TIPO_PERMISSIVIDADE")
public class TipoPermissividade implements Serializable {

	private static final long serialVersionUID = 4171848777094330045L;

	@Id
	@Column(name = "TIPM_ID")
	private Long id;

	@Column(name = "TIPM_TX_DESCRICAO")
	private String descricao;

	public TipoPermissividade() {
		super();
	}

	public TipoPermissividade(Long id) {
		this();
		this.id = id;
	}

	public TipoPermissividade(Long id, String descricao) {
		this(id);
		this.descricao = descricao;
	}

	/**
	 * Retorna a chave primaria do tipo da pendencia.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo da pendencia.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do tipo de pendencia.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do tipo de pendencia.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + (int) ( id ^ ( id >>> 32 ) );
		return result;
	}

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
		TipoPermissividade other = (TipoPermissividade) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id != other.id) {
			return false;
		}
		return true;
	}

}