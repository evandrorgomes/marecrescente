package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entidade de recebimento da coleta retirada do doador. Nesta entidade há 
 * o registro se houve o recebimento da amostra assim como o total do que foi coletado
 * e o destino dado à coleta.
 * 
 * @author ergomes
 */
@Entity
@Table(name="RECEBIMENTO_COLETA")
@SequenceGenerator(name = "SQ_RECO_ID", sequenceName = "SQ_RECO_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class RecebimentoColeta implements Serializable {

	private static final long serialVersionUID = 9154736452793159849L;

	@Id
	@Column(name="RECE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RECO_ID")
	private Long id;

	@NotNull
	@Column(name="RECE_DT_RECEBIMENTO")
	private LocalDate dataRecebimento;

	@Column(name="RECE_DT_INFUSAO")
	private LocalDate dataInfusao;
	
	@Column(name="RECE_DT_PREVISTA_INFUSAO")
	private LocalDate dataPrevistaInfusao;
	
	@Column(name="RECE_DT_DESCARTE")
	private LocalDate dataDescarte;
	
	@Column(name="RECE_IN_RECEBEU")
	private Boolean recebeuColeta;

	@Column(name="RECE_NR_TOTAL_CD34")
	private Float totalTotalCd34;

	@Column(name="RECE_NR_TOTAL_TCN")
	private Float totalTotalTcn;

	@Column(name="RECE_TX_JUST_CONGELAMENTO")
	private String justificativaCongelamento;

	@Column(name="RECE_TX_JUST_DESCARTE")
	private String justificativaDescarte;

	@Column(name="RECE_NR_NUMERO_BOLSAS")
	private Long numeroDeBolsas;

	@Column(name="RECE_NR_VOLUME_PRODUTO")
	private Long volume;

	@Column(name="RECE_IN_COLETA_DEACORDO_PRESCRICAO")
	private Boolean produtoColetadoDeAcordo;

	@Column(name="RECE_TX_MOTIVO_COLETA_INCORRETA")
	private String motivoProdutoColetadoIncorreto;

	@Column(name="RECE_IN_INDENTIFICACAO_DOADOR_CONFERE")
	private Boolean identificacaoDoadorConfere;

	@Column(name="RECE_TX_MOTIVO_IDENTIFICACAO_DOADOR_INCORRETO")
	private String motivoIdentificacaoDoadorIncorreta;

	@Column(name="RECE_IN_ACONDICIONADO_CORRETO")
	private Boolean produdoAcondicionadoCorreto;

	@Column(name="RECE_TX_MOTIVO_ACONDICIONADO_INCORRETO")
	private String motivoProdudoAcondicionadoIncorreto;

	@Column(name="RECE_IN_EVENTO_ADVERSO")
	private Boolean produdoEventoAdverso;

	@Column(name="RECE_TX_DESCREVA_EVENTO_ADVERSO")
	private String descrevaProdudoEventoAdverso;

	@Column(name="RECE_TX_COMENTARIO_ADICIONAL")
	private String comentarioAdicional;
	
	@ManyToOne
	@JoinColumn(name="DECO_ID")
	private DestinoColeta destinoColeta;

	@ManyToOne
	@JoinColumn(name="FOCE_ID")
	private FonteCelula fonteCelula;

	@ManyToOne
	@JoinColumn(name="PECL_ID")
	private PedidoColeta pedidoColeta;

}