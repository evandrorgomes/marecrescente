package br.org.cancer.redome.workup.service;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AprovarAvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacoesPrescricaoDTO;
import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de funcionalidades ligadas a avaliação de prescrição do paciente.
 * 
 * @author Queiroz
 *
 */
public interface IAvaliacaoPrescricaoService extends IService<AvaliacaoPrescricao, Long> {

	/**
	 * Criar um avaliação associado a prescrição.
	 * 
	 * @param rmr RMR do paciente.
	 * @param prescricao - entidade de prescricao associada
	 * @return avaliacao relacionada a prescricao a ser avaliada.
	 */
	AvaliacaoPrescricao criarAvaliacaoPrescricao(Long rmr, Prescricao prescricao);

	/**
	 * Aprova uma avaliação de prescrição, após a aprovação, deve ser criada uma tarefa para pedido de workup do doador, assim
	 * como a entidade de pedido de workup associados a solicitação de prescrição.
	 * 
	 * @param idAvaliacaoPrescricao id da avaliação para aprovar
	 * @param aprovarAvaliacaoPrescricaoDTO DTO contendo a fonte de celula descartada e a justificativa
	 */
	void aprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, AprovarAvaliacaoPrescricaoDTO aprovarAvaliacaoPrescricaoDTO) throws JsonProcessingException;

	/**
	 * Serviço para reprovar uma avaliação de prescrição com justificativa obrigatório.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliação
	 * @param justificativaReprovacao - justificativa da reprovação
	 * @return Mensagem de sucesso
	 */
	void reprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, String justificativaReprovacao) throws JsonProcessingException;

	/**
	 * Obtém os dados de avaliação da prescrição.
	 * 
	 * @param id - identificador
	 * @return AvaliacaoPrescricao com dados retornados pelo jsonview
	 */
	AvaliacaoPrescricao obterAvaliacao(Long id);

	
	/**
	 * Obtém os dados de todas as tarefas abertas da avaliação da prescrição.
	 * 
	 * @param pagina Número da página para buscar
	 * @param quantidadeRegistros Quantidade de registros por página
	 * @return Page<AvaliacoesPrescricaoDTO>
	 * @throws JsonProcessingException
	 */
	Page<AvaliacoesPrescricaoDTO> listarAvaliacoesPendentes(int pagina, int quantidadeRegistros) throws JsonProcessingException;

	/**
	 * Obtém a avaliação da prescrição transformando em um dto com as todas as informações da prescrição e doador.
	 * 
	 * @param id identificador da avaliação da prescrição 
	 * @return AvaliacaoPrescricaoDTO
	 */
	AvaliacaoPrescricaoDTO obterAvaliacaoPrescricao(Long id);

	/**
	 * Obtém a avaliação da prescrição.
	 *  
	 * @param idPrescricao - Identificador da prescrição
	 * @return AvaliacaoPrescricao
	 */
	AvaliacaoPrescricao obterAvaliacaoPeloIdPrescricao(Long idPrescricao);

	
}
