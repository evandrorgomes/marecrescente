package br.org.cancer.modred.service;

import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;
import br.org.cancer.modred.vo.PedidoContatoFinalizadoVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoContato.
 * 
 * @author Pizão
 */
public interface IPedidoContatoService {

	/**
	 * Obtém o pedido contato associado a solicitação informada.
	 * 
	 * @param idDoador ID do doador associado ao pedido.
	 */
	PedidoContato obterPedido(Long idDoador);

	/**
	 * Obtém o pedido de contato a partir do ID da solicitação.
	 * 
	 * @param solicitacaoId ID da solicitação.
	 * @return pedido de contato associado.
	 */
	PedidoContato obterPedidoPorSolicitacao(Long solicitacaoId);

	/**
	 * Método para criar um pedido de contato com uma solicitação.
	 * 
	 * @param solicitacao - solicitacao para criar o pedido
	 * @return PedidoContato - retorna o novo pedidoContato ou nulo, caso já exista pedido na base.
	 */
	PedidoContato criarPedidoContato(Solicitacao solicitacao);
	
	/**
	 * Método para finalizar o pedido de contato automaticamente.
	 * 
	 * @param idPedidoContato
	 */
	void finalizarPedidoContatoAutomaticamente(Long idPedidoContato);

	/**
	 * Método para finalizar o pedido de contato.
	 * 
	 * @param pedido
	 */
	void finalizarPedidoContato(Long idPedidoContato, Boolean finalizacaoAutomatica, PedidoContatoFinalizadoVo pedidoContatoFinalizadoVo );

	/**
	 * Busca pedidos por tipo de solicitacao, idDoador, rmr e se está ou não aberto.
	 * 
	 * @param tipoSolicitacao - fase2, fase3 ou workup.
	 * @param idDoador - identificador do doador.
	 * @param rmr - idenrificador do paciente.
	 * @param aberto - se o pedido está ou não aberto.
	 * @return listagem de pedidos de contato.
	 */
	List<PedidoContato> buscarPedidosPor(Long tipoSolicitacao, Long idDoador, Long rmr, boolean aberto);

	/**
	 * Cancela pedidos de contato por solicitação.
	 * 
	 * @param solicitacao que se deseja cancelar os pedidos de contato.
	 */
	void cancelarPedidoContatoPorSolicitacao(Solicitacao solicitacao);

	/**
	 * Método para salvar o formulário para o pedido de contato.
	 * 
	 * @param pedidoContatoId - Identificador do pedido de contato.
	 * @param formulario - formulário preenchido
	 */
	void salvarFormularioContato(Long pedidoContatoId, Formulario formulario);
	
	/**
	 * Obtém o pedido de contato pelo id.
	 * 
	 * @param id - Identificador do pedido de contato.
	 * @return PedidoContato se exitir.
	 */
	PedidoContato obterPedidoContatoPorId(Long id);

	TarefaDTO obterPrimeiroPedidoContatoDaFilaDeTarefas(Long tipoTarefaId);

	TarefaDTO obterPedidoContatoPoridTarefa(Long tentativaId, Long tarefaId);
	
	LocalDateTime obterDataUltimoPedidoContatoFechado(Long idDoador);

	/**
	 * Cancelar pedido por rmr, idDoador e tipo.
	 * 
	 * @param rmr - id do paciente.
	 * @param idDoador - id do doador.
	 * @param tipoSolicitacao - id do tipo de solicitação.
	 */
	//void cancelarPedido(Long rmr, Long idDoador, Long tipoSolicitacao);

	
	Boolean obterVerificacaoUltimoPedidoContatoContactadoFechado(Long idDoador);
	
	PedidoContato obterUltimoPedidoContatoFechado(Long idDoador);
	
	ConsultaDoadorNacionalVo criarPedidoContatoPassivo(Long idDoador);
	
	PedidoContato obterPedidoEmAberto(Long idDoador);
	
	PedidoContato obterUltimoPedidoContato(Long idDoador);
	
}
