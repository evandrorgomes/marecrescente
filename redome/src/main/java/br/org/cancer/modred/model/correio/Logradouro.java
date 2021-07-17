package br.org.cancer.modred.model.correio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Classe que representa os logradouros do correio.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "LOGRADOURO_CORREIO")
public class Logradouro implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "LOGC_ID")
    private Long id;
    /**
     * O tipo do logradouro.
     */
    // bi-directional many-to-one association to TipoLogradouroCorreio
    @ManyToOne
    @JoinColumn(name = "TILC_ID")
    private TipoLogradouro tipoLogradouro;
    /**
     * O nome do logradouro.
     */
    @Column(name = "LOGC_TX_NOME")
    private String nome;
    /**
     * O número do cep.
     * 
     * Formato: 99999999
     */
    @Column(name = "LOGC_TX_CEP")
    private String cep;
    /**
     * O bairro inicial do logradouro.
     */
    // bi-directional many-to-one association to BairroCorreio
    @ManyToOne
    @JoinColumn(name = "BACO_ID_INICIAL")
    private Bairro bairroInicial;
    /**
     * O bairro final do logradouro caso exista.
     */
    // bi-directional many-to-one association to BairroCorreio
    @ManyToOne
    @JoinColumn(name = "BACO_ID_FINAL")
    private Bairro bairroFinal;
    
    /**
     * Código DNE do logradouro nos correios.
     */
    @Column(name="LOGC_TX_CHAVE_DNE")
    private String codigoDne;
    
    /**
     * Método construtor da classe LogradouroCorreio.
     * 
     * @return LogradouroCorreio - instância da classe LogradouroCorreio.
     */
    public Logradouro() {}

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
     * @return the tipoLogradouro
     */
    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    /**
     * @param tipoLogradouro
     *            the tipoLogradouro to set
     */
    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
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
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep
     *            the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the bairroInicial
     */
    public Bairro getBairroInicial() {
        return bairroInicial;
    }

    /**
     * @param bairroInicial
     *            the bairroInicial to set
     */
    public void setBairroInicial(Bairro bairroInicial) {
        this.bairroInicial = bairroInicial;
    }

    /**
     * @return the bairroFinal
     */
    public Bairro getBairroFinal() {
        return bairroFinal;
    }

    /**
     * @param bairroFinal
     *            the bairroFinal to set
     */
    public void setBairroFinal(Bairro bairroFinal) {
        this.bairroFinal = bairroFinal;
    }
    
    /**
     * @return the codigoDne
     */
    public String getCodigoDne() {
        return codigoDne;
    }
    
    /**
     * @param codigoDne the codigoDne to set
     */
    public void setCodigoDne(String codigoDne) {
        this.codigoDne = codigoDne;
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
        Logradouro other = (Logradouro) obj;
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