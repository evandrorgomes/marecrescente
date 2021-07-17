package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IAnaliseMedicaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.AnaliseMedicaFinalizadaVo;
import br.org.cancer.modred.vo.DetalheAnaliseMedicaVo;


/**
 * Endpoint para analise médica.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/analisesmedica", produces = "application/json;charset=UTF-8")
public class AnaliseMedicaController {

	@Autowired
	private IAnaliseMedicaService analiseMedicaService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Lista tarefas de análise médica.
	 * @param pagina página da pagina a ser listada.
	 * @param quantidadeRegistro quantidade de registros por página.
	 * @return lista paginada de tarefas.
	 */
	@GetMapping(value = "tarefas")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR + "')")
	public ResponseEntity<JsonViewPage<TarefaDTO>> listarTarefas(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistro) {
		return ResponseEntity.ok().body(analiseMedicaService.listarTarefas(new PageRequest(pagina, quantidadeRegistro)));
	}
	
	/**
	 * Método para obter os detalhes da análise médica do doador.
	 * 
	 * @param id identificador da analise médica.
	 * @return Detalhes da anlise médica como a ultima tarefa de tentantiva de contato.
	 */
	@GetMapping(value="{id}")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR + "')")
	@JsonView(AnaliseMedicaView.Detalhe.class)
	public ResponseEntity<DetalheAnaliseMedicaVo> obterAnalise(@PathVariable("id") Long id){
		return ResponseEntity.ok().body(analiseMedicaService.obterDetalheAnaliseMedica(id));
	}
	
	/**
	 * Método que finaliza a análise médica do doador.
	 * 
	 * @param id - identificador da analise médica do doador.
	 * @param analiseMedicaFinalizadaVo  - Value object com os dados de finalização.
	 * @return mensagem de sucesso.
	 */
	@PutMapping(value="{id}")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR + "')")
	public ResponseEntity<CampoMensagem> finalizarAnaliseMedica(@PathVariable("id")Long id,
			@RequestBody AnaliseMedicaFinalizadaVo analiseMedicaFinalizadaVo) {
		
		analiseMedicaService.finalizar(id, analiseMedicaFinalizadaVo);
		
		return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "analise.medica.finalizada.mensagem.sucesso")));	
	}
	
}
