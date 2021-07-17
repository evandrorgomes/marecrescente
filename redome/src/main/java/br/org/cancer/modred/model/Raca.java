package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe de persistencia de raça para a tabela RACA.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "RACA")
public class Raca implements Serializable {

    private static final long serialVersionUID = -2264314361961331189L;

    /**
     * Constante que representa a Id da raça Indígina dentro da base de dados.
     */
    public static final Long INDIGENA = 5L;

    /**
     * Constante que representa a Id da raça PRETA dentro da base de dados.
     */
    public static final Long PRETA = 2L;

    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "RACA_ID")
    @JsonView({PacienteView.DadosPessoais.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
    			PacienteView.Rascunho.class})
    private Long id;
    
    /**
     * Nome descritivo da raça.
     */
    @Column(name = "RACA_TX_NOME")
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
    private String nome;
    
    
    /**
     * Identifica raça no REDOMEWEB.
     */
    @Column(name = "RACA_NR_ID_REDOMEWEB")
    private Long idRedomeweb; 

    /**
     * Construtor padrão.
     */
    public Raca() {}

    /**
     * Construtor com todos os campos da classe.
     */
    public Raca(Long id, String nome) {
        this.id = id;
        this.nome = nome;        
    }

    /**
     * Construtor com todos os campos da classe.
     */
    public Raca(Long id, String nome, Long idRedomeweb) {
    	this(id, nome);
        this.idRedomeweb = idRedomeweb;
    }
    
    
    /**
     * Método que obtém a id da raça.
     * 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que atribui a id da raça.
     * 
     * @param Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtém o nome da raça.
     * 
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que atribui nome da raça.
     * 
     * @param String
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
	/**
	 * Método que obtém a codigo da raça do Redomeweb.
	 * 
	 * @return
	 */
	public Long getIdRedomeweb() {
		return idRedomeweb;
	}

	/**
	 * Método que atribui a codigo da raça do REDOMEWEB.
     * 
     *  @param Long
	 */
	public void setIdRedomeweb(Long idRedomeweb) {
		this.idRedomeweb = idRedomeweb;
	}


    /* (non-Javadoc)
     * {@inheritDoc}
     * 
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
        Raca other = (Raca) obj;
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