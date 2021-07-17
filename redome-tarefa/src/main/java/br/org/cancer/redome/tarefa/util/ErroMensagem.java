package br.org.cancer.redome.tarefa.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe para comunicação dos erros.
 * 
 * @author Cintia Oliveira
 *
 */
public class ErroMensagem {

	private String mensagem;

	@JsonInclude(Include.NON_NULL)
	private List<CampoMensagem> erros;

	/**
	 * Construtor sobrecarregado recebendo uma mensagem.
	 * 
	 * @param mensagem mensagem
	 */
	public ErroMensagem(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	/**
	 * Construtor sobrecarregado recebendo uma mensagem e uma lista de erros.
	 * 
	 * @param mensagem mensagem
	 * @param erros erros
	 */
	public ErroMensagem(String mensagem, List<CampoMensagem> erros) {
		this(mensagem);
		if (erros != null && !erros.isEmpty()) {
			this.erros = new ArrayList<>();
			this.erros.addAll(erros);
		}
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the erros
	 */
	public List<CampoMensagem> getErros() {
		return erros;
	}

	/**
	 * @param erros the erros to set
	 */
	public void setErros(List<CampoMensagem> erros) {
		this.erros = erros;
	}
}
