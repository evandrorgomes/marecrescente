package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.RelatorioView;

/**
 * Classe que representa um arquivo de exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_RELA_ID", sequenceName = "SQ_RELA_ID", allocationSize = 1)
@Table(name = "RELATORIO")
public class Relatorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4604298602466576125L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RELA_ID")
	@Column(name = "RELA_ID")
	@JsonView(RelatorioView.Consulta.class)
	private Long id;

	@Column(name = "RELA_TX_HTML")
	@NotNull
	@Lob
	@JsonView(RelatorioView.Consulta.class)
	private String html;

	@Column(name = "RELA_TX_CODIGO")
	@NotNull
	@JsonView(RelatorioView.Consulta.class)
	private String codigo;

	@Column(name = "RELA_IN_TIPO")
	@NotNull
	@JsonView(RelatorioView.Consulta.class)
	private Long tipo;

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
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Long tipo) {
		this.tipo = tipo;
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
		result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
		result = prime * result + ( ( html == null ) ? 0 : html.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( tipo == null ) ? 0 : tipo.hashCode() );
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
		Relatorio other = (Relatorio) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		}
		else
			if (!codigo.equals(other.codigo)) {
				return false;
			}
		if (html == null) {
			if (other.html != null) {
				return false;
			}
		}
		else
			if (!html.equals(other.html)) {
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
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		}
		else
			if (!tipo.equals(other.tipo)) {
				return false;
			}
		return true;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
