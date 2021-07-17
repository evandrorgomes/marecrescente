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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe referente ao cancelamento da busca do paciente.
 * 
 * @author fillipe.queiroz
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_CABU_ID", sequenceName = "SQ_CABU_ID", allocationSize = 1)
@Table(name = "CANCELAMENTO_BUSCA")
public class CancelamentoBusca extends CriacaoAuditavel implements Serializable {

	private static final long serialVersionUID = -8929028867705425216L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CABU_ID")
	@Column(name = "CABU_ID")
	private Long id;

	@Column(name = "CABU_DT_EVENTO")
	private LocalDateTime dataEvento;

	@Column(name = "CABU_TX_ESPECIFIQUE")
	private String especifique;

	@ManyToOne
	@JoinColumn(name = "MOCB_ID")
	@NotNull
	private MotivoCancelamentoBusca motivoCancelamentoBusca;

	@OneToOne
	@JoinColumn(name = "BUSC_ID")
	@NotNull
	private Busca busca;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotNull
	private Usuario usuario;

	@Column(name = "CABU_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	public CancelamentoBusca() {
		super();
	}

	/**
	 * Id do cancelamento da busca.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Id do cancelamento da busca.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data do evento.
	 * 
	 * @return
	 */
	public LocalDateTime getDataEvento() {
		return dataEvento;
	}

	/**
	 * Data do evento.
	 * 
	 * @param dataEvento
	 */
	public void setDataEvento(LocalDateTime dataEvento) {
		this.dataEvento = dataEvento;
	}

	/**
	 * Especifique do cancelamento.
	 * 
	 * @return
	 */
	public String getEspecifique() {
		return especifique;
	}

	/**
	 * Especifique do cancelamento.
	 * 
	 * @param especifique
	 */
	public void setEspecifique(String especifique) {
		this.especifique = especifique;
	}

	/**
	 * Motivo de cancelamento.
	 * 
	 * @return
	 */
	public MotivoCancelamentoBusca getMotivoCancelamentoBusca() {
		return motivoCancelamentoBusca;
	}

	/**
	 * Motivo de cancelamento.
	 * 
	 * @param motivoCancelamentoBusca
	 */
	public void setMotivoCancelamentoBusca(MotivoCancelamentoBusca motivoCancelamentoBusca) {
		this.motivoCancelamentoBusca = motivoCancelamentoBusca;
	}

	/**
	 * Busca do paciente.
	 * 
	 * @return
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * Busca do paciente.
	 * 
	 * @param busca
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	/**
	 * Usuário que cancelou a busca.
	 * 
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Usuário que cancelou a busca.
	 * 
	 * @param usuario
	 */
	protected void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Data do cancelamento da busca.
	 * 
	 * @return
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Data do cancelamento da busca.
	 * 
	 * @param dataCriacao
	 */
	protected void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dataEvento == null ) ? 0 : dataEvento.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( motivoCancelamentoBusca == null ) ? 0 : motivoCancelamentoBusca.hashCode() );
		result = prime * result + ( ( especifique == null ) ? 0 : especifique.hashCode() );
		return result;
	}

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
		CancelamentoBusca other = (CancelamentoBusca) obj;
		if (dataEvento == null) {
			if (other.dataEvento != null) {
				return false;
			}
		}
		else
			if (!dataEvento.equals(other.dataEvento)) {
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
		if (motivoCancelamentoBusca == null) {
			if (other.motivoCancelamentoBusca != null) {
				return false;
			}
		}
		else
			if (!motivoCancelamentoBusca.equals(other.motivoCancelamentoBusca)) {
				return false;
			}
		if (especifique == null) {
			if (other.especifique != null) {
				return false;
			}
		}
		else
			if (!especifique.equals(other.especifique)) {
				return false;
			}
		return true;
	}

}