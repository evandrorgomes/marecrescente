package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.Locus;

/**
 * Informações sobre os dados de mismatch do paciente.
 * 
 * @author brunosousa
 *
 */
public class MismatchDTO {
	
	private Long rmr;
	private Integer aceiteMismatch;
	private List<Locus> locusMismatch;
	
	public MismatchDTO() {
	}

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
	 * @return the aceiteMismatch
	 */
	public Integer getAceiteMismatch() {
		return aceiteMismatch;
	}

	/**
	 * @param aceiteMismatch the aceiteMismatch to set
	 */
	public void setAceiteMismatch(Integer aceiteMismatch) {
		this.aceiteMismatch = aceiteMismatch;
	}

	/**
	 * @return the locusMismatch
	 */
	public List<Locus> getLocusMismatch() {
		return locusMismatch;
	}

	/**
	 * @param locusMismatch the locusMismatch to set
	 */
	public void setLocusMismatch(List<Locus> locusMismatch) {
		this.locusMismatch = locusMismatch;
	}
	
	

}
