package br.org.cancer.modred.model.domain;

/**
 * Enum de perfis do sistema.
 * 
 * @author Cintia Oliveira
 *
 */
public enum Perfis {
	MEDICO(1L),
	MEDICO_AVALIADOR(2L),
	AVALIADOR_EXAME_HLA(3L),
	ANALISTA_BUSCA(5L),
	ENRIQUECEDOR(6L),
	ANALISTA_CONTATO_FASE2(7L),
	ANALISTA_CONTATO_FASE3(8L),
	ANALISTA_WORKUP(9L),
	ANALISTA_TM_OFFICE(10L),
	CADASTRADOR_RESULTADO_WORKUP(11L),
	ANALISTA_LOGISTICA(12L),
	MEDICO_REDOME(13L),
	CADASTRADOR_RECEBIMENTO_COLETA(14L),
	MEDICO_TRANSPLANTADOR(15L),
	CONTROLADOR_LISTA(16L),
	TRANSPORTADORA(17L), 
	LABORATORIO(18L),
	ADMIN(19L),
	ANALISTA_WORKUP_INTERNACIONAL(20L),
	WEBSERVICE(21L), 
	ANALISTA_LOGISTICA_INTERNACIONAL(22L),
	AVALIADOR_CAMARA_TECNICA(24L),
	AVALIADOR_NOVA_BUSCA(25L),
	CONTATO_SMS(27L),
	TODOS(0L),
	CENTRO_COLETA(29L),
	MEDIDO_CENTRO_COLETA(32L);

	private Long id;

	Perfis(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public static Perfis valueOf(Long id) {
		
		if (id == null) {
			return null;
		}
		
		Perfis perfilEncontrado = null;
		for (Perfis perfil: Perfis.values()) {
			if (perfil.getId().equals(id)) {
				perfilEncontrado = perfil;
			}
		}
		
		return perfilEncontrado;
	}
	
	

}