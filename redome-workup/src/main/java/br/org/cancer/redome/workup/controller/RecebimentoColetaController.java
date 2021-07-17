package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.model.RecebimentoColeta;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.impl.RecebimentoColetaService;
import br.org.cancer.redome.workup.util.AppUtil;

/**
 * Classe de métodos REST para recebimento de coleta.
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class RecebimentoColetaController {
	
	@Autowired
	private RecebimentoColetaService recebimentoColetaService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método para cadastrar recebimento de coleta.
	 * 
	 * @param idRecebimento - identificador do recebimento.
	 * @param recebimento - recebimento de coleta a ser persistido.
	 * @return ResponseEntity<String> - mensagem de sucesso
	 * @throws JsonProcessingException 
	 */
	@PutMapping(value = "/recebimentocoleta")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_RECEBIMENTO_COLETA + "')")
	public ResponseEntity<String> salvarRecebimentoColeta(
			@RequestBody(required = true) RecebimentoColeta recebimento) throws JsonProcessingException {
		
		recebimentoColetaService.salvarRecebimentoColeta(recebimento);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "recebimento.coleta.mensagem.sucesso"));
	}
}
