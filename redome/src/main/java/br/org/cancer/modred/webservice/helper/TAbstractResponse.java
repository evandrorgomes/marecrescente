package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Classe que representa o response do webservice.
 * 
 * @author brunosousa
 *
 */
public abstract class TAbstractResponse {

	@JacksonXmlProperty(isAttribute=false, localName="return")
	private String dados;

	/**
	 * @return the dados
	 */
	public String getDados() {
		return dados;
	}

	/**
	 * @param dados the dados to set
	 */
	public void setDados(String dados) {
		this.dados = dados;
	}
	
}
