package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Registro do log de evolução que podem ocorrer em vários pontos do sistema.
 * Desde a criação da busca até a coleta, os pontos que geram entrada para o histórico
 * serão definidos conforme o andamento do projeto.
 * 
 * @author Pizão.
 */
@Entity
@SequenceGenerator(name = "SQ_LOEV_ID", sequenceName = "SQ_LOEV_ID", allocationSize = 1)
@Table(name = "LOG_EVOLUCAO")
public class LogEvolucao implements Serializable {
	private static final long serialVersionUID = 9138270710831192742L;

	@Id
	@Column(name = "LOEV_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOEV_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	private Paciente paciente;

	@Column(name = "LOEV_DT_DATA")
	private LocalDateTime data;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOEV_IN_TIPO_EVENTO")
	private TipoLogEvolucao tipoEvento;

	@Column(name = "LOEV_TX_PARAMETROS")
	private String parametros;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinTable(	name = "PERFIL_LOG_EVOLUCAO",
				joinColumns =
	{ @JoinColumn(name = "LOEV_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "PERF_ID") })
	private List<Perfil> perfisExcluidos;

	@Transient
	private Long totalRegistros;
	
	public LogEvolucao() {}

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
		LogEvolucao other = (LogEvolucao) obj;
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public TipoLogEvolucao getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoLogEvolucao tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * Serve para compor a mensagem (descrição) do log, quando
	 * o mesmo for exibido.
	 * 
	 * @return parametros para a mensagem.
	 */
	public String[] getParametros() {
		if (parametros != null) {
			return parametros.split(";");
		}
		return null;
	}

	/**
	 * Seta os paramentros.
	 * 
	 * @param parametros parametros a serem gravados
	 */
	public void setParametros(String[] parametros) {
		if (parametros != null) {
			this.parametros = String.join(";", parametros);
		}
		else {
			this.parametros = null;
		}
	}

	/**
	 * @return the perfisExcluidos
	 */
	public List<Perfil> getPerfisExcluidos() {
		return perfisExcluidos;
	}

	/**
	 * @param perfisExcluidos the perfisExcluidos to set
	 */
	public void setPerfisExcluidos(List<Perfil> perfisExcluidos) {
		this.perfisExcluidos = perfisExcluidos;
	}

	/**
	 * @return the totalRegistros
	 */
	public Long getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	
}