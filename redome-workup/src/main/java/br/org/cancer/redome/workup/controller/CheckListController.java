	package br.org.cancer.redome.workup.controller;

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

import br.org.cancer.redome.workup.model.RespostaChecklist;
import br.org.cancer.redome.workup.model.TipoChecklist;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IRespostaChecklistService;
import br.org.cancer.redome.workup.service.ITipoChecklistService;

/**
 * Classe com métodos REST para checklist.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class CheckListController {

	@Autowired
	private ITipoChecklistService tipoChecklistService;
	
	@Autowired
	private IRespostaChecklistService respostaChecklist;

	
	/**
	 * Método para obter o checklist por id do tipo.
	 * @param id - id do tipo a ser carregado.
	 * @return objeto do checklist.
	 */
	@GetMapping(value = "/checklist/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	public ResponseEntity<TipoChecklist> obterChecklist(@PathVariable(name = "id", required = true) Long id) {
		return ResponseEntity.ok(tipoChecklistService.obterTipoChecklist(id));
	}
	
	/**
	 * Método para obter listagem de respostas de checklist.
	 * @param id - id do tipo a ser carregado.
	 * @param idLogistica - id da logística.
	 * @return listagem de respostas.
	 */
	@GetMapping(value = "/checklist/respostas")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	public ResponseEntity<List<RespostaChecklist>> obterRespostasChecklist(@RequestParam("idPedidoLogistica") Long idPedidoLogistica) {
		return ResponseEntity.ok(respostaChecklist.obterRepostasPorIdPedidoLogistica(idPedidoLogistica));
	}
	
	
	/**
	 * Método para salvar listagem de checklist.
	 * @param resposta - listagem de respostas a serem persistidas.
	 * @return ResponseEntity.OK caso seja persistido com sucesso.
	 */
	@PostMapping(value = "/checklist")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "')")
	public ResponseEntity<String> salvarChecklist(@RequestBody(required=true)RespostaChecklist resposta) {
		tipoChecklistService.salvarChecklist(resposta);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
