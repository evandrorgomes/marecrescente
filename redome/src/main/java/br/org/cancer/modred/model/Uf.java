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
 * Classe de persistencia de uf para a tabela UF.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "UF")
public class Uf implements Serializable {

    private static final long serialVersionUID = -5934379585715305879L;

    /**
     * Chave que identifica com exclusividade uma instância desta classe e sigla.
     * da UF.
     */
    @Id
    @Column(name = "UF_SIGLA")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
		PacienteView.DadosPessoais.class, PreCadastroMedicoView.Detalhe.class, DoadorView.ContatoFase2.class,
		DoadorView.LogisticaDoador.class})
    private String sigla;
    /**
     * Nome da UF.
     */
    @Column(name = "UF_TX_NOME")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
		PacienteView.DadosPessoais.class, PreCadastroMedicoView.Detalhe.class, DoadorView.ContatoFase2.class})
    private String nome;

    /**
     * Construtor básico.
     */
    public Uf() {}

    /**
     * Construtor com todos os campos da classe.
     */
    public Uf(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    /**
     * Método que obtém a sigla da uf.
     * 
     * @return String
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Método que atribui a sigla da uf.
     * 
     * @param String
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Método que obtém o nome da UF.
     * 
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que atribui nome da UF.
     * 
     * @param String
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
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
        Uf other = (Uf) obj;
        if (sigla == null) {
            if (other.sigla != null) {
                return false;
            }
        }
        else
            if (!sigla.equals(other.sigla)) {
                return false;
            }
        return true;
    }
    
    @Override
    public String toString() {
    	return sigla;
    }
}