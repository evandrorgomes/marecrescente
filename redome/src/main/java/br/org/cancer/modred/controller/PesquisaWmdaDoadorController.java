package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.PesquisaWmdaDoadorDTO;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPesquisaWmdaDoadorService;

/**
 * Controlador para pesquisas de wmda.
 * @author ergomes
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class PesquisaWmdaDoadorController {
    
    @Autowired
    private IPesquisaWmdaDoadorService pesquisaWmdaDoadorService;
    
	/**
     * Atualizar Doador WMDA.
     * 
     * @param Long id - identificação da pesquisa wmda. 
     * @param PesquisaWmda pesquisaWmda - Objeto PesquisaWmda. 
     * @return ResponseEntity<PesquisaWmdaDoadorDTO> - PesquisaWmdaDoadorDTO para ser atualizado.
     */
	@PostMapping("/api/pesquisaswmdadoador") 
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<PesquisaWmdaDoadorDTO> manterRotinaPesquisaWmdaDoador(
			@RequestBody(required=true) PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto) {
		
		return ResponseEntity.ok(pesquisaWmdaDoadorService.manterRotinaPesquisaWmdaDoador(pesquisaWmdaDoadorDto));
	}

	@GetMapping("/api/pesquisaswmdadoador/{pesquisaWmdaId}") 
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<List<String>> obterListaDeIdentificacaoDoadorWmdaExistente(
			@PathVariable(name="pesquisaWmdaId", required=true) Long pesquisaWmdaId) {
		
		return ResponseEntity.ok(pesquisaWmdaDoadorService.obterListaDeIdentificacaoDoadorWmdaExistente(pesquisaWmdaId));
	}

 }
