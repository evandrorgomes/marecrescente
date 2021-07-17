package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO de um pedido adicional de workup
 * 
 * @author ergomes
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class PedidoAdicionalWorkupDTO implements Serializable {

	private static final long serialVersionUID = 8931305560045736288L;

	private Long id;
	
	private String descricao;
	
	private LocalDateTime dataCriacao;

	private List<ArquivoPedidoAdicionalWorkupDTO> arquivosPedidoAdicionalWorkup;

	public PedidoAdicionalWorkupDTO(PedidoAdicionalWorkup pedidoAdicionalWorkup) {
		this.id = pedidoAdicionalWorkup.getId();
		this.descricao = pedidoAdicionalWorkup.getDescricao();
		this.dataCriacao = pedidoAdicionalWorkup.getDataCriacao();

		if(!pedidoAdicionalWorkup.getArquivosPedidoAdicionalWorkup().isEmpty()) {
			this.arquivosPedidoAdicionalWorkup = pedidoAdicionalWorkup.getArquivosPedidoAdicionalWorkup().stream()
				.map(ArquivoPedidoAdicionalWorkupDTO::new).collect(Collectors.toList());
		}
	}

}