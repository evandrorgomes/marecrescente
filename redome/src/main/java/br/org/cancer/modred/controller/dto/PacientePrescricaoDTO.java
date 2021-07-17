package br.org.cancer.modred.controller.dto;

/**
 * DTO para listar os pacientes com prescrição.
 * 
 * @author bruno.sousa
 *
 */
public class PacientePrescricaoDTO {

	private Long rmr;
	private String nomePaciente;
	private String status;
	private String agingDaTarefa;
	private Long idPrescricao;
	private String identificadorDoador;
	private Long idTipoDoador;
	
	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}
	
	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	
	/**
	 * @return the nomePaciente
	 */
	public String getNomePaciente() {
		return nomePaciente;
	}
	
	/**
	 * @param nomePaciente the nomePaciente to set
	 */
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the agingDaTarefa
	 */
	public String getAgingDaTarefa() {
		return agingDaTarefa;
	}

	/**
	 * @param agingDaTarefa the agingDaTarefa to set
	 */
	public void setAgingDaTarefa(String agingDaTarefa) {
		this.agingDaTarefa = agingDaTarefa;
	}

	/**
	 * @return the idPrescricao
	 */
	public Long getIdPrescricao() {
		return idPrescricao;
	}

	/**
	 * @param idPrescricao the idPrescricao to set
	 */
	public void setIdPrescricao(Long idPrescricao) {
		this.idPrescricao = idPrescricao;
	}

	/**
	 * @return the identificadorDoador
	 */
	public String getIdentificadorDoador() {
		return identificadorDoador;
	}

	/**
	 * @param identificadorDoador the identificadorDoador to set
	 */
	public void setIdentificadorDoador(String identificadorDoador) {
		this.identificadorDoador = identificadorDoador;
	}

	/**
	 * @return the idTipoDoador
	 */
	public Long getIdTipoDoador() {
		return idTipoDoador;
	}

	/**
	 * @param idTipoDoador the idTipoDoador to set
	 */
	public void setIdTipoDoador(Long idTipoDoador) {
		this.idTipoDoador = idTipoDoador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rmr == null) ? 0 : rmr.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PacientePrescricaoDTO other = (PacientePrescricaoDTO) obj;
		if (rmr == null) {
			if (other.rmr != null)
				return false;
		} else if (!rmr.equals(other.rmr))
			return false;
		return true;
	}
	
	
}
