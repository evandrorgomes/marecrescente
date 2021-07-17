package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArquivoPedidoAdicionalWorkupDTO implements Serializable {

	private static final long serialVersionUID = -5048366769781693666L;

	private Long id;
	
	private String caminho;
	
	public ArquivoPedidoAdicionalWorkupDTO(ArquivoPedidoAdicionalWorkup arquivoPedidoAdicionalWorkup) {
		this.id = arquivoPedidoAdicionalWorkup.getId();
		this.caminho = arquivoPedidoAdicionalWorkup.getCaminho();
	}

}