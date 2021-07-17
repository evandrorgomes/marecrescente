package br.org.cancer.modred.model.domain;

/**
 * Enum para tipo de Pendencias.
 * 
 * @author bruno.sousa
 *
 */
public enum TipoPendencias {
    EXAME(1L),
    EVOLUCAO(2L),
    QUESTIONAMENTO(3L);
    
    private Long id;
    
    TipoPendencias(Long id) {
        this.id = id;
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
