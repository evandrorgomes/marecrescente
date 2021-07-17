package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe de persistencia de CondicaoPaciente.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "CONDICAO_PACIENTE")
public class CondicaoPaciente implements Serializable {

    private static final long serialVersionUID = -3016531860942203473L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "COPA_ID")
    @JsonView({EvolucaoView.ListaEvolucao.class, PacienteView.Rascunho.class})
    private Long id;
    /**
     * Descrição da condição do paciente.
     */
    @Column(name = "COPA_TX_DESCRICAO")
    @JsonView({EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
    private String descricao;
    
    /**
     * Lista de evoluções.
     */
    @OneToMany(mappedBy = "condicaoPaciente")
    @JsonIgnore
    private List<Evolucao> evolucoes;

    /**
     * Construtor padrão da classe.
     */
    public CondicaoPaciente() {}
    
    /**
     * Construtor sobrecarregado.
     * @param id
     * @param descricao
     */
    public CondicaoPaciente(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * Retorna a primary key da condicao do paciente.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Seta a primary key da condicao do paciente.
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna a descricao.
     * 
     * @return descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Seta a descricao.
     * 
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
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
        if (!( obj instanceof CondicaoPaciente )) {
            return false;
        }
        CondicaoPaciente other = (CondicaoPaciente) obj;
        if (descricao == null) {
            if (other.descricao != null) {
                return false;
            }
        }
        else
            if (!descricao.equals(other.descricao)) {
                return false;
            }
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