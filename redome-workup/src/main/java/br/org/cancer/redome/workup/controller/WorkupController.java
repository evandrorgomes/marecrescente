package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IWorkupService;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class WorkupController {
	
	@Autowired
	private IWorkupService workupService;
	
	@GetMapping(value = "/workup/tarefas")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP + "') || "
			+ "hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL + "') || "
			+ "hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "')")
	public ResponseEntity<Page<ConsultaTarefasWorkupDTO>> listarTarefasWorkup(
			@RequestParam(required = false) Long idCentroTransplante,
			@RequestParam(required = false) Long idFuncaoCentro,
			@RequestParam(required = true ) int pagina, 
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {
		
		return ResponseEntity.ok(workupService.listarTarefasWorkupView(idCentroTransplante, idFuncaoCentro, pagina, quantidadeRegistros));
	}
	
	
	@GetMapping(value = "/workup/solicitacoes")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP + "') || "
			+ "hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL + "') ||"
			+ "hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "')")
	public ResponseEntity<Page<ConsultaTarefasWorkupDTO>> listarSolicitacoesWorkup(
			@RequestParam(required = false) Long idCentroTransplante,
			@RequestParam(required = false) Long idFuncaoCentro,
			@RequestParam(required = true ) int pagina, 
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {
		
		return ResponseEntity.ok(workupService.listarSolicitacoesWorkupView(idCentroTransplante, idFuncaoCentro, pagina, quantidadeRegistros));
	}

}
