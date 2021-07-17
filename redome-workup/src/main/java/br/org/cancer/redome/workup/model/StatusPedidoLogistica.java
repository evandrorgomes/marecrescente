package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.cancer.redome.workup.model.domain.StatusPedidosLogistica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Status do pedido de logística.
 *  
 * @author Pizão
 */
@Entity
@Table(name="STATUS_PEDIDO_LOGISTICA")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedidoLogistica implements Serializable {
	
	private static final long serialVersionUID = -4392322436034173896L;
		
	public static final Long ABERTO = 1L;
	public static final Long FECHADO = 2L;
	public static final Long CANCELADO = 4L;
	
	@Id
	@Column(name="STPL_ID")
	private Long id;

	@Column(name="STPL_TX_DESCRICAO")
	private String descricao;

	public StatusPedidoLogistica(StatusPedidosLogistica status) {
		this(status.getId());
	}
	
	public StatusPedidoLogistica(Long id) {
		this.id = id;
	}
	
	

}