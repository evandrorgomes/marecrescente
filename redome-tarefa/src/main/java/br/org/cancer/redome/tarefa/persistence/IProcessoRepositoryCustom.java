package br.org.cancer.redome.tarefa.persistence;

import java.util.List;

import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.domain.ITipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.StatusProcesso;

/**
 * Inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da plataforma redome.
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade Processo.
 * 
 * @author Thiago Moraes
 *
 */
public interface IProcessoRepositoryCustom {

	/**
	 * Método para recuperar o conjuto de processos disponíveis no motor de processos.
	 * 
	 * Caso o tipo do processo seja informado como null, então, todos os processos serão retornadas independente do seu tipo.
	 * 
	 * Caso o statusProcesso seja informado como null, então, todas os processos serão retornadas independente do status atual.
	 * 
	 * @param paciente - identificação do paciente associado ao processo (informação opcional)
	 * @param tipo - identificação do tipo de processos (informação opcional)
	 * @param statusProcesso - situação atual do processo (informação opcional)
	 * @param pageRequest - informações sobre paginação do conjunto que será retornado (informação opcional)
	 * 
	 * @return List<Processo> - um conjunto de processos conforme os parâmetros informados no acionamento deste método.
	 */
	List<Processo> listarProcessos(Long rmr, Long idDoador, ITipoProcesso tipo, StatusProcesso status);

	/**
	 * Método para modificar o status do processo.
	 * 
	 * @param processoId - identificação do processo.
	 * @param status - novo statuso do processo.
	 * @param now - data corrente.
	 */
//	int atualizarStatusProcesso(Long processoId, StatusProcesso status, LocalDateTime now);

}
