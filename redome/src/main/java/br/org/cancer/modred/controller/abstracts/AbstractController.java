package br.org.cancer.modred.controller.abstracts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Extrai e generaliza responses, comportamentos e funcionalidades 
 * que são repetidas em várias a API do Redome.
 * 
 * @author Pizão
 *
 */
@Controller
public abstract class AbstractController {
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Serializa o arquivo no response para realizar o download no front-end.
	 * 
	 * @param response response relativo a requisição que chamou este método.
	 * @param arquivo arquivo que deverá ser baixado.
	 * @throws IOException exceção lançada, caso não consiga realizar a gravação no response. 
	 * @throws FileNotFoundException quando arquivo não é encontrado.
	 */
	protected void serializarArquivo(HttpServletResponse response, File arquivo)
			throws IOException, FileNotFoundException {
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * Retorna a mensagem de OK com o texto da mensagem já internacionalizado.
	 * 
	 * @param mensagem chave para a mensagem no arquivo de internacionalização.
	 * @return response com a mensagem.
	 */
	public ResponseEntity<CampoMensagem> statusOK(String mensagem) {
		return configurarResposta(mensagem, HttpStatus.OK);
	}
	
	private ResponseEntity<CampoMensagem> configurarResposta(String chaveMensagem, HttpStatus status) {
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, chaveMensagem));
		return new ResponseEntity<CampoMensagem>(mensagem, status);
	}
	
	public <T> ResponseEntity<T> statusOK(T entity) {
		return new ResponseEntity<T>(entity, HttpStatus.OK);
	}
	
}

