package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Raca;
import br.org.cancer.modred.service.IRacaService;

/**
 * Controlador para raças.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/raca")
public class RacaController {

	@Autowired
	private IRacaService racaService;

	
	/**
	 * Método rest obter lista ordenada de raças.
	 * @Return List<Raca>
	 * */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Raca>> getRacas() {
        return new ResponseEntity<List<Raca>>(racaService.listarRacas(), HttpStatus.OK);
	}
}
