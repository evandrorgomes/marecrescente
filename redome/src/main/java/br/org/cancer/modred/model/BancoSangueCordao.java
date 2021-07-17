package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.UsuarioView;

/**
 * Classe que representa a tabela de Banco de Sangue de cordão.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "BANCO_SANGUE_CORDAO")
@SequenceGenerator(name = "SQ_BASC_ID", sequenceName = "SQ_BASC_ID", allocationSize = 1)
public class BancoSangueCordao implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -244771304842110462L;

	@Id
	@Column(name = "BASC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BASC_ID")
	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				UsuarioView.Listar.class, UsuarioView.Consultar.class,  SolicitacaoView.detalhe.class})
	private Long id;

	@Column(name = "BASC_TX_SIGLA")
	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				PedidoColetaView.AgendamentoColeta.class, UsuarioView.Listar.class,
				UsuarioView.Consultar.class})
	@NotNull
	private String sigla;

	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, 
				TransportadoraView.AgendarTransporte.class, UsuarioView.Listar.class,
				UsuarioView.Consultar.class, PrescricaoView.DetalheDoador.class,  SolicitacaoView.detalhe.class})
	@Column(name = "BASC_TX_NOME")
	@NotNull
	private String nome;
	
	@JsonView({	TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class,  SolicitacaoView.detalhe.class })
	@Column(name = "BASC_TX_ENDERECO")
	@Length(max = 1000)
	@NotNull
	private String endereco;
	
	@Column(name = "BASC_TX_CONTATO")
	@Length(max = 1000)
	@NotNull
	@JsonView(SolicitacaoView.detalhe.class)
	private String contato;
	
	@Column(name = "BASC_NR_ID_BRASILCORD")
	@NotNull
	private Long idBancoSangueCordao;
	

	public BancoSangueCordao() {
	}
	
	public BancoSangueCordao(Long id) {
		this.id = id;
	}
	
	public BancoSangueCordao(Long id, String nome) {
		this(id);
		this.nome = nome;
	}

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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
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
		if (!( obj instanceof BancoSangueCordao )) {
			return false;
		}
		BancoSangueCordao other = (BancoSangueCordao) obj;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Long getIdBancoSangueCordao() {
		return idBancoSangueCordao;
	}

	public void setIdBancoSangueCordao(Long idBancoSangueCordao) {
		this.idBancoSangueCordao = idBancoSangueCordao;
	}

	@Override
	public BancoSangueCordao clone() {
		BancoSangueCordao clone = new BancoSangueCordao();
		clone.setNome(this.nome);
		clone.setSigla(this.sigla);
		clone.setEndereco(this.endereco);
		clone.setContato(this.contato);
		clone.setIdBancoSangueCordao(this.idBancoSangueCordao);
		return clone;
	}
	
}