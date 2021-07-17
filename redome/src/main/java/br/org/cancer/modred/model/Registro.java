package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;

/**
 * Classe que representa o centro transplante (avaliadores e/ou transplantadores) do cadastro do paciente. Selecionado apennas
 * quando o mesmo é não aparentado {@see Paciente}.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "REGISTRO")
public class Registro implements Serializable {

	private static final long serialVersionUID = 7216523921918968048L;
	
	public static final Long ID_REGISTRO_REDOME = 1L;

	@Id
	@Column(name = "REGI_ID")
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	private Long id;

	@Column(name = "REGI_TX_NOME")
	@JsonView({	PedidoExameView.ListarTarefas.class, DoadorView.IdentificacaoCompleta.class, 
				DoadorView.IdentificacaoParcial.class, PedidoWorkupView.CadastrarResultado.class,
				PedidoColetaView.AgendamentoColeta.class, TarefaLogisticaView.Listar.class,
				TransportadoraView.Listar.class, AvaliacaoPrescricaoView.Detalhe.class, 
				PrescricaoView.DetalheDoador.class, SolicitacaoView.detalhe.class})
	private String nome;
	
	@Column(name = "REGI_TX_SIGLA")	
	private String sigla;
	
	@ManyToOne
	@JoinColumn(name = "PAIS_ID")
	private Pais pais;

	@Column(name = "REGI_NR_DON_POOL")	
	private Long donPool;
	
	public Registro() {}

	public Registro(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Registro(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

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
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}
	
	/**
	 * @param pais the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	

	/**
	 * @return the donPool
	 */
	public Long getDonPool() {
		return donPool;
	}

	/**
	 * @param donPool the donPool to set
	 */
	public void setDonPool(Long donPool) {
		this.donPool = donPool;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
		result = prime * result + ( ( pais == null ) ? 0 : pais.hashCode() );
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
		if (!( obj instanceof Registro )) {
			return false;
		}
		Registro other = (Registro) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else
			if (!nome.equals(other.nome)) {
				return false;
			}
		if (pais == null) {
			if (other.pais != null) {
				return false;
			}
		}
		else
			if (!pais.equals(other.pais)) {
				return false;
			}
		return true;
	}

}