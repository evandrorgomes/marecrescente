package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.controller.dto.AnaliseMatchDTO;
import br.org.cancer.modred.controller.dto.ContatoPacienteDTO;
import br.org.cancer.modred.controller.dto.DetalheAvaliacaoPacienteDTO;
import br.org.cancer.modred.controller.dto.LogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.MismatchDTO;
import br.org.cancer.modred.controller.dto.PacienteTarefaDTO;
import br.org.cancer.modred.controller.dto.PacienteWmdaDTO;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.StatusPacienteDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para metodos de negocio de paciente.
 * 
 * @author Filipe Paes
 *
 */
public interface IPacienteService extends IService<Paciente, Long>{

	/**
	 * Método para persistir paciente.
	 * 
	 * @param Paciente paciente a ser persistido
	 * @param listaArquivosLaudo
	 */
	void salvar(Paciente paciente);

	/**
	 * Método para validar paciente.
	 * 
	 * @param Paciente paciente a ser persistido
	 * @return List<CampoMensagem> lista de mensagens de erro
	 */
	List<CampoMensagem> validar(Paciente paciente);

	/**
	 * Pesquisa se já existe paciente com os mesmos dados informados cadastrado na base. Como chave candidata de paciente temos os
	 * seguintes campos, nessa ordem de prioridade, caso estejam preenchidos: - CPF - CNS - Nome, data nascimento e nome da mãe
	 * (pode vir nulo e deve ser verificado).
	 * 
	 * @author Pizão
	 * @param paciente Objeto com os dados do paciente que está sendo incluso ou alterado.
	 */
	void verificarPacienteJaCadastradoRetorno(Paciente paciente);

	/**
	 * Retorno o paciente somente com os dados de identificação, dados pessoais e os dados de contato.
	 * 
	 * @param id
	 * @return Paciente
	 */
	Paciente obterPaciente(Long id);

	/**
	 * Busca a última evolução de um paciente.
	 * 
	 * @param rmr do paciente a ser buscado as evoluções no banco
	 * @return uma evolução
	 */
	Evolucao obterUltimaEvolucao(Long rmr);

	/**
	 * Retorna a lista com os pacientes encontrados pelos parametros de busca.
	 * 
	 * @param rmr
	 * @param nome
	 * @param meusPacientes
	 * @param idCentroAvaliador
	 * @param pageRequest
	 * @return
	 */
	Page<Paciente> listarPacientePorRmrOuNome(Long rmr, String nome, Boolean meusPacientes, Long idFuncaoTransplante, Long idCentroAvaliador, PageRequest pageRequest);

	/**
	 * Método que busca apenas os dados de identificação do paciente.
	 * 
	 * @param rmr
	 * @return
	 */
	Paciente obterDadosIdentificadaoPorPaciente(Long rmr);

	/**
	 * Lista de pacientes em avaliação com tarefas atribuídas ao usuário (médico) logado Representa uma espécie de sumário com o
	 * total de tarefas a serem realizadas pelo paciente.
	 * 
	 * @param idCentroAvaliador - Identificador do centro avaliador.
	 * @param paginacao "fatia" da lista que deverá ser exibida na tela.
	 * @return lista de pacientes em avaliação.
	 */
	Paginacao<PacienteTarefaDTO> listarPacientesEmAvaliacaoPorMedicoLogado(Long idCentroAvaliador, PageRequest paginacao);


	/**
	 * Obtém endereço contato do paciente (endereço e telefones), a partir do RMR informado.
	 * 
	 * @param rmr Identificador do paciente utilizado na busca.
	 */
	ContatoPacienteDTO obterContatoPaciente(Long rmr);

	/**
	 * Atualiza as informações do paciente.
	 * 
	 * @param paciente paciente a ser atualizado.
	 */
	void atualizarContatoPaciente(Paciente paciente);

	/**
	 * Método que atualiza os dados de identificação e dados pessoais.
	 * 
	 * @param paciente paciente a ser atualizado com novos dados pessoais.
	 */
	void atualizarDadosPessoais(Paciente paciente);

	/**
	 * Método para obter somente a identificação e os dados pessoais do paciente.
	 * 
	 * @param rmr referência para busca dos dados pessoais.
	 * @return uma instância de paciente
	 */
	Paciente obterDadosPessoais(Long rmr);

