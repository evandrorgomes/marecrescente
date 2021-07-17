package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.EstadoCivil;
import br.org.cancer.modred.service.IEstadoCivilService;

/**
 * Controlador para Estado Civil.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api/estadoCivil")
public class EstadoCivilController {

	@Autowired
	private IEstadoCivilService estadoCivilService;

	
	/**
	 * Método rest obter lista ordenada de estado civis.
	 * @Return List<EstadoCivil>
	 * */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoCivil>> getEstadoCivis() {
        return new ResponseEntity<List<EstadoCivil>>(estadoCivilService.listarEstadosCivis(), HttpStatus.OK);
	}
}
