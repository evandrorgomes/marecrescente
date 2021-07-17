package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.modred.model.security.Usuario;

/**
 * Registro de evoluções de busca.
 * 
 * @author Filipe Paes
 */
@Entity
@SequenceGenerator(name = "SQ_EVBU_ID", sequenceName = "SQ_EVBU_ID", allocationSize = 1)
@Table(name = "EVOLUCAO_BUSCA")
public class EvolucaoBusca implements Serializable {
	private static final long serialVersionUID = -8970534142357494639L;

	@Id
	@Column(name = "EVBU_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVBU_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "BUSC_ID")
	private Busca busca;

	@Column(name = "EVBU_DT_DATA")
	private LocalDateTime data;

	@Column(name = "EVBU_TX_EVENTO")
	private String evento;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "MATC_ID")
	private Match match;

	
	public EvolucaoBusca() {
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
	 * @return the busca
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * @param busca
	 *            the busca to set
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	/**
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento
	 *            the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param match
	 *            the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		EvolucaoBusca other = (EvolucaoBusca) obj;
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

}