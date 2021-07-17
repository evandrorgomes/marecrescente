package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Classe que representa os paises do correio.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "PAIS_CORREIO")
public class PaisCorreio implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "PACO_ID")
    private String id;
    /**
     * O nome do tipo de logradouro.
     */
    @Column(name = "PACO_TX_NOME")
    private String nome;

    /**
     * Método construtor da classe PaisCorreio.
     * 
     * @return PaisCorreio - instância da classe PaisCorreio.
     */
    public PaisCorreio() {}

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        PaisCorreio other = (PaisCorreio) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else
            if (!id.equals(other.id)) {
                return false;
            }
        return true;
    }
}