package br.org.cancer.modred.model.correio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.org.cancer.modred.model.PaisCorreio;

/**
 * 
 * Classe que representa as unidades federativas do correio.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "UNIDADE_FEDERATIVA_CORREIO")
public class UnidadeFederativa implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "UNFC_ID")
    private Long id;
    /**
     * O nome do Unidade Federativa.
     */
    @Column(name = "UNFC_TX_NOME")
    private String nome;
    /**
     * a sigla da Unidade Federativa.
     */
    @Column(name = "UNFC_TX_SIGLA")
    private String sigla;
    /**
     * lista de Localidades.
     */

    @OneToMany(mappedBy = "unidadeFederativa", fetch = FetchType.LAZY)
    private List<Localidade> localidades;
    /**
     * País da unidade Federativa.
     */
    // bi-directional many-to-one association to PaisCorreio
    @ManyToOne
    @JoinColumn(name = "PACO_ID")
    private PaisCorreio pais;
    
    /**
     * Código DNE da unidade da federacao.
     */
    @Column(name="UNFC_TX_DNE")
    private String codigoDne;

    /**
     * Método construtor da classe UnidadeFederativaCorreio.
     * 
     * @return UnidadeFederativaCorreio - instância da classe
     *         UnidadeFederativaCorreio.
     */
    public UnidadeFederativa() {}

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
     * @return the pais
     */
    public PaisCorreio getPais() {
        return pais;
    }

    /**
     * @param pais
     *            the pais to set
     */
    public void setPais(PaisCorreio pais) {
        this.pais = pais;
    }

    /**
     * @return the localidades
     */
    public List<Localidade> getLocalidades() {
        return localidades;
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
     * Método para adicionar um localidade.
     * 
     * @param localidadeCorreio loc
     * @return LocalidadeCorreio loc
     */
    public Localidade addLocalidadeCorreio(Localidade localidadeCorreio) {
        getLocalidades().add(localidadeCorreio);
        localidadeCorreio.setUnidadeFederativa(this);
        return localidadeCorreio;
    }

    /**
     * Método para remover uma localidade.
     * 
     * @param localidadeCorreio loc
     * @return LocalidadeCorreio loc
     */
    public Localidade removeLocalidadeCorreio(Localidade localidadeCorreio) {
        getLocalidades().remove(localidadeCorreio);
        localidadeCorreio.setUnidadeFederativa(null);
        return localidadeCorreio;
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
        UnidadeFederativa other = (UnidadeFederativa) obj;
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