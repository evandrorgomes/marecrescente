package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.RegistroTipoOcorrencia;
import br.org.cancer.modred.service.IRegistroTipoOcorrenciaService;
import br.org.cancer.modred.service.impl.config.OrderBy;


/**
 * Endpoint de tipo de ocorrencias de registro de contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/tipoocorrencia", produces = "application/json;charset=UTF-8")
public class RegistroTipoOcorrenciaController {
	
	@Autowired
	private IRegistroTipoOcorrenciaService registroTipoOcorrenciaService;
	
	
	/**
	 * Lista todos os tipo de ocorrencia para registro de contato.
	 * @return lista de tipos de ocorrencia.
	 */
	@GetMapping
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<List<RegistroTipoOcorrencia>> listar(){
		return ResponseEntity.ok().body(registroTipoOcorrenciaService.find(new OrderBy("nome",true)));
	}
	
}
