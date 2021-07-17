package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.model.RespostaChecklist;
import br.org.cancer.redome.workup.model.TipoChecklist;

/**
 * Inteface de negócios de tipo de checklist.
 * @author ergomes
 *
 */
public interface ITipoChecklistService {

	/**
	 * Obtem um tipo de checklist por id.
	 * @param idTipoChecklist
	 * @return
	 */
	TipoChecklist obterTipoChecklist(Long idTipoChecklist);
	
	/**
	 * 
	 * @param tipoChecklist
	 */
	void salvarChecklist(RespostaChecklist resposta);

}
