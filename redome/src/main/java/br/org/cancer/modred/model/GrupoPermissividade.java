package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa o grupo de permissividade.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "GRUPO_PERMISSIVIDADE")
public class GrupoPermissividade implements Serializable {

	private static final long serialVersionUID = -6980587314322883679L;
	
	@Id
	@Column(name = "GRPE_ID")
	private Long id;

	public GrupoPermissividade() {
		super();
	}

	public GrupoPermissividade(Long id) {
		this();
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
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
		GrupoPermissividade other = (GrupoPermissividade) obj;		
		if (id != other.id) {
			return false;
		}
		return true;
	}

}