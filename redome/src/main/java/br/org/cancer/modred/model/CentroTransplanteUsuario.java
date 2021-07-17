package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.model.security.Usuario;


/**
 * Classe de associação entre CentroTransplantador e Usuario.
 * @author Filipe Paes
 *
 */
@Entity
@Table(name="CENTRO_TRANSPLANTE_USUARIO")
@SequenceGenerator(name = "SQ_CETU_ID", sequenceName = "SQ_CETU_ID", allocationSize = 1)
public class CentroTransplanteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CETU_ID")
	@Column(name="CETU_ID")
	private Long id;
	
	@Column(name="CETU_IN_RESPONSAVEL")
	private Boolean responsavel;
	
	
	@ManyToOne
	@JoinColumn(name="CETR_ID")
	@JsonProperty(access=Access.WRITE_ONLY)
	private CentroTransplante centroTransplante;
	
	
	@ManyToOne
	@JoinColumn(name="USUA_ID")
	@JsonView(CentroTransplanteView.Detalhe.class)
	private Usuario usuario;


	

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
	 * @return the responsavel
	 */
	public Boolean getResponsavel() {
		return responsavel;
	}


	/**
	 * @param responsavel the responsavel to set
	 */
	public void setResponsavel(Boolean responsavel) {
		this.responsavel = responsavel;
	}


	/**
	 * @return the centroTransplante
	 */
	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}


	/**
	 * @param centroTransplante the centroTransplante to set
	 */
	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}


	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centroTransplante == null) ? 0 : centroTransplante.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		CentroTransplanteUsuario other = (CentroTransplanteUsuario) obj;
		if (centroTransplante == null) {
			if (other.centroTransplante != null) {
				return false;
			}
		} 
		else if (!centroTransplante.equals(other.centroTransplante)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (responsavel == null) {
			if (other.responsavel != null) {
				return false;
			}
		} 
		else if (!responsavel.equals(other.responsavel)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} 
		else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}


	
}