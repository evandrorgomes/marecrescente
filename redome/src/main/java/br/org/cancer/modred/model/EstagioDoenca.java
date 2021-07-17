package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe de persistencia de EstagioDoenca.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "ESTAGIO_DOENCA")
public class EstagioDoenca implements Serializable {

	private static final long serialVersionUID = 3704146689770612427L;
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "ESDO_ID")
	@JsonView(PacienteView.Rascunho.class)
	private Long id;
	/**
	 * Descrição do estagio.
	 */
	@Column(name = "ESDO_TX_DESCRICAO")
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class })
	private String descricao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "CID_ESTAGIO_DOENCA",
				joinColumns =
	{ @JoinColumn(name = "ESDO_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "CID_ID") })
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	private List<Cid> cids;

	/**
	 * Construtor padrão.
	 */
	public EstagioDoenca() {
		super();
	}

	/**
	 * Construtor com id e descricao do estágio.
	 * 
	 * @param id do estagio
	 * @param descricao do estagio
	 */
	public EstagioDoenca(Long id, String descricao) {
		this();
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * Recupera a primary key do estagio da doenca.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a primary key do estagio da doenca.
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
	 * @return the cids
	 */
	public List<Cid> getCids() {
		return cids;
	}

	/**
	 * @param cids the cids to set
	 */
	public void setCids(List<Cid> cids) {
		this.cids = cids;
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
		if (!( obj instanceof EstagioDoenca )) {
			return false;
		}
		EstagioDoenca other = (EstagioDoenca) obj;

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