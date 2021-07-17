package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.util.AppUtil;

/**
 * Classe de REST controller para pedido de coleta.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PedidoColetaController {

	@Autowired
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value = "/pedidocoleta/{idPedidoColeta}")
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_PEDIDO_COLETA + "')"
			+ " || hasPermission('" + Recursos.TRATAR_RECEBIMENTO_COLETA + "') ")
	
	public ResponseEntity<PedidoColeta> obterPedidoColeta(
			@PathVariable(required = true) Long idPedidoColeta) {

		return ResponseEntity.ok(pedidoColetaService.obterPedidoColetaEDatasWorkupPorId(idPedidoColeta));
	}

	/*
	 * Teste Unitário - Feito
	 */
	@PutMapping(value = "/pedidocoleta/{idPedidoColeta}")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_PEDIDO_COLETA + "')")
	public ResponseEntity<String> agendarPedidoColeta(
			@PathVariable(required = true) Long idPedidoColeta,
			@RequestBody(required = true) PedidoColeta pedidoColeta) throws JsonProcessingException {
		
		String mensagemRetorno = pedidoColetaService.agendarPedidoColeta(idPedidoColeta, pedidoColeta);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}
}
