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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.model.ArquivoRelatorioInternacional;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IArquivoRelatorioInternacionalService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe controladora de arquivo de voucher de logistica.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/arquivosrelatoriointernacional", produces = "application/json;charset=UTF-8")
public class ArquivoRelatorioInternacionalController {

	@Autowired
	private IArquivoRelatorioInternacionalService arquivoRelatorioInternacionalService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método utilizado para excluir um arquivo a partir do ID.
	 * 
	 * @param id identificador único da entidade ArquivoRelatorioInternacional
	 * @return ResponseEntity<String> Mensagem de sucesso
	 * @throws IOException ao excluir o arquivo
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('" + Recurso.EXCLUIR_ARQUIVO_RELATORIO_BUSCA_INTERNACIONAL + "')")
	public ResponseEntity<String> excluir(
	                @PathVariable(name = "id", required = true) Long id) throws IOException {
		
		arquivoRelatorioInternacionalService.excluirArquivo(id);
		
		final String mensagem = AppUtil.getMensagem(messageSource, "arquivoRelatorioInternacional.excluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);		
	}
	
	/**
	 * upload do arquivo de relatório da busca internacional.
	 * 
	 * @param file stream do arquivo.
	 * @param arquivoRelatorioInternacional entidade a ser criada.
	 * @return mesnagem de sucesso
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_ARQUIVO_RELATORIO_INTERNACIONAL + "')")
	public ResponseEntity<CampoMensagem> listarArquivosRelatorioBuscaInternacional(
			@RequestPart(name = "file") MultipartFile file,
			@RequestPart(required = true) ArquivoRelatorioInternacional arquivoRelatorioInternacional) {
		
		arquivoRelatorioInternacionalService.salvarArquivo(file, arquivoRelatorioInternacional);
		
		final CampoMensagem campoMensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"arquivoRelatorioInternacional.incluido.sucesso"));
		
		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}

	
	/**
	 * Baixar relatório zipado por codigo.
	 * 
	 * @param id identificador do relatorio.
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/downloadzip", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL + "')")
	public void baixarArquivoZipado(
			@PathVariable(name = "id", required = true) Long id,
			HttpServletResponse response) throws IOException {
		
		File arquivo = arquivoRelatorioInternacionalService.obterZip(id);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	} 
	
	
	/**
	 * Baixar relatório por codigo.
	 * 
	 * @param id identificador do relatorio.
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL + "')")
	public void baixarArquivoExameC(
			@PathVariable(name = "id", required = true) Long id,
			HttpServletResponse response) throws IOException {
		
		File arquivo = arquivoRelatorioInternacionalService.obterArquivo(id);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	} 
	
	

}
