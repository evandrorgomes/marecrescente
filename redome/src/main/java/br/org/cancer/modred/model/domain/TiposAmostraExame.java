package br.org.cancer.modred.model.domain;



import java.util.stream.Stream;

import br.org.cancer.modred.exception.BusinessException;


/**
 * Enum para os tipos de amostra de exame - CT.
 * 
 * @author ergomes
 *
 */
public enum TiposAmostraExame {
	AMOSTRA_SANGUE(0L, "Amostra de Sangue"),    
	AMOSTRA_SWAB_ORAL(1L, "Amostra de Swab Oral"),
	AMOSTRA_SANGUE_SWAB_ORAL(2L, "Amostra de Sangue + Swab Oral");
	
	private Long id;
	private String descricao;

	TiposAmostraExame(Long id) {
		this.id = id;
	}

	TiposAmostraExame(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static TiposAmostraExame get(Long id) {
		for (TiposAmostraExame tipo : values()) {
			if (tipo.equals(id)) {
				return tipo;
			}
		}
		throw new BusinessException("erro.interno");
	}

	private boolean equals(Long id) {
		return this.id.equals(id);
	}

	public static Stream<TiposAmostraExame> stream() {
        return Stream.of(TiposAmostraExame.values()); 
    }
}
