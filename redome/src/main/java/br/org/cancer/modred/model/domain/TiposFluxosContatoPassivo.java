package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para status de solicitacoes.
 * 
 * @author Cintia Oliveira
 *
 */
public enum TiposFluxosContatoPassivo implements EnumType<Integer>{
	FLUXO_NORMAL(0),
	FLUXO_PASSIVO(1),
	FLUXO_CRIACAO(2),
	FLUXO_ATUALIZACAO(3),
	FLUXO_ENRIQUECIMENTO(4);

	private Integer id;

	TiposFluxosContatoPassivo(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Override
	public Integer getId() {
		return id;
	}
	
	/**
	 * Método estatico para o obter a descrição por id. 
	 * 
	 * @param id id do status da solicitacao
	 * @return string
	 */
	public static String getDescricaoPorId(Integer id) {
		if (id == null) {
			return null;
		}
		for (int contador = 0; contador < TiposFluxosContatoPassivo.values().length; contador++) {
			if (TiposFluxosContatoPassivo.values()[contador].getId().equals(id)) {
				return TiposFluxosContatoPassivo.values()[contador].name();
			}
		}
		
		return null;
		
	}
	
	
}
