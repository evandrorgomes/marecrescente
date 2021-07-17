package br.org.cancer.redome.workup.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DistribuicaoWorkupPorUsuarioDTO;
import br.org.cancer.redome.workup.dto.DistribuicoesWorkupDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.model.DistribuicaoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Distribuicao do Workup.
 * @author bruno.sousa
 *
 */
public interface IDistribuicaoWorkupService extends IService<DistribuicaoWorkup, Long> {

	/**
	 * Método para criar a distribuição do workup e a tarefa associada.
	 * 
	 * @param solicitacao Solicitação contendo as informações necessárias para criação da distribuição.
	 */
	void criarDistribuicao(SolicitacaoDTO solicitacao);

	/**
	 * Lista as tarefas DISTRIBUIR_WORKUP_NACIONAL E DISTRIBUIR_WORKUP_INTERNACIONAL de acordo com o perfil do usuário logado.
	 * 
	 * @param pagina Página inicial
	 * @param quantidadeRegistros Quantidade de registros por página.
	 * @return Lista pagina com a tarefa e as distribuições.
	 * @throws JsonProcessingException 
	 */
	Page<DistribuicoesWorkupDTO> listarTarefasDisribuicoesWorkup(int pagina, int quantidadeRegistros) throws JsonProcessingException;

	/**
	 * Lista a distribuição do wokrup agrupadas por usuário, cujas as solicitações ainda estão em andamento. 
	 * 
	 * @return Map<String, List<DistribuicaoWorkupPorUsuarioDTO>>
	 */
	Map<String, List<DistribuicaoWorkupPorUsuarioDTO>> listarDistribuicoesWorkupPorUsuario();

	/**
	 * Atribui o usuário a distribuição workup, fecha a tarefa de distribuição e cria a tarefa para cadastrar o formulário do doador do pedido de workup.  
	 * 
	 * @param id Identificação da distribuição do workup.
	 * @param idUsuario Identificação do usuário que receberá o pedido de workup.
	 * @throws JsonProcessingException
	 */
	void atribuirUsuarioDistribuicaoWorkup(Long id, Long idUsuario) throws JsonProcessingException;

	/**
	 * Reatribui o usuário na distribuição workup. Altera as taerfas em aberto para o novo usuário.
	 *  
	 * @param id Identificação da distribuição workup
	 * @param idUsuario Identificação do usuário que receberá o pedido de workup.
	 */
	void reatribuirUsuarioDistribuicaoWorkup(Long id, Long idUsuario) throws Exception;
	
    
}
