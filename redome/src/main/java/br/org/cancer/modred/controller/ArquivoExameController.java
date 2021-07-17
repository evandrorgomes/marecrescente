package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.service.IArquivoExameService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.StorageService;

/**
 * Classe controladora de arquivo exame.
 * 
 * @author filipe.souza
 *
 */
@RestController
@RequestMapping(value = "/api/arquivosexame")
public class ArquivoExameController {

	@Autowired
	private IArquivoExameService arquivoExameService;

	@Autowired
	private IStorageService storageService;

	/**
	 * Método utilizado para buscar somente um arquivo exame a partir do ID.
	 * 
	 * @param arquivoExameId arquivoExameId
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	public void baixarArquivoStorage(
	                @PathVariable(name = "id", required = true) Long arquivoExameId,
			HttpServletResponse response) throws IOException {
		File arquivo = arquivoExameService.obterArquivoDoStorage(arquivoExameId);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Método para baixar o zip.
	 * 
	 * @param id id
	 * @param response res
	 * @throws FileNotFoundException file
	 * @throws IOException ioex
	 */
	@RequestMapping(value = "{id}/download/zip", method = RequestMethod.GET)
	public void baixarArquivoZipadoStorage(@PathVariable(name = "id", required = true) Long id,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		File file = arquivoExameService.obterArquivoZipadoDoStorage(id);
		response.setContentType("application/zip, application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file
				.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Remove o arquivo do usuário.
	 * 
	 * @param nomeArquivo nome do arquivo
	 * @param usuarioId id do usuário
	 * @param response HttpServletResponse
	 * @return HttpStatus.OK se o arquivo for removido com sucesso.
	 */
	@RequestMapping(value = "{nomeArquivo}/usuario/{usuarioId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removerArquivo(
	            @PathVariable(name = "nomeArquivo", required = true) String nomeArquivo,
	            @PathVariable(name = "usuarioId", required = true) Long usuarioId,
	            HttpServletResponse response) {

		// FIXME levar este código para a camada de negócio.
		String caminhoArquivoRascunho = StorageService.obterCaminhoArquivoRascunho(usuarioId,
				nomeArquivo);
		storageService.removerArquivo(caminhoArquivoRascunho);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Salva o arquivo do exame.
	 * 
	 * @param arquivo a ser salvo.
	 * @return nome do arquivo salvo
	 */
	@RequestMapping(value = "salvarArquivo", method = RequestMethod.POST,
					consumes = "multipart/form-data", produces = "text/plain;charset=utf-8")
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<String> salvarArquivo(
			@RequestPart(name = "file") MultipartFile arquivo) {

		return new ResponseEntity<String>(arquivoExameService.salvarArquivo(
				arquivo), HttpStatus.OK);
	}

}
