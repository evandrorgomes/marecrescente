package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.ClassificacaoABO;
import br.org.cancer.modred.service.IClassificacaoABOService;

/**
 * Classe de REST controller para classificações de ABO.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/classificacoesabo", produces = "application/json;charset=UTF-8")
public class ClassificacaoABOController {

	@Autowired
	private IClassificacaoABOService classificacaoABOService;

	/**
	 * Buscar todas as classificações de ABO.
	 * 
	 * @return List<ClassificacaoABO> lista com as classificacoes
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClassificacaoABO>> buscarClassificacoesABO() {

		return new ResponseEntity<List<ClassificacaoABO>>(classificacaoABOService.findAll(), HttpStatus.OK);
	}

}
