package br.org.cancer.modred.model.correio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa os tipos de logradouros.
 *
 * @author bruno.sousa
 */
@Entity
@Table(name = "TIPO_LOGRADOURO_CORREIO")
public class TipoLogradouro implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "TILC_ID")
    private Long id;
    /**
     * O nome do tipo de logradouro.
     */
    @Column(name = "TILC_TX_NOME")
    private String nome;
    /**
     * O nome abreviado do tipo de logradouro.
     */
    @Column(name = "TILC_TX_NOME_ABREVIADO")
    private String nomeAbreviado;

    /**
     * Construtor da classe TipoLobradouroCorreio.
     * 
     * @return TipoLogradouroCorreio - instância da classe
     *         TipoLogradouroCorreio.
     */
    public TipoLogradouro() {}

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
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
     * @return the nomeAbreviado
     */
    public String getNomeAbreviado() {
        return nomeAbreviado;
    }

    /**
     * @param nomeAbreviado
     *            the nomeAbreviado to set
     */
    public void setNomeAbreviado(String nomeAbreviado) {
        this.nomeAbreviado = nomeAbreviado;
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
        TipoLogradouro other = (TipoLogradouro) obj;
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