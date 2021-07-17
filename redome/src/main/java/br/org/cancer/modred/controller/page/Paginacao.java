package br.org.cancer.modred.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PageView;

/**
 * Implementação da classe do Spring responsável pela paginação.
 * 
 * @author Pizão
 *
 * @param <T>
 */
public class Paginacao <T> extends PageImpl<T> {

	private static final long serialVersionUID = 7336373053169492264L;

	
    /** Contrutor sobrecarregado.
     * @param content
     * @param pageable
     * @param total
     */
    public Paginacao(final List<T> content, final Pageable pageable, final long total) {
   		super(pageable != null && content.size() > pageable.getPageSize() ? content.subList(new Long(pageable.getOffset()).intValue(), pageable != null && (pageable.getOffset() + pageable.getPageSize())>content.size()?content.size(): new Long(pageable.getOffset() + pageable.getPageSize()).intValue()) : content, Pageable.unpaged(), total);
    }

	/**
	 * Contrutor sobrecarregado.
	 * 
	 * @param content
	 */
	public Paginacao(final List<T> content) {
		super(content);
	}

	/**
	 * Contrutor sobrecarregado.
	 * 
	 * @param page
	 * @param pageable
	 */
	public Paginacao(final Page<T> page, final Pageable pageable) {
		super(page.getContent(), pageable, page.getTotalElements());
	}

	/**
	 * Método para obter o total de páginas.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public int getTotalPages() {
		return super.getTotalPages();
	}

	/**
	 * Método para obter o total de elementos.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public long getTotalElements() {
		return super.getTotalElements();
	}

	/**
	 * Método para descobrir se há um próximo elemento.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public boolean hasNext() {
		return super.hasNext();
	}

	/**
	 * Método para saber se é o último elemento.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public boolean isLast() {
		return super.isLast();
	}

	/**
	 * Método para saber se o elemento possui conteúdo.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public boolean hasContent() {
		return super.hasContent();
	}

	/**
	 * Método para obter o conteúdo.
	 */
	@JsonView(PageView.ListarPaginacao.class)
	public List<T> getContent() {
		return super.getContent();
	}

	/**
	 * Método para obter o número do slice corrente.
	 */
	@Override
	@JsonView(PageView.ListarPaginacao.class)
	public int getNumber() {
		return super.getNumber();
	}

	/**
	 * Método para saber se o slice é primeiro.
	 */
	@Override
	@JsonView(PageView.ListarPaginacao.class)
	public boolean isFirst() {
		return super.isFirst();
	}
	
	@Override
    @JsonView({PageView.ListarPaginacao.class})
    public int getSize() {
    	return super.getSize();
    }
}
