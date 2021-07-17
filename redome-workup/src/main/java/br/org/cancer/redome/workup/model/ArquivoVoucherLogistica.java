package br.org.cancer.redome.workup.model;

import java.io.Serializable;

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
 * Vouchers, como hospedagem e passagens aéreas, anexado ao processo de workup do doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "ARQUIVO_VOUCHER_LOGISTICA")
@SequenceGenerator(name = "SQ_ARVL_ID", sequenceName = "SQ_ARVL_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArquivoVoucherLogistica implements Serializable {

	private static final long serialVersionUID = 159747301664836819L;
	
	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 257;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARVL_ID")
	@Column(name = "ARVL_ID")
	private Long id;

	@Column(name = "ARVL_TX_COMENTARIO")
	@Length(max = 100)
	private String comentario;

	@Column(name = "ARVL_TX_CAMINHO")
	@Length(max = 263)
	private String caminho;

	@Transient
	@Default
	private Boolean excluido = false;

	/**
	 * Indica o tipo do voucher
	 * 1-hospedagem, 2-transporte aereo.
	 */
	@Column(name = "ARVL_NR_TIPO")
	@NotNull
	private Long tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PELO_ID")
	@JsonIgnore
	private PedidoLogistica pedidoLogistica;
	

}