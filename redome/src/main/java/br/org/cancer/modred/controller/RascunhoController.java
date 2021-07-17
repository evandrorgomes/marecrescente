package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.service.IRascunhoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para rascunho.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/rascunhos", produces = "application/json;charset=UTF-8")
public class RascunhoController {

	@Autowired
	private IRascunhoService rascunhoService;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IUsuarioService usuarioService;

	/**
	 * Método rest para gravar novo rascunho.
	 *
	 * @param paciente a ser persistido.
	 * @return ResponseEntity<CampoMensagem> Lista de erros
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> salvar(@RequestBody Paciente paciente) {
		final Long usuarioLogadoId = usuarioService.obterUsuarioLogadoId();
		rascunhoService.salvarOuAtualizar(paciente, usuarioLogadoId);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"rascunho.incluido.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Retorna um rascunho por id de Usuario.
	 * 
	 * @return ResponseEntity<Paciente> rascunho do usuário
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	@JsonView(PacienteView.Rascunho.class)
	public ResponseEntity<Paciente> obterRascunhoDeUsuario() {
		final Long usuarioLogadoId = usuarioService.obterUsuarioLogadoId();
		Paciente paciente = rascunhoService.obterRascunho(usuarioLogadoId);
//		rascunhoService.removeDoDraftOqueNaoEstaNoStorage(usuarioLogadoId, paciente);
//		rascunhoService.removeDoStorageOqueNaoEstaNoDraft(usuarioLogadoId, paciente);
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}

	/**
	 * Método rest para excluir rascunho por id de usuario.
	 *
	 * @return ResponseEntity<CampoMensagem> Lista de erros
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> excluir() {
		final Long usuarioLogadoId = usuarioService.obterUsuarioLogadoId();
		rascunhoService.excluirPorIdDeUsuario(usuarioLogadoId);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("", AppUtil.getMensagem(
				messageSource, "rascunho.excluido.sucesso")), HttpStatus.OK);
	}
}