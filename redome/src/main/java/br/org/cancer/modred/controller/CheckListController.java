package br.org.cancer.modred.controller;

import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ChecklistView;
import br.org.cancer.modred.model.RespostaChecklist;
import br.org.cancer.modred.model.TipoChecklist;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.impl.RespostaChecklistService;
import br.org.cancer.modred.service.impl.TipoChecklistService;

/**
 * Classe com métodos REST para checklist.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/checklist")
public class CheckListController {

	@Autowired
	private TipoChecklistService tipoChecklistService;
	
	@Autowired
	private RespostaChecklistService respostaChecklist;

	
	/**
	 * Método para obter o checklist por id do tipo.
	 * @param id - id do tipo a ser carregado.
	 * @return objeto do checklist.
	 */
	@GetMapping(value = "{id}")
	@PreAuthorize("hasPermission('" + Recurso.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	@JsonView(ChecklistView.Detalhe.class)
	public ResponseEntity<TipoChecklist> obterChecklist(@PathVariable(name = "id", required = true) Long id) {
		return new ResponseEntity<>(tipoChecklistService.obterTipoChecklist(id), HttpStatus.OK);
	}
	
	/**
	 * Método para obter listagem de respostas de checklist.
	 * @param id - id do tipo a ser carregado.
	 * @param idLogistica - id da logística.
	 * @return listagem de respostas.
	 */
	@GetMapping(value = "respostas")
	@PreAuthorize("hasPermission('" + Recurso.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	@JsonView(ChecklistView.Resposta.class)
	public ResponseEntity<List<RespostaChecklist>> obterRespostasChecklist(@RequestParam("idlogistica") Long idLogistica) {
		return new ResponseEntity<>(respostaChecklist.obterRepostasPorIdPedidoLogistica(idLogistica), HttpStatus.OK);
	}
	
	
	/**
	 * Método para salvar listagem de checklist.
	 * @param resposta - listagem de respostas a serem persistidas.
	 * @return ResponseEntity.OK caso seja persistido com sucesso.
	 */
	@PostMapping
	@PreAuthorize("hasPermission('" + Recurso.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	public ResponseEntity<String> salvarChecklist(@RequestBody(required=true)RespostaChecklist resposta) {
		tipoChecklistService.salvarChecklist(resposta);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
