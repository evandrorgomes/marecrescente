package br.org.cancer.modred.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe para armazenamento do histórico de alteração de status 
 * de paciente.
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_HISP_ID", sequenceName = "SQ_HISP_ID", allocationSize = 1)
@Table(name = "HISTORICO_STATUS_PACIENTE")
public class HistoricoStatusPaciente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_HISP_ID")
	@Column(name = "HISP_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "STPA_ID")
	private StatusPaciente status;
	
	@Column(name = "HISP_DT_ALTERACAO")
	private LocalDateTime dataAlteracao;
	
	
	public HistoricoStatusPaciente() {
		// TODO Auto-generated constructor stub
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
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the status
	 */
	public StatusPaciente getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusPaciente status) {
		this.status = status;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		HistoricoStatusPaciente other = (HistoricoStatusPaciente) obj;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null) {
				return false;
			}
		}
		else if (!dataAlteracao.equals(other.dataAlteracao)) {
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
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else if (!paciente.equals(other.paciente)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} 
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}
}
