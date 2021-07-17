package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;

/**
 * Classe que representa o tipo do formulário.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "TIPO_FORMULARIO")
public class TipoFormulario implements Serializable {

	private static final long serialVersionUID = 1926285985170121365L;

	@Id
	@Column(name = "TIFO_ID")
	@JsonView(value = { FormularioView.Formulario.class})
	private Long id;

	@Column(name = "TIFO_TX_DESCRICAO")
	@JsonView(value = { FormularioView.Formulario.class})
	private String descricao;
	
	@Column(name = "TIFO_IN_NACIONAL")
	private Boolean nacional;
	
	@Column(name = "TIFO_IN_INTERNACIONAL")
	private Boolean internacional;

	/**
	 * Construtor padrão.
	 */
	public TipoFormulario() {
		super();
	}

	/**
	 * Construtor sobrecarregado com id.
	 * 
	 * @param id
	 */
	public TipoFormulario(Long id) {
		this();
		this.id = id;
	}

	/**
	 * Construtor sobrecarregado com id e descrição.
	 * 
	 * @param id
	 * @param descricao
	 */
	public TipoFormulario(Long id, String descricao) {
		this(id);
		this.descricao = descricao;
	}

	/**
	 * Retorna a chave primaria do tipo do formulário.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo do formulário.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do tipo do fomulário.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do tipo do formulário.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	/**
	 * Informa se o tipo de formulário é nacional.
	 * 
	 * @return the nacional
	 */
	public Boolean getNacional() {
		return nacional;
	}

	
	/**
	 * Informa se o tipo de formulário é nacional.
	 * 
	 * @param nacional the nacional to set
	 */
	public void setNacional(Boolean nacional) {
		this.nacional = nacional;
	}

	
	/**
	 * Informa se o tipo de formulário é internacional.
	 * 
	 * @return the internacional
	 */
	public Boolean getInternacional() {
		return internacional;
	}

	
	/**
	 * Informa se o tipo de formulário é internacional.
	 * 
	 * @param internacional the internacional to set
	 */
	public void setInternacional(Boolean internacional) {
		this.internacional = internacional;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( internacional == null ) ? 0 : internacional.hashCode() );
		result = prime * result + ( ( nacional == null ) ? 0 : nacional.hashCode() );
		return result;
	}

	/* (non-Javadoc)
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
		if (!( obj instanceof TipoFormulario )) {
			return false;
		}
		TipoFormulario other = (TipoFormulario) obj;
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
		if (internacional == null) {
			if (other.internacional != null) {
				return false;
			}
		}
		else
			if (!internacional.equals(other.internacional)) {
				return false;
			}
		if (nacional == null) {
			if (other.nacional != null) {
				return false;
			}
		}
		else
			if (!nacional.equals(other.nacional)) {
				return false;
			}
		return true;
	}
	
}