package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.model.formulario.Pagina;
import br.org.cancer.modred.model.formulario.Pergunta;

/**
 * Classe que representa o formulário.  
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "FORMULARIO")
@SequenceGenerator(name = "SQ_FORM_ID", sequenceName = "SQ_FORM_ID", allocationSize = 1)
public class Formulario implements Serializable {

    private static final long serialVersionUID = -3749821347178713784L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FORM_ID")
    @Column(name = "FORM_ID")
    @JsonView(value = { FormularioView.Formulario.class })
    private Long id;
    
    @ManyToOne
	@JoinColumn(name = "TIFO_ID")
	@NotNull
	@JsonView(value = { FormularioView.Formulario.class })
    private TipoFormulario tipoFormulario;
    
    @Column(name = "FORM_IN_ATIVO")
    private Boolean ativo;
    
    @Lob
    @Column(name = "FORM_TX_FORMATO")
    private String formato;
    
    @Transient
    @JsonView(value = { FormularioView.Formulario.class })
    private List<Pergunta> perguntas;

    @Transient
    @JsonView(value = { FormularioView.Formulario.class })
    private List<Pagina> paginas;
    
    @Transient
    @JsonView(value = { FormularioView.Formulario.class })
    private boolean comValidacao;

    @Transient
    private boolean comErro = false;
    
    public Formulario() {
    	
    }
	
	/**
	 * Identificador do formulário.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	
	/**
	 * Identificador do formulário.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * Tipo do Formulário.
	 * 
	 * @return the tipoFormulario
	 */
	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	
	/**
	 * Tipo do Formulário.
	 * 
	 * @param tipoFormulario the tipoFormulario to set
	 */
	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	
	/**
	 * Ativo.
	 * 
	 * @return the ativo
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	
	/**
	 * Ativo.
	 * 
	 * @param ativo the ativo to set
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	
	/**
	 * Formato - Contem o json das perguntas.
	 * 
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	
	/**
	 * Formato - Contem o json das perguntas.
	 * 
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	/**
	 * Lista de Perguntas do formulário. 
	 * 
	 * @return the perguntas
	 */
	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	/**
	 * Lista de Perguntas do formulário.
	 * 
	 * @param perguntas the perguntas to set
	 */
	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}


	/**
	 * @return the comValidacao
	 */
	public boolean isComValidacao() {
		return comValidacao;
	}


	/**
	 * @param comValidacao the comValidacao to set
	 */
	public void setComValidacao(boolean comValidacao) {
		this.comValidacao = comValidacao;
	}

	/**
	 * @return the paginas
	 */
	public List<Pagina> getPaginas() {
		return paginas;
	}

	/**
	 * @param paginas the paginas to set
	 */
	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}
	
	/**
	 * @return the comErro
	 */
	public boolean isComErro() {
		return comErro;
	}

	/**
	 * @param comErro the comErro to set
	 */
	public void setComErro(boolean comErro) {
		this.comErro = comErro;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( ativo == null ) ? 0 : ativo.hashCode() );
		result = prime * result + ( ( formato == null ) ? 0 : formato.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( tipoFormulario == null ) ? 0 : tipoFormulario.hashCode() );
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
		if (!( obj instanceof Formulario )) {
			return false;
		}
		Formulario other = (Formulario) obj;
		if (ativo == null) {
			if (other.ativo != null) {
				return false;
			}
		}
		else
			if (!ativo.equals(other.ativo)) {
				return false;
			}
		if (formato == null) {
			if (other.formato != null) {
				return false;
			}
		}
		else
			if (!formato.equals(other.formato)) {
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
		if (tipoFormulario == null) {
			if (other.tipoFormulario != null) {
				return false;
			}
		}
		else
			if (!tipoFormulario.equals(other.tipoFormulario)) {
				return false;
			}
		return true;
	}
    
}