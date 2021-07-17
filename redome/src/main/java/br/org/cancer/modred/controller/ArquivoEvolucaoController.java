package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
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

import br.org.cancer.modred.service.impl.ArquivoEvolucaoService;

/**
 * Classe controladora de arquivo evolução.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/arquivosevolucao")
public class ArquivoEvolucaoController {
	
	@Autowired
	private ArquivoEvolucaoService arquivoEvolucaoService;
	
	/**
	 * Salva o arquivo do exame.
	 * 
	 * @param arquivo a ser salvo.
	 * @return nome do arquivo salvo
	 */
	@RequestMapping(method = RequestMethod.POST,
					consumes = "multipart/form-data", produces = "text/plain;charset=utf-8")
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<String> salvarArquivo(
			@RequestPart(name = "file") MultipartFile arquivo) {
		return new ResponseEntity<String>(arquivoEvolucaoService.salvarArquivo(arquivo), HttpStatus.OK);
	}

	/**
	 * Método utilizado para buscar somente um arquivo evolução a partir do ID 
	 * e baixar seu respectivo arquivo.
	 * 
	 * @param idArquivoEvolucao id do arquivo evolucao
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	public void baixarArquivoStorage(
	                @PathVariable(name = "id", required = true) Long idArquivoEvolucao,
			HttpServletResponse response) throws IOException {
		File arquivo = arquivoEvolucaoService.obterArquivoDoStorage(idArquivoEvolucao);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
}
