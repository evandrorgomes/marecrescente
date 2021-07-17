package br.org.cancer.modred.model.formulario;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;

/**
 * Classe que representa as opçoes das perguntas do tipo RADIO, CHECK e SELECT.
 * 
 * @author bruno.sousa
 *
 */
public class OpcaoPergunta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String descricao;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String valor;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean invalidarOutras;
		
	/**
	 * Construtor padrão.
	 */
	public OpcaoPergunta() {
	}

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param descricao - Descrição da opção 
	 * @param valor - valor da opção
	 */
	public OpcaoPergunta(String descricao, String valor) {
		super();
		this.descricao = descricao;
		this.valor = valor;
	}

	/**
	 * Descrição da opção.
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Descrição da opção.
	 * 
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Valor da opção.
	 * 
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	
	/**
	 * Valor da opção.
	 * 
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	

	/**
	 * @return the invalidarOutras
	 */
	public Boolean getInvalidarOutras() {
		return invalidarOutras;
	}

	/**
	 * @param invalidarOutras the invalidarOutras to set
	 */
	public void setInvalidarOutras(Boolean invalidarOutras) {
		this.invalidarOutras = invalidarOutras;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( valor == null ) ? 0 : valor.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof OpcaoPergunta )) {
			return false;
		}
		OpcaoPergunta other = (OpcaoPergunta) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		}
		else
			if (!valor.equals(other.valor)) {
				return false;
			}
		return true;
	}

}
