package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe que representa um lócus.
 * 
 * @author ergomes
 *
 */
public class LocusDTO implements Serializable {

	private static final long serialVersionUID = -3059707646283374326L;

	private String codigo;
    private Integer ordem;
    
    public static final String LOCUS_A = "A";
    public static final String LOCUS_B = "B";
    public static final String LOCUS_DRB1 = "DRB1";
    public static final String LOCUS_C = "C";
    public static final String LOCUS_DQB1 = "DQB1";
    public static final String LOCUS_DPA1 = "DPA1";
    public static final String LOCUS_DPB1 = "DPB1";
    public static final String LOCUS_DRB3 = "DRB3";
    public static final String LOCUS_DRB4 = "DRB4";
    public static final String LOCUS_DRB5 = "DRB5";
    
    /**
     * Construtor padrão.
     */
    public LocusDTO() {}

    /**
     * Construtor sobrecarregado que recebe o código do lócus.
     * @param codigo
     */
    public LocusDTO(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the ordem
     */
    public Integer getOrdem() {
        return ordem;
    }
    
    /**
     * @param ordem the ordem to set
     */
    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    /**
     * Implementação do método hashCode para a classe Locus.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
        return result;
    }

    /**
     * Implementação do método equals para a classe Locus.
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
        LocusDTO other = (LocusDTO) obj;
        if (codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        }
        else
            if (!codigo.equals(other.codigo)) {
                return false;
            }
        return true;
    }
}
