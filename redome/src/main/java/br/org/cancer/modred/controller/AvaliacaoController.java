package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.PacienteAvaliacaoTarefaDTO;
import br.org.cancer.modred.controller.page.AvaliacaoJsonPage;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para a entidade Avaliação.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoes", produces = "application/json;charset=UTF-8")
public class AvaliacaoController {

	@Autowired
	private IAvaliacaoService avaliacaoService;

	@Autowired
	private IPendenciaService pendenciaService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Serviço para listar pendencias, de uma determinada avaliação, de forma paginada.
	 * 
	 * @param avaliacaoId avaliação usada como base para buscar as pendências
	 * @param pagina contador da paginação
	 * @param quantidadeRegistros quantidade de registros exibidos em cada página.
	 * @return ResponseEntity<Page<Pendencia>> response com sucesso e a lista encontrada após a execução do método.
	 */
	@RequestMapping(value = "{id}/pendencias", method = RequestMethod.GET)
	@PreAuthorize("(hasPermission(#avaliacaoId, 'Avaliacao', 'AVALIAR_PACIENTE')"
			+ "|| hasPermission(#avaliacaoId, 'Avaliacao', 'VISUALIZAR_AVALIACAO'))")	
	@JsonView(AvaliacaoView.ListarPendencias.class)
	public ResponseEntity<Page<Pendencia>> listarPendencias(
			@PathVariable(name = "id") Long avaliacaoId,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return new ResponseEntity<Page<Pendencia>>(
				avaliacaoService.listarPendencias(avaliacaoId, new PageRequest(pagina, quantidadeRegistros)),
				HttpStatus.OK);
	}

	/**
	 * Aprova ou reprova uma avaliação.
	 * 
	 * @param avaliacaoId id da avaliacao
	 * @param avaliacao avaliacao preenchida
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(#avaliacaoId, 'Avaliacao', 'AVALIAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> aprovarOuReprovarAvaliacaoPaciente(
			@PathVariable(name = "id") Long avaliacaoId,
			@RequestBody(required = true) Avaliacao avaliacao) {
		
		avaliacaoService.alterarAprovacaoPaciente(avaliacaoId, avaliacao);
		String mensagemRetorno = avaliacao.getAprovado() ? "avaliacao.aprovado.sucesso" : "avaliacao.reprovado.sucesso";

		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, mensagemRetorno)), HttpStatus.OK);
	}

	/**
	 * Método para salvar uma nova pendência.
	 * 
	 * @param avaliacaoId id da avaliacao
	 * @param pendencia objeto de pendencia
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método
	 */
	@RequestMapping(value = "{id}/pendencias", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#avaliacaoId, 'Avaliacao', 'AVALIAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> criarPendencia(@PathVariable(name = "id") Long avaliacaoId,
			@RequestBody(required = true) Pendencia pendencia) {
		pendenciaService.criarPendencia(pendencia);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "pendencia.criada.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Retorna a lista de tarefas com os pacientes que ainda não tem nenhum avaliador associado quando status for STATUS = ABERTA.
	 * Retorna a lista de tarefas com os pacientes que possuem avaliador quando status for STATUS = ATRIBUIDA.
	 * @param pagina - pagina atual
	 * @param quantidadeRegistros - quantidade de registros
	 * @param idCentroTransplante - Identificador do centro de transplante
	 * @param status - status da tarefa
	 * @return Page<PacienteAvaliacaoTarefaDTO>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{status}")
	@PreAuthorize("hasPermission('CONSULTAR_AVALIACOES')")
	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	public ResponseEntity<AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO>> listarAvaliacoes(@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros,
			@RequestParam(required = true) Long idCentroTransplante,
			@PathVariable(name = "status") String status) {
		AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listaPacientes = null;
		if (StatusTarefa.valueOf(status).equals(StatusTarefa.ABERTA)) {
			listaPacientes = avaliacaoService.listarAvaliacoesDeUmCentroTransplantador(idCentroTransplante, PageRequest.of(pagina,
					quantidadeRegistros));
		}
		else
			if (StatusTarefa.valueOf(status).equals(StatusTarefa.ATRIBUIDA)) {
				listaPacientes = avaliacaoService.listarPacientesDoAvaliadorLogado(idCentroTransplante, PageRequest.of(pagina, quantidadeRegistros));
			}

		return new ResponseEntity<AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO>>(listaPacientes, HttpStatus.OK);
	}
	
	/**
	 * Método para atribuir uma avaliação de um novo paciente a um avaliador.
	 * 
	 * @param avaliacaoId - id da avaliação a ser iniciada
	 * @return Status OK
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "{id}/iniciar")
	@PreAuthorize("hasPermission(#avaliacaoId, 'Avaliacao', 'AVALIAR_PACIENTE')")
	public ResponseEntity<String> atribuirAvaliacaoDoPacienteAoAvaliadorLogado(@PathVariable(name = "id") Long avaliacaoId) {
		avaliacaoService.atribuirAvaliacaoAoAvaliador(avaliacaoId);
		return new ResponseEntity<String>(HttpStatus.OK);

	}
}