package br.org.cancer.redome.workup.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DistribuicaoWorkupPorUsuarioDTO;
import br.org.cancer.redome.workup.dto.DistribuicoesWorkupDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IDistribuicaoWorkupService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class DistribuicaoWorkupController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDistribuicaoWorkupService distribuicaoWorkupService;	
	
	@GetMapping(value = "/distribuicoesworkup")
	@PreAuthorize("hasPermission('" + Recursos.DISTRIBUIR_WORKUP + "') || hasPermission('" + Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<Page<DistribuicoesWorkupDTO>> listarDistribuicoesWorkup(
			@RequestParam(required = true ) int pagina, 
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {
		
		return ResponseEntity.ok(distribuicaoWorkupService.listarTarefasDisribuicoesWorkup(pagina, quantidadeRegistros));
	}
	
	@GetMapping(value = "/distribuicoesworkup/usuarios")
	@PreAuthorize("hasPermission('" + Recursos.DISTRIBUIR_WORKUP + "') "
			+ "|| hasPermission('" + Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL + "')")	
	public ResponseEntity<Map<String, List<DistribuicaoWorkupPorUsuarioDTO>>> listarDistribuicoesWorkupPorUsuario() {
		return ResponseEntity.ok(distribuicaoWorkupService.listarDistribuicoesWorkupPorUsuario());
	}
	
	@PostMapping(value = "/distribuicaoworkup/{id}")
	@PreAuthorize("hasPermission('" + Recursos.DISTRIBUIR_WORKUP + "') || hasPermission('" + Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> atribuirDistribuicaoWorkup(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) Long idUsuario) throws JsonProcessingException {
		
		distribuicaoWorkupService.atribuirUsuarioDistribuicaoWorkup(id, idUsuario);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "distribuicao.workup.usuario.atribuido"));
	}
	
	@PutMapping(value = "/distribuicaoworkup/{id}")
	@PreAuthorize("hasPermission('" + Recursos.DISTRIBUIR_WORKUP + "') || hasPermission('" + Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> reatribuirDistribuicaoWorkup(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) Long idUsuario) throws Exception {
		
		distribuicaoWorkupService.reatribuirUsuarioDistribuicaoWorkup(id, idUsuario);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "distribuicao.workup.usuario.reatribuido"));
	}

	
	
	
}
