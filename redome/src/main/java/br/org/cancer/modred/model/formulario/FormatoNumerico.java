package br.org.cancer.modred.model.formulario;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;

/**
 * Classe que representa os parametros para o tipo de pergunta númerico.
 * 
 * @author brunosousa
 *
 */
public class FormatoNumerico implements Serializable {

	private static final long serialVersionUID = 6730464069361763438L;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String alinhamento = "right";
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean permitirNegativo = true;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private char separadorDecimal = ',';
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Integer casasDecimais = 2;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String prefixo = "R$ ";
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String sufixo = "";
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private char separadorMilhares = '.';
	
	public FormatoNumerico() {}

	/**
	 * @return the alinhamento
	 */
	public String getAlinhamento() {
		return alinhamento;
	}

	/**
	 * @param alinhamento the alinhamento to set
	 */
	public void setAlinhamento(String alinhamento) {
		this.alinhamento = alinhamento;
	}

	/**
	 * @return the permitirNegativo
	 */
	public Boolean getPermitirNegativo() {
		return permitirNegativo;
	}

	/**
	 * @param permitirNegativo the permitirNegativo to set
	 */
	public void setPermitirNegativo(Boolean permitirNegativo) {
		this.permitirNegativo = permitirNegativo;
	}

	/**
	 * @return the separadorDecimal
	 */
	public char getSeparadorDecimal() {
		return separadorDecimal;
	}

	/**
	 * @param separadorDecimal the separadorDecimal to set
	 */
	public void setSeparadorDecimal(char separadorDecimal) {
		this.separadorDecimal = separadorDecimal;
	}

	/**
	 * @return the casasDecimais
	 */
	public Integer getCasasDecimais() {
		return casasDecimais;
	}

	/**
	 * @param casasDecimais the casasDecimais to set
	 */
	public void setCasasDecimais(Integer casasDecimais) {
		this.casasDecimais = casasDecimais;
	}

	/**
	 * @return the prefixo
	 */
	public String getPrefixo() {
		return prefixo;
	}

	/**
	 * @param prefixo the prefixo to set
	 */
	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	/**
	 * @return the sufixo
	 */
	public String getSufixo() {
		return sufixo;
	}

	/**
	 * @param sufixo the sufixo to set
	 */
	public void setSufixo(String sufixo) {
		this.sufixo = sufixo;
	}

	/**
	 * @return the separadorMilhares
	 */
	public char getSeparadorMilhares() {
		return separadorMilhares;
	}

	/**
	 * @param separadorMilhares the separadorMilhares to set
	 */
	public void setSeparadorMilhares(char separadorMilhares) {
		this.separadorMilhares = separadorMilhares;
	}
	

}
