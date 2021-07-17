package br.org.cancer.modred.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoPrescricao;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.service.custom.IService;

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
	 * @param idFonteCelulaDescartada id da fonte celula a ser descartada
	 * @param justificativaDescarteFonteCelula Justificativa de descarte da fonte celula
	 * @return Mensagem de sucesso
	 */
	String aprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, Long idFonteCelulaDescartada,
			String justificativaDescarteFonteCelula);

	/**
	 * Serviço para reprovar uma avaliação de prescrição com justificativa obrigatório.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliação
	 * @param justificativaReprovacao - justificativa da reprovação
	 * @return Mensagem de sucesso
	 */
	String reprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, String justificativaReprovacao);

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
	 * @param paginacao - PageRequest
	 * @return JsonViewPage<TarefaDTO> com dados retornados pelo jsonviewpage
	 */
	Page<TarefaDTO> listarAvaliacoesPendentes(PageRequest paginacao);

	
}
