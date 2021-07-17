package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.model.FormularioPosColeta;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Formulário Pós Coleta
 * @author elizabete.poly
 *
 */
public interface IFormularioPosColetaService extends IService<FormularioPosColeta, Long> {		
	/**
	 * Finaliza o formulário Pós Coleta
	 * 
	 * @param id - Identificador do pedido Workup
	 */
	void finalizarFormularioPosColeta(Long idPedidoWorkup) throws JsonProcessingException;
}
