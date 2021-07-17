package br.org.cancer.modred.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.AgendamentoWorkupDTO;
import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios para pedido de workup.
 * 
 * @author Filipe Paes
 *
 */
public interface IPedidoWorkupService extends IService<PedidoWorkup, Long> {

	/**
	 * Método para criação de um pedido de workup.
	 * 
	 * @param solicitacao - solicitação de referencia para criação do pedido.
	 * @return pedido de workup criado.
	 */
	PedidoWorkup criarPedidoWorkup(Solicitacao solicitacao);

	/**
	 * Lista os pedidos de workup nacionais disponíveis para realização.
	 * 
	 * @param paginacao paginação aplicada ao resultado.
	 * @return tarefas de workup paginadas.
	 */
	Page<TarefaDTO> listarPedidosWorkupDisponiveis(PageRequest paginacao);
	
	/**
	 * Lista os pedidos de workup nacionais em andamento (atribuídos).
	 * 
	 * @param paginacao paginação do resultado.
	 * @return lista de tarefas paginadas.
	 */
	Page<TarefaDTO> listarPedidosWorkupAtribuidos(PageRequest paginacao);
	
	/**
	 * Lista de tarefas contendo todos os pedidos de workup internacionais disponíveis para realização. 
	 * 
	 * @param paginacao paginação aplicada ao resultado.
	 * @return tarefas de workup paginadas.
	 */
	Page<TarefaDTO> listarPedidosWorkupInternacionaisDisponiveis(PageRequest paginacao);
	
	/**
	 * Lista de tarefas contendo todos os pedidos de workup internacionais atribuídos para o usuário logado. 
	 * 
	 * @param paginacao paginação do resultado.
	 * @return lista de tarefas paginadas.
	 */
	Page<TarefaDTO> listarPedidosWorkupInternacionais(PageRequest paginacao);

	/**
	 * Método para cancelar o pedido de workup.
	 * 
	 * @param idPedido
	 * @param idMotivoCancelamento
	 */
	void cancelarPedidoWorkup(Long idPedido, Long idMotivoCancelamento);

	/**
	 * Método para obter o pedido de workup.
	 * 
	 * @param idPedido
	 */
	PedidoWorkup obterPedidoWorkup(Long idPedido);

	/**
	 * Método que agenda um pedido de workup.
	 * 
	 * @param idPedido
	 * @param agendamentoWorkupDTO
	 */
	void agendarPedidoWorkup(Long idPedido, AgendamentoWorkupDTO agendamentoWorkupDTO);

	/**
	 * Cancela todos os pedidos em que o doador está associado.
	 * 
	 * @param idDoador identificação do doador.
	 */
	void fecharTodosPedidos(Long idDoador);

	/**
	 * Método para alterar o status do pedido de workup para aguardando CT e icrementar o número de tentativas.
	 * 
	 * @param idPedido
	 * @return Pedidoworkup como o status alterado. 
	 */
	PedidoWorkup alterarStatusParaAguardandoCT(Long idPedido);

	/**
	 * Obtém a disponibilidade associado ao pedido de workup informado.
	 * 
	 * @param idPedido pedido a ser cancelado.
	 * @return retorna a disponibilidade associado ao pedido.
	 */
	Disponibilidade obtemUltimaDisponibilidade(Long idPedido);

	/**
	 * Adiciona uma nova sugestão de datas para workup.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 * @param disponibilidade nova disponibilidade informada pelo CT.
	 */
	void responderDisponibilidadeWorkup(Long pedidoWorkupId, Disponibilidade disponibilidade);

	/**
	 * Obtém o paciente associado ao pedido de workup informado.
	 * 
	 * @param pedidoWorkupId ID do pedido.
	 * @return paciente associado.
	 */
	Paciente obterPaciente(Long pedidoWorkupId);

	/**
	 * Atualiza o status do pedido de workup para o novo status informado.
	 * Se for a primeira atualização do pedido, o usuário logado será considerado
	 * o usuário responsável pelo mesmo.
	 * 
	 * @param pedidoWorkupId pedido a ser atualizado.
	 * @param novoStatus novo status que o pedido irá receber.
	 */
	void atualizarStatusPedido(Long pedidoWorkupId, StatusPedidosWorkup novoStatus);

	/**
	 * Metodo para alterar o status e setar o usuario no pedido de workup.
	 * 
	 * @param idPedido
	 */
	void atribuirPedidoWorkup(Long idPedido);

	/**
	 * Método para listar os dados de ct e medico.
	 * 
	 * @param idPedido identificador do pedido
	 * @return dto com as informações necessarias
	 */
	DadosCentroTransplanteDTO listarDadosCT(Long idPedido);

	/**
	 * Metodo para finalizar pedido de workup.
	 * 
	 * @param idPedido
	 */
	void finalizarPedidoWorkup(Long idPedido);

	/**
	 * Deve fechar o pedido de workup alterando seu status para Realizado.
	 * 
	 * @param idPedido - identificador do pedido
	 */
	void fecharPedidoWorkupComoRealizado(Long idPedido);
	
	/**
	 * Obter pedido de workup associado a solicitação que esteja no status informado.
	 * 
	 * @param solicitacaoId ID da solicitação associado ao pedido de workup.
	 * @param status status do pedido de workup.
	 * @return pedido de workup, caso exista.
	 */
	PedidoWorkup obterPedidoWorkup(Long solicitacaoId, StatusPedidosWorkup status);

	/**
	 * Cancela todos os pedidos de workup de acordo com o id do match associado 
	 * a solicitação atrelada ao pedido. 
	 * @param idMatch - id do match a ser excluido.
	 */
	void fecharTodosPedidosPorMatch(Long idMatch);
	
	/**
	 * Obtém o paciente associado ao pedido de workup informado.
	 * 
	 * @param pedidoWorkup pedido de workup associado.
	 * @return paciente relacionado ao processo de match/workup.
	 */
	Paciente obterPaciente(PedidoWorkup pedidoWorkup);
	
	/**
	 * Obtém o doador associado ao pedido de workup informado.
	 * 
	 * @param pedidoWorkup pedido de workup associado.
	 * @return doador relacionado ao processo de match/workup.
	 */
	Doador obterDoador(PedidoWorkup pedidoWorkup);

	/**
	 * Lista as tarefas de SUGERIR_DATA_WORKUP para um determinado centro de transplante.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante
	 * @param pageRequest - paginação
	 * @return lista de tarefas paginada
	 */
	JsonViewPage<TarefaDTO> listarDisponibilidadesPorCentroTransplante(Long idCentroTransplante, PageRequest pageRequest);

	/**
	 * Método para cancelar um pediod de workup pela solicitação.
	 * 
	 * @param solicitacao
	 * @param idMotivoCancelamento
	 */
	void cancelarPedidoWorkupPorsolicitacao(Solicitacao solicitacao, Long idMotivoCancelamento);
	
}
