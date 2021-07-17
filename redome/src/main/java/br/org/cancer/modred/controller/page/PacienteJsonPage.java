package br.org.cancer.modred.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Implementação da classe do Srping responsável pela paginação.
 * @author Bruno Sousa
 *
 * @param <T>
 */
public class PacienteJsonPage <T> extends org.springframework.data.domain.PageImpl<T> {

    private static final long serialVersionUID = -5514232574427232873L;

    /**
     * Contrutor sobrecarregado.
     * @param content
     * @param pageable
     * @param total
     */
    public PacienteJsonPage(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable!=null?pageable:Pageable.unpaged(), total);
    }

    /**
     * Contrutor sobrecarregado.
     * @param content
     */
    public PacienteJsonPage(final List<T> content) {
        super(content);
    }

    /**
     * Contrutor sobrecarregado.
     * @param page
     * @param pageable
     */
    public PacienteJsonPage(final Page<T> page, final Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());
    }

    /**
     * Método para obter o total de páginas.
     */
    @JsonView(PacienteView.Consulta.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    /**
     * Método para obter o total de elementos.
     */
    @JsonView(PacienteView.Consulta.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    /**
     * Método para descobrir se há um próximo elemento.
     */
    @JsonView(PacienteView.Consulta.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    /**
     * Método para saber se é o último elemento.
     */
    @JsonView(PacienteView.Consulta.class)
    public boolean isLast() {
        return super.isLast();
    }

    /**
     * Método para saber se o elemento possui conteúdo.
     */
    @JsonView(PacienteView.Consulta.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    /**
     * Método para obter o conteúdo.
     */
    @JsonView(PacienteView.Consulta.class)
    public List<T> getContent() {
        return super.getContent();
    }
    
    /** 
     * Método para obter o número do slice corrente.
     */
    @Override
    @JsonView(PacienteView.Consulta.class)
    public int getNumber() {
        return super.getNumber();
    }
    
    /** 
     * Método para saber se o slice é primeiro.
     */
    @Override
    @JsonView(PacienteView.Consulta.class)
    public boolean isFirst() {
        return super.isFirst();
    }
    
}
