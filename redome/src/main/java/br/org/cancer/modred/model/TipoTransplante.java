package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe de persistência do tipo de transplnate.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@Table(name = "TIPO_TRANSPLANTE")
public class TipoTransplante implements Serializable {

    private static final long serialVersionUID = -6102436225548679960L;
    
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @JsonView({PedidoTransferenciaCentroView.Detalhe.class, PacienteView.Rascunho.class, EvolucaoView.NovaEvolucao.class, 
    	AvaliacaoPrescricaoView.Detalhe.class, EvolucaoView.ConsultaEvolucao.class})
    @Column(name = "TITR_ID")
    private Long id;
    
    /**
     * Descrição do tipo de tranplante.
     */
    @Column(name = "TITR_TX_DESCRICAO")
    @JsonView({EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
    	, AvaliacaoCamaraTecnicaView.Detalhe.class,  EvolucaoView.NovaEvolucao.class, AvaliacaoPrescricaoView.Detalhe.class,
    	EvolucaoView.ConsultaEvolucao.class})
    private String descricao;

    /**
     * Construtor padrão.
     */
    public TipoTransplante() {}

    /**
     * Construtor sobrecarregado com todos os campos da classe.
     */
    public TipoTransplante(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * Método que obtém a id do tipo de transplante.
     * 
     * @return Long
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Método que atribui a id do tipo de transplante.
     * 
     * @param Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que obtém a descricao do tipo de transplante.
     * 
     * @return String
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Método que atribui a descricao do tipo de transplante.
     * 
     * @param String
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
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
        if (!( obj instanceof TipoTransplante )) {
            return false;
        }
        TipoTransplante other = (TipoTransplante) obj;
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