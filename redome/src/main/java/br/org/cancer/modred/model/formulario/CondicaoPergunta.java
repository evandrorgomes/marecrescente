package br.org.cancer.modred.model.formulario;

import java.io.Serializable;

import br.org.cancer.modred.model.domain.TiposComparacao;

/**
 * Classe que representa a condição de visualização de uma pergunta de um formulário.
 * 
 * @author brunosousa
 *
 */
public class CondicaoPergunta implements Serializable {
	
	private static final long serialVersionUID = 5453771852967574021L;
		
	private String atributo;
	private String valor;
	private TiposComparacao tipoComparacao;
	
	/**
	 * @return the atributo
	 */
	public String getAtributo() {
		return atributo;
	}

	/**
	 * @param atributo the atributo to set
	 */
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the tipoComparacao
	 */
	public TiposComparacao getTipoComparacao() {
		return tipoComparacao;
	}

	/**
	 * @param tipoComparacao the tipoComparacao to set
	 */
	public void setTipoComparacao(TiposComparacao tipoComparacao) {
		this.tipoComparacao = tipoComparacao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atributo == null) ? 0 : atributo.hashCode());
		result = prime * result + ((tipoComparacao == null) ? 0 : tipoComparacao.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		CondicaoPergunta other = (CondicaoPergunta) obj;
		if (atributo == null) {
			if (other.atributo != null) {
				return false;
			}
		} 
		else if (!atributo.equals(other.atributo)) {
			return false;
		}
		if (tipoComparacao != other.tipoComparacao) {
			return false;
		}
		return true;
	}

}
