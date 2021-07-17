package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Solicitacao;

/**
 * Contato dto.
 * 
 * @author Bruno
 *
 */
public class ContatoDTO implements Serializable {

	private static final long serialVersionUID = -485305659577314244L;

	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
	private Doador doador;

	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
	private Solicitacao solicitacao;

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	/**
	 * @return the solicitacao
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao the solicitacao to set
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

}
