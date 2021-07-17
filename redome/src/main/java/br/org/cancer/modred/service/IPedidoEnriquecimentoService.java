package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoEnriquecimento.
 * 
 * @author Fillipe QUeiroz
 */
public interface IPedidoEnriquecimentoService extends IService<PedidoEnriquecimento, Long>{


	/**
	 * Fecha o pedido de enriquecimento.
	 * 
	 * @param idPedidoEnriquecimento
	 */
	void fecharPedidoEnriquecimento(Long idPedidoEnriquecimento);
	
	/**
	 * Cancela todos os pedidos em que o doador está associado.
	 * 
	 * @param idDoador identificação do doador.
	 */
	void fecharTodosPedidos(Long idDoador);
	
	/**
	 * Cancela todos os pedidos em que o match está associado.
	 *
	 * @param matchId - id do match.
	 */
	void fecharTodosPedidosPorMatch(Long matchId);
	
	/**
	 * Método para obter pedido de enriquecimento por id de RMR, idDoador e se está 
	 * ou não aberto.
	 * 
	 * @param rmr - identificador do paciente.
	 * @param idDoador - identificador do doador.
	 * @param aberto - se o pedido está ou não aberto.
	 * @return pedidos de acordo com os parâmetros passados. 
	 */
	List<PedidoEnriquecimento> obterPedidosDeEnriquecimentoPor(Long rmr, Long idDoador, boolean aberto);
	
	/**
	 * Cria um pedido de enriquecimento (assim como a tarefa destinada para atende-lo) a partir da 
	 * solicitação informada.
	 * 
	 * @param solicitacao solicitação associada ao pedido de enriquecimento.
	 * @param rmr identificador do paciente utilizado para encontrar o processo e criar a tarefa para o mesmo.
	 * @return pedido d enriquecimento criado se necessário
	 */
	PedidoEnriquecimento criarPedidoEnriquecimento(Solicitacao solicitacao, Long rmr);

	void atualizarEnriquecimentoDoadorNacionalRedomeWeb(DoadorNacional doador);
	
	
	/**
	 * Obtém a primeira tarefa disponível para o enriquecimento do doador.
	 * 
	 * @return TarefaDTO atribuída.
	 */
	TarefaDTO obterPrimeiroPedidoEnriquecimentoDaFilaDeTarefas();
	
	PedidoEnriquecimento obterPedidoDeEnriqueciomentoPorIdDoadorEStatus(Long idDoador, boolean aberto);
	
	ConsultaDoadorNacionalVo fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(Long idPedidoEnriquecimento, Long idDoador);
}
