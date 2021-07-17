package br.org.cancer.modred.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.domain.StatusTarefa;

/**
 * Interface de acesso as funcionalidades envolvendo a classe Doador.
 * 
 * @author Pizão
 */
public interface ITentativaContatoDoadorService {
	
	
	/**
	 * Finaliza a tentativa de contato.
	 * 
	 * @param tentativaContatoDoador
	 * @return tarefa fechada associada a tentativa de contato.
	 */
	TarefaDTO finalizarTentaticaContato(TentativaContatoDoador tentativaContatoDoador);

	/**
	 * Finaliza a tentativa de contato passivo.
	 * 
	 * @param tentativaContatoDoador
	 */
	void finalizarTentaticaContatoPassivo(TentativaContatoDoador tentativaContatoDoador);
	
	/**
	 * Finaliza a tentativa de contato e cria uma nova para o próximo período.
	 * 
	 * @param idTentativaContatoDoador - identificador da tentativa a set finalizada.
	 * @param idcontatoTelefone - id do telefone a ser ligado.
	 * @param dataAgendamento - data do agendamento da tentativa.
	 * @param horaInicio - horário do início do agendamento.
	 * @param horaFim - horário fim do agendamento.
	 * @param atribuirUsuario - "S" caso atribua para o usuário logado o agendamento que está sendo criado.
	
	 */
	void finalizarTentativaContatoECriarNovaTentativa(Long idTentativaContatoDoador, Long idcontatoTelefone, LocalDate dataAgendamento, LocalTime horaInicio, LocalTime horaFim, String atribuirUsuario);

	/**
	 * Criar uma tentativa de contato prévia para depois apenas haver a resposta.
	 * 
	 * @param pedidoContato - pedido de contato ao qual pertence a tentativa.
	 * @return tentativa de contato gravada.
	 */
	TentativaContatoDoador criarTentativaContato(PedidoContato pedidoContato, Long idContatoTelefone, LocalDate dataAgendamento, LocalTime horaInicio, LocalTime horaFim);

	/**
	 * Obtem uma tentativa de contato por id.
	 * 
	 * @param tentativaContatoId - id da tentativa de contato.
	 * @return tentativa de contato populada.
	 */
	TentativaContatoDoador obterTentativaPorId(Long tentativaContatoId);

	/**
	 * Opeter tentativas por pedido.
	 * 
	 * @param idPedido
	 * @return listagem de tentativas.
	 */
	List<TentativaContatoDoador> obterPorPedido(Long idPedido);

	/**
	 * Salva a tentativa passada por parametro.
	 * 
	 * @param tentativa a ser salva.
	 */
	void salvarTentativa(TentativaContatoDoador tentativa);

	/**
	 * Cancela tentativas de contato atreladas ao pedido.
	 * 
	 * @param pedido ao qual pertencem as tentativas.
	 */
	void cancelarTentativasDeContato(PedidoContato pedido);

	/**
	 * Método utilizado para atribuir a tarefa de tentativa de contato pelo id da tenatativa e tipoTarefa.
	 * 
	 * @param idTentativaContato - id da tentativa de contato
	 * @param idTipoTarefa - id do tipo de tarefa
	 * @return retorna a tarefa serializada.
	 */
	MappingJacksonValue atribuirTarefa(Long idTentativaContato, Long idTipoTarefa);

	/**
	 * Método que obtém a última tetnativa de contato de um pedido de contato.
	 * 
	 * @param idPedidoContato - identificador do pedido de contato
	 * @return Última tentativa de contato do doador.
	 */
	TentativaContatoDoador obterUltimaTentativaContatoPorPedidoContatoId(Long idPedidoContato);

	/**
	 * Método que obtém a tarefa associada a tentativa de contato.
	 * 
	 * @param tentativaContato Tentativa de contato o qual a tarefa está associada.
	 * @return a tarefa associada a tentativa de contato.
	 */
	TarefaDTO obterTarefaAssociadaATentativaContatoEStatusTarefa(TentativaContatoDoador tentativaContato, List<StatusTarefa> listStatusTarefa, Boolean atribuidoQualquerUsuario, Boolean tarefaSemAgendamento);

	/**
	 * Determina se a tentativa pode ser finalizada verificando o tempo e as tentativas realizadas anteriormente.
	 * 
	 * @param id - Identificador da tentativa de contato.
	 * @return True se a tentativa já passou do prazo e número mínimo de tetativas foi atingido.
	 */
	Boolean podeFinalizarTentativaContato(Long id);

	/**
	 * Finaliza o pedido de contato e determina a próxima ação dependendo do tipo de contato.
	 * 	 * 
	 * @param id Identificador da tentativa de contato.
	 */
	void finalizarTentaticaContato(Long id);

}
