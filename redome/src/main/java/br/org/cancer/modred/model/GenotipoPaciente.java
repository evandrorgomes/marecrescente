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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Agrupador de valores de genótipo. Esta classe é responsável por unir todos os resultados de exame para cada locus.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "GENOTIPO_PACIENTE")
@SequenceGenerator(name = "SQ_GEPA_ID", sequenceName = "SQ_GEPA_ID", allocationSize = 1)
public class GenotipoPaciente implements IGenotipo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GEPA_ID")
	@Column(name = "GEPA_ID")
	private Long id;

	@Column(name = "GEPA_DT_ALTERACAO")
	private LocalDateTime dataAlteracao;

	@OneToMany(mappedBy = "genotipo", fetch = FetchType.EAGER)
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class})
	private List<ValorGenotipoPaciente> valores;

	@Column(name = "GEPA_IN_EXCLUIDO")
	private Boolean excluido = false;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PACI_NR_RMR")
	private Paciente paciente;

	
	
	public GenotipoPaciente() {
		super();
	}

	public GenotipoPaciente(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
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
	@Override
	public List<ValorGenotipoPaciente> getValores() {
		return valores;
	}

	/**
	 * @param valores the valores to set
	 */
	@Override
	public void setValores(List<? extends IValorGenotipo> valores) {
		this.valores = (List<ValorGenotipoPaciente>) valores;
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
		GenotipoPaciente other = (GenotipoPaciente) obj;
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
	 * Retorna o paciente associado ao genótipo.
	 * 
	 * @return paciente associado ao genótipo.
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public IProprietarioHla getProprietario() {
		return getPaciente();
	}

	@Override
	public void setProprietario(IProprietarioHla proprietario) {
		setPaciente((Paciente) proprietario);
	}
	
	@Override
	public TiposDoador getTipoDoador() {
		return null;
	}

}