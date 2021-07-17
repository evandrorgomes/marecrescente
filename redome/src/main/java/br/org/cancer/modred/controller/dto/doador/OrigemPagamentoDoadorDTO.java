package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe referente aos parametros necessários para a finalização do agendamento de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public class OrigemPagamentoDoadorDTO implements Serializable{

	private static final long serialVersionUID = 7580603026363839376L;
	
	private Long idRegistroOrigem;
	private Long idRegistroPagamento;
	private Long idDoador;

	/**
	 * 
	 */
	public OrigemPagamentoDoadorDTO() {
		super();
	}
	/**
	 * @return the idRegistroOrigem
	 */
	public Long getIdRegistroOrigem() {
		return idRegistroOrigem;
	}
	/**
	 * @param idRegistroOrigem the idRegistroOrigem to set
	 */
	public void setIdRegistroOrigem(Long idRegistroOrigem) {
		this.idRegistroOrigem = idRegistroOrigem;
	}
	/**
	 * @return the idRegistroPagamento
	 */
	public Long getIdRegistroPagamento() {
		return idRegistroPagamento;
	}
	/**
	 * @param idRegistroPagamento the idRegistroPagamento to set
	 */
	public void setIdRegistroPagamento(Long idRegistroPagamento) {
		this.idRegistroPagamento = idRegistroPagamento;
	}
	/**
	 * @return the idDoador
	 */
	public Long getIdDoador() {
		return idDoador;
	}
	/**
	 * @param idDoador the idDoador to set
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}

	
}
