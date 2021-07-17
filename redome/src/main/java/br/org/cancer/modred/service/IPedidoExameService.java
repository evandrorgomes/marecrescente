package br.org.cancer.modred.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.UltimoPedidoExameDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.StatusPedidosExame;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.vo.PedidoExameDoadorInternacionalVo;
import br.org.cancer.modred.vo.PedidoExameDoadorNacionalVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoExame.
 * 
 * TODO: Refatorar esta classe para que ela faça ligação com solicitação e não direto a paciente.
 * 
 * @author Pizão
 */
public interface IPedidoExameService extends IService<PedidoExame, Long> {

	/**
	 * Cria um novo pedido de exame para o doador nacional (Fase 2 ou fase 3).
	 * 
	 * @param solicitacao solicitação que originou o pedido de contato.
	 * 
	 * @return Pedido de exame salvo
	 */
	PedidoExame criarPedidoDoadorNacional(Solicitacao solicitacao);

	/**
	 * Cancela um pedido de exame, se possível (solicitação ainda não tiver sido atendida), caso contrário o exame será realizado
	 * e registrado no sistema, normalmente.
	 * 
	 * @param solicitacao solicitação que originou o pedido de exame.
	 * @param justificativa - Justificativa do cancelameto quando necessário
	 * @param dataCancelamentoPedido - data do cancelamento do pedido de exame. 
	 * @param motivoStatusId - Motivo do status do doador
	 * @param timeRetornoInatividade - tempo de retorno de inatividade do doador.
	 * 
	 */
	void cancelarPedido(Solicitacao solicitacao, String justificativa, LocalDate dataCancelamentoPedido, Long motivoStatusId, Long timeRetornoInatividade);

	/**
	 * Obtem pedidos de exame de acordo com solicitação e status.
	 * 
	 * @param idSolicitacao - id da solicitação de fase 2 ou 3.
	 * @return pedidos localizados.
	 */
	List<PedidoExame> buscarPedidosDeExamePorSolicitacao(Long idSolicitacao);

	/**
	 * Método para que o centro de coleta receba o pedido de exame.
	 * 
	 * @param pedidoExame pedido a ser recebido contendo a id do pedido, data da coleta e a data do recebimento.
	 */
	void receberPedido(PedidoExame pedidoExame);

	/**
	 * Método para salvar o resultado o exame do pedido.
	 * 
	 * @param pedidoExamde - pedido de exame a ser salvo.
	 * @param listaArquivosLaudo - arquivos de resultado.
	 */
	void salvarResultadoPedidoExamePaciente(PedidoExame pedidoExame, List<MultipartFile> listaArquivosLaudo) throws Exception;

	/**
	 * Método para obter o ultimo pedido de exame CT pela busca.
	 * 
	 * @param buscaId
	 * @return ultimo pedido de exame
	 */
	UltimoPedidoExameDTO obterUltimoPedidoExamePelaBusca(Long buscaId);

	/**
	 * Altera o laboratório responsável pelo CT destinado ao pedido de exame do paciente. Só é possível alterar o laboratório caso
	 * o pedido ainda esteja em aberto.
	 * 
	 * @param pedidoExameId identificador do pedido de exame.
	 * @param idLaboratorio Novo laboratorio
	 * @return TRUE se foi possível alterar o laboratório, FALSE caso não consiga.
	 */
	boolean alterarLaboratorioCT(Long pedidoExameId, Long idLaboratorio);

	/**
	 * Gera um documento com detalhes do pedido de exame para o médido do paciente.
	 * 
	 * @param idBusca
	 * @return Documento com detalhes do pedido de exame
	 */
	File obterDocumentoPedidoExame(Long idBusca);

	/**
	 * Gera um documento com detalhes da instrução de coleta de sangue ou swap.
	 * 
	 * @param idBusca
	 * @return Documento com detalhes do pedido de exame
	 */
	File obterDocumentoInstrucaoColeta(Long idBusca, Long idTipoExame);
	
	/**
	 * Obtém pedido de exame de CT.
	 * 
	 * @param idPedidoExame -identificador do pedido de exame
	 * @param codigo - codigo do relatorio.
	 * @return
	 */
	File obterArquivoPedidoExameCT(Long idPedidoExame, String codigo);