	/**
	 * Método que verifica se o paciente está em óbito pelo mótivo de cancelamento da busca.
	 * 
	 * @param rmr
	 * @param geraExcecao
	 * @return Boolean
	 */
	Boolean verificarPacienteEmObito(Long rmr, Boolean geraExcecao);

	/**
	 * Obtém o paciente associado ao ID da solicitação informado.
	 * 
	 * @param solicitacaoId ID da solicitação informado.
	 * @return paciente associado a solicitação.
	 */
	Paciente obterPacientePorSolicitacao(Long solicitacaoId);

	/**
	 * Obtem o paciente associado ao pedido de logística.
	 * 
	 * @param pedidoLogistica parametro utilizado na pesquisa.
	 * @return paciente associado.
	 */
	Paciente obterPacientePorPedidoLogistica(PedidoLogistica pedidoLogistica);

	/**
	 * Obtém a lista de matchas para um paciente.
	 * 
	 * @param rmr
	 * @param filtro
	 * @return
	 */
	AnaliseMatchDTO listarMatchs(Long rmr, FiltroMatch filtro);
	
	/**
	 * Lista o histórico de atualizações relativo a busca do paciente.
	 * 
	 * @param rmr RMR do paciente associado ao log.
	 * @return lista de logs ocorridos para o paciente.
	 */
	Paginacao<LogEvolucaoDTO> listarLogEvolucao(Long rmr, Pageable pageable);
	
	
	/**
	 * Obtém paciente a partir do ID do pedido contato.
	 * 
	 * @param pedidoContatoId ID do pedido de contato a ser pesquisado.
	 * @return paciente associado ao pedido contato informado.
	 */
	Paciente obterPacientePorPedidoContato(Long pedidoContatoId);
	
	/**
	 * Obtém o paciente associado ao ID do match.
	 * 
	 * @param matchId ID do match.
	 * @return paciente associado ao match.
	 */
	Paciente obterPacientePorMatch(Long matchId);
	
	/**
	 * Obtém o paciente associado ao genótipo informado.
	 * 
	 * @param matchId ID do match.
	 * @return paciente associado ao match.
	 */
	Paciente obterPacientePorGenotipo(Long genotipoId);
	
	/**
	 * Obtem o detalhe da avaliação do paciente para ser exibida na 
	 * tela de confirmação de centro de transplante.
	 * 
	 * @param rmr Identificador do paciente.
	 * @return o detalhe da avaliação do paciente informado.
	 */
	DetalheAvaliacaoPacienteDTO obterDetalheAvaliacaoPaciente(Long rmr);
	
	/**
	 * Confirmar o centro transplantador para o paciente.
	 * Esta informação é gravada na busca, indicando uma sobreposição
	 * ao centro definido no momento do cadastro.
	 * 
	 * @param rmr identificador do RMR.
	 * @param centroTransplantadorId identificador do centro transplantador que deverá ser confirmado como o do paciente.
	 */
	void confirmarCentroTransplantador(Long rmr, Long centroTransplantadorId);
	
	/**
	 * "Marca" a busca como indefinida quanto ao centro de transplante.
	 * Ocorre quando o analista de busca verifica que o centro do cadastro do paciente 
	 * não será o transplantador e não sabe quem será o novo centro. 
	 * 
	 * @param rmr identificador do paciente
	 */
	void indefinirCentroTransplantador(Long rmr);
	
	/**
	 * Verifica se paciente está proibido de receber novos exames.
	 * Isso ocorre quando o paciente possui match selecionado com algum doador, tendo
	 * ou não prescrição realizada.
	 * 
	 * @param rmr identificador do paciente.
	 * @return TRUE se é possível receber novos exames.
	 */
	boolean verificarSePacienteProibidoReceberNovosExames(Long rmr);
	
	/**
	 * Recusa o CT, selecionado anteriormente, para a busca do paciente.
	 * 
	 * @param rmr identificador do paciente
	 * @param justificativa justificativa pela recusa do CT ao paciente.
	 */
	void recusarPacientePeloCentroTransplante(Long rmr, String justificativa);
	
	/**
	 * Obtém o paciente associado ao pedido de exame informado.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return paciente associado ao pedido informado.
	 */
	Paciente obterPacientePorPedidoExame(Long pedidoExameId);
	
	/**
	 * Obter paciente a partir do ID da solicitação de exame do teste confirmatório.
	 * 
	 * @param solicitacaoId ID da solicitação do CT.
	 * @return paciente associado a solicitação.
	 */
	Paciente obterPacientePorSolicitacaoCT(@Param("solicitacaoId") Long solicitacaoId);
	
