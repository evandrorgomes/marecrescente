package br.org.cancer.redome.workup.model;

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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe de persistência para avaliação do pedido de coleta.
 * Nesta entidade será gravada a avaliação que o médico do REDOME fará
 * em caso de pedido de coleta do doador.
 * @author Filipe Paes
 * 
 * @author ergomes
 *
 */
@Entity
@Table(name="AVALIACAO_PEDIDO_COLETA")
@SequenceGenerator(name = "SQ_AVPC_ID", sequenceName = "SQ_AVPC_ID", allocationSize = 1)
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AvaliacaoPedidoColeta implements Serializable {
	
	private static final long serialVersionUID = -7526609614339948073L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVPC_ID")
	@Column(name="AVPC_ID")
	private Long id;

	@Column(name="AVPC_DT_CRIACAO")
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name="AVPC_IN_PEDIDO_PROSSEGUE")
	private Boolean pedidoProssegue;

	@Column(name="AVPC_TX_JUSTIFICATIVA")
	private String justificativa;

	@ManyToOne
	@JoinColumn(name="AVRW_ID")
	private AvaliacaoResultadoWorkup avaliacaoResultadoWorkup;
	
	@Column(name="USUA_ID")
	private Long usuario;

	
		
}
