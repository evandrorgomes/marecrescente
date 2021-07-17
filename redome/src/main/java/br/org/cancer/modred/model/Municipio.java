package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * Classe de persistencia de Municipio.
 * 
 * @author brunosousa
 *
 */
@Entity
@Table(name = "MUNICIPIO")
public class Municipio implements Serializable {

	private static final long serialVersionUID = -9097977928192779169L;
	
    @Id
    @Column(name = "MUNI_ID")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class})    
    private Long id;
    
    @Column(name = "MUNI_TX_NOME")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, PreCadastroMedicoView.Detalhe.class,
		DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})    
    private String descricao;
    
    @Column(name = "MUNI_TX_CODIGO_IBGE")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, PreCadastroMedicoView.Detalhe.class})    
    private String codigoIbge;
    
    @Column(name = "MUNI_TX_CODIGO_DNE")
    private String codigoDne;
    
	@ManyToOne
	@JoinColumn(name = "UF_SIGLA")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
		DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
		LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, PreCadastroMedicoView.Detalhe.class,
		DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})	
	private Uf uf;
	
	/**
	 * Construtor padrao.
	 */
	public Municipio() {
	}

	/**
	 * @param id
	 */
	public Municipio(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the codigoIbge
	 */
	public String getCodigoIbge() {
		return codigoIbge;
	}

	/**
	 * @param codigoIbge the codigoIbge to set
	 */
	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
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
	 * @return the uf
	 */
	public Uf getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(Uf uf) {
		this.uf = uf;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoDne == null) ? 0 : codigoDne.hashCode());
		result = prime * result + ((codigoIbge == null) ? 0 : codigoIbge.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Municipio other = (Municipio) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return descricao + ", " + uf;
	}


}
