package br.org.cancer.modred.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PerfilView;

/**
 * Classe para serialização de paginação de Busca.
 * 
 * @author Filipe Paes
 * @param <T>
 *            classe que será serializada.
 */
public class BuscaJsonPage<T> extends org.springframework.data.domain.PageImpl<T> {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = -3157245853980149504L;

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param content
	 */
	public BuscaJsonPage(List<T> content) {
		super(content);
	}

	/**
	 * Contrutor sobrecarregado.
	 * 
	 * @param content
	 * @param pageable
	 * @param total
	 */
	public BuscaJsonPage(final List<T> content, final Pageable pageable, final long total) {
		super(content, pageable!=null?pageable:Pageable.unpaged(), total);
	}

	/**
	 * Contrutor sobrecarregado.
	 * 
	 * @param page
	 * @param pageable
	 */
	public BuscaJsonPage(final Page<T> page, final Pageable pageable) {
		super(page.getContent(), pageable, page.getTotalElements());
	}

	/**
	 * Método para obter o total de páginas.
	 */
	@JsonView(PerfilView.Listar.class)
	public int getTotalPages() {
		return super.getTotalPages();
	}

	/**
	 * Método para obter o total de elementos.
	 */
	// @JsonView(PerfilView.Listar.class)
	public long getTotalElements() {
		return super.getTotalElements();
	}

	/**
	 * Método para descobrir se há um próximo elemento.
	 */
	// @JsonView(PerfilView.Listar.class)
	public boolean hasNext() {
		return super.hasNext();
	}

	/**
	 * Método para saber se é o último elemento.
	 */
	@JsonView(PerfilView.Listar.class)
	public boolean isLast() {
		return super.isLast();
	}

	/**
	 * Método para saber se o elemento possui conteúdo.
	 */
	// @JsonView(PerfilView.Listar.class)
	public boolean hasContent() {
		return super.hasContent();
	}

	/**
	 * Método para obter o conteúdo.
	 */
	// @JsonView(PerfilView.Listar.class)
	public List<T> getContent() {
		return super.getContent();
	}

	/**
	 * Método para obter o número do slice corrente.
	 */
	@Override
	// @JsonView(PerfilView.Listar.class)
	public int getNumber() {
		return super.getNumber();
	}

	/**
	 * Método para saber se o slice é primeiro.
	 */
	@Override
	// @JsonView(PerfilView.Listar.class)
	public boolean isFirst() {
		return super.isFirst();
	}

}
