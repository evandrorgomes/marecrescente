package br.org.cancer.modred.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDashboardContatoService;
import br.org.cancer.modred.vo.dashboard.ContatoVo;

/**
 * Classe com métodos REST para checklist.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/dashboard/contatos")
public class DashboardContatoController {

	@Autowired
	private IDashboardContatoService dashboardContatoService;
	
	
	/**
	 * Método para obter o checklist por id do tipo.
	 * @param id - id do tipo a ser carregado.
	 * @return objeto do checklist.
	 */
	@PreAuthorize("hasPermission('" + Recurso.VISUALIZAR_DASHBOARD + "')")
	@GetMapping(value = "")
	public ResponseEntity<ContatoVo> obterChecklist(
			@DateTimeFormat(pattern = "ddMMyyyy") @RequestParam(name = "dataInicio", required = true) LocalDate dataInicio, 
			@DateTimeFormat(pattern = "ddMMyyyy") @RequestParam(name = "dataFinal", required = true) LocalDate dataFinal) {
		return ResponseEntity.ok(dashboardContatoService.obterTotaisPorPeriodo(dataInicio, dataFinal));
	}
	

}
