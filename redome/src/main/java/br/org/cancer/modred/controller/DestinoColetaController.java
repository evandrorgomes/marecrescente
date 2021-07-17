package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.DestinoColeta;
import br.org.cancer.modred.service.impl.DestinoColetaService;


/**
 * Classe controladora para destinos de coleta.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/destinoscoleta", produces = "application/json;charset=UTF-8")
public class DestinoColetaController {

	@Autowired
	private DestinoColetaService destinoColetaService;
	
	/**
	 * Método para listar destinos de coleta.
	 * 
	 * @return ResponseEntity<List<DestinoColeta>>
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_RECEBIMENTO_COLETA')")
	public ResponseEntity<List<DestinoColeta>> listarDestinoColeta() {
		return new ResponseEntity<List<DestinoColeta>>(destinoColetaService.listarDestinos(), HttpStatus.OK);
	}

}
