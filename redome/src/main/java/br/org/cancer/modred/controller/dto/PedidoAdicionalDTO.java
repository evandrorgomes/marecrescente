package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;

import br.org.cancer.modred.util.DateUtils;

/**
 * Lista de respostas adicionais associados a um determinado pedido adicional, que compõem o histórico associado ao pedido de
 * workup.
 * 
 * @author Pizão
 *
 */
public class PedidoAdicionalDTO {

	/**
	 * Data de quando o pedido adicional foi realizado.
	 */
	private LocalDateTime data;

	/**
	 * Texto que descreve o pedido.
	 */
	private String texto;

	/**
	 * Usuário responsável pelo pedido.
	 */
	private String username;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getDataFormatadaDialogo() {
		return DateUtils.getDataFormatadaComHoraEMinutos(data);

	}

}
