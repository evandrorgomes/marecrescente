package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.org.cancer.modred.controller.dto.AvaliacaoPrescricaoDTO;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoPrescricao;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IAvaliacaoPrescricaoService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de REST controller para motivo de cancelamento de pedido de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoesprescricao", produces = "application/json;charset=UTF-8")
public class AvaliacaoPrescricaoController {

	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService;

	/**
	 * Método para aprovar a prescrição.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliacao
	 * @param avaliacaoPrescricaoDTO - dto com fonte e justificativa do descarte da fonte
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso.
	 */
	@RequestMapping(value = "{idAvaliacaoPrescricao}/aprovar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('AVALIAR_PRESCRICAO')")
	public ResponseEntity<CampoMensagem> aprovarPrescricao(@PathVariable(required = true) Long idAvaliacaoPrescricao,
			@RequestBody(required = false) AvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO) {

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(avaliacaoPrescricaoService
				.aprovarAvaliacaoPrescricao(idAvaliacaoPrescricao, avaliacaoPrescricaoDTO.getIdFonteCelulaDescartada(),
						avaliacaoPrescricaoDTO.getJustificativaDescarteFonteCelula())),
				HttpStatus.OK);
	}

	/**
	 * Método para reprovar a prescrição.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliacao
	 * @param justificativa - justificativa da reprovação da avaliação
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso.
	 */
	@RequestMapping(value = "{idAvaliacaoPrescricao}/reprovar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('AVALIAR_PRESCRICAO')")
	public ResponseEntity<CampoMensagem> reprovarPrescricao(@PathVariable(required = true) Long idAvaliacaoPrescricao,
			@RequestBody(required = false) String justificativa) {
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(avaliacaoPrescricaoService
				.reprovarAvaliacaoPrescricao(idAvaliacaoPrescricao, justificativa)),
				HttpStatus.OK);
	}

	/**
	 * Método que verifica se existe paciente em duplicidade com o parametro informado.
	 * 
	 * @param paciente
	 * @return TRUE se existir, FALSE se não existir.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(AvaliacaoPrescricaoView.Detalhe.class)
	@PreAuthorize("hasPermission('AVALIAR_PRESCRICAO')")
	public ResponseEntity<AvaliacaoPrescricao> obterDetalheAvaliacao(@PathVariable(required = true) Long id) {
		return new ResponseEntity<AvaliacaoPrescricao>(avaliacaoPrescricaoService.obterAvaliacao(id), HttpStatus.OK);
	}
	
	/**
	 * Lista todos as avaliações de prescrição disponíveis.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "pendentes", method = RequestMethod.GET)
	@JsonView(AvaliacaoPrescricaoView.ListarAvaliacao.class)
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_PRESCRICAO + "')")
	public ResponseEntity<Page<TarefaDTO>> listarAvaliacoesPendentes(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		return new ResponseEntity<Page<TarefaDTO>>(
				avaliacaoPrescricaoService.listarAvaliacoesPendentes(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}

}
