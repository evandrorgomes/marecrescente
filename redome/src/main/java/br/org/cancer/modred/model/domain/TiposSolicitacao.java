package br.org.cancer.modred.model.domain;

import java.util.stream.Stream;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de solicitacoes.
 * 
 * @author Filipe Paes
 *
 */
public enum TiposSolicitacao implements EnumType<Long>{
	FASE_2(1L),
	FASE_3(2L), 
	FASE_2_INTERNACIONAL(5L),
	FASE_3_INTERNACIONAL(6L),
	WORKUP(3L),
	WORKUP_CORDAO(4L),
	ENVIO_PACIENTE_EMDIS(7L),
	
	WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL(8L),
	WORKUP_DOADOR_INTERNACIONAL(9L),
	CORDAO_NACIONAL_PACIENTE_NACIONAL(10L),
	CORDAO_INTERNACIONAL(11l);
	
	
	

	private Long id;

	TiposSolicitacao(Long id) {
		this.id = id;
	}

	@Override
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
	public static TiposSolicitacao valueOf(Long value) {
		if (value != null) {
			TiposSolicitacao[] values = TiposSolicitacao.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getId().equals(value)) {
					return values[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * Método estatico para o obter a descrição por id. 
	 * 
	 * @param id id do tipo de solicitacao
	 * @return string
	 */
	public static String getDescricaoPorId(Long id) {
		if (id == null) {
			return null;
		}
		for (int contador = 0; contador < TiposSolicitacao.values().length; contador++) {
			if (TiposSolicitacao.values()[contador].getId().equals(id)) {
				return TiposSolicitacao.values()[contador].name();
			}
		}
		return null;
	}

	public static Stream<TiposSolicitacao> stream() {
        return Stream.of(TiposSolicitacao.values()); 
    }
}
