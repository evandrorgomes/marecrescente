package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe utilizada validação dos HLAs informados no exame
 * {@see LocusExame.java}.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "VALOR_DNA_NMDP")
public class ValorDnaNmdp implements Serializable {

    private static final long serialVersionUID = 3089551634406867228L;

    @EmbeddedId
    private ValorDnaNmdpPk id;

    @Column(name = "DNNM_TX_VALOR")
    @NotNull
    private String valor;

    /**
     * @return the id
     */
    public ValorDnaNmdpPk getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(ValorDnaNmdpPk id) {
        this.id = id;
    }
    
    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
        if (!( obj instanceof ValorDnaNmdp )) {
            return false;
        }
        ValorDnaNmdp other = (ValorDnaNmdp) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else
            if (!id.equals(other.id)) {
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
