package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.IArquivoVoucherLogisticaService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe controladora de arquivo de voucher de logistica.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/arquivosvoucherlogistica")
public class ArquivoVoucherLogisticaController {

	@Autowired
	private IArquivoVoucherLogisticaService arquivoVoucherLogisticaService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método utilizado para excluir um arquivo de voucher a partir do ID.
	 * 
	 * @param arquivoVoucherLogisticaId arquivoVoucherLogisticaId
	 * @return ResponseEntity<String> Mensagem de sucesso
	 * @throws IOException ao excluir o arquivo
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public ResponseEntity<String> excluir(
	                @PathVariable(name = "id", required = true) Long arquivoVoucherLogisticaId) throws IOException {
		
		arquivoVoucherLogisticaService.excluir(arquivoVoucherLogisticaId);
		
		final String mensagem = AppUtil.getMensagem(messageSource, "arquivoVoucherLogistica.excluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);		
	}
	
	/**
	 * Remove o arquivo do storage para o pedido de logistica.
	 * 
	 * @param nomeArquivo nome do arquivo
	 * @param pedidoLogisticaId id do pedido de logistica
	 * @return HttpStatus.OK se o arquivo for removido com sucesso.
	 */
	@RequestMapping(value = "{nomeArquivo}/pedidologistica/{pedidoLogisticaId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public ResponseEntity<String> removerArquivo(
	            @PathVariable(name = "nomeArquivo", required = true) String nomeArquivo,
	            @PathVariable(name = "pedidoLogisticaId", required = true) Long pedidoLogisticaId) {
		
		arquivoVoucherLogisticaService.excluirVoucherDoStorage(pedidoLogisticaId, nomeArquivo);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * Método utilizado para buscar somente um arquivo de prescrição a partir do ID.
	 * 
	 * @param idArquivo id do arquivo da prescrição
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	public void baixarArquivoStorage(
			@PathVariable(name = "id", required = true) Long idArquivo,
			HttpServletResponse response) throws IOException {
		File arquivo = arquivoVoucherLogisticaService.obterArquivoNoStorage(idArquivo);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
}
