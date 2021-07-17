package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.impl.CordaoNacionalService;
import br.org.cancer.modred.vo.CordaoNacionalVo;


/**
 * Classe para métodos rest de Cordão Nacional.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/cordaonacional", produces = "application/json;charset=UTF-8")
public class CordaoNacionalController {
	
	@Autowired
	private CordaoNacionalService cordaoNacionalService;
	
	/**
	 * Importa um lote de VO de cordão nacional.
	 * 
	 * @param ressalva - nova ressalva adicionada ao doador.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('CADASTRAR_CORDAO_NACIONAL')")
	@RequestMapping(value = "importar", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> salvarLoteVo(@RequestBody(required = true) List<CordaoNacionalVo> cordoes) {
		cordaoNacionalService.salvarCordoesImportados(cordoes);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
