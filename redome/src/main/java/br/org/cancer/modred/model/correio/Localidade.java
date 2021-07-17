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

/**
 * 
 * Classe que representa as localidades do correio.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "LOCALIDADE_CORREIO")
public class Localidade implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "LOCC_ID")
    private Long id;
    /**
     * O nome da localidade.
     */
    @Column(name = "LOCC_TX_NOME")
    private String nome;
    /**
     * O tipo da Localidade.
     */
    @Column(name = "LOCC_IN_TIPO")
    private String tipo;
    /**
     * Id da localidade pai quando for Distrito ou Povoado.
     */
    @Column(name = "LOCC_ID_SUBORDINACAO")
    private Long idSubordinacao;
    /**
     * Código do ibge.
     */
    @Column(name = "LOCC_NR_CODIGO_IBGE")
    private String codigoIbge;
    /**
     * Cep da Localidade.
     * 
     * Formato 99999999
     */
    @Column(name = "LOCC_TX_CEP")
    private String cep;
    /**
     * lista de bairros da localidade.
     */
    @OneToMany(mappedBy = "localidade", fetch = FetchType.LAZY)
    private List<Bairro> bairros;
    /**
     * Unidade Federativa o qual a localidade faz parte.
     */
    @ManyToOne
    @JoinColumn(name = "UNFC_ID")
    private UnidadeFederativa unidadeFederativa;
    
    /**
     * Código DNE da localidade nos correios.
     */
    @Column(name="LOCC_TX_DNE")
    private String codigoDne;

    /**
     * Método construtor da classe localidadeCorreio.
     * 
     * @return LocalidadeCorreio - instância da classe LocalidadeCorreio.
     */
    public Localidade() {}

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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     *            the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the idSubordinacao
     */
    public Long getIdSubordinacao() {
        return idSubordinacao;
    }

    /**
     * @param idSubordinacao
     *            the idSubordinacao to set
     */
    public void setIdSubordinacao(Long idSubordinacao) {
        this.idSubordinacao = idSubordinacao;
    }

    /**
     * @return the codigoIbge
     */
    public String getCodigoIbge() {
        return codigoIbge;
    }

    /**
     * @param codigoIbge
     *            the codigoIbge to set
     */
    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
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
     * @return the unidadeFederativa
     */
    public UnidadeFederativa getUnidadeFederativa() {
        return unidadeFederativa;
    }

    /**
     * @param unidadeFederativa
     *            the unidadeFederativa to set
     */
    public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }

    /**
     * @return the bairros
     */
    public List<Bairro> getBairros() {
        return bairros;
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
     * Método para adicionar um bairro.
     * 
     * @param bairroCorreio bai
     * @return BairroCorreio bai
     */
    public Bairro addBairroCorreio(Bairro bairroCorreio) {
        getBairros().add(bairroCorreio);
        bairroCorreio.setLocalidade(this);
        return bairroCorreio;
    }

    /**
     * Método para remover um bairro.
     * 
     * @param bairroCorreio bai
     * @return BairroCorreio bai
     */
    public Bairro removeBairroCorreio(Bairro bairroCorreio) {
        getBairros().remove(bairroCorreio);
        bairroCorreio.setLocalidade(null);
        return bairroCorreio;
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
        Localidade other = (Localidade) obj;
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