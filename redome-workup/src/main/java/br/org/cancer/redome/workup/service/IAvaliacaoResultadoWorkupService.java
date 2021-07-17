package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.dto.AvaliacaoResultadoWorkupDTO;
import br.org.cancer.redome.workup.model.AvaliacaoResultadoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de funcionalidades ligadas a avaliação de resultado de workup.
 * 
 * @author Bruno Sousa
 *
 */
public interface IAvaliacaoResultadoWorkupService extends IService<AvaliacaoResultadoWorkup, Long> {

	/**
	 * Confirma o resultado de workup, e dá seguimento ao processo.
	 * 
	 * @param idResultadoWorkup Identificador do resultado de workup
	 * @param justificativa - Caso o resultado de workup esteja como inviável e queira prosseguir.
	 * @return avaliacao.resulado.workup.confirmada.pedido.coleta OU avaliacao.resulado.workup.confirmada.autorizacao.paciente 
	 * @throws Exception 
	 */
	String prosseguirResultadoWorkupInternacional(Long idResultadoWorkup, String justificativa) throws Exception;

	/**
	 * Cancela o pedido de workup e a prescrição pois o resultado de workup não atende as necessidades da prescrição.
	 * 
	 * @param idResultadoWorkup Identificador do resultado de workup.
	 * @param justificativa Motivo pelo qual está sendo cancelado.
	 */
	void naoProsseguirResultadoWorkupInternacional(Long idResultadoWorkup, String justificativa);
	

	/**
	 * Obter avaliação de resultado de workup por identificador.
	 * 
	 * @param idAvaliacao - identificador da avaliação de resultado.
	 * @return AvaliacaoResultadoWorkup - objeto de avaliação de resultado.
	 */
	AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorId(Long idAvaliacao);

	
	/**
	 * Confirma o resultado de workup nacional, e dá seguimento ao processo.
	 * 
	 * @param idResultadoWorkup Identificador do resultado de workup
	 * @return avaliacao.resulado.workup.confirmada.pedido.coleta OU avaliacao.resulado.workup.confirmada.autorizacao.paciente 
	 * @throws Exception 
	 */
	String prosseguirResultadoWorkupNacional(Long idResultadoWorkup) throws Exception;

	/**
	 * Cancela o pedido de workup e a prescrição pois o resultado de workup nacional não atende as necessidades da prescrição.
	 * 
	 * @param idResultadoWorkup Identificador do resultado de workup.
	 * @param justificativa Motivo pelo qual está sendo cancelado.
	 */
	void naoProsseguirResultadoWorkupNacional(Long idResultadoWorkup, String justificativa);


	/**
	 * Confirma o resultado de workup nacional mesmo com a coleta inviável, e dá seguimento ao processo para médico redome.
	 * 
	 * @param idResultadoWorkup Identificador do resultado de workup
	 * @param justificativa - Caso o resultado de workup esteja como inviável e queira prosseguir.
	 * @return avaliacao.resulado.workup.confirmada.pedido.coleta OU avaliacao.resulado.workup.confirmada.autorizacao.paciente 
	 * @throws Exception 
	 */
	String prosseguirColetaInviavelResultadoWorkupNacional(Long idResultadoWorkup, String justificativa) throws Exception;

	/**
	 * Obtém a avaliação de resultado de workup pela solicitação.
	 * 
	 * @param idSolicitacao Identificador da solicitação
	 * @return AvaliacaoResultadoWorkup ou null se não existir.
	 */
	AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorSolicitacao(Long idSolicitacao);

	/**
	 * Obtém a avaliação de resultado de workup nacional pelo identificador.
	 * 
	 * @param idAvaliacaoResultadoWorkup Identificador da avaliação de resultado de workup
	 * @return AvaliacaoResultadoWorkupDTO ou throw se a avaliação não exitir ou se ela não for nacional.
	 */
	AvaliacaoResultadoWorkupDTO obterAvaliacaoResultadoWorkupNacionalPorId(Long idAvaliacaoResultadoWorkup);
}
