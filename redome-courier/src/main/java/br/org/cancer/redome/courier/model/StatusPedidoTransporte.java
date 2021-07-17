package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Status do pedido de transporte.
 * 
 * @author Queiroz
 */
@Entity
@Table(name = "STATUS_PEDIDO_TRANSPORTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusPedidoTransporte implements Serializable {

	private static final long serialVersionUID = 8902076051087871927L;

	@Id
	@Column(name = "STPT_ID")
	private Long id;

	@Column(name = "STPT_TX_DESCRICAO")
	private String descricao;


}