package br.org.cancer.modred.model.domain;

/**
 * Enum para tipos de solicitacoes do redomeweb.
 * 
 * @author bruno.sousa
 *
 */
public enum TiposSolicitacaoRedomeweb {
	EXAME(1L),
	AMOSTRA(2L);

	private Long id;

	TiposSolicitacaoRedomeweb(Long id) {
		this.id = id;
	}

	/**
	 * @return the TipoSolicicacao
	 */
	public Long getId() {
		return id;
	}
	
	public void setTipoSolicitacao(Long idTipoSolicitacao){
		this.id = idTipoSolicitacao;
	}
	
	/**
	 * Retorna o tipo de acordo com o código informado no parâmetro.
	 * 
	 * @param value código associado ao tipo.
	 * @return tipo da solicitação de mesmo value (ID) informado.
	 */
	public static TiposSolicitacaoRedomeweb valueOf(Long value) {
		if (value != null) {
			TiposSolicitacaoRedomeweb[] values = TiposSolicitacaoRedomeweb.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getId().equals(value)) {
					return values[i];
				}
			}
		}
		return null;
	}
}
