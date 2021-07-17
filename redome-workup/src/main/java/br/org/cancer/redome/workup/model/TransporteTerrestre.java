package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dados de transporte terrestre associado a necessidade de logística identificada para determinado doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "TRANSPORTE_TERRESTRE")
@SequenceGenerator(name = "SQ_TRTE_ID", sequenceName = "SQ_TRTE_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransporteTerrestre implements Serializable {

	private static final long serialVersionUID = -8380808700325833021L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TRTE_ID")
	@Column(name = "TRTE_ID")
	private Long id;

	@Column(name = "TRTE_DT_DATA")
	@NotNull
	private LocalDateTime dataRealizacao;

	@Column(name = "TRTE_TX_ORIGEM")
	@Length(max = 200)
	private String origem;

	@Column(name = "TRTE_TX_DESTINO")
	@Length(max = 200)
	private String destino;

	@Column(name = "TRTE_TX_OBJETO_TRANSPORTE")
	@Length(max = 200)
	private String objetoTransportado;

	@Column(name = "TRTE_DT_CRIACAO")
	@NotNull
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Transient
	@Default
	private Boolean excluido = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PELO_ID")
	@JsonIgnore
	private PedidoLogistica pedidoLogistica;

	

}