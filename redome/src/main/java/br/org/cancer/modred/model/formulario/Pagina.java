package br.org.cancer.modred.model.formulario;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;

/**
 * Classe que representa a seção das perguntas de um formulário.
 * 
 * @author ergomes
 *
 */
public class Pagina implements Serializable {
	
	private static final long serialVersionUID = -1089548497106377172L;

	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String titulo;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private List<Secao> secoes;
	
	
	/**
	 * Contrutor padrão.
	 */
	public Pagina() {
	}


	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 * @return the secoes
	 */
	public List<Secao> getSecoes() {
		return secoes;
	}


	/**
	 * @param secoes the secoes to set
	 */
	public void setSecoes(List<Secao> secoes) {
		this.secoes = secoes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((secoes == null) ? 0 : secoes.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagina other = (Pagina) obj;
		if (secoes == null) {
			if (other.secoes != null)
				return false;
		} else if (!secoes.equals(other.secoes))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	
	
}