	/**
	 * Salva um pedido de exame solicitando os valores dos lócus para o doador, ambos informados por parâmetro.
	 * 
	 * @param solicitacao - Solicitacao  
	 * @param locusSolicitados - Lista de locus solicitados pelo usuário.
	 */
	void criarPedidoFase2Internacional(Solicitacao solicitacao, List<Locus> locusSolicitados );

	/**
	 * Obtém pedido exame por id.
	 * 
	 * @param id - identificador do pedido de exame
	 * @return PedidoExame - entidade com pedido de exame
	 */
	PedidoExame obterPedidoExame(Long idPedidoExame);

	/**
	 * Lista os pedidos associados ao match (doador).
	 * 
	 * @param matchId ID do match.
	 * @param status lista de status do pedido de exame.
	 * @return lista de pedidos que atendam aos filtros.
	 */
	List<PedidoExame> listarPedidoExamePorDoador(Long matchId, List<StatusPedidosExame> status);

	/**
	 * Listar os pedidos de exame em andamento para o doador. Se o doador for nacional, os status são todos EXCETO
	 * RESULTADO_CADASTRADO E CANCELADO. Se o doador é internacional, o status é AGUARDANDO_RESULTADO.
	 * 
	 * @param matchId ID do match.
	 * @return lista de pedidos do doador.
	 */
	List<PedidoExame> listarPedidoExamePorDoadorEmAndamento(Long matchId);

	/**
	 * Método para salvar o resultado do pedido de exame de fase2 de um doador internacional.
	 * 
	 * @param idPedidoExame - id do pedido de exame
	 * @param exame - o exame a ser salvo
	 * @param motivoStatusId - id do motivo de status para inativação do doador
	 * @param timeRetornoInatividade - tempo de retorno do doador inativo.
	 */
	void salvarResultadoPedidoExameDoadorInternacional(Long idPedidoExame, ExameDoadorInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade) throws Exception;

	/**
	 * Atualiza o pedido de exame.
	 * 
	 * @param pedidoExame
	 */
	void atualizarPedidoExameInternacional(PedidoExame pedidoExame);

	/**
	 * Método para obter o id do pedido de exame do doador.
	 * 
	 * @param idDoador id do doador
	 * @return id do pedido de exame
	 */
	Long obterIdPedidoExamePorIdMatch(Long idMatch);

	/**
	 * Obtém o pedido de exame por id da solicitação.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @return PedidoExame entidade
	 */
	PedidoExame obterPedidoExamePorSolicitacaoId(Long idSolicitacao);

	/**
	 * Método que cria um pedido de exame de ct internacional. 
	 * 
	 * @param solicitacao
	 */
	void criarPedidoExameCtInternacional(Solicitacao solicitacao);

	/**
	 * Método para salvar o resultado do pedido de exame de fase2 de um doador internacional.
	 * 
	 * @param idPedidoExame - id do pedido de exame
	 * @param exame - o exame a ser salvo
	 * @param motivoStatusId - id do motivo de status para inativação do doador
	 * @param timeRetornoInatividade - tempo de retorno do doador inativo.
	 * @param listaArquivosLaudo - arquivos de laudo do exame
	 */
	PedidoExame salvarResultadoPedidoExameCTDoadorInternacional(Long id, ExameDoadorInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade, List<MultipartFile> listaArquivosLaudo) throws Exception;
	
	/**
	 * Verifica se tem pedido Idm aberto para redirecionar para a pagina de cadastro de pedido IDM.
	 * @param pedidoExame - pedido de exame
	 * @return true se existir pedido e false se não existir pedido
	 */
	boolean temPedidoIdmAberto(PedidoExame pedidoExame);
	
	/**
	 * Obtém a tarefa associada ao pedido de exame.
	 * Isso é necessário porque, se a tarefa estiver atribuída, alguém pode estar resolvendo.
	 * 
	 * @param pedidoExame pedido de exame associado a tarefa.
	 * @return tarefa ativa associada ao pedido.
	 */
	TarefaDTO obterTarefaPorPedidoEmAberto(PedidoExame pedidoExame);
	
