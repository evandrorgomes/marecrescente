package br.org.cancer.modred.controller.dto.doador;

/**
 * Classe que representa a metodologia usada no exame.
 * 
 * @author Rafael Pizão
 *
 */
public class MetodologiaDTO {

    private Long id;
    private String sigla;
    private String descricao;

    /**
     * Construtor padrão.
     */
    public MetodologiaDTO() {}

    /**
     * Construtor sobrecarregado.
     * 
     * @param sigla
     * @param descricao
     */
    public MetodologiaDTO(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    /**
     * Construtor sobrecarregado.
     * 
     * @param id
     * @param sigla
     * @param descricao
     */
    public MetodologiaDTO(Long id, String sigla, String descricao) {
        this(sigla, descricao);
        this.id = id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla
     *            the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao
     *            the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Implementação do método hashCode para a classe Metodologia.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
        return result;
    }

    /**
     * Implementação do método equals para a entidade Metodologia.
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
        MetodologiaDTO other = (MetodologiaDTO) obj;
        if (sigla == null) {
            if (other.sigla != null) {
                return false;
            }
        }
        else
            if (!sigla.equals(other.sigla)) {
                return false;
            }
        return true;
    }
}
