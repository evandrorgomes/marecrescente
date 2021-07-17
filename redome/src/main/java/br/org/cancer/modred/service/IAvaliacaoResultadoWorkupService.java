package br.org.cancer.modred.service;

import java.io.File;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negócios de Avaliação de Resultado Workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IAvaliacaoResultadoWorkupService extends IService<AvaliacaoResultadoWorkup, Long>{

	/**
	 * Efetua um pedido de coleta.
	 * 
	 * @param idAvaliacao -identificador da avaliação.
	 * @param justificativa - justificativa para quando medico quer prosseguir com a coleta porém ela está inviável.
	 * @return entidade atualizada de avaliação de resultado de exame
	 */
	AvaliacaoResultadoWorkup efetuarPedidoColeta(Long idAvaliacao, String justificativa);

	/**
	 * Obtém uma avaliação de resultado de workup.
	 * 
	 * @param idAvaliacao - identificador
	 * @return entidade AvaliacaoResultadoWorkup
	 */
	AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorId(Long idAvaliacao);

	/**
	 * Cria na base uma nova avaliação inicial contendo somente o resultado e a data de criação.
	 * 
	 * @param avaliacao - avaliacao a ser persistida.
	 * 
	 */
	void salvar(AvaliacaoResultadoWorkup avaliacao);

	/**
	 * Descarta o doador fechando a avaliação de workup e criando tarefa para o médico do redome.
	 * 
	 * @param idAvaliacao - identificação da avaliação
	 * @param justificativa - descrição da justificativa
	 */
	void negarAvaliacaoResultadoWorkup(Long idAvaliacao, String justificativa);
	

	/**
	 * Obtem arquivo de laudo de arquivo de resultado de exame de workup.
	 * @param idArquivo - id do arquivo a ser baixado. 
	 * @return arquivo referido do storage.
	 */
	File obterArquivoLaudo(Long idArquivo);

	/**
	 * Listar as tarefas de avaliação de resultado de workup de um centro de transplante.
	 * Somente as tarefas em aberto ou atribuídas para o usuário logado.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante
	 * @param pageRequest - Paginação
	 * @return Lista de tarefas de avaliação de resultado de workup
	 */
	JsonViewPage<TarefaDTO> listarTarefasPorCentroTransplante(Long idCentroTransplante, PageRequest pageRequest);
	
}
