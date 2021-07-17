package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

/**
 * Dto para confirmar uma logistica de transporte realizado por um usuário de uma transportadora.
 * 
 * @author Fillipe Queiroz
 *
 */
public class ConfirmacaoTransporteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4449354356416831382L;
	private Long idCourier;
	private String dadosVoo;
	private boolean voo;

	/**
	 * @return the idCourier
	 */
	public Long getIdCourier() {
		return idCourier;
	}

	/**
	 * @param idCourier the idCourier to set
	 */
	public void setIdCourier(Long idCourier) {
		this.idCourier = idCourier;
	}

	/**
	 * @return the dadosVoo
	 */
	public String getDadosVoo() {
		return dadosVoo;
	}

	/**
	 * @param dadosVoo the dadosVoo to set
	 */
	public void setDadosVoo(String dadosVoo) {
		this.dadosVoo = dadosVoo;
	}

	/**
	 * @return the voo
	 */
	public boolean isVoo() {
		return voo;
	}

	/**
	 * @param voo the voo to set
	 */
	public void setVoo(boolean voo) {
		this.voo = voo;
	}

}
