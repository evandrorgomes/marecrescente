package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para status de solicitacoes.
 * 
 * @author Cintia Oliveira
 *
 */
public enum StatusSolicitacao implements EnumType<Integer>{
	ABERTA(1),
	CONCLUIDA(2),
	CANCELADA(3);

	private Integer id;

	StatusSolicitacao(Integer id) {
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
		for (int contador = 0; contador < StatusSolicitacao.values().length; contador++) {
			if (StatusSolicitacao.values()[contador].getId().equals(id)) {
				return StatusSolicitacao.values()[contador].name();
			}
		}
		
		return null;
		
	}
	
	/**
	 * Retorna o status de acordo com o código informado no parâmetro.
	 * 
	 * @param value código associado ao status.
	 * @return status da solicitação de mesmo value (ID) informado.
	 */
	public static StatusSolicitacao valueOf(Long value) {
		if (value != null) {
			StatusSolicitacao[] values = StatusSolicitacao.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getId().equals(value)) {
					return values[i];
				}
			}
		}
		return null;
	}
	
	
	
}
