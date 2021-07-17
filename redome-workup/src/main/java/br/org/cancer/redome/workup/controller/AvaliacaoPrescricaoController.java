package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AprovarAvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacoesPrescricaoDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IAvaliacaoPrescricaoService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class AvaliacaoPrescricaoController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService;
	
	/**
	 * Método para aprovar a prescrição.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliacao
	 * @param avaliacaoPrescricaoDTO - dto com fonte e justificativa do descarte da fonte
	 * @return ResponseEntity<String> mensagem de sucesso.
	 * @throws JsonProcessingException 
	 */
	@PostMapping(value = "/avaliacaoprescricao/{id}/aprovar")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PRESCRICAO + "')")
	public ResponseEntity<String> aprovarPrescricao(@PathVariable(required = true) Long id,
			@RequestBody(required = false) AprovarAvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO) throws JsonProcessingException {

		avaliacaoPrescricaoService.aprovarAvaliacaoPrescricao(id, avaliacaoPrescricaoDTO);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "avaliacao.prescricao.aprovada.sucesso"));
	}
	
	/**
	 * Método para reprovar a prescrição.
	 * 
	 * @param idAvaliacaoPrescricao - identificador da avaliacao
	 * @param justificativa - justificativa da reprovação da avaliação
	 * @return ResponseEntity<String> mensagem de sucesso.
	 * @throws JsonProcessingException 
	 */
	@PostMapping(value = "/avaliacaoprescricao/{id}/reprovar")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PRESCRICAO + "')")
	public ResponseEntity<String> reprovarPrescricao(@PathVariable(required = true) Long id,
			@RequestBody(required = true) String justificativa) throws JsonProcessingException {
		
		avaliacaoPrescricaoService.reprovarAvaliacaoPrescricao(id, justificativa);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "avaliacao.prescricao.reprovada.sucesso"));
	}
	
	/**
	 * Lista todos as avaliações de prescrição disponíveis.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@GetMapping(value = "/avaliacoesprescricao")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PRESCRICAO + "')")
	public ResponseEntity<Page<AvaliacoesPrescricaoDTO>> listarAvaliacoesPrescricao (
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {

		return ResponseEntity.ok(
				avaliacaoPrescricaoService.listarAvaliacoesPendentes(pagina, quantidadeRegistros));
	}
	
	/**
	 * Obtém avaliação da prescrição.
	 * 
	 * @param id Identificação da avaliação
	 * @return AvaliacaoPrescricaoDTO 
	 */
	@GetMapping(value = "/avaliacoesprescricao/{id}")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PRESCRICAO + "')")
	public ResponseEntity<AvaliacaoPrescricaoDTO> obterDetalheAvaliacao(@PathVariable(required = true) Long id) {
		return ResponseEntity.ok(avaliacaoPrescricaoService.obterAvaliacaoPrescricao(id));
	}

	
	

}
