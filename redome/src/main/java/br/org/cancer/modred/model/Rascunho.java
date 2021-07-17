package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de persistencia de rascunho.
 * 
 * @author Filipe Paes
 */
@Entity
@SequenceGenerator(name = "SQ_RASC_ID", sequenceName = "SQ_RASC_ID", allocationSize = 1)
public class Rascunho implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id do rascunho.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RASC_ID")
    @Column(name = "RASC_ID")
    private Long id;
    /**
     * Dados em formato JSON de paciente para que sejam recuperados pelo
     * rascunho.
     */
    @Lob
    @Column(name = "RASC_TX_JSON")
    @NotEmpty
    private byte[] json;
    /**
     * Referência do usuário que é dono do rascunho.
     */
    @OneToOne
    @JoinColumn(name = "USUA_ID")
    @NotNull
    @JsonIgnore
    private Usuario usuario;

    public Rascunho() {}

    /**
     * Retorna o id do rascunho.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Seta o id do rascunho.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o json com os dados do draft do paciente.
     * 
     * @return the json
     */
    public byte[] getJson() {
        return json;
    }

    /**
     * seta o json com os dados do draft do paciente.
     * 
     * @param json
     *            the json to set
     */
    public void setJson(byte[] json) {
        this.json = json;
    }

    /**
     * Retorna o usuario que guardou o rascunho.
     * 
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Seta o usuario que guardou o rascunho.
     * 
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
        Rascunho other = (Rascunho) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }
}