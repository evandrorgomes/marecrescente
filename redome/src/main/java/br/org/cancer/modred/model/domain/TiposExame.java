package br.org.cancer.modred.model.domain;



import java.util.stream.Stream;

import br.org.cancer.modred.exception.BusinessException;


/**
 * Enum para os tipos de um pedido de exame.
 * 
 * Fase 2 (2L) e Fase 3 (3L) 
 * 
 * @author Pizão
 *
 */
public enum TiposExame {
	TIPIFICACAO_HLA_ALTA_RESOLUCAO(0L),    
	TESTE_CONFIRMATORIO(1L),
	LOCUS_C_ALTA_RESOLUCAO_CLASSE_II(2L),  
	LOCUS_C(3L),							  	
	ALTA_RESOLUCAO_CLASSE_II(4L),		  
	TESTE_CONFIRMATORIO_SWAB(5L),
	TESTE_CONFIRMATORIO_SOMENTE_SWAB(6L);
	
	private Long id;

	TiposExame(Long id) {
		this.id = id;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static TiposExame get(Long id) {
		for (TiposExame status : values()) {
			if (status.equals(id)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

	private boolean equals(Long id) {
		return this.id.equals(id);
	}

	public static Stream<TiposExame> stream() {
        return Stream.of(TiposExame.values()); 
    }
	
	/**
	 * Método estatico para o obter a descrição por id. 
	 * 
	 * @param id id do tipo de exame
	 * @return string
	 */
	public static String getDescricaoPorId(Long id) {
		if (id == null) {
			return null;
		}
		for (int contador = 0; contador < TiposExame.values().length; contador++) {
			if (TiposExame.values()[contador].getId().equals(id)) {
				return TiposExame.values()[contador].name();
			}
		}
		return null;
	}

}
