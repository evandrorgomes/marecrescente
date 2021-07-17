package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de doador.
 * @author Filipe Paes
 *
 */
public enum TiposDoador implements EnumType<Long>{
	
	NACIONAL(0L),
	INTERNACIONAL(1L),
	CORDAO_NACIONAL(2L),
	CORDAO_INTERNACIONAL(3L);
	
	public static final String MEDULA_NACIONAL_ID = "0";
	public static final String MEDULA_INTERNACIONAL_ID = "1";
	public static final String CORDAO_NACIONAL_ID = "2";
	public static final String CORDAO_INTERNACIONAL_ID = "3";
	
	private Long id;
	
	TiposDoador(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Gera um ID como string para ser utilizado no mapeamento das
	 * classes de doador (discriminator).
	 * 
	 * @return ID do tipo doador como string.
	 */
	public String get() {
		return String.valueOf(id);
	}
	
	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static TiposDoador get(Long id) {
		for (TiposDoador tipo : values()) {
			if (tipo.getId().equals(id)) {
				return tipo;
			}
		}
		throw new BusinessException("erro.interno");
	}

}
