package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.TipoAmostra;
import br.org.cancer.modred.service.ITipoAmostraService;

/**
 * Controlador de tipo de amostra.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/tiposamostra", produces = "application/json;charset=UTF-8")
public class TipoAmostraController {

	@Autowired
	private ITipoAmostraService tipoAmostraService;

	/**
	 * Retorna listagem de tipo de amostras.
	 * 
	 * @return listagem de tipos de amostras.
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<TipoAmostra>> listarTiposDeAmostras() {
		return new ResponseEntity<List<TipoAmostra>>(tipoAmostraService.findAll(), HttpStatus.OK);
	}

}
