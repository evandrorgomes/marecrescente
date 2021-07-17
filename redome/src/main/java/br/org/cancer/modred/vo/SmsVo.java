package br.org.cancer.modred.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.modred.gateway.sms.StatusSms;

/**
 * Vo utilizado para a consulta de envio de sms.
 * 
 * @author brunosousa
 *
 */
public class SmsVo implements Serializable {

	private static final long serialVersionUID = 3271656738281152100L;
	
	private LocalDateTime data;
	
	private Long dmr;
	
	private String telefone;
	
	private StatusSms status;

	/**
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the status
	 */
	public StatusSms getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusSms status) {
		this.status = status;
	}
	
}
