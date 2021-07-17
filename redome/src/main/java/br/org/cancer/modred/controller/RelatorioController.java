package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.RelatorioDTO;
import br.org.cancer.modred.controller.view.RelatorioView;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.service.IConstanteRelatorioService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe controladora de relatorios.
 * 
 * @author Fillipe Queiroz.
 *
 */
@RestController
@RequestMapping(value = "/api/relatorios")
public class RelatorioController {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IRelatorioService relatorioService;
	
	@Autowired
	private IConstanteRelatorioService constanteRelatorioService; 

	/**
	 * Método utilizado para buscar todos os relatorios.
	 *
	 * @return lista com relatorios
	 */
	@RequestMapping(value = "listar", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	@JsonView(RelatorioView.Consulta.class)
	public ResponseEntity<List<Relatorio>> listarRelatorios() {

		return new ResponseEntity<List<Relatorio>>(relatorioService.listar(), HttpStatus.OK);
	}

	/**
	 * Salva o relatorio em formato html.
	 * 
	 * @param relatorio em formato html.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public ResponseEntity<String> salvarArquivo(@RequestBody() RelatorioDTO relatorio) {
		relatorioService.salvarRelatorio(relatorio);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	/**
	 * Baixar relatório por codigo.
	 * 
	 * @param codigo codigo
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{codigo}/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public void baixarArquivo(
			@PathVariable(name = "codigo", required = true) String codigo,
			HttpServletResponse response) throws IOException {
		File arquivo = relatorioService.obterArquivo(codigo);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * Método utilizado para buscar todos os relatorios.
	 *
	 * @return lista com relatorios
	 */
	@RequestMapping(value = "listar/parametros", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	@JsonView(RelatorioView.Consulta.class)
	public ResponseEntity<List<String>> listarParametros() {

		return new ResponseEntity<List<String>>(relatorioService.listarParametros(), HttpStatus.OK);
	}
	
	/**
	 * Método utilizado para buscar todos os relatorios.
	 *
	 * @return lista com relatorios
	 */
	@RequestMapping(value = "listar/constantes", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public ResponseEntity<List<String>> listarConstantes() {
		return new ResponseEntity<List<String>>(constanteRelatorioService.listarCodigos(), HttpStatus.OK);
	}
	
	/**
	 * Método utilizado para buscar o valor da constante por codigo.
	 *
	 * @return lista com os códigos das constantes
	 */
	@RequestMapping(value = "constantes/{codigo}", method = RequestMethod.GET, produces = "text/plan")
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public ResponseEntity<String> obterValorConstantes( 
			@PathVariable(name = "codigo", required = true) String codigo) {
		return new ResponseEntity<String>(constanteRelatorioService.obterValorConstante(codigo), HttpStatus.OK);
	}
	
	/**
	 * Método utilizado para atualizar o valor da constante por codigo.
	 * 
	 * @param codigo - Código do relatório
	 * @param valor - Valor a ser atualizado
	 * @return Mensagem de sucesso.
	 */
	@RequestMapping(value = "constantes/{codigo}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public ResponseEntity<CampoMensagem> atualizarValorConstantes( 
			@PathVariable(name = "codigo", required = true) String codigo,
			@RequestBody(required=true) String valor) {
		constanteRelatorioService.atualizarValorConstante(codigo, valor);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "mensagem.atualizado.sucesso", "constante", "a"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	
	/**
	 * Baixar relatório em pdf com parametros por codigo.
	 * 
	 * @param codigo codigo
	 * @param parametros parametros do relatório em base64
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@GetMapping(value = "{codigo}/pdf")
	//@PreAuthorize("hasPermission('EDITAR_RELATORIO')")
	public void baixarArquivo(
			@PathVariable(name = "codigo", required = true) String codigo,
			@RequestParam(name = "parametros", required = false) String parametros,
			HttpServletResponse response) throws IOException {
		
		File arquivo = relatorioService.gerarPdf(codigo, parametros);
		
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}	
	
	

}
