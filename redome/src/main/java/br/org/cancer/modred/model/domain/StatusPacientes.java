package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum que define os status possíveis no ciclo de vida
 * de um paciente no ModRed.
 * Esses status são só parte do ciclo de vida, após aprovado,
 * os status seguem sendo alterados e acompanhados pelo status da 
 * Busca @see {@link #StatusBusca.class}.
 * 
 * @author Filipe Paes
 *
 */
public enum StatusPacientes implements EnumType<Long>{
	AGUARDANDO_APROVACAO_CENTRO_AVALIDADOR(1L),
	AGUARDANDO_APROVACAO_CAMARA_TECNICA(2L),
	AGUARDANDO_APROVACAO_CONFIRMACAO_HLA(3L),
	APROVADO(4L),
	REPROVADO(5L),
	OBITO(6L),
	AGUARDANDO_APROVACAO_REDOME(7L),
	AFASTADO_POR_FALTA_DE_EVOLUCAO(8L);
	
	private Long id;
	
	StatusPacientes(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}	

}
