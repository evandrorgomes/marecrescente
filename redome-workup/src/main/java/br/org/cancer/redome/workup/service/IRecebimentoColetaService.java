package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.model.RecebimentoColeta;

/**
 * Interface de negócios para recebimento de coleta.
 * @author ergomes
 *
 */
public interface IRecebimentoColetaService {

	/**
	 * Salva o recebimento de coleta e fecha a tarefa de recebimento como concluida.
	 * @param recebimento a ser gravado.
	 */
	void salvarRecebimentoColeta(RecebimentoColeta recebimento) throws JsonProcessingException;
}
