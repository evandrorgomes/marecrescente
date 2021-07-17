package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe para controle de acessos ao serviço de disponibilização de cordões do
 * sistema BrasilCord.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "CONTROLE_JOB_BRASILCORD")
@SequenceGenerator(name = "SQ_NR_ID", sequenceName = "SQ_COJB_ID", allocationSize = 1)
public class ControleJobBrasilcord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NR_ID")
	@Column(name = "COJB_ID")
	private Long id;

	@Column(name = "COJB_DT_SINCRONIZACAO")
	private LocalDateTime dataAcesso;

	@Column(name = "COJB_IN_SUCESSO")
	private Boolean sucesso;

	public ControleJobBrasilcord() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataAcesso
	 */
	public LocalDateTime getDataAcesso() {
		return dataAcesso;
	}

	/**
	 * @param dataAcesso
	 *            the dataAcesso to set
	 */
	public void setDataAcesso(LocalDateTime dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	/**
	 * @return the sucesso
	 */
	public Boolean getSucesso() {
		return sucesso;
	}

	/**
	 * @param sucesso
	 *            the sucesso to set
	 */
	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
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
		result = prime * result + ((dataAcesso == null) ? 0 : dataAcesso.hashCode());
		result = prime * result + ((sucesso == null) ? 0 : sucesso.hashCode());
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
		ControleJobBrasilcord other = (ControleJobBrasilcord) obj;
		if (dataAcesso == null) {
			if (other.dataAcesso != null) {
				return false;
			}
		} 
		else if (!dataAcesso.equals(other.dataAcesso)) {
			return false;
		}
		if (sucesso == null) {
			if (other.sucesso != null) {
				return false;
			}
		} 
		else if (!sucesso.equals(other.sucesso)) {
			return false;
		}
		return true;
	}

}