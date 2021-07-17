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
 * Listagem de itens de checklist de busca.
 * Através desta classe serão marcados os itens feitos em cada passo de 
 * algo feito para a busca.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name="BUSCA_CHECKLIST")
@SequenceGenerator(name = "SQ_BUCH_ID", sequenceName = "SQ_BUCH_ID", allocationSize = 1)
public class BuscaChecklist implements Serializable {
	private static final long serialVersionUID = 2168489444290577744L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BUCH_ID")
	@Column(name="BUCH_ID")
	private Long id;

	@Column(name="BUCH_DT_CRIACAO")
	private LocalDateTime dataCriacao;

	@Column(name="BUCH_DT_VISTO")
	private LocalDateTime dataHoraVisto;

	@Column(name="BUCH_IN_VISTO")
	private Boolean visto;

	@ManyToOne
	@JoinColumn(name="USUA_ID")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="BUSC_ID")
	private Busca busca;
	
	@Column(name="BUCH_NR_AGE")
	private Integer ageEmDias;

	@ManyToOne
	@JoinColumn(name="TIBC_ID")
	private TipoBuscaChecklist tipoBuscaChecklist;
	
	@ManyToOne
	@JoinColumn(name="MATC_ID")
	private Match match;

	
	public BuscaChecklist() {
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
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the dataHoraVisto
	 */
	public LocalDateTime getDataHoraVisto() {
		return dataHoraVisto;
	}

	/**
	 * @param dataHoraVisto the dataHoraVisto to set
	 */
	public void setDataHoraVisto(LocalDateTime dataHoraVisto) {
		this.dataHoraVisto = dataHoraVisto;
	}

	/**
	 * @return the visto
	 */
	public Boolean getVisto() {
		return visto;
	}

	/**
	 * @param visto the visto to set
	 */
	public void setVisto(Boolean visto) {
		this.visto = visto;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the busca
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * @param busca the busca to set
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	/**
	 * @return the tipoBuscaChecklist
	 */
	public TipoBuscaChecklist getTipoBuscaChecklist() {
		return tipoBuscaChecklist;
	}

	/**
	 * @param tipoBuscaChecklist the tipoBuscaChecklist to set
	 */
	public void setTipoBuscaChecklist(TipoBuscaChecklist tipoBuscaChecklist) {
		this.tipoBuscaChecklist = tipoBuscaChecklist;
	}

	/**
	 * @return the ageEmDias
	 */
	public Integer getAgeEmDias() {
		return ageEmDias;
	}

	/**
	 * @param ageEmDias the ageEmDias to set
	 */
	public void setAgeEmDias(Integer ageEmDias) {
		this.ageEmDias = ageEmDias;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ageEmDias == null) ? 0 : ageEmDias.hashCode());
		result = prime * result + ((busca == null) ? 0 : busca.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((dataHoraVisto == null) ? 0 : dataHoraVisto.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((match == null) ? 0 : match.hashCode());
		result = prime * result + ((tipoBuscaChecklist == null) ? 0 : tipoBuscaChecklist.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((visto == null) ? 0 : visto.hashCode());
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
		BuscaChecklist other = (BuscaChecklist) obj;
		if (ageEmDias == null) {
			if (other.ageEmDias != null) {
				return false;
			}
		} 
		else if (!ageEmDias.equals(other.ageEmDias)) {
			return false;
		}
		if (busca == null) {
			if (other.busca != null) {
				return false;
			}
		}
		else if (!busca.equals(other.busca)) {
			return false;
		}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else if (!dataCriacao.equals(other.dataCriacao)) {
			return false;
		}
		if (dataHoraVisto == null) {
			if (other.dataHoraVisto != null) {
				return false;
			}
		} 
		else if (!dataHoraVisto.equals(other.dataHoraVisto)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		if (match == null) {
			if (other.match != null) {
				return false;
			}
		}
		else if (!match.equals(other.match)) {
			return false;
		}
		if (tipoBuscaChecklist == null) {
			if (other.tipoBuscaChecklist != null) {
				return false;
			}
		}
		else if (!tipoBuscaChecklist.equals(other.tipoBuscaChecklist)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		}
		else if (!usuario.equals(other.usuario)) {
			return false;
		}
		if (visto == null) {
			if (other.visto != null) {
				return false;
			}
		}
		else if (!visto.equals(other.visto)) {
			return false;
		}
		return true;
	}

}

