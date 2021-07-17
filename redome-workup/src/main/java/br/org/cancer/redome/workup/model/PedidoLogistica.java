package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.org.cancer.redome.workup.model.interfaces.ISolicitacao;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Pedido de logística associado ao workup do doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "PEDIDO_LOGISTICA")
@SequenceGenerator(name = "SQ_PELO_ID", sequenceName = "SQ_PELO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PELO_IN_TIPO", discriminatorType = DiscriminatorType.INTEGER)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
public abstract class PedidoLogistica implements ISolicitacao, Serializable {

	private static final long serialVersionUID = -8200479769690099391L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PELO_ID")
	@Column(name = "PELO_ID")
	protected Long id;

	@Column(name = "PELO_DT_CRIACAO")
	@NotNull
	protected LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "PELO_DT_ATUALIZACAO")
	protected LocalDateTime dataAtualizacao = LocalDateTime.now();
	
	@Column(name = "USUA_ID_RESPONSAVEL")
	protected Long usuarioResponsavel;
	
	@Column(name = "PELO_IN_TIPO", insertable = false, updatable = false)
	protected Integer tipo;

	@ManyToOne
	@JoinColumn(name = "STPL_ID")
	protected StatusPedidoLogistica status;

	@Transient
	protected Long solicitacao;
	
	protected PedidoLogistica(Long id, @NotNull LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
			Long usuarioResponsavel, Integer tipo, StatusPedidoLogistica status, Long solicitacao) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
		this.usuarioResponsavel = usuarioResponsavel;
		this.tipo = tipo;
		this.status = status;
		this.solicitacao = solicitacao;
	}
		
	
}