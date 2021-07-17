package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PreCadastroMedicoView;
import br.org.cancer.modred.controller.view.TransportadoraView;

/**
 * Classe de persistência do modelo Pais para a tabela país.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "PAIS")
public class Pais implements Serializable {

    private static final long serialVersionUID = 4853364171386190731L;

    /**
     * Constate com o Id do Bradil na base de dados.
     */
    public static final Long BRASIL = 1L;
    
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "PAIS_ID")
    @JsonView({LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, PacienteView.DadosPessoais.class,
    	BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, 
    	DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class,
    	CentroTransplanteView.Detalhe.class, PreCadastroMedicoView.Detalhe.class,
    	MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
    	DoadorView.ContatoFase2.class})
    private Long id;
    
    /**
     * Nome descritivo do país.
     */
    @Column(name = "PAIS_TX_NOME")
    @JsonView({LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, 
    	DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
    	TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, 
    	PreCadastroMedicoView.Detalhe.class, MedicoView.Detalhe.class,TransportadoraView.Detalhe.class,
    	DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})    
    private String nome;
    
    
    /**
     * Código do País no REDOMEWEB.
     */
    @Column(name = "PAIS_TX_CD_NACIONAL_REDOMEWEB")    
    private String codigoPaisRedomeweb;    
    

    /**
     * Construtor padrão.
     */
    public Pais() {}

    /**
     * Construtor com todos os campos da classe.
     */
    public Pais(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    /**
     * Construtor com todos os campos da classe.
     */
    public Pais(Long id, String nome, String codigoPaisRedomeweb) {
    	this(id, nome);
        this.codigoPaisRedomeweb = codigoPaisRedomeweb;
    }


    /**
     * Método que obtém a id do país.
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Método que atribui a id do país.
     * 
     * @param Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtém o nome do país.
     * 
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que atribui nome do país.
     * 
     * @param Long
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

	/**
	 * Método que obtém o código do país do REDOMEWEB.
	 * 
	 * @return string
	 */
	public String getCodigoPaisRedomeweb() {
		return codigoPaisRedomeweb;
	}

	/**
	 * Método que atribui nome do país.
	 *  
	 * @param String
	 */
	public void setCodigoPaisRedomeweb(String codigoPaisRedomeweb) {
		this.codigoPaisRedomeweb = codigoPaisRedomeweb;
	}

    /**
     * Método que obtém se o país é Brasil.
     * 
	 * @return boolean 
	 */	
    public boolean isBrasil() {
        return Pais.BRASIL.equals(this.getId());
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
        Pais other = (Pais) obj;
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
