package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.MotivoStatusDoador;
import br.org.cancer.modred.service.IMotivoStatusDoadorService;

/**
 * Classe de REST controller para status de doador.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/statusdoador", produces = "application/json;charset=UTF-8")
public class StatusDoadorController {

	@Autowired
	private IMotivoStatusDoadorService motivoStatusDoadorService;

	/**
	 * Método para listar motivos status de doador por recurso.
	 * 
	 * @param idRecurso
	 *            - id do recurso que contem os motivos.
	 * @return ResponseEntity<List<MotivoStatusDoador>> listagem de motivos
	 *         status de doador por recurso.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MotivoStatusDoador>> listarMotivosStatusDoadorPorRecurso(
			@RequestParam(required = false) String siglaRecurso) {
		return new ResponseEntity<List<MotivoStatusDoador>>(
				motivoStatusDoadorService.listarMotivosPorRecurso(siglaRecurso), HttpStatus.OK);
	}
}