	/**
	 * Obtém o médico responsável pelo paciente.
	 * 
	 * @param paciente paciente a ser pesquisado.
	 * @return médico responsável pelo paciente informado.
	 */
	Medico obterMedicoResponsavel(Paciente paciente);
	
	/**
	 * Obtém o centro de transplante confirmado como o centro responsável
	 * pelo paciente. Isto é realizado quando o paciente o primeiro é disponibilizado
	 * para a seguir para prescriçao e o analista de busca faz o contato e confirma
	 * quem será o centro.
	 * 
	 * @param paciente paciente a ser pesquisado.
	 * @return centro de tranplante já confirmado para o paciente.
	 */
	CentroTransplante obterCentroTransplanteConfirmado(Paciente paciente);
	
	/**
	 * Popula o MatchDTO a partir das informações contidas no Match.
	 * 
	 * @param match match que deve gerar o DTO.
	 * @return DTO contendo as informações da entidade.
	 */
	//MatchDTO popularMatchDTO(Match match);
	
	/**
	 * Transfere o paciente para um novo centro avaliador.
	 * 
	 * @param rmr Identificador do paciente.
	 * @param centroAvaliadorDestino centro avaliador de transferência.
	 */
	void transferirCentroAvaliador(Long rmr, CentroTransplante centroAvaliadorDestino);

	/**
	 * Obtem o diagnostico do paciente por rmr.
	 * 
	 * @param rmr Identificador do paciente
	 * @return instância de Diagnostico
	 */
	Diagnostico obterDiagnostico(Long rmr);

	/**
	 * Obtem os dados de mismatch do paciente por rmr.
	 * 
	 * @param rmr Identificador do paciente.
	 * @return DTO com os dados do mismatch
	 */
	MismatchDTO obterDadosMismatch(Long rmr);

	/**
	 * Método que altera os dados de mismatch do paciente por rmr.
	 * 
	 * @param rmr Identificador do paciente.
	 * @param mismatchDTO DTO com os dados do mismatch.
	 */
	void alterarDadosMismatch(Long rmr, MismatchDTO mismatchDTO);
	
	/**
	 * Solicita uma nova busca para o paciente que esta,
     * necessariamente, com busca inativa (cancelado, transplantado, etc)
     * e precisa realizar um novo acompanhamento, independente do motivo.
     * 
	 * @param rmr identificador do paciente.
	 */
	void solicitarNovaBusca(Long rmr);
	
	
	/**
	 * Lista os matchs inativos de um paciente por fase. 
	 * 
	 * @param rmr - Identificação do paciente 
	 * @param filtro - Medulo ou cordão
	 * @param fase - Fase a ser pesquisada
	 * @return lista de matchs inativos
	 */
	List<MatchDTO> listarFaseInativos(Long rmr, FiltroMatch filtro, FasesMatch fase);

	/**
	 * Obtem o estado do paciente.
	 * 
	 * @param rmr
	 * @return dto status do paciente
	 */
	StatusPacienteDTO obterStatusPacientePorRmr(Long rmr);
	
	/**
	 * Obtem todos os pedidos de exame conciliados.
	 * 
	 * @param rmr - filtro rmr do paciente
	 * @param nomePaciente - filtro nome do paciente
	 * @param pageable - paginação
	 * @return PedidosPacienteInvoiceDTO lista paginada de pedidos
	 */
	Paginacao<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nomePaciente, Pageable pageable);
	
	
	/**
	 * Obtem todos os pedidos de exame conciliados.
	 * 
	 * @param rmr - filtro rmr do paciente 
	 * @param nome do paciente.
	 * @return PedidosPacienteInvoiceDTO lista de pedidos.
	 */
	List<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nome);
	
	/**
	 * Obtém um paciente por rmr.
	 * @param rmr do paciente.
	 * @return PacienteWmdaDTO para WMDA.
	 */
	PacienteWmdaDTO obterPacienteDtoWmdaPorRmr(Long rmr);

	/**
	 * Método que atualiza o paciente com o código do wmda.
	 * 
	 * @param rmr - Identificação do paciente
	 * @param idWmda - identificado do paciente no WMDA.
	 */
	void atualizarWmdaIdPorRmrDoPaciente(Long rmr, String idWmda);

}
