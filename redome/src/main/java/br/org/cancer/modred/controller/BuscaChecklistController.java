package br.org.cancer.modred.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.abstracts.AbstractController;
import br.org.cancer.modred.controller.dto.BuscaChecklistDTO;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra os serviços RestFul associados as chamadas envolvendo
 * a entidade BuscaChecklist.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/checklist")
public class BuscaChecklistController extends AbstractController{

	@Autowired
	private IBuscaChecklistService buscaChecklistService;
	
	@PutMapping(value = "{id}/visto")
	@PreAuthorize("hasPermission('" + Recurso.VISTAR_CHECKLIST + "')")
	public ResponseEntity<CampoMensagem> vistarChecklist(
			@PathVariable(name="id", required = true) Long id) throws IOException {
		buscaChecklistService.marcarVisto(id);
		return statusOK("busca.checklist.vistado");
	}

	/**
	 * Visto em uma lista de checklist na busca.
	 * 
	 * @param listaIdsChecklists - lista de checklist para visto
	 * @return CampoMensagem - messagem de retorno.
	 * @throws IOException - erro na aplicação.
	 */
	@PutMapping(value = "vistos")
	@PreAuthorize("hasPermission('" + Recurso.VISTAR_CHECKLIST + "')")
	public ResponseEntity<CampoMensagem> vistarListaChecklist(
			@RequestParam(name="listaIdsChecklists", required = true) Long[] listaIdsChecklists) throws IOException {
		
		List<Long> ids = null;
		if (listaIdsChecklists != null && !"".equals(listaIdsChecklists)) {
			ids = Arrays.asList(listaIdsChecklists);
		}
		buscaChecklistService.marcarListaDeVistos(ids);
		return statusOK("busca.checklist.vistado");
	}
	
	@GetMapping("/")
	@PreAuthorize("hasPermission('" + Recurso.CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA + "')")
	public ResponseEntity<Page<BuscaChecklistDTO>> listarBuscaChecklistPorAnalistaETipo(@RequestParam(required = false) String loginAnalista
			, @RequestParam(required = false) Long idTipoChecklist
			, @RequestParam(required = true) int pagina
			, @RequestParam(required = true) int quantidadeRegistros){
		return ResponseEntity.ok().body(buscaChecklistService.listarChecklistPorAnalistaETipo(loginAnalista, idTipoChecklist,new PageRequest(pagina, quantidadeRegistros)));
	}

	@PutMapping(value = "{id}/analise/visto")
	@PreAuthorize("hasPermission('" + Recurso.VISTAR_CHECKLIST + "')")
	public ResponseEntity<CampoMensagem> analisarBuscaViaItemCheckList(
			@PathVariable(name="id", required = true) Long id) throws IOException {
		buscaChecklistService.analisarBuscaViaItemCheckList(id);
		return statusOK("analise.busca.checklist.vistado");
	}
	
	@GetMapping(value = "/busca/visto")
	@PreAuthorize("hasPermission('" + Recurso.VISTAR_CHECKLIST + "')")
	public ResponseEntity<CampoMensagem> vistarChecklistPorIdBuscaETipo(
			@RequestParam(required = true) Long idBusca
			, @RequestParam(required = true) Long idTipoChecklist) throws IOException {
		buscaChecklistService.vistarChecklistPorIdBuscaETipo(idBusca, idTipoChecklist);
		return statusOK("busca.checklist.vistado");
	}
	
}
