package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representação da chave primária da entidade Permissao.
 * 
 * @author Cintia Oliveira
 *
 */
@Embeddable
public class PermissaoId implements Serializable {

    private static final long serialVersionUID = -4799529574084085409L;

    @ManyToOne
    @JoinColumn(name = "PERF_ID")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "RECU_ID")
    private Recurso recurso;

    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil
     *            the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the recurso
     */
    public Recurso getRecurso() {
        return recurso;
    }

    /**
     * @param recurso
     *            the recurso to set
     */
    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( perfil == null ) ? 0 : perfil.hashCode() );
        result = prime * result + ( ( recurso == null ) ? 0 : recurso.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * 
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        PermissaoId other = (PermissaoId) obj;
        if (perfil == null) {
            if (other.perfil != null) {
                return false;
            }
        }
        else
            if (!perfil.equals(other.perfil)) {
                return false;
            }
        if (recurso == null) {
            if (other.recurso != null) {
                return false;
            }
        }
        else
            if (!recurso.equals(other.recurso)) {
                return false;
            }
        return true;
    }

}
