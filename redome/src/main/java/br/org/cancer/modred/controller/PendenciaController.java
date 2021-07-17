package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.RespostaPendenciaDTO;
import br.org.cancer.modred.model.RespostaPendencia;
import br.org.cancer.modred.model.TipoPendencia;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.service.IRespostaPendenciaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe controladora de pendência.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/pendencias", produces = "application/json;charset=UTF-8")
public class PendenciaController {

	@Autowired
	private IPendenciaService pendenciaService;

	@Autowired
	private IRespostaPendenciaService respostaPendenciaService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método para atualizar o status de pendência para fechado.
	 * 
	 * @param idPendencia ID da Pendência a ser atualizada.
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método
	 */
	@RequestMapping(value = "{id}/fechar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(#idPendencia, 'Pendencia', 'AVALIAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> atualizarStatusPendenciaParaFechado(@PathVariable(	name = "id",
																							required = true) Long idPendencia) {
		pendenciaService.atualizarStatusDePendenciaParaFechado(idPendencia);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"status.alterado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método para atualizar o status de pendência para cancelado.
	 * 
	 * @param idPendencia ID da Pendência a ser cancelada.
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método.
	 */
	@RequestMapping(value = "{id}/cancelar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(#idPendencia, 'Pendencia', 'AVALIAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> atualizarStatusPendenciaParaCancelado(@PathVariable(	name = "id",
																								required = true) Long idPendencia) {
		pendenciaService.atualizarStatusDePendenciaParaCancelado(idPendencia);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"status.alterado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método para atualizar o status de pendência para aberto e gravar uma resposta para a pendência.
	 * 
	 * @param idPendencia ID da Pendência a ser cancelada.
	 * @param resposta resposta associada a pendência informada.
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método
	 */
	@RequestMapping(value = "{id}/reabrir", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#idPendencia, 'Pendencia', 'AVALIAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> atualizarStatusPendenciaParaAberto(
			@PathVariable(name = "id", required = true) Long idPendencia,
			@RequestBody RespostaPendencia resposta) {
		respostaPendenciaService.anotarReabrir(resposta);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "status.alterado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}/respostas", method = RequestMethod.GET)
	@PreAuthorize("(hasPermission(#pendenciaId, 'Pendencia', 'AVALIAR_PACIENTE')"
			+ " || hasPermission(#pendenciaId, 'Pendencia', 'VISUALIZAR_AVALIACAO'))")
	public ResponseEntity<List<RespostaPendenciaDTO>> listarHistoricoPendencia(@PathVariable(	name = "id",
																								required = true) Long pendenciaId) {
		return new ResponseEntity<List<RespostaPendenciaDTO>>(pendenciaService.listarRespostas(pendenciaId), HttpStatus.OK);
	}

	@RequestMapping(value = "tipos", method = RequestMethod.GET)
	public ResponseEntity<List<TipoPendencia>> listarTiposPendencia() {
		return new ResponseEntity<List<TipoPendencia>>(pendenciaService.listarTiposPendencia(), HttpStatus.OK);
	}

	/**
	 * Método para atualizar o status de pendência para respondida e gravar uma resposta para a pendência.
	 * 
	 * @param idPendencia ID da Pendência a ser respondida.
	 * @param resposta resposta associada a pendência informada.
	 * @return ResponseEntity<CampoMensagem> response com sucesso ou erro na execução do método
	 */
	@RequestMapping(value = "{id}/responder", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#idPendencia, 'Pendencia', 'VISUALIZAR_AVALIACAO')")
	public ResponseEntity<CampoMensagem> atualizarStatusPendenciaParaRespondido(
			@PathVariable(name = "id", required = true) Long idPendencia,
			@RequestBody RespostaPendencia resposta) {
		respostaPendenciaService.responder(resposta);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "status.alterado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

}
