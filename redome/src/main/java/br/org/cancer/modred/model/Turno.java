package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Entidade de persistencia de turno.
 * @author Filipe Paes
 */
@Entity
public class Turno implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Identificação da tabela de turno.
	 */
	@Id
	@Column(name="TURN_ID")
	private Long id;


	/**
	 * Hora incio do turno.
	 */
	@Column(name="TURN_DT_HR_INICIO")
	private LocalDateTime horaInicio;

	/**
	 * Hora fim do turno.
	 */
	@Column(name="TURN_DT_HR_FIM")
	private LocalDateTime horaFim;
	
	/**
	 * Descrição do turno.
	 */
	@Column(name="TURN_TX_DESCRICAO")
	private String descricao;
	
	/**
	 * Define se a fatia de horário é inclusiva ou exclusiva.
	 */
	@Column(name = "TURN_IN_INCLUSIVO_EXCLUSIVO")
	private Boolean inclusivoExclusivo;
	

	public Turno() {
	}

	public Turno(Long id) {
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
	 * @return the horaInicio
	 */
	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the horaFim
	 */
	public LocalDateTime getHoraFim() {
		return horaFim;
	}

	/**
	 * @param horaFim the horaFim to set
	 */
	public void setHoraFim(LocalDateTime horaFim) {
		this.horaFim = horaFim;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	/**
	 * @return the inclusivoExclusivo
	 */
	public Boolean getInclusivoExclusivo() {
		return inclusivoExclusivo;
	}
	
	/**
	 * @param inclusivoExclusivo the inclusivoExclusivo to set
	 */
	public void setInclusivoExclusivo(Boolean inclusivoExclusivo) {
		this.inclusivoExclusivo = inclusivoExclusivo;
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
		Turno other = (Turno) obj;
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