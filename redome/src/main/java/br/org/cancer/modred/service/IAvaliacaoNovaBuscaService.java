package br.org.cancer.modred.service;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AvaliacaoNovaBusca;
import br.org.cancer.modred.model.Paciente;

/**
 * Interface para definição das funcionalidades do
 * service.
 * 
 * @author Pizão
 *
 */
public interface IAvaliacaoNovaBuscaService {

	/**
	 * Cria uma nova avaliação (envolvendo um nova busca)
	 * para o paciente informado.
	 * 
	 * @param paciente paciente que será avaliado.
	 * @return avaliação criada.
	 */
	AvaliacaoNovaBusca criarNovaAvaliacao(Paciente paciente);
	
	/**
	 * Verifica se o paciente possui avaliação para uma nova busca
	 * em aberto no sistema.
	 * 
	 * @param rmr identificação do paciente.
	 * @return TRUE quando existe avaliação em andamento.
	 */
	boolean verificarAvaliacaoEmAndamento(Long rmr);
	
	/**
	 * Realiza a aprovação de uma nova busca, fecha a 
	 * tarefa de nova busca e altera o status da busca para
	 * liberada.
	 * @param id da avaliação a ser a provada.
	 */
	void aprovarAvaliacaoNovaBusca(Long id);
	
	/**
	 * Altera o resultado da avaliação para reprovado, fehca tarefa de avaliação de 
	 * nova busca e altera o status do paciente para REPROVADO.
	 * @param id da avaliação a ser a provada.
	 * @param justificativa da reprovação.
	 */
	void reprovarAvaliacaoNovaBusca(Long id, String justificativa);
	
	/**
	 * Lista as tarefas de avaliação em aberto.
	 * 
	 * @param pageRequest paginação utilizada.
	 * @return lista de tarefas paginadas.
	 */
	JsonViewPage<TarefaDTO> listarTarefas(PageRequest pageRequest);
	
}
