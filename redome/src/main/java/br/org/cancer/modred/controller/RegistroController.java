package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.service.IRegistroService;
/**
 * Classe controladora de domínio de registro.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/registros")
public class RegistroController {


	@Autowired
	private IRegistroService registroService;
	
	/**
	 * Método rest obter lista de registros.
	 * @Return List<Registro>
	 * */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Registro>> listarRegistros() {
        return new ResponseEntity<List<Registro>>(registroService.obterTodosRegistrosOrdenados(), HttpStatus.OK);
	}
	

	/**
	 * Método rest obter lista de registros.
	 * @Return List<Registro>
	 * */
	@RequestMapping(value="internacional", method = RequestMethod.GET)
	public ResponseEntity<List<Registro>> listarRegistrosInternacionais() {
        return new ResponseEntity<List<Registro>>(registroService.obterTodosRegistrosInternacionais(), HttpStatus.OK);
	}
	
	@GetMapping(value="{sigla}")
	public ResponseEntity<RegistroDTO> obterRegistroPorSilga(@PathVariable(name = "sigla", required = true) String sigla) {
		return ResponseEntity.ok(registroService.obterRegistroPorSigla(sigla));
	}
	
	
}
