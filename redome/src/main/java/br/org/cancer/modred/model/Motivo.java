package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe de persistencia de motivo.
 * 
 * @author bruno.sousa
 *
 */
@Entity
public class Motivo implements Serializable {

    private static final long serialVersionUID = 5170101253604507919L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "MOTI_ID")
    @JsonView(PacienteView.Rascunho.class)
    private Long id;
    /**
     * Descrição do motivo.
     */
    @Column(name = "MOTI_TX_DESCRICAO")
    @JsonView({EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class})
    private String descricao;


    /**
     * Construtor padrão da classe.
     */
    public Motivo() {}
    
    /**
     * Construtor sobrecarregado.
     * @return
     */
    public Motivo(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * Retorna a primary key de motivo.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Seta a primary key de motivo.
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
        if (!( obj instanceof Motivo )) {
            return false;
        }
        Motivo other = (Motivo) obj;
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