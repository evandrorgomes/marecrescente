package br.org.cancer.modred.controller.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Implementação generica da classe do Spring responsável pela paginação.
 * 
 * @author Cintia Oliveira
 *
 */
public class JsonPage implements Serializable {

	private static final long serialVersionUID = 5334242979326953279L;

	private Class<?> jsonView;
	private Page<?> page;

	/**
	 * Contrutor.
	 * 
	 * @param jsonView utilizado na serialização em formato json
	 * @param page lista de objetos paginados
	 */
	public JsonPage(final Class<?> jsonView, final Page<?> page) {
		this.jsonView = jsonView;
		this.page = page;
	}

	/**
	 * Método para obter o total de páginas.
	 */
	public int getTotalPages() {
		return page.getTotalPages();
	}

	/**
	 * Método para obter o total de elementos.
	 */
	public long getTotalElements() {
		return page.getTotalElements();
	}

	/**
	 * Método para descobrir se há um próximo elemento.
	 */
	public boolean hasNext() {
		return page.hasNext();
	}

	/**
	 * Método para saber se é o último elemento.
	 */
	public boolean isLast() {
		return page.isLast();
	}

	/**
	 * Método para saber se o elemento possui conteúdo.
	 */
	public boolean hasContent() {
		return page.hasContent();
	}

	/**
	 * Método para obter o conteúdo.
	 */
	public List<?> getContent() {
		return page.getContent();
	}

	/**
	 * Método para obter o número do slice corrente.
	 */
	public int getNumber() {
		return page.getNumber();
	}

	/**
	 * Método para saber se o slice é primeiro.
	 */
	public boolean isFirst() {
		return page.isFirst();
	}

	/**
	 * @return the jsonView
	 */
	public Class<?> getJsonView() {
		return jsonView;
	}

	/**
	 * @param jsonView the jsonView to set
	 */
	public void setJsonView(Class<?> jsonView) {
		this.jsonView = jsonView;
	}

}
