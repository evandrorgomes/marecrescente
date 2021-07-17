package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.service.ILocusService;

/**
 * Classe REST de locus.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/locus")
public class LocusController {

	@Autowired
	private ILocusService locusService;

	/**
	 * Método que traz a lista de locus cadastrada na base.
	 * 
	 * @Return ResponseEntity<List<Locus>> lista de locus
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Locus>> listarLocus() {
		return new ResponseEntity<List<Locus>>(locusService.listarLocus(), HttpStatus.OK);
	}
}
