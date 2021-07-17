package br.org.cancer.redome.courier.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Classe de DTO retorno com id do objeto inserido e mensagem de retorno.
 * 
 * @author Fillipe Queiroz
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RetornoInclusaoDTO {

	private Long idObjeto;
	private String mensagem;
	private String stringRetorno;
	private Long status;


	public static class RetornoInclusaoDTOBuilder {
		
		private Long idObjeto;
		private String mensagem;
		private String stringRetorno;
		private Long status = StatusRetornoInclusaoDTO.SUCESSO.getId();
		
		private RetornoInclusaoDTOBuilder() {}
		
		public RetornoInclusaoDTOBuilder setIdObjeto(Long idObjeto) {
			this.idObjeto = idObjeto;
			return this;
		}
		
		public RetornoInclusaoDTOBuilder setMensagem(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}
		
		public RetornoInclusaoDTOBuilder setStringRetorno(String stringRetorno) {
			this.stringRetorno = stringRetorno;
			return this;
		}
		
		public RetornoInclusaoDTOBuilder setStatus(StatusRetornoInclusaoDTO status) {
			if (status != null) {
				this.status = status.getId();
			}
			return this;
		}
				
	}

	
}
