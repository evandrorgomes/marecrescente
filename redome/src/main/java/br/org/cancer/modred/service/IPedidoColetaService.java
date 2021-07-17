package br.org.cancer.modred.service;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.MotivosCancelamentoColeta;
import br.org.cancer.modred.model.domain.StatusPedidosColeta;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoColeta.
 * 
 * @author Fillipe Queiroz
 */
public interface IPedidoColetaService  extends IService<PedidoColeta, Long>{

	/**
	 * Método para obter o pedido de coleta por id.
	 * 
	 * @param pedidoColetaId
	 * @return PedidoColeta
	 */
	PedidoColeta obterPedidoColeta(Long pedidoColetaId);
	
	
	/**
	 * Método para agednar o pedido de coleta.
	 * 
	 * @param id
	 * @param pedidoColeta
	 */
	void agendarPedidoColeta(Long id, PedidoColeta pedidoColeta);
	
	/**
	 * Obtém o pedido de coleta associado a solicitação informada no parâmetro.
	 * 
	 * @param solicitacaoId ID da solicitação.
	 * @param status Status do pedido de coleta.
	 * @return
	 */
	PedidoColeta obterPedidoColeta(Long solicitacaoId, StatusPedidosColeta status);

	/**
	 * Consulta pedido de coleta por idDoador.
	 * @param idDoador
	 * @return pedido de coleta localizado.
	 */
	PedidoColeta obterPedidoColetaPor(Long idDoador);
	
	/**
	 * Obtém o paciente associado ao pedido de coleta realizado para um doador
	 * durante um processo de match em que ambos estão envolvidos.
	 * 
	 * @param pedidoColeta pedido de coleta que está sendo citado.
	 * @return paciente envolvido no processo de match.
	 */
	Paciente obterPacientePorPedidoColeta(PedidoColeta pedidoColeta);
	
	/**
	 * Obtém o doador associado ao pedido de coleta realizado 
	 * durante um processo de match com paciente.
	 * 
	 * @param pedidoColeta pedido de coleta que está sendo citado.
	 * @return doador envolvido no processo de match.
	 */
	Doador obterDoador(PedidoColeta pedidoColeta);
	
	/**
	 * Obtém o centro de coleta associado ao pedido de coleta.
	 * 
	 * @param pedidoColeta pedido de coleta.
	 * @return centro de transplante (que também pode ser coleta) definido para o procedimento.
	 */
	CentroTransplante obterCentroColeta(PedidoColeta pedidoColeta);

	/**
	 * Método que cria um pedido de coleta para um cordão nacional ou internacional.
	 * 
	 * @param solicitacao
	 * @return PedidoCoelta
	 */
	PedidoColeta criarPedidoColetaCordao(Solicitacao solicitacao);
	
	/**
	 * Método que cria um pedido de coleta para um doador nacional ou internacional.
	 * 
	 * @param pedidoWorkup
	 * @return PedidoColeta
	 */
	PedidoColeta criarPedidoColetaMedula(PedidoWorkup pedidoWorkup);
	
	
	/**
	 * Método para alterar o status do pedido de coleta para aguardando CT.
	 * 
	 * @param idPedido
	 * @return PedidoColeta como o status alterado. 
	 */
	PedidoColeta alterarStatusParaAguardandoCT(Long idPedido);

	/**
	 * Obtém a disponibilidade associado ao pedido de coleta informado.
	 * 
	 * @param idPedido pedido de coleta.
	 * @return retorna a disponibilidade associado ao pedido.
	 */
	Disponibilidade obterUltimaDisponibilidade(Long idPedido);
	
	/**
	 * Responde a solicitação de agendamento recusando as datas propostas e sugerir uma nova data sugerida
	 * para o analista de workup.
	 * 
	 * @param pedidoColetaId ID do pedido de workup.
	 * @param disponibilidade nova disponibilidade informada pelo CT.
	 */
	void responderDisponibilidadeColeta(Long pedidoColetaId, Disponibilidade disponibilidade);
	
	/**
	 * Cancela o pedido de coleta quando um médico do CT realiza o cancelamento.  
	 * 
	 * @param idPedidoColeta ID do pedido de coleta.
	 */
	void cancelarPedidoColetaPeloCT(Long idPedidoColeta);
	
	/**
	 * Método para listar os dados de ct e medico.
	 * 
	 * @param idPedido identificador do pedido
	 * @return dto com as informações necessarias
	 */
	DadosCentroTransplanteDTO listarDadosCT(Long idPedido);

	/**
	 * Lista as tarefas de SUGERIR_DATA_COLETA para um determinado centro de transplante.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante
	 * @param pageRequest - paginação
	 * @return lista de tarefas paginada
	 */
	JsonViewPage<TarefaDTO> listarDisponibilidadesPorCentroTransplante(Long idCentroTransplante, PageRequest pageRequest);


	/**
	 * Método para cancelar Pedido de Coleta por solicitação.
	 * 
	 * @param solicitacao - Solicitação
	 * @param motivoCancelamento - Motivo do cancelamento.
	 */
	void cancelarPedidoColetaPorSolicitacao(Solicitacao solicitacao, MotivosCancelamentoColeta motivoCancelamento);
	
	
	/**
	 * Atualiza data de coleta fecha possiveis tarefas de transporte abertas  ou atribuidas e cria uma nova tarefa
	 * para courier.
	 * 
	 * @param idPedidoLogistica identificação do pedido de logistica.
	 * @param nova data de coleta.
	 */
	void atualizarDataColetaTarefaDeCourier(Long idPedidoLogistica, LocalDate data);

	/**
	 * Lista de tarefas contendo todos os pedidos de coleta para todos usuário. 
	 * 
	 * @param paginacao paginação do resultado.
	 * @return lista de tarefas paginadas.
	 */
	JsonViewPage<TarefaDTO> listarTarefasAgendadasInternacional(PageRequest paginacao);

	/**
	 * Lista de tarefas contendo todos os pedidos de coleta para todos usuário. 
	 * 
	 * @param paginacao paginação do resultado.
	 * @return lista de tarefas paginadas.
	 */
	JsonViewPage<TarefaDTO> listarTarefasAgendadas(PageRequest paginacao);


	/**
	 * Altera o status para "REALIZADO" o pedido de coleta 
	 * 
	 * @param id  Identificador do pedido de coleta.
	 */
	void fecharPedidoColeta(Long id);

	
}
