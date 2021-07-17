package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ResultadoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Resultado do Workup.
 * @author bruno.sousa
 *
 */
public interface IResultadoWorkupService extends IService<ResultadoWorkup, Long> {

	/**
	 * Obtém o resultado de workup pela identificacação do pedido de workup. 
	 * 
	 * @param idPedidoWorkup Identificador do Pedido de Workup
	 * @return ResultadoWorkup se existir ou null.
	 */
	ResultadoWorkup obterResultadoWorkupPeloPedidoWorkupId(Long idPedidoWorkup);

	/**
	 * Salva o resultado de workyp fechando a tarefa e criar a tarefa para logistica do doador.  
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup.
	 * @param resultadoWorkup Resultado do workup a ser salvo. 
	 */
	void salvarResultadoWorkupInternacional(Long idPedidoWorkup, ResultadoWorkup resultadoWorkup) throws JsonProcessingException;

	
	/**
	 * Obtém o DTO do resultado de workup internacional pelo identificador único do registro. 
	 * 
	 * @param id - Identificador único do registro.
	 * @return DTO com as informações de resultado de workup internacional ou 
	 * throw exception se não encontrado ou não for internacional  
	 */
	ResultadoWorkupInternacionalDTO obterResultadoWorkupInternacionalDTO(Long id);

	/**
	 * Criar o resultado de workyp nacional.  
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup.
	 * @param ResultadoWorkupNacionalDTO - DTO de resultado de workup nacional 
	 * 
	 */
	void criarResultadoWorkupNacional(Long idPedidoWorkup, ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO) throws JsonProcessingException;

	/**
	 * Obtém o resultado de workup internacional pelo identificador único do registro. 
	 * 
	 * @param id - Identificador único do registro.
	 * @return reeultdo de workup internacional ou 
	 * throw exception se não encontrado ou não for internacional  
	 */
	ResultadoWorkup obterResultadoWorkupInternacional(Long id);

	
	/**
	 * Obtém o resultado de workup nacional pelo identificador único do registro. 
	 * 
	 * @param id - Identificador único do registro.
	 * @return reeultdo de workup internacional ou 
	 * throw exception se não encontrado ou não for nacional
	 */
	ResultadoWorkup obterResultadoWorkupNacional(Long id);

	/**
	 * Obtém o DTO do resultado de workup nacional pelo identificador único do registro. 
	 * 
	 * @param id - Identificador único do registro.
	 * @return DTO com as informações de resultado de workup nacional ou 
	 * throw exception se não encontrado ou não for nacional  
	 */
	ResultadoWorkupNacionalDTO obterResultadoWorkupNacionalDTO(Long id);

	/**
	 * Obtém o resultado de workup pela solicitacao.
	 * 
	 * @param idSolicitacao Identificação da Solicitacao
	 * @return Resultado de workup ou BusinessException
	 */
	ResultadoWorkup obterResultadoWorkupPelaSolicitacao(Long idSolicitacao);

}
