package br.org.cancer.redome.notificacao.util;

import lombok.Data;

/**
 * Classe utilitária com o campo e o tipo de validacao.
 * 
 * @author ergomes
 *
 */
@Data
public class CampoMensagem {

	/**
	 * Campo a ser validado.
	 */
	private String campo;
	/**
	 * Mensagem a ser exibida.
	 */
	private String mensagem;

	private Object object;
	
	public CampoMensagem() {
		this.campo = "";
		this.mensagem = "";
	}

	/**
	 * Construtor sobrecarregado para aceitar apenas a mensagem, quando não se tem a referência do campo.
	 * 
	 * @param mensagem mensagem
	 */
	public CampoMensagem(String mensagem) {
		// FIXME: Campo pode ser nulo?
		this();
		this.campo = "";
		this.mensagem = mensagem;
	}
	
	/**
	 * Construtor sobrecarregado para aceitar apenas a mensagem, quando não se tem a referência do campo.
	 * 
	 * @param mensagem mensagem
	 * @param campo - campo a ser exibida a validação
	 * @param object - objeto a ser retornado
	 */
	public CampoMensagem(String mensagem, String campo, Object object) {
		this(campo, mensagem);		
		this.object = object;
	}

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param campo campo
	 * @param mensagem mensagem
	 */
	public CampoMensagem(String campo, String mensagem) {
		this();
		this.campo = campo;
		this.mensagem = mensagem;
	}

}
