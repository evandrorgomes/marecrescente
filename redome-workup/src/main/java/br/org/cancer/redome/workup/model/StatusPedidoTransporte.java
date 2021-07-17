package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Status do pedido de transporte.
 * 
 * @author ergomes
 */
@Entity
@Table(name = "STATUS_PEDIDO_TRANSPORTE")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatusPedidoTransporte implements Serializable {

	private static final long serialVersionUID = -3050032874753348315L;

	@Id
	@Column(name = "STPT_ID")
	private Long id;

	@Column(name = "STPT_TX_DESCRICAO")
	private String descricao;

}