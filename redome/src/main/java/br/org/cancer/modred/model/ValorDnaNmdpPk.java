package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de associação entre o ValorDNA e NMDP.
 * @author Bruno Sousa
 *
 */
@Embeddable
public class ValorDnaNmdpPk implements Serializable {

    private static final long serialVersionUID = 3468376958126733152L;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCU_ID")
    private Locus locus;

    /**
     * @return the locus
     */
    public Locus getLocus() {
        return locus;
    }
    
    /**
     * @param locus the locus to set
     */
    public void setLocus(Locus locus) {
        this.locus = locus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( locus == null ) ? 0 : locus.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!( obj instanceof ValorDnaNmdpPk )) {
            return false;
        }
        ValorDnaNmdpPk other = (ValorDnaNmdpPk) obj;
        if (locus == null) {
            if (other.locus != null) {
                return false;
            }
        }
        else
            if (!locus.equals(other.locus)) {
                return false;
            }
        return true;
    }
}
