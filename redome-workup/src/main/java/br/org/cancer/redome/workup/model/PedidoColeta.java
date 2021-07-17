package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.redome.workup.model.domain.StatusPedidosColeta;
import br.org.cancer.redome.workup.observable.EntityObservable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de um pedido de coleta.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PECL_ID", sequenceName = "SQ_PECL_ID", allocationSize = 1)
@Table(name="PEDIDO_COLETA")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PedidoColeta extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	/**
	 * Identificador do pedido de coleta.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PECL_ID")
	@Column(name = "PECL_ID")
	private Long id;

	/**
	 * Data da criação do pedido.
	 */
	@Column(name = "PECL_DT_CRIACAO")
	@NotNull
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	/**
	 * Data em que o doador deverá estar disponível no centro de coleta.
	 */
	@Column(name = "PECL_DT_DISPONIBILIDADE_DOADOR")
	private LocalDate dataDisponibilidadeDoador;

	/**
	 * Data em que o doador será liberado pelo centro de coleta.
	 */
	@Column(name = "PECL_DT_LIBERACAO_DOADOR")
	private LocalDate dataLiberacaoDoador;

	/**
	 * Indica se necessita de logistica para o doador.
	 */
	@Column(name = "PECL_IN_LOGISTICA_DOADOR")
	private Boolean necessitaLogisticaDoador;

	/**
	 * Indica se necessita de logistica para o material.
	 */
	@Column(name = "PECL_IN_LOGISTICA_MATERIAL")
	private Boolean necessitaLogisticaMaterial;

	/**
	 * Centro de transplante responsável pela coleta.
	 */
	@Column(name = "CETR_ID_COLETA")
	private Long centroColeta;
		
	/**
	 * Status do pedido.
	 */
	@Column(name = "STPC_ID")
	@Default
	private Long status = StatusPedidosColeta.INICIADO.getId();
	
	@Column(name = "SOLI_ID")
	private Long solicitacao;
	
	@Column(name = "PECL_DT_COLETA")
	private LocalDateTime dataColeta;
			
	/**
	 * Tipo de produto selecionado 0-MO | 1-PBSC | DLI
	 */
	@Column(name = "PECL_IN_PRODUDO")
	private Long tipoProdudo;
	
	/**
	 * Data e hora de início do G-CSF do doador.
	 */
	@Column(name = "PECL_DT_INICIO_GCSF")
	private LocalDateTime dataInicioGcsf;
	
	/**
	 * Data e hora da Internação do doador.
	 */
	@Column(name = "PECL_DT_INTERNACAO")
	private LocalDateTime dataInternacao;
	
	/**
	 * Setor ou andar do endereço da G-CSF.
	 */
	@Column(name = "PECL_TX_GCSF_SETOR_ANDAR")
	private String gcsfSetorAndar;
	
	/**
	 * Procurar por nome indicação no endereço da G-CSF.
	 */
	@Column(name = "PECL_TX_GCSF_PROCURAR_POR")
	private String gcsfProcurarPor;
	
	/**
	 * Setor ou andar do endereço da Internacao.
	 */
	@Column(name = "PECL_TX_INTERNACAO_SETOR_ANDAR")
	private String internacaoSetorAndar;
	
	/**
	 * Procurar por nome indicação no endereço de Internacao.
	 */
	@Column(name = "PECL_TX_INTERNACAO_PROCURAR_POR")
	private String internacaoProcurarPor;
	
	/**
	 * Flag doador em jejum informado no agendamento de coleta.
	 */
	@Column(name = "PECL_IN_JEJUM")
	private Boolean estaEmJejum;
	
	/**
	 * Define a quantidade de horas em jejum.
	 */
	@Column(name = "PECL_NR_HR_JEJUM")
	private Long quantasHorasEmJejum;

	/**
	 * Define se o doador esta totalmente em jejum.
	 */
	@Column(name = "PECL_IN_JEJUM_TOTAL")
	private Boolean estaTotalmenteEmJejum;

	/**
	 * Informações adicionais sobre os agendamento.
	 */
	@Column(name = "PECL_TX_INFORMACOES_ADICIONAIS")
	private String informacoesAdicionais;
	
	
	
	
}
