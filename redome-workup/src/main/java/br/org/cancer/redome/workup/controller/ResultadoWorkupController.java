package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ResultadoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IArquivoResultadoWorkupService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ResultadoWorkupController {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IArquivoResultadoWorkupService arquivoResultadoWorkupService;
	
	@Autowired
	private IResultadoWorkupService resultadoWorkupService;

	@PostMapping(value = "/resultadosworkup/arquivo", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<RetornoInclusaoDTO> salvarArquivoResultadoWorkup(
			@RequestPart(name = "file", required = true) MultipartFile arquivoLaudo,
			@RequestPart(required = true) Long idPedidoWorkup) throws Exception {

		String caminho = arquivoResultadoWorkupService.enviarArquivoResultadoWorkupParaStorage(idPedidoWorkup, arquivoLaudo);
		return ResponseEntity.ok(
				RetornoInclusaoDTO.builder()
					.mensagem(AppUtil.getMensagem(messageSource, "resultado.workup.arquivo.incluido.sucesso"))
					.stringRetorno(caminho)
					.build()
				);
	}
	
	@DeleteMapping(value = "/resultadosworkup/arquivo", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<RetornoInclusaoDTO> excluirArquivoResultadoWorkup(			
			@RequestPart(required = true) String caminho,
			@RequestPart(required = true) Long idPedidoWorkup) throws Exception {

		arquivoResultadoWorkupService.excluirArquivoResultadoWorkupDoStorage(idPedidoWorkup, caminho);
		return ResponseEntity.ok(
					RetornoInclusaoDTO.builder()
					.mensagem( AppUtil.getMensagem(messageSource, "resultado.workup.arquivo.excluido.sucesso"))
					.stringRetorno(caminho)
					.build()
				);
	}
	
	
	@PostMapping(value = "/resultadosworkup", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> salvarResultadoWorkup(
			@RequestPart(required = true) Long idPedidoWorkup,
			@RequestPart(required = true) ResultadoWorkup resultadoWorkup) throws JsonProcessingException {

		resultadoWorkupService.salvarResultadoWorkupInternacional(idPedidoWorkup, resultadoWorkup);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "resulado.workup.incluido.sucesso"));
	}
	
	
	@GetMapping(value = "/resultadoworkup/{id}/internacional")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<ResultadoWorkupInternacionalDTO> obterResultadoWorkupInternacional (
			@PathVariable(name = "id", required = true) Long id) {
		
		return ResponseEntity.ok(resultadoWorkupService.obterResultadoWorkupInternacionalDTO(id));
		
	}

	@PutMapping(value = "/resultadosworkup/nacional")
	@PreAuthorize("hasPermission('" + Recursos.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> criarResultadoWorkupNacional(
			@RequestParam(name="idPedidoWorkup", required = true) Long idPedidoWorkup,
			@RequestBody(required = true) ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO) throws JsonProcessingException {

		resultadoWorkupService.criarResultadoWorkupNacional(idPedidoWorkup, resultadoWorkupNacionalDTO);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "resulado.workup.incluido.sucesso"));
	}

	
	@GetMapping(value = "/resultadoworkup/{id}/nacional")
	@PreAuthorize("hasPermission('" + Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL + "')")
	public ResponseEntity<ResultadoWorkupNacionalDTO> obterResultadoWorkupNacionalDTO (
			@PathVariable(name = "id", required = true) Long id) {
		
		return ResponseEntity.ok(resultadoWorkupService.obterResultadoWorkupNacionalDTO(id));
		
	}

}
