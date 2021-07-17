package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.IArquivoPrescricaoService;

/**
 * Classe controladora de arquivo de prescrição.
 * 
 * @author filipe.souza
 *
 */
@RestController
@RequestMapping(value = "/api/arquivosprescricao")
public class ArquivoPrescricaoController {

	@Autowired
	private IArquivoPrescricaoService arquivoPrescricaoService;

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
		File arquivo = arquivoPrescricaoService.obterArquivoDoStorage(idArquivo);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}

}
