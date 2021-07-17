package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.org.cancer.redome.workup.model.domain.StatusPedidosWorkup;
import br.org.cancer.redome.workup.model.interfaces.ISolicitacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pedido de workup representa o pedido dos exames realizados para o doador
 * saber se está em condições de realizar a doação, ou seja, é uma espécie de 
 * check-up atualizado do doador escolhido como compatível para determinado 
 * paciente.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "PEDIDO_WORKUP")
@SequenceGenerator(name = "SQ_PEWO_ID", sequenceName = "SQ_PEWO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PedidoWorkup implements ISolicitacao, Serializable {

	private static final long serialVersionUID = -9083410770403234584L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEWO_ID")
	@Column(name = "PEWO_ID")
	private Long id;
	
	@Column(name = "PEWO_DT_CRIACAO")
	@NotNull
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Column(name = "PEWO_IN_TIPO")
	private Long tipo;
	
	@Column(name = "PEWO_IN_STATUS")
	@Default
	private Long status = StatusPedidosWorkup.INICIADO.getId();

	@Column(name = "SOLI_ID")
	private Long solicitacao;

	@Column(name = "CETR_ID_TRANSP")
	private Long centroTransplante;

	@Column(name = "CETR_ID_COLETA")
	private Long centroColeta;
	
	/**
	 * Data do exame informado pelo centro de coleta no plano de workup
	 */
	@Column(name = "PEWO_DT_EXAME")
	private LocalDate dataExame;

	/**
	 * Data do resulado informado pelo centro de coleta no plano de workup
	 */
	@Column(name = "PEWO_DT_RESULTADO")
	private LocalDate dataResultado;
	
	/**
	 * Data da internacao informada pelo centro de coleta no plano de workup
	 */
	@Column(name = "PEWO_DT_INTERNACAO")
	private LocalDate dataInternacao;

	/**
	 * Data da coleta informada pelo centro de coleta no plano de workup
	 */
	@Column(name = "PEWO_DT_COLETA")
	private LocalDate dataColeta;

	/**
	 * Data de condicionamento do paciente no plano de workup.
	 */
	@Column(name = "PEWO_DT_CONDICIONAMENTO")
	private LocalDate dataCondicionamento;

	/**
	 * Data planejada de infusão do plano de workup.
	 */
	@Column(name = "PEWO_DT_INFUSAO")
	private LocalDate dataInfusao;
	
	/**
	 * Data em que o centro de coleta insere o plano de workup.
	 */
	@Column(name = "PEWO_DT_CRIACAO_PLANO")
	private LocalDateTime dataCriacaoPlano;

	/**
	 * Criopreservação do produto - não 0 e sim 1.
	 */
	@Column(name = "PEWO_IN_CRIOPRESERVACAO")
	private Boolean criopreservacao;
	
	@Column(name = "PEWO_TX_OBSERVACAO")
	@Lob
	private String observacaoPlanoWorkup;
	
	/**
	 * 	Campo livre de observaçao que podera ser informado aprovação do plano de workup.
	 */
	@Column(name = "PEWO_TX_OBS_APROVA_PLANO")
	@Lob
	private String observacaoAprovaPlanoWorkup;
	
	@OneToMany(mappedBy = "pedidoWorkup", cascade = CascadeType.ALL)
	@NotAudited
	private List<ArquivoPedidoWorkup> arquivos;
	
	@OneToMany(mappedBy="pedidoWorkup", cascade=CascadeType.ALL)
	@NotAudited
	private List<PedidoAdicionalWorkup> pedidosAdicionaisWorkup;

	/**
	 * Data exame médico 1 do plano de workup.
	 */
	@Column(name = "PEWO_DT_EXAME_MEDICO_1")
	private LocalDateTime dataExameMedico1;


	/**
	 * Data exame médico 2 do plano de workup.
	 */
	@Column(name = "PEWO_DT_EXAME_MEDICO_2")
	private LocalDateTime dataExameMedico2;

	/**
	 * Data repetição BTHCG do plano de workup.
	 */
	@Column(name = "PEWO_DT_REPETICAO_BTHCG")
	private LocalDateTime dataRepeticaoBthcg;

	/**
	 * Setor ou andar do plano de workup.
	 */
	@Column(name = "PEWO_TX_SETOR_ANDAR")
	private String setorAndar;

	/**
	 * Nome para procurar do plano de workup.
	 */
	@Column(name = "PEWO_TX_PROCURAR_POR")
	private String procurarPor;

	/**
	 * Doador esta em jejum do plano de workup.
	 */
	@Column(name = "PEWO_IN_JEJUM")
	private Boolean doadorEmJejum;

	/**
	 * Quantas horas em jejum do plano de workup.
	 */
	@Column(name = "PEWO_HR_JEJUM")
	private Integer horasEmJejum;

	/**
	 * Informações adicionais do plano de workup.
	 */
	@Column(name = "PEWO_TX_INFORMACOES_ADICIONAIS")
	@Lob
	private String informacoesAdicionais;
	
	@Override
	public Long getIdCentroTransplante() {		
		return centroTransplante;
	}
	
	@Override
	public Long getIdCentroColeta() {
		return centroColeta;
	}

}
