package br.org.cancer.modred.service;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.AvaliacaoDTO;
import br.org.cancer.modred.controller.dto.PacienteAvaliacaoTarefaDTO;
import br.org.cancer.modred.controller.page.AvaliacaoJsonPage;
import br.org.cancer.modred.controller.page.PendenciaJsonPage;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;

/**
 * Interface para disponibilizar métodos de Avaliação.
 * 
 * @author bruno.sousa
 *
 */
public interface IAvaliacaoService {

	/**
	 * @param rmr do paciente.
	 * @return a ultima avaliacao do paciente
	 */
	Avaliacao obterUltimaAvaliacaoPorPaciente(Long rmr);

	/**
	 * @param rmr do paciente
	 * @return avaliacao atual em aberto do paciente.
	 */
	AvaliacaoDTO obterAvaliacaoAtual(Long rmr);

	/**
	 * Lista todas as pendências relacionadas a avaliação informada como parametro.
	 * 
	 * @param avaliacaoId
	 * @return
	 */
	PendenciaJsonPage<Pendencia> listarPendencias(Long avaliacaoId, PageRequest pageRequest);

	/**
	 * Aprova ou reprova uma avaliacao.
	 * 
	 * @param idAvaliacao
	 * @param Avaliacao
	 */
	void alterarAprovacaoPaciente(Long idAvaliacao, Avaliacao avaliacao);

	/**
	 * Verificar se o usuário logado é o médico responsável pela avaliação.
	 * 
	 * @param avaliacaoId
	 * @return
	 */
	boolean verificarUsuarioLogadoResponsavelPelaAvaliacao(Long avaliacaoId);

	/**
	 * Retorna uma lista de pacientes que ainda não tem nenhum avaliador associado. Somente os que estão para um determinado
	 * centro de transplante.
	 * 
	 * @param idCentroTransplante - identificação do centro de transplante
	 * @param paginacao
	 * @return
	 */
	AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listarAvaliacoesDeUmCentroTransplantador(Long idCentroTransplante, PageRequest paginacao);

	/**
	 * Retorna a lista de pacientes do avaliador logado.
	 * 
	 * * @param idCentroTransplante - identificação do centro de transplante
	 * @param pageRequest
	 * @return
	 */
	AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listarPacientesDoAvaliadorLogado(Long idCentroTransplante, PageRequest pageRequest);

	/**
	 * Método para atribuir uma avaliação de um novo paciente a um avaliador.
	 * 
	 * @param avaliacaoId
	 */
	void atribuirAvaliacaoAoAvaliador(Long avaliacaoId);

	/**
	 * Método para cancelar a avaliação, suas tarefas e pendências.
	 * 
	 * @param avaliacao
	 * @param rmr
	 */
	void cancelarAvaliacaoEProcessoDeAvaliacao(Avaliacao avaliacao, Long rmr);

	/**
	 * Cria a avaliação inicial para o paciente.
	 * 
	 * @param paciente
	 */
	void criarAvaliacaoInicial(Paciente paciente);
	
	/**
	 * Verifica se a avaliação do paciente informado
	 * está em andamento ou não.
	 * 
	 * @param rmr Identificador do paciente.
	 * @return TRUE, caso esteja iniciada.
	 */
	boolean verificarSeAvaliacaoEmAndamento(Long rmr);
	
	/**
	 * Alterar o centro avaliador da avaliação.
	 * Para esta alteração, é necessário recriar a tarefa (cancelar/criar) para o novo centro
	 * avaliador e trocar o centro avaliador na avaliação e no paciente.
	 * 
	 * @param idAvaliador ID da avaliação.
	 * @param novoCentroAvaliador novo centro avaliador que receberá a atribuição.
	 */
	void alterarCentroAvaliador(Long idAvaliador, CentroTransplante novoCentroAvaliador);
	
	/**
	 * Verifica se a avaliação do paciente informado
	 * está aprovada.
	 * 
	 * @param rmr Identificador do paciente.
	 * @return TRUE, caso esteja aprovada.
	 */
	boolean verificarSeAvaliacaoAprovada(Long rmr);
}
