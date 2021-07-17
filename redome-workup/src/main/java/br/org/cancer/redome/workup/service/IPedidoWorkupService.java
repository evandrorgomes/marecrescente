package br.org.cancer.redome.workup.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AprovarPlanoWorkupDTO;
import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO;
import br.org.cancer.redome.workup.dto.PedidoWorkupDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Distribuicao do Workup.
 * @author bruno.sousa
 *
 */
public interface IPedidoWorkupService extends IService<PedidoWorkup, Long> {

	/**
	 * Método para criar o pedido de workup e a tarefa associada.
	 * 
	 * @param solicitacao Solicitação contendo as informações necessárias para criação do pedido de workup.
	 */
	void criarPedidoWorkup(SolicitacaoWorkupDTO solicitacao);

	/**
	 * Obtém o pedido de workup.
	 * 
	 * @param id Identificador do pedido de workup.
	 * @return PedidoWorkup ou throw BusinessException se não encontrar.
	 */
	PedidoWorkup obterPedidoWorkup(Long id);

	/*
	 * Método para definir o centro de coleta associado ao pedido de workup.
	 * 
	 * @param idPedidoWorkup - identificação do pedido necessária para criação do pedido de workup.
 	 * @param idCentroColeta - identificação do centro de transplante necessária para alteração do pedido de workup.
	 */
	void DefinirCentroColetaPorPedidoWorkup(Long idPedidoWorkup, Long idCentroColeta) throws JsonProcessingException;
	
	/**
	 * Obtém o pedido de workup DTO.
	 * 
	 * @param id Identificador do pedido de workup.
	 * @return PedidoWorkupDTO ou throw BusinessException se não encontrar.
	 */
	PedidoWorkupDTO obterPedidoWorkupDTO(Long id);

	/**
	 * Centro de coleta informa o plano de workup para que o centro de transplante possa aprovar.
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup
	 * @param planoWorkup plano de workup nacional preenchido
	 * @throws JsonProcessingException
	 */
	void informarPlanoWorkupNacional(Long idPedidoWorkup, PlanoWorkupNacionalDTO planoWorkup,
			MultipartFile arquivo) throws JsonProcessingException;

	/**
	 * Analista Workup internacional informa o plano de workup para que o centro de transplante possa aprovar.
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup
	 * @param planoWorkup plano de workup internacional preenchido
	 * @param arquivo arquivo com o plano de workup.
	 * @throws JsonProcessingException
	 */
	void informarPlanoWorkupInternacional(Long idPedidoWorkup, PlanoWorkupInternacionalDTO planoWorkup,
			MultipartFile arquivo) throws JsonProcessingException;

	/**
	 * Aprovação do plano de workup pelo centro de transplante.
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup
	 * @param planoWorkup DTO plano de workup preenchido
	 * @throws JsonProcessingException
	 */
	void aprovarPlanoWorkup(Long idPedidoWorkup, AprovarPlanoWorkupDTO planoWorkup) throws JsonProcessingException;

	/**
	 * Obtém a pedido workup associada a solicitação passada por parâmetro.
	 * 
	 * @param idSolicitacao Identificador da solicitação a ser utilizada no filtro.
	 * @return PedidoWorkup
	 */
	PedidoWorkup obterPedidoWorkupPorSolicitacao(Long idSolicitacao);

	/**
	 * Retorna o pedido de workup somente o status for igual a ABERTO
	 * 
	 * @param id Identificador do pedido de workup
	 * @return pedido de workup em aberto.
	 */
	PedidoWorkup obterPedidoWorkupEmAberto(Long id);
	
	/**
	 * Retorna dto plano de workup nacional.
	 * 
	 * @param id Identificador do pedido de workup
	 * @return plano de workup nacional.
	 */
	PlanoWorkupNacionalDTO obterPlanoWorkupNacional(Long id);

	/**
	 * Método que finaliza o pedido de workup.
	 * Throws se  pedido de workup não encontrado ou pedido de workup não estiver em aberto.
	 * 
	 * @param id Identificador do pedido de workup
	 */
	void finalizarWorkupInternacional(Long id);

	/**
	 * Cancela o workup internacinal e a solicitação.
	 * 
	 * @param id Identificador do pedido de workup.
	 */
	void cancelarWorkupInternacional(Long id);

	/**
	 * Cancela o workup nacinal e a solicitação.
	 * 
	 * @param id Identificador do pedido de workup.
	 */
	void cancelarWorkupNacional(Long id);
	
	/**
	 * Método que finaliza o pedido de workup nacional.
	 * Throws se  pedido de workup não encontrado ou pedido de workup não estiver em aberto.
	 * 
	 * @param id Identificador do pedido de workup
	 */
	void finalizarWorkupNacional(Long id);
	

	/**
	 * Método que lista as tarefa viculadas ao pedido de workup.
	 * throws JsonProcessingException 
	 * 
	 * @param FiltroListaTarefaWorkupDTO - Objeto DTO para filtrar a lista de tarefas workup.
	 * 
	 * @return lista do dto ConsultaTarefasWorkupDTO
	 */
	List<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(FiltroListaTarefaWorkupDTO filtro) throws JsonProcessingException;

	/**
	 * Método que lista as solicitações viculadas ao pedido de workup.
	 * throws JsonProcessingException 
	 * 
	 * @param FiltroListaTarefaWorkupDTO - Objeto DTO para filtrar a lista de tarefas workup.
	 * 
	 * @return lista do dto ConsultaTarefasWorkupDTO
	 */
	List<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(FiltroListaTarefaWorkupDTO filtro) throws JsonProcessingException;
}
