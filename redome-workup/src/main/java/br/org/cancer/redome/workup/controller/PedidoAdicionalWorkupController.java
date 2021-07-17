package br.org.cancer.redome.workup.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.workup.dto.RetornoInclusaoExameAdicionalDTO;
import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.impl.ArquivoPedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.service.impl.PedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PedidoAdicionalWorkupController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PedidoAdicionalWorkupService pedidoAdicionalWorkupService;

	@Autowired
	private ArquivoPedidoAdicionalWorkupService arquivoPedidoAdicionalWorkupService;
	
	/* ############################ AVALIAÇÃO DE RESULTADO WORKUP ################################# */
	
	@PostMapping(value = "/pedidoadicionalworkup/nacional/exameadicional", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_PEDIDO_ADICIONAL_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> criarPedidoAdicionalDoadorNacional (
			@RequestPart(name="idPedidoWorkup", required = true) Long idPedidoWorkup,
			@RequestPart(name="descricao", required = false) String descricao) throws Exception {

		String mensagemRetorno = pedidoAdicionalWorkupService.criarPedidoAdicionalDoadorNacional(idPedidoWorkup, descricao);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}
	
	@PostMapping(value = "/pedidoadicionalworkup/internacional/exameadicional", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_PEDIDO_ADICIONAL_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> criarPedidoAdicionalDoadorInternacional (
			@RequestPart(name="idPedidoWorkup", required = true) Long idPedidoWorkup,
			@RequestPart(name="descricao", required = false) String descricao) throws Exception {

		String mensagemRetorno = pedidoAdicionalWorkupService.criarPedidoAdicionalDoadorInternacional(idPedidoWorkup, descricao);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}

	@RequestMapping(value = "/pedidoadicionalworkup/{idArquivoAdicional}/download", method = RequestMethod.GET)
	public void baixarArquivoAdicionalStorage(
			@PathVariable(name = "idArquivoAdicional", required = true) Long idArquivoAdicional,
			HttpServletResponse response) throws IOException {
		
		File arquivo = arquivoPedidoAdicionalWorkupService.obterArquivoDoStorage(idArquivoAdicional);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition", String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}

	/* ############################ INCLUSÃO DE ARQUIVO PEDIDO ADICIONAL WORKUP ########################### */

	@PostMapping(value = "/pedidoadicionalworkup/finalizarpedidoadicionalworkup", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.FINALIZAR_PEDIDO_ADICIONAL_WORKUP + "')")
	public ResponseEntity<String> finalizarPedidoAdicionalWorkup(
			@RequestPart(required = true) PedidoAdicionalWorkup pedidoAdicionalWorkup) throws JsonProcessingException {

		String mensagemRetorno = pedidoAdicionalWorkupService.finalizarPedidoAdicionalWorkup(pedidoAdicionalWorkup);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, mensagemRetorno));
	}
	
	@PostMapping(value = "/pedidoadicionalworkup", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_ARQUIVO_PEDIDO_ADICIONAL_WORKUP + "')")
	public ResponseEntity<RetornoInclusaoExameAdicionalDTO> salvarArquivosExameAdicionalWorkup(
			@RequestPart(name="idPedidoAdicional", required = true) Long idPedidoAdicional,
			@RequestPart(required = true) List<ArquivoPedidoAdicionalWorkup> arquivosExamesAdicionais) throws JsonProcessingException {

		List<ArquivoPedidoAdicionalWorkup> arquivos = arquivoPedidoAdicionalWorkupService.salvarArquivosExamesAdicionaisWorkup(idPedidoAdicional, arquivosExamesAdicionais);
		
		return ResponseEntity.ok(
				RetornoInclusaoExameAdicionalDTO.builder()
				.mensagem(AppUtil.getMensagem(messageSource, "pedido.adicional.workup.arquivo.incluido.sucesso"))
				.arquivosExamesAdicionais(arquivos)
				.build()
		);
	}
	
	@PostMapping(value = "/pedidoadicionalworkup/arquivo", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_ARQUIVO_PEDIDO_ADICIONAL_WORKUP + "')")
	public ResponseEntity<RetornoInclusaoDTO> salvarArquivoExameAdicionalDoStorage(
			@RequestPart(name = "file", required = true) MultipartFile arquivoAdicional,
			@RequestPart(required = true) Long idPedidoAdicional) throws Exception {

		String caminho = arquivoPedidoAdicionalWorkupService.enviarArquivoPedidoAdicionalWorkupParaStorage(idPedidoAdicional, arquivoAdicional);
		return ResponseEntity.ok(
				RetornoInclusaoDTO.builder()
					.mensagem(AppUtil.getMensagem(messageSource, "pedido.adicional.workup.arquivo.incluido.sucesso"))
					.stringRetorno(caminho)
					.build()
				);
	}
	
	@DeleteMapping(value = "/pedidoadicionalworkup/arquivo", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_ARQUIVO_PEDIDO_ADICIONAL_WORKUP + "')")
	public ResponseEntity<RetornoInclusaoDTO> excluirArquivoExameAdicionalDoStorage(			
			@RequestPart(required = true) String caminho,
			@RequestPart(required = true) Long idArquivoAdicional) throws Exception {

		arquivoPedidoAdicionalWorkupService.excluirArquivoExameAdicionalWorkupDoStorage(idArquivoAdicional, caminho);
		return ResponseEntity.ok(
				RetornoInclusaoDTO.builder()
					.mensagem( AppUtil.getMensagem(messageSource, "pedido.adicional.workup.arquivo.excluido.sucesso"))
					.stringRetorno(caminho)
					.build()
				);
	}
	
	@GetMapping(value = "/pedidoadicionalworkup/{idPedidoAdicional}")
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_PEDIDO_ADICIONAL_WORKUP + "') ")
	public ResponseEntity<PedidoAdicionalWorkup> obterPedidosAdicionaisWorkup (
			@PathVariable(name="idPedidoAdicional", required = true) Long idPedidoAdicional) throws Exception {

		return ResponseEntity.ok(pedidoAdicionalWorkupService.obterPedidoAdicionalWorkup(idPedidoAdicional));
	}

	@GetMapping(value = "/pedidoadicionalworkup")
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_PEDIDO_ADICIONAL_WORKUP + "') || "
			+ "hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<List<PedidoAdicionalWorkup>> listarPedidosAdicionaisWorkup (
			@RequestParam(name="idPedidoWorkup", required = true) Long idPedidoWorkup) throws Exception {

		return ResponseEntity.ok(pedidoAdicionalWorkupService.listarPedidosAdicionaisWorkupPorIdPedidoWorkup(idPedidoWorkup));
	}
	
	@GetMapping(value = "/pedidoadicionalworkup/arquivosexamesadicionais")
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_PEDIDO_ADICIONAL_WORKUP + "') ")
	public ResponseEntity<List<ArquivoPedidoAdicionalWorkup>> listarArquivosExamesAdicionais (
			@RequestParam(name="idPedidoAdicional", required = true) Long idPedidoAdicional) throws Exception {

		return ResponseEntity.ok(arquivoPedidoAdicionalWorkupService.listarArquivosExamesAdicionais(idPedidoAdicional));
	}

}
