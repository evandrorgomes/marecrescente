package br.org.cancer.redome.workup.dto;

import java.util.List;

import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe de DTO retorno com id do objeto inserido e mensagem de retorno.
 * 
 * @author ergomes
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class RetornoInclusaoExameAdicionalDTO {

	private String mensagem;
	private List<ArquivoPedidoAdicionalWorkup> arquivosExamesAdicionais;
}
