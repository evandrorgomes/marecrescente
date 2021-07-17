package br.org.cancer.modred.model.domain;

/**
 * Enum para descrever campos de ordenação para consulta de buscas.
 * @author Filipe Paes
 *
 */
public enum CamposOrdenacaoBusca {
	RMR("P.PACI_NR_RMR", "RMR"),
	NOME("P.PACI_TX_NOME", "NOME"),
	IDADE("IDADE", "IDADE"),
	SCORE("SCORE", "SCORE"),
	DATA_CRIACAO("A.AVAL_DT_CRIACAO", "DATA_CRIACAO"),
	CENTRO_TRANSPLANTE("CE.CETR_TX_NOME", "CENTRO_TRANSPLANTE"),
	CID_DESCRICAO("C.CID_TX_DESCRICAO", "CID_DESCRICAO"),
	CID_CODIGO("C.CID_TX_CODIGO", "CID_CODIGO");
	
	private String nomeReferencia;
	private String nomeColuna;
	
	CamposOrdenacaoBusca( String nomeColuna, String nomeReferencia) {
		this.nomeColuna = nomeColuna;
		this.nomeReferencia = nomeReferencia;
	}

	/**
	 * @return the nomeReferencia
	 */
	public String getNomeReferencia() {
		return nomeReferencia;
	}

	/**
	 * @return the nomeColuna
	 */
	public String getNomeColuna() {
		return nomeColuna;
	}
	
	
	/**
	 * Método para criar o tipo enum a partir de seu valor de referencia.
	 * 
	 * @param nomeReferencia - valor do id do tipo enum
	 * @return CamposOrdenacaoBusca - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static CamposOrdenacaoBusca getEnum(String nomeReferencia) {

		CamposOrdenacaoBusca[] values = CamposOrdenacaoBusca.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getNomeReferencia().equals(nomeReferencia)) {
				return values[i];
			}
		}
		return null;
	}
	
}
