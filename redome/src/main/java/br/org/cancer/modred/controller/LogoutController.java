package br.org.cancer.modred.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.ISegurancaService;

/**
 * Controlador para tratamento do logout da aplicação (o login é realizado internamente pelo SpringSecurity).
 * 
 * @author Bruno Sousa
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class LogoutController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

	@Autowired
	private ISegurancaService service;

	/**
	 * Método para autenticar usuário.
	 * 
	 * @param request HttpServletRequest
	 * @return ResponseEntity<String> Retorno indicando a o status da autenticação (autorizado ou não) e a mensagem informando o
	 * ocorrido.
	 */
	@RequestMapping(value = "/api/logout", method = RequestMethod.DELETE)
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		OAuth2AccessToken token = service.obterToken(request);

		if (token != null && service.removerToken(token)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

	}

}
