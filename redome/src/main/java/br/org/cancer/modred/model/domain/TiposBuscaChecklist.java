package br.org.cancer.modred.model.domain;


/**
 * Enum para domínio de tipos de checklist de busca.
 * @author Filipe Paes
 *
 */
public enum TiposBuscaChecklist {
	NOVA_BUSCA(1L),
	ALTEROU_GENOTIPO(2L),
	RESULTADO_EXAME_SEGUNDA_FASE(3L),
	RESULTADO_EXAME_CT(4L),		
	DIALOGO_MEDICO(6L),
	EXAME_SEM_RESULTADO_A_MAIS_DE_30_DIAS(7L),
	DISCREPANCIA_GENOTIPO(8L),
	COBRANCA_LABORATORIO_DIVERGENCIA(9L),
	NOVA_EVOLUCAO_CLINICA(10L);
	
	TiposBuscaChecklist(Long id) {
		this.id = id;
	}
	
	private Long id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
}
