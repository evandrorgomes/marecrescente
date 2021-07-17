package br.org.cancer.modred.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.util.EnumUtil;

/**
 * Agrupador de valores de genótipo. Esta classe é responsável por unir todos os resultados de exame para cada locus.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "GENOTIPO_DOADOR")
@SequenceGenerator(name = "SQ_GEDO_ID", sequenceName = "SQ_GEDO_ID", allocationSize = 1)
public class GenotipoDoador implements IGenotipo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GEDO_ID")
	@Column(name = "GEDO_ID")
	private Long id;

	@Column(name = "GEDO_DT_ALTERACAO")
	private LocalDateTime dataAlteracao;

	@OneToMany(mappedBy = "genotipo")
	@JsonView({ PacienteView.Detalhe.class})
	private List<ValorGenotipoDoador> valores;

	@Column(name = "GEDO_IN_EXCLUIDO")
	private Boolean excluido = false;

	@NotNull
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DOAD_ID")
	private Doador doador;

	@NotNull
	@EnumValues(TiposDoador.class)
	@Column(name = "GEDO_IN_TIPO_DOADOR")
	private Long tipoDoador;
	
	
	public GenotipoDoador() {
		super();
	}

	public GenotipoDoador(Long id) {
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
	 * @return the dataAlteracao
	 */
	public LocalDateTime getDataAlteracao() {
		return dataAlteracao;
	}

	/**
	 * @param dataAlteracao the dataAlteracao to set
	 */
	public void setDataAlteracao(LocalDateTime dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}


	
	/**
	 * @return the valores
	 */
	public List<ValorGenotipoDoador> getValores() {
		return valores;
	}

	
	/**
	 * @param valores the valores to set
	 */
	public void setValores(List<? extends IValorGenotipo> valores) {
		this.valores = (List<ValorGenotipoDoador>) valores;
	}
	
	@Override
	public IProprietarioHla getProprietario() {
		return getDoador();
	}

	@Override
	public void setProprietario(IProprietarioHla proprietario) {
		setDoador((Doador) proprietario);
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
		result = prime * result + ( ( dataAlteracao == null ) ? 0 : dataAlteracao.hashCode() );
		result = prime * result + ( ( excluido == null ) ? 0 : excluido.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( valores == null ) ? 0 : valores.hashCode() );
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
		GenotipoDoador other = (GenotipoDoador) obj;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null) {
				return false;
			}
		}
		else
			if (!dataAlteracao.equals(other.dataAlteracao)) {
				return false;
			}
		if (excluido == null) {
			if (other.excluido != null) {
				return false;
			}
		}
		else
			if (!excluido.equals(other.excluido)) {
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
		if (valores == null) {
			if (other.valores != null) {
				return false;
			}
		}
		else
			if (!valores.equals(other.valores)) {
				return false;
			}
		return true;
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


	
	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	
	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	
	/**
	 * @return the tipoDoador
	 */
	public TiposDoador getTipoDoador() {
		if (tipoDoador != null) {
			return (TiposDoador) EnumUtil.valueOf(TiposDoador.class, tipoDoador);
		}
		return null;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(TiposDoador tipoDoador) {
		if (tipoDoador != null) {
			this.tipoDoador = tipoDoador.getId();
		}

	}	

}