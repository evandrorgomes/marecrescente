package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

/**
 * Dto para inserir relatorio.
 * 
 * @author Fillipe Queiroz
 *
 */
public class RelatorioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3509805502177620050L;
	private String codigo;
	private String html;

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

}
