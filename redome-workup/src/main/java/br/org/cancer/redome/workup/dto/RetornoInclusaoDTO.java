package br.org.cancer.redome.workup.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe de DTO retorno com id do objeto inserido e mensagem de retorno.
 * 
 * @author Fillipe Queiroz
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class RetornoInclusaoDTO {

	private Long idObjeto;
	private String mensagem;
	private String stringRetorno;
	@Default
	private Long status = StatusRetornoInclusaoDTO.SUCESSO.getId();

}
