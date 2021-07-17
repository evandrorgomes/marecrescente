package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.MatchWmdaDTO;
import br.org.cancer.modred.controller.dto.PesquisaWmdaDTO;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPesquisaWmdaService;

/**
 * Controlador para pesquisas de wmda.
 * @author ergomes
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class PesquisaWmdaController {
    
    @Autowired
    private IPesquisaWmdaService pesquisaWmdaService;
    
    /**
     * Criação de Pesquisa WMDA.
     * 
     * @param PesquisaWmda pesquisaWmda
     * @return ResponseEntity<PesquisaWmda>
     */
	@PostMapping("/api/pesquisaswmda")
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
    public ResponseEntity<PesquisaWmdaDTO> criarPesquisaWmda(@RequestBody(required = true) PesquisaWmdaDTO pesquisaWmdaDto) {
		return ResponseEntity.ok(pesquisaWmdaService.criarPesquisaWmda(pesquisaWmdaDto));
    }

	/**
     * Atualizar Pesquisa WMDA.
     * 
     * @param Long id - identificação da pesquisa wmda. 
     * @param PesquisaWmda pesquisaWmda - Objeto PesquisaWmda. 
     * @return ResponseEntity<String> - Mensagem de retorno do serviço.
     */
	@PostMapping("/api/pesquisaswmda/mantermatch") 
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<String> manterRotinaMatchWmda(
			@RequestBody(required=true) MatchWmdaDTO matchWmdaDto) {
	
		pesquisaWmdaService.manterRotinaMatchWmda(matchWmdaDto);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

    /**
     * Obter Pesquisa WMDA pelo id.
     * 
     * @param id - identificador da pesquisa.
     * @return ResponseEntity<PesquisaWmda> - Objeto pesquisa wmda. 
     */
	@GetMapping("/api/pesquisaswmda/{id}")
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<PesquisaWmdaDTO> obterPesquisaWmda(@PathVariable(name="id", required=true) Long id) {
		return ResponseEntity.ok(pesquisaWmdaService.obterPesquisaWmda(id));
	}

    /**
     * Obter Pesquisa WMDA pelo id.
     * 
     * @param id - identificador da pesquisa.
     * @return ResponseEntity<PesquisaWmda> - Objeto pesquisa wmda. 
     */
	@GetMapping("/api/pesquisaswmda/status")
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<PesquisaWmdaDTO> obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(
			@RequestParam(name="buscaId", required=true) Long buscaId) {
		return ResponseEntity.ok(pesquisaWmdaService.obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(buscaId));
	}

	/**
     * Atualizar Pesquisa WMDA.
     * 
     * @param Long id - identificação da pesquisa wmda. 
     * @param PesquisaWmda pesquisaWmda - Objeto PesquisaWmda. 
     * @return ResponseEntity<String> - Mensagem de retorno do serviço.
     */
	@PostMapping("/api/pesquisaswmda/{id}") 
	@PreAuthorize("hasPermission('" + Recurso.MANTER_PESQUISA_WMDA + "')")
	public ResponseEntity<String> atualizarRotinaPesquisaWmda(
			@PathVariable(name="id", required=true) Long id, 
			@RequestBody(required=true) PesquisaWmdaDTO pesquisaWmdaDTO) {
	
		pesquisaWmdaService.atualizarPesquisaWmda(id, pesquisaWmdaDTO);
		return new ResponseEntity<String>(HttpStatus.OK);

	}

 }