	/**
	 * Método para salvar o resultado do pedido de exame de ct de um cordão internacional.
	 * 
	 * @param idPedidoExame - id do pedido de exame
	 * @param exame - o exame a ser salvo
	 * @param motivoStatusId - id do motivo de status para inativação do doador
	 * @param timeRetornoInatividade - tempo de retorno do doador inativo.
	 * @param listaArquivosLaudo - arquivos de laudo do exame
	 */
	PedidoExame salvarResultadoPedidoExameCTCordaoInternacional(Long id, ExameCordaoInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade, List<MultipartFile> listaArquivosLaudo) throws Exception;

	/**
	 * Método para vertificar se exite pedido de exame ct para um doador com resultado cadastrado.
	 * 
	 * @param idDoador
	 * @return true se exitir pedido de exame de ct com resultado cadastrado.
	 */
	Boolean temPedidoExameCtComResultadoCadastrado(Long idDoador);
	
	/**
	 * Método para vertificar se exite pedido de exame ct para um paciente com resultado cadastrado.
	 * 
	 * @param rmr
	 * @return true se exitir pedido de exame de ct com resultado cadastrado.
	 */
	Boolean temPedidoExameCtParaPacienteComResultadoCadastrado(Long rmr);

	/**
	 * Método para vertificar se exite pedido de exame ct para um paciente com resultado não atendido.
	 * 
	 * @param rmr
	 * @return true se exitir pedido de exame de ct com resultado não atendido.
	 */
	Long obterTipoExameCtParaPacienteComResultadoAguardandoAmostra(Long rmr);
	
	/**
	 * Método para criar um pedido de exame fase 3 para paciente.
	 * 
	 * @param solicitacao
	 * @param idLaboratorio
	 */
	void criarPedidoFase3Paciente(Solicitacao solicitacao, Long idLaboratorio, Long idTipoExame);

	/**
	 * Lista tarefas de pedido de exame - coletas Laboratório.
	 * 
	 * @param pageRequest - paginação
	 * @return lista paginada de tarefas de pedido de exame abertas para coleta do laboratório.
	 */
	JsonViewPage<TarefaDTO> listarTarefasDeColetaPedidoExameLaboratorio(PageRequest pageRequest);

	/**
	 * Lista tarefas de pedido de exame - resultados Laboratório.
	 * 
	 * @param pageRequest - paginação
	 * @return lista paginada de tarefas de pedido de exame coletados para resultados do laboratório.
	 */
	JsonViewPage<TarefaDTO> listarTarefasDeResultadoPedidoExameLaboratorio(PageRequest pageRequest);

	/**
	 * Lista de pedidos de exame de ct para resolver divergencias.
	 * 
	 * @param idDoador
	 * @return Lista de pedidos de exame.
	 */
	List<PedidoExame> listarPedidosExameParaResolverDivergencia(Long idDoador);
	
	
	Paginacao<PedidoExameDoadorNacionalVo> listarPedidosDeExameNacional(Long idDoador, Long idPaciente, Long idBusca, Boolean exibirHistorico, 
			Long filtroFaseTipoExame, PageRequest pageable);
	
	Paginacao<PedidoExameDoadorInternacionalVo> listarAndamentoPedidosDeExameInternacional(Long idBusca, Boolean exibirHistorico, Long filtroFaseTipoExame, PageRequest pageable);
	
	Paginacao<PedidoExameDoadorNacionalVo> listarAndamentoDePedidosExamesPorDoador(Long idDoador, PageRequest pageable);
	
	void atualizarPedidosInvoiceConcilidados(List<PedidosPacienteInvoiceDTO> pedidosDTO);
	
	
	/**
	 * Método que obtém o id do registro do doador internacional pelo pedido de exame.
	 * 
	 * @param idPedidoExame
	 * @return
	 */
	String obterIdRegistroDoadorInternacionalPorPedidoExameId(Long idPedidoExame);
	
	
	/**
	 * Método que obtém o id do registro do doador internacional pelo pedido de idm.
	 * 
	 * @param idPedidoIdm
	 * @return
	 */
	String obterIdRegistroDoadorInternacionalPorPedidoIdmId(Long idPedidoIdm);
	
	/**
	 * Cria checklist de exame sem resultado há mais de 30 dias.
	 * @param id da tarefa sem resultado ha mais de 30 dias
	 */
	void criarCheckListExameSemResultadoMais30Dias(Long idTarefa);

}