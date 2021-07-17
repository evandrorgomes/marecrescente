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
 * Classe que representa os bairros do correio.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "BAIRRO_CORREIO")
public class Bairro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "BACO_ID")
    private Long id;
    
    /**
     * O nome do bairro.
     */
    @Column(name = "BACO_TX_NOME")
    private String nome;
    
    /**
     * localidade do bairro.
     */
    @ManyToOne
    @JoinColumn(name = "LOCC_ID")
    private Localidade localidade;
    /**
     * lista de logradouros.
     */
    @OneToMany(mappedBy = "bairroInicial", fetch = FetchType.LAZY)
    private List<Logradouro> logradouros;
    
    /**
     * Código DNE do bairro nos correios.
     */
    @Column(name="BACO_TX_DNE")
    private String codigoDne;

    /**
     * Método construtor da classe BairroCorreio.
     * 
     * @return BairroCorreio - instância da classe BairroCorreio.
     */
    public Bairro() {}

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
     * @return the localidade
     */
    public Localidade getLocalidade() {
        return localidade;
    }

    /**
     * @param localidade
     *            the localidade to set
     */
    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    /**
     * @return the logradouros
     */
    public List<Logradouro> getLogradouros() {
        return logradouros;
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
     * Método para adicionar um logradouro.
     * 
     * @param logradouro log
     * @return LogradouroCorreio log
     */
    public Logradouro addLogradouro(Logradouro logradouro) {
        getLogradouros().add(logradouro);
        logradouro.setBairroInicial(this);
        return logradouro;
    }

    /**
     * Método para remover um logradouro.
     * 
     * @param logradouro log
     * @return LogradouroCorreio log
     */
    public Logradouro removeLogradouro(Logradouro logradouro) {
        getLogradouros().remove(logradouro);
        logradouro.setBairroInicial(null);
        return logradouro;
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
        Bairro other = (Bairro) obj;
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