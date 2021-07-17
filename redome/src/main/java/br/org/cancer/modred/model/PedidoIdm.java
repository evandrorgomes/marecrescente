package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoExameView;

/**
 * Representa o pedido de idm solicitado para determinado doador,
 * podendo estes serem nacionais ou internacionais.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PEID_ID", sequenceName = "SQ_PEID_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "PEDIDO_IDM")
public class PedidoIdm implements Serializable {
		
	private static final long serialVersionUID = -5535469395959493763L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEID_ID")
	@Column(name = "PEID_ID")
	@JsonView(PedidoExameView.ListarTarefas.class)
	private Long id;

	@Column(name = "PEID_DT_CRIACAO")
	@NotNull
	@JsonView(PedidoExameView.ListarTarefas.class)
	private LocalDateTime dataCriacao;
	
	@Column(name = "PEID_DT_CANCELAMENTO")
	private LocalDate dataCancelamento;
	
	
	@ManyToOne
    @JoinColumn(name = "SOLI_ID")
	@JsonView(PedidoExameView.ListarTarefas.class)
    private Solicitacao solicitacao;
	
	@ManyToOne
    @JoinColumn(name = "STPI_ID")
	@NotNull
	@JsonView(PedidoExameView.ListarTarefas.class)
	private StatusPedidoIdm statusPedidoIdm;
	
	@NotAudited
	@OneToMany(mappedBy="pedidoIdm")
	private List<ArquivoPedidoIdm> arquivosResultado;
	
	public PedidoIdm() {
		super();
	}
	
	/**
	 * Construtor para facilitar a instanciação do 
	 * pedido já com o ID preenchido.
	 * 
	 * @param pedidoIdmId ID do pedido de IDM a ser instanciado.
	 */
	public PedidoIdm(Long pedidoIdmId) {
		super();
		this.id = pedidoIdmId;
	}

	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	
	/**
	 * @return the statusPedidoIdm
	 */
	public StatusPedidoIdm getStatusPedidoIdm() {
		return statusPedidoIdm;
	}

	
	/**
	 * @param statusPedidoIdm the statusPedidoIdm to set
	 */
	public void setStatusPedidoIdm(StatusPedidoIdm statusPedidoIdm) {
		this.statusPedidoIdm = statusPedidoIdm;
	}

	/**
	 * @return the dataCancelamento
	 */
	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	/**
	 * @param dataCancelamento the dataCancelamento to set
	 */
	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	
	

	/**
	 * @return the arquivosResultado
	 */
	public List<ArquivoPedidoIdm> getArquivosResultado() {
		return arquivosResultado;
	}

	/**
	 * @param arquivosResultado the arquivosResultado to set
	 */
	public void setArquivosResultado(List<ArquivoPedidoIdm> arquivosResultado) {
		this.arquivosResultado = arquivosResultado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( solicitacao == null ) ? 0 : solicitacao.hashCode() );
		result = prime * result + ( ( statusPedidoIdm == null ) ? 0 : statusPedidoIdm.hashCode() );
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
		if (!( obj instanceof PedidoIdm )) {
			return false;
		}
		PedidoIdm other = (PedidoIdm) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		}
		else
			if (!solicitacao.equals(other.solicitacao)) {
				return false;
			}
		if (statusPedidoIdm == null) {
			if (other.statusPedidoIdm != null) {
				return false;
			}
		}
		else
			if (!statusPedidoIdm.equals(other.statusPedidoIdm)) {
				return false;
			}
		return true;
	}

}