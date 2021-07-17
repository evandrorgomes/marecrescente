package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.workup.dto.AvaliacaoResultadoWorkupDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class AvaliacaoResultadoWorkupController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IAvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService;
	
	@GetMapping(value = "/avaliacaoresultadoworkup/{id}/nacional")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<AvaliacaoResultadoWorkupDTO> obterAvaliacaoResultado(
			@PathVariable(name = "id", required = true) Long idAvaliacaoResultadoWorkup) {
		
		return ResponseEntity.ok(avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupNacionalPorId(idAvaliacaoResultadoWorkup));
	}

	@PostMapping(value = "/avaliacoesresultadoworkup/nacional/prosseguir", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> prosseguirAvaliacaoResultadoWorkupNacional (
			@RequestPart(name="idResultadoWorkup", required = true) Long idResultadoWorkup) throws Exception {

		String mensagemRetorno = avaliacaoResultadoWorkupService.prosseguirResultadoWorkupNacional(idResultadoWorkup);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}

	@PostMapping(value = "/avaliacoesresultadoworkup/nacional/prosseguircoletainviavel", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> prosseguirColetaInviavelAvaliacaoResultadoWorkupNacional (
			@RequestPart(name="idResultadoWorkup", required = true) Long idResultadoWorkup,
			@RequestPart(name="justificativa", required = true) String justificativa) throws Exception {

		String mensagemRetorno = avaliacaoResultadoWorkupService.prosseguirColetaInviavelResultadoWorkupNacional(idResultadoWorkup, justificativa);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}
	
	@PostMapping(value = "/avaliacoesresultadoworkup/nacional/naoprosseguir", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> naoProsseguirAvaliacaoResultadoWorkupNacional (
			@RequestPart(name="idResultadoWorkup", required = true) Long idResultadoWorkup,
			@RequestPart(name="justificativa", required = false) String justificativa) throws Exception {

		avaliacaoResultadoWorkupService.naoProsseguirResultadoWorkupNacional(idResultadoWorkup, justificativa);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "avaliacao.resultado.workup.nao.confirmada.sucesso"));
	}


	/*############################## INTERNACIONAL ########################*/	
	
	@PostMapping(value = "/avaliacoesresultadoworkup/internacional/prosseguir", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> prosseguirResultadoWorkupInternacional (
			@RequestPart(name="idResultadoWorkup", required = true) Long idResultadoWorkup,
			@RequestPart(name="justificativa", required = false) String justificativa) throws Exception {

		String mensagemRetorno = avaliacaoResultadoWorkupService.prosseguirResultadoWorkupInternacional(idResultadoWorkup, justificativa);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}
	
	@PostMapping(value = "/avaliacoesresultadoworkup/internacional/naoprosseguir", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> naoProsseguirResultadoWorkupInternacional (
			@RequestPart(name="idResultadoWorkup", required = true) Long idResultadoWorkup,
			@RequestPart(name="justificativa", required = false) String justificativa) throws Exception {

		avaliacaoResultadoWorkupService.naoProsseguirResultadoWorkupInternacional(idResultadoWorkup, justificativa);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "avaliacao.resultado.workup.nao.confirmada.sucesso"));
	}
}
