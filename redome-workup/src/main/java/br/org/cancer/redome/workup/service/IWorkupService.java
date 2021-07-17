package br.org.cancer.redome.workup.service;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;

/**
 * Interface para agrupar servços para Workup.
 * @author bruno.sousa
 *
 */
public interface IWorkupService {


	/**
	 * Lista as tarefas agrupadas de WORKUP de acordo com o perfil do usuário logado.
	 * 
	 * @param pagina Página inicial
	 * @param quantidadeRegistros Quantidade de registros por página.
	 * @param idCentroTransplante identificacao do centro de transplante.
	 * @return Lista pagina com a tarefa.
	 * @throws JsonProcessingException 
	 */
//	Page<ConsultaTarefasWorkupDTO> listarTarefasWorkup(Long idCentroTransplante, Long idFuncaoCentroTransplante, int pagina, int quantidadeRegistros) throws JsonProcessingException;
	
	
	/**
	 * Lista as solicitações de workup que o analista de workup está como responsável e que ele estja aguardando ação de outra pessoa.
	 * 
	 * @param pagina Página inicial
	 * @param quantidadeRegistros Quantidade de registros por página.
	 * @param idCentroTransplante identificacao do centro de transplante.	 
	 * @return lista paginada de solicitações
	 * @throws JsonProcessingException
	 */
//	Page<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkup(Long idCentroTransplante, Long idFuncaoCentroTransplante, int pagina, int quantidadeRegistros) throws JsonProcessingException;


	
	Page<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(Long idCentroTransplante, Long idFuncaoCentroTransplante, int pagina, int quantidadeRegistros) throws JsonProcessingException;
	
	Page<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(Long idCentroTransplante, Long idFuncaoCentroTransplante, int pagina, int quantidadeRegistros) throws JsonProcessingException;

}
