package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.workup.dto.ConsultaTarefasAvaliacaoPedidoColetaDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IAvaliacaoPedidoColetaService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class AvaliacaoPedidoColetaController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IAvaliacaoPedidoColetaService avaliacaoPedidoColetaService;
	
	@GetMapping(value = "/avaliacoespedidoscoleta")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<Page<ConsultaTarefasAvaliacaoPedidoColetaDTO>> listarTarefas(
			@RequestParam(required = true ) int pagina, 
			@RequestParam(required = true) int quantidadeRegistros) {
		return ResponseEntity.ok(avaliacaoPedidoColetaService.listarTarefasAvaliacaoPedidoColeta(pagina, quantidadeRegistros));
	}
	
	@PostMapping(value = "/avaliacoespedidoscoleta/prosseguir")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<String> prosseguirComPedidoColeta(
			@RequestBody(required = true) Long idAvaliacaoResultadoWorkup) {
		
		avaliacaoPedidoColetaService.prosseguirPedidoColeta(idAvaliacaoResultadoWorkup);		
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource,	"avaliacao.pedido.coleta.confirmada.pedido.coleta"));
	}
	
	@PostMapping(value = "/avaliacoespedidoscoleta/naoprosseguir", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<String> naoProsseguirComPedidoColeta(
			@RequestPart(required = true) Long idAvaliacaoResultadoWorkup,
			@RequestPart(required = false) String justificativa) {
		
		avaliacaoPedidoColetaService.naoProsseguirPedidoColeta(idAvaliacaoResultadoWorkup, justificativa);		
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource,	"avaliacao.pedido.coleta.confirmada.workup.cancelado"));
	}
	
	
	
	
	

}
