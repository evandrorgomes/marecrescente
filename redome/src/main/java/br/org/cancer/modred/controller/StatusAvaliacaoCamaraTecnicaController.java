package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;
import br.org.cancer.modred.service.impl.StatusAvaliacaoCamaraTecnicaService;

/**
 * Controlador de Status de Avaliação de Camara Técnica.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/statusavaliacaocamaratecnica", produces = "application/json;charset=UTF-8")
public class StatusAvaliacaoCamaraTecnicaController {

	@Autowired
	private StatusAvaliacaoCamaraTecnicaService statusAvaliacaoCamaraTecnicaService;
	
	/**
	 * Método para listar status de avaliação de câmara técnica.
	 *
	 * @return ResponseEntity<List<StatusAvaliacaoCamaraTecnica>> listagem status de avaliação de câmara técnica.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<StatusAvaliacaoCamaraTecnica>> listarMotivosStatusDoadorPorRecurso(
			@RequestParam(required = false) String siglaRecurso) {
		return new ResponseEntity<List<StatusAvaliacaoCamaraTecnica>>(
				statusAvaliacaoCamaraTecnicaService.findAll(), HttpStatus.OK);
	}
}
