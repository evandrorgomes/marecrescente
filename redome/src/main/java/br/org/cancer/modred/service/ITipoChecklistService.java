package br.org.cancer.modred.service;

import br.org.cancer.modred.model.RespostaChecklist;
import br.org.cancer.modred.model.TipoChecklist;

/**
 * Inteface de negócios de tipo de checklist.
 * @author Filipe Paes
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
