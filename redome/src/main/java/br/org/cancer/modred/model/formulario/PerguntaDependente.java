package br.org.cancer.modred.model.formulario;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.model.domain.TiposComparacao;

/**
 * Classe que representa as perguntas dependentes de outra pergunta.
 * 
 * @author bruno.sousa
 *
 */
public class PerguntaDependente implements Serializable {

	private static final long serialVersionUID = -6174793675408137497L;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String idPergunta;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
    private String valor;
    
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private TiposComparacao tipoComparacao;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Pergunta pergunta;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean comValidacao = true;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean comErro = false;
	
	/**
	 * Construtor padrão.
	 */
	public PerguntaDependente() {
	}

	/**
	 * Construtor com parametros.
	 * 
	 * @param idPergunta - Identificador da pergunta dependente
	 * @param valor -  Valor da resposta da pergunta
	 * @param tipoComparacao - Valor do identificador do tipo de operação.
	 */
	public PerguntaDependente(String idPergunta, String valor, TiposComparacao tipoComparacao) {
		super();
		this.idPergunta = idPergunta;
		this.valor = valor;
		this.tipoComparacao = tipoComparacao;
	}
	
	/**
	 * Identificador da pergunta dependente.
	 * 
	 * @return the idPergunta
	 */
	public String getIdPergunta() {
		return idPergunta;
	}
	
	/**
	 * Identificador da pergunta dependente.
	 * 
	 * @param idPergunta the idPergunta to set
	 */
	public void setIdPergunta(String idPergunta) {
		this.idPergunta = idPergunta;
	}
	
	/**
	 * Valor da resposta da pergunta.  
	 * 
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	
	/**
	 * Valor da resposta da pergunta.
	 * 
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the tipo
	 */
	public TiposComparacao getTipoComparacao() {
		return tipoComparacao;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipoComparacao(TiposComparacao tipoComparacao) {
		this.tipoComparacao = tipoComparacao;
	}

	/**
	 * @return the comValidacao
	 */
	public Boolean getComValidacao() {
		return comValidacao;
	}

	/**
	 * @param comValidacao the comValidacao to set
	 */
	public void setComValidacao(Boolean comValidacao) {
		this.comValidacao = comValidacao;
	}

	/**
	 * @return the comErro
	 */
	public Boolean getComErro() {
		return comErro;
	}

	/**
	 * @param comErro the comErro to set
	 */
	public void setComErro(Boolean comErro) {
		this.comErro = comErro;
	}
	
	/**
	 * @return the pergunta
	 */
	public Pergunta getPergunta() {
		return pergunta;
	}

	/**
	 * @param pergunta the pergunta to set
	 */
	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( idPergunta == null ) ? 0 : idPergunta.hashCode() );
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
		if (!( obj instanceof PerguntaDependente )) {
			return false;
		}
		PerguntaDependente other = (PerguntaDependente) obj;
		if (idPergunta == null) {
			if (other.idPergunta != null) {
				return false;
			}
		}
		else
			if (!idPergunta.equals(other.idPergunta)) {
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
