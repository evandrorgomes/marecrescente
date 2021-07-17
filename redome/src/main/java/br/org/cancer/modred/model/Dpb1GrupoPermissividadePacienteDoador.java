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
import javax.validation.constraints.NotNull;

/**
 * Classe que representa o cruzamento dos grupos de permissividade de dpb1  para paciente e doador.
 * 
 * @author bruno.sousa
 */
@Entity
@SequenceGenerator(name = "SQ_DGPD_ID", sequenceName = "SQ_DGPD_ID", allocationSize = 1)
@Table(name = "DPB1_GRUPO_PERMIS_PACIE_DOADOR")
public class Dpb1GrupoPermissividadePacienteDoador implements Serializable {

	private static final long serialVersionUID = -6980587314322883679L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DGPD_ID")
	@Column(name = "DGPD_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "GRPE_ID_PACIENTE_ALELO1")
	@NotNull
	private GrupoPermissividade grupoPermissividadePacienteAlelo1;
	
	@ManyToOne
	@JoinColumn(name = "GRPE_ID_PACIENTE_ALELO2")
	@NotNull
	private GrupoPermissividade grupoPermissividadePacienteAlelo2;
	
	@ManyToOne
	@JoinColumn(name = "GRPE_ID_DOADOR_ALELO1")
	@NotNull
	private GrupoPermissividade grupoPermissividadeDoadorAlelo1;
	
	@ManyToOne
	@JoinColumn(name = "GRPE_ID_DOADOR_ALELO2")
	@NotNull
	private GrupoPermissividade grupoPermissividadeDoadorAlelo2;
	
	@ManyToOne
	@JoinColumn(name = "TIPM_ID")
	@NotNull
	private TipoPermissividade tipoPermissividade;
	
	
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
	 * @return the grupoPermissividadePacienteAlelo1
	 */
	public GrupoPermissividade getGrupoPermissividadePacienteAlelo1() {
		return grupoPermissividadePacienteAlelo1;
	}

	/**
	 * @param grupoPermissividadePacienteAlelo1 the grupoPermissividadePacienteAlelo1 to set
	 */
	public void setGrupoPermissividadePacienteAlelo1(
			GrupoPermissividade grupoPermissividadePacienteAlelo1) {
		this.grupoPermissividadePacienteAlelo1 = grupoPermissividadePacienteAlelo1;
	}

	/**
	 * @return the grupoPermissividadePacienteAlelo2
	 */
	public GrupoPermissividade getGrupoPermissividadePacienteAlelo2() {
		return grupoPermissividadePacienteAlelo2;
	}

	/**
	 * @param grupoPermissividadePacienteAlelo2 the grupoPermissividadePacienteAlelo2 to set
	 */
	public void setGrupoPermissividadePacienteAlelo2(
			GrupoPermissividade grupoPermissividadePacienteAlelo2) {
		this.grupoPermissividadePacienteAlelo2 = grupoPermissividadePacienteAlelo2;
	}

	/**
	 * @return the grupoPermissividadeDoadorAlelo1
	 */
	public GrupoPermissividade getGrupoPermissividadeDoadorAlelo1() {
		return grupoPermissividadeDoadorAlelo1;
	}

	/**
	 * @param grupoPermissividadeDoadorAlelo1 the grupoPermissividadeDoadorAlelo1 to set
	 */
	public void setGrupoPermissividadeDoadorAlelo1(GrupoPermissividade grupoPermissividadeDoadorAlelo1) {
		this.grupoPermissividadeDoadorAlelo1 = grupoPermissividadeDoadorAlelo1;
	}

	/**
	 * @return the grupoPermissividadeDoadorAlelo2
	 */
	public GrupoPermissividade getGrupoPermissividadeDoadorAlelo2() {
		return grupoPermissividadeDoadorAlelo2;
	}

	/**
	 * @param grupoPermissividadeDoadorAlelo2 the grupoPermissividadeDoadorAlelo2 to set
	 */
	public void setGrupoPermissividadeDoadorAlelo2(GrupoPermissividade grupoPermissividadeDoadorAlelo2) {
		this.grupoPermissividadeDoadorAlelo2 = grupoPermissividadeDoadorAlelo2;
	}

	/**
	 * @return the tipoPermissividade
	 */
	public TipoPermissividade getTipoPermissividade() {
		return tipoPermissividade;
	}

	/**
	 * @param tipoPermissividade the tipoPermissividade to set
	 */
	public void setTipoPermissividade(TipoPermissividade tipoPermissividade) {
		this.tipoPermissividade = tipoPermissividade;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((grupoPermissividadeDoadorAlelo1 == null) ? 0 : grupoPermissividadeDoadorAlelo1.hashCode());
		result = prime * result
				+ ((grupoPermissividadeDoadorAlelo2 == null) ? 0 : grupoPermissividadeDoadorAlelo2.hashCode());
		result = prime * result
				+ ((grupoPermissividadePacienteAlelo1 == null) ? 0 : grupoPermissividadePacienteAlelo1.hashCode());
		result = prime * result
				+ ((grupoPermissividadePacienteAlelo2 == null) ? 0 : grupoPermissividadePacienteAlelo2.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Dpb1GrupoPermissividadePacienteDoador other = (Dpb1GrupoPermissividadePacienteDoador) obj;
		if (grupoPermissividadeDoadorAlelo1 == null) {
			if (other.grupoPermissividadeDoadorAlelo1 != null) {
				return false;
			}
		} 
		else if (!grupoPermissividadeDoadorAlelo1.equals(other.grupoPermissividadeDoadorAlelo1)) {
			return false;
		}
		if (grupoPermissividadeDoadorAlelo2 == null) {
			if (other.grupoPermissividadeDoadorAlelo2 != null) {
				return false;
			}
		} 
		else if (!grupoPermissividadeDoadorAlelo2.equals(other.grupoPermissividadeDoadorAlelo2)) {
			return false;
		}
		if (grupoPermissividadePacienteAlelo1 == null) {
			if (other.grupoPermissividadePacienteAlelo1 != null) {
				return false;
			}
		} 
		else if (!grupoPermissividadePacienteAlelo1.equals(other.grupoPermissividadePacienteAlelo1)) {
			return false;
		}
		if (grupoPermissividadePacienteAlelo2 == null) {
			if (other.grupoPermissividadePacienteAlelo2 != null) {
				return false;
			}
		} 
		else if (!grupoPermissividadePacienteAlelo2.equals(other.grupoPermissividadePacienteAlelo2)) {
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
		return true;
	}

	
}