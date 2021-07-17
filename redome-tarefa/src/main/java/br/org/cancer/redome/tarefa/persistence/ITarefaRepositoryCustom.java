package br.org.cancer.redome.tarefa.persistence;

import org.springframework.data.domain.Page;

import br.org.cancer.redome.tarefa.dto.ListaTarefaDTO;
import br.org.cancer.redome.tarefa.model.Tarefa;

/**
 * Inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da plataforma redome.
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade TarefaDTO.
 * 
 * @author Thiago Moraes
 *
 */
public interface ITarefaRepositoryCustom {

	/**
	 * Método para modificar o status da tarefa.
	 * 
	 * @param tarefaId - identificação da tarefa.
	 * @param status - novo status da tarefa.
	 * @param now - data corrente.
	 */
	//int atualizarStatusTarefa(Long tarefaId, StatusTarefa status, LocalDateTime now);
	
	/**
	 * Método para recuperar o conjuto de tarefas disponíveis no motor de processos.
	 * 
	 * Caso o tipo da tarefa seja informado como null, então, todas as tarefas serão retornadas independente do seu tipo.
	 * 
	 * Caso o statusTarefa seja informado como null, então, todas as tarefas serão retornadas independente do status atual de cada
	 * tarefa.
	 * 
	 * Caso o statusProcesso seja informado como null, então, todas as tarefas serão retornadas independente do status atual do
	 * processo.
	 * 
	 * @param parametros - DTO com as informações para a realização da consulta.
	 * 
	 * @return Page<TarefaDTO> - um conjunto de tarefas conforme os parâmetros informados no acionamento deste método.
	 */
	Page<Tarefa> listarTarefas(ListaTarefaDTO parametros);
	


}
