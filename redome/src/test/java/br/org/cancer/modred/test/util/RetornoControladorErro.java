package br.org.cancer.modred.test.util;

import java.util.ArrayList;
import java.util.stream.Collectors;

import br.org.cancer.modred.util.CampoMensagem;

/**
 * Retorno de erro do controlador.
 * 
 * @author Filipe Paes
 *
 */
public class RetornoControladorErro {
	private String mensagem;
	private ArrayList<CampoMensagem> erros;

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem
	 *            the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the erros
	 */
	public ArrayList<CampoMensagem> getErros() {
		return erros;
	}

	/**
	 * @param erros
	 *            the erros to set
	 */
	public void setErros(ArrayList<CampoMensagem> erros) {
		this.erros = erros;
	}

	@Override
	public String toString() {
		return "{mesangem: " + mensagem + ", Erros: [" + (erros != null ? erros.stream().map(CampoMensagem::toString).collect(Collectors.joining(",")) : "") + "]}" ;
	}
}
