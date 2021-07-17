package br.org.cancer.modred.model.domain;

/**
 * Enum para motivo de cancelamento de pedido de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum MotivosCancelamentoWorkup {
	CT_NAO_RESPONDE(1L),
	PRESCRICAO_EXPIRADA(2L),
	SEM_DATAS_DISPONIVEIS(3L),
	PRESCRICAO_CANCELADA(4L), 
	CANCELAMENTO_BUSCA(5L),
	BUSCA_ENCERRADA(6L);

	private Long codigo;

	MotivosCancelamentoWorkup(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the sexo
	 */
	public Long getCodigo() {
		return codigo;
	}
}
