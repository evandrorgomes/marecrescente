package br.org.cancer.redome.courier.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pedido de transporte de material gerado para um processo de logística. Neste pedido são definidas a data de retirada e a
 * transportadora responsável por levar o material até o centro de transplante.
 * 
 * @author Pizão.
 */
@Entity
@SequenceGenerator(name = "SQ_PETR_ID", sequenceName = "SQ_PETR_ID", allocationSize = 1)
@Table(name = "PEDIDO_TRANSPORTE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PedidoTransporte implements Serializable {

	private static final long serialVersionUID = -6199054113331385050L;

	@Id
	@Column(name = "PETR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PETR_ID")
	private Long id;

	@NotNull
	@Column(name = "PETR_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

//	@CustomFuture(today = true)
	@Column(name = "PETR_HR_PREVISTA_RETIRADA")
	private LocalDateTime horaPrevistaRetirada;

	@Column(name = "USUA_ID_RESPONSAVEL")
	private Long usuarioResponsavel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAN_ID")
	private Transportadora transportadora;

	@OneToMany(mappedBy = "pedidoTransporte", cascade = CascadeType.ALL)
	private List<ArquivoPedidoTransporte> arquivoPedidoTransporte;

	@Column(name = "PELO_ID")
	@NotNull
	private Long pedidoLogistica;

	@ManyToOne
	@JoinColumn(name = "STPT_ID")
	@NotNull
	private StatusPedidoTransporte status;

	@Column(name = "PETR_DADOS_VOO")
	private String dadosVoo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUR_ID")
	private Courier courier;

	@Column(name = "PETR_DT_RETIRADA")
	private LocalDateTime dataRetirada;

	@Column(name = "PETR_DT_ENTREGA")
	private LocalDateTime dataEntrega;
	
	@Column(name = "SOLI_ID")
	private Long solicitacao;
	
	@Column(name = "FOCE_ID")
	private Long fonteCelula;
	
	/**
	 * Propriedade utilizada no método de confirmar o transporte e no metodo de criar o log 
	 *  para informar o status anterior. 
	 */
	@Transient
	private StatusPedidoTransporte statusAnterior;
	
	
	

}