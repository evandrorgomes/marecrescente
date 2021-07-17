package br.org.cancer.modred.service;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ResultadoWorkup;

/**
 * Interface para métodos de negócios de Resultado Workup.
 * @author Filipe Paes
 *
 */
public interface IResultadoWorkupService {
	
	/**
	 * Método para gravação de resultado de workup.
	 * @param resultado a ser persistido.
	 */
	void salvarResultadoWorkup(ResultadoWorkup resultado);
	
	/**
	 * Método para obter doador com resultado 
	 * de exame de workup em aberto por idDoador.
	 * @param idDoador - id do doador.
	 * @param idCentroColeta - id do centro de coleta do doador.
	 * @param aberto - se o resultado está ou não em aberto.
	 * @return resultado de workup localizado.
	 */
	ResultadoWorkup obterDoadorComResultado(Long idDoador, Long idCentroColeta, Boolean aberto);

	/**
	 * Método para exclusao de todos os arquivos que estão no storage 
	 * e não estão vinculados a algum resultado de workup finalizado.
	 * 
	 * @param resultado - Resultado de workup
	 */
	void excluirArquivosNaoVinculados(ResultadoWorkup resultado);
	
	/**
	 * Obter um objeto de resultado de workup por id.
	 * @param id do objeto a ser recuperado.
	 * @return objeto de workup populado.
	 */
	ResultadoWorkup obterResultadoWorkupPorId(Long id);
	
	/**
	 * Método para atualizar o resultado de workup e salva-lo com a data de atualização
	 * com os seus devidos arquivos para indicar que ele foi realiza-lo.
	 * @param resultado
	 */
	void finalizarResultadoWorkup(ResultadoWorkup resultado);
	
	/**
	 * Obtém um resultado de workup pelo id.
	 * @param idResultadoWorkup - identificador do resultado
	 * @return Entidade resultado de workup
	 */
	ResultadoWorkup obterResultado(Long idResultadoWorkup);
	
	/**
	 * Obtem resultado de workup por id de pedido de workup.
	 * @param idPedidoWorkup
	 * @return Entidade resultado de workup
	 */
	ResultadoWorkup obterResultadoPorPedidoWorkup(Long idPedidoWorkup);
	
	/**
	 * Obtém o id da tarefa do resultado de workup de um doador nacional.
	 * @param dmr - id do doador.
	 * @param idCentroColeta - id do centro de coleta do doador.
	 * @param aberto - se o resultado está ou não em aberto.
	 * @return TarefaBase - tarefa associada
	 */
	TarefaDTO obterTarefaResultadoWorkupNacional(Long dmr, Long idCentroColeta, Boolean aberto);

	
	/**
	 * Lista tarefas de resultado de workup por id do centro de coleta.
	 * @param id do centro de coleta.
	 * @param pageRequest paginação
	 * @return lista paginada de tarefas de resultado de workup para serem cadastradas.
	 */
	JsonViewPage<TarefaDTO> listarTarefasPorCentroTransplante(Long idCentroColeta, PageRequest pageRequest);
	
	/**
	 * Lista de tarefas contendo todos os pedidos de workup para todos usuário. 
	 * 
	 * @param paginacao paginação do resultado.
	 * @return lista de tarefas paginadas.
	 */
	JsonViewPage<TarefaDTO> listarTarefasCadastroResultado(PageRequest paginacao);

}
