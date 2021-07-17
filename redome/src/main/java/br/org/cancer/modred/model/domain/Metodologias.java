package br.org.cancer.modred.model.domain;

/**
 * Classe de Enum de metodologias.
 * 
 * @author evandro.gomes
 *
 */
public enum Metodologias {
	PCR_NGS(4L, 4L),
	PCR_SSO(1L,2L),
	PCR_SSP(2L,1L),
	PCR_SBT(3L,4L),
	PCR_IND(5L,5L);
	
	private Long idLegado;
	private Long idModred;
	
	Metodologias(Long idLegado, Long idModred) {
		this.idLegado = idLegado;
		this.idModred = idModred;
	}

	/**
	 * @return the idLegado retorna o id do legado
	 */
	public Long getIdLegado() {
		return idLegado;
	}

	/**
	 * @param idLegado the idLegado to set
	 */
	public void setIdLegado(Long idLegado) {
		this.idLegado = idLegado;
	}

	/**
	 * @return the idModred
	 */
	public Long getIdModred() {
		return idModred;
	}

	/**
	 * @param idModred the idModred to set
	 */
	public void setIdModred(Long idModred) {
		this.idModred = idModred;
	}

	
	/**
	 * Obtem metodologia por id do legado.
	 * @param idLegado long do id do legado.
	 * @return Metodologias do legado.
	 */
	public static Metodologias getMetodoLogiaPorIdLegado(Long idLegado){
		for(Metodologias m: Metodologias.values()){
			if(m.getIdLegado().equals(idLegado)){
				return m;
			}
		}
		return null;
	}
}
