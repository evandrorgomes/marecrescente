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
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Representa o serviço a ser pago ou cobrado para os solicitações para determinado paciente ou doador,
 * podendo estes serem nacionais ou internacionais.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PAGA_ID", sequenceName = "SQ_PAGA_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "PAGAMENTO")
public class Pagamento implements Serializable {
	
	private static final long serialVersionUID = 222824027604714934L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PAGA_ID")
	@Column(name = "PAGA_ID")
	private Long id;

	@Column(name = "PAGA_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;
	
	@Column(name = "PAGA_DT_ATUALIZACAO")
	@NotNull
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "PAGA_IN_COBRACA")
	private Boolean cobranca;
	
	@Column(name = "PAGA_ID_OBEJTORELACIONADO")
	private Long idObjetoRelacionado;
			
	@ManyToOne
    @JoinColumn(name = "STPA_ID")
	@NotNull
	private StatusPagamento statusPagamento;
	
	@ManyToOne
    @JoinColumn(name = "TISE_ID")
	@NotNull
	private TipoServico tipoServico;
	
	@ManyToOne
    @JoinColumn(name = "REGI_ID")
	@NotNull
	private Registro registro;
	
	@ManyToOne
    @JoinColumn(name = "MATC_ID")
	private Match match;
	
	public Pagamento() {
		super();
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
	 * @return the cobranca
	 */
	public Boolean getCobranca() {
		return cobranca;
	}

	
	/**
	 * @param cobranca the cobranca to set
	 */
	public void setCobranca(Boolean cobranca) {
		this.cobranca = cobranca;
	}

	
	/**
	 * @return the idObjetoRelacionado
	 */
	public Long getIdObjetoRelacionado() {
		return idObjetoRelacionado;
	}

	
	/**
	 * @param idObjetoRelacionado the idObjetoRelacionado to set
	 */
	public void setIdObjetoRelacionado(Long idObjetoRelacionado) {
		this.idObjetoRelacionado = idObjetoRelacionado;
	}

	
	/**
	 * @return the statusPagamento
	 */
	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	
	/**
	 * @param statusPagamento the statusPagamento to set
	 */
	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	
	/**
	 * @return the tipoServico
	 */
	public TipoServico getTipoServico() {
		return tipoServico;
	}

	
	/**
	 * @param tipoServico the tipoServico to set
	 */
	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	
	/**
	 * @return the registro
	 */
	public Registro getRegistro() {
		return registro;
	}

	
	/**
	 * @param registro the registro to set
	 */
	public void setRegistro(Registro registro) {
		this.registro = registro;
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

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( statusPagamento == null ) ? 0 : statusPagamento.hashCode() );
		result = prime * result + ( ( tipoServico == null ) ? 0 : tipoServico.hashCode() );
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
		if (!( obj instanceof Pagamento )) {
			return false;
		}
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (statusPagamento == null) {
			if (other.statusPagamento != null) {
				return false;
			}
		}
		else
			if (!statusPagamento.equals(other.statusPagamento)) {
				return false;
			}
		if (tipoServico == null) {
			if (other.tipoServico != null) {
				return false;
			}
		}
		else
			if (!tipoServico.equals(other.tipoServico)) {
				return false;
			}
		return true;
	}
	

}