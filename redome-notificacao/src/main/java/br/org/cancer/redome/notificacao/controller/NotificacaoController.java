package br.org.cancer.redome.notificacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.notificacao.dto.ListaNotificacaoDTO;
import br.org.cancer.redome.notificacao.dto.NotificacaoDTO;
import br.org.cancer.redome.notificacao.model.Notificacao;
import br.org.cancer.redome.notificacao.service.INotificacaoService;
import br.org.cancer.redome.notificacao.util.CampoMensagem;

/**
 * Classe de REST controller para motivo de evolução paciente.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class NotificacaoController {

	@Autowired
	private INotificacaoService notificacaoService;
	
	@GetMapping("/api/notificacoes")
	public ResponseEntity<Page<Notificacao>> listarNotificacoes(
			@RequestParam(required = false, name= "idCentroTransplante") Long idCentroTransplante,
			@RequestParam(required = false, name= "rmr") Long rmr,
			@RequestParam(required = false, name= "meusPacientes") Boolean meusPacientes,
			@RequestParam(required = false, name= "pagina") Integer pagina,
			@RequestParam(required = false, name= "quantidadeRegistro") Integer quantidadeRegistro) {
		
		ListaNotificacaoDTO parametros = new ListaNotificacaoDTO(idCentroTransplante, rmr,
				meusPacientes, PageRequest.of(pagina, quantidadeRegistro));
		
		return ResponseEntity.ok(notificacaoService.listarNotificacoes(parametros));
	}

	/**
	 * Marca uma notificação como lida.
	 * 
	 * @param id - identificador da notificação.
	 * 
	 * @return ResponseEntity<List<Motivo>> listagem de motivos de evolução encontrados.
	 */
	@PutMapping(value = "/api/notificacoes/{id}/lida")
	public ResponseEntity<CampoMensagem> marcarNotificacaoComoLida(@PathVariable(required = true) Long id) {
		return new ResponseEntity<CampoMensagem>(notificacaoService.marcarNotificacaoComoLida(id), HttpStatus.OK);
	}

	@GetMapping("/api/notificacoes/{rmr}/contar")
	public ResponseEntity<Long> contarNotificacoes(@PathVariable(name="rmr", required=true) Long rmr,
			@RequestParam(required = false, name = "lida") Boolean lida){
		return ResponseEntity.ok(notificacaoService.contarNotificacoes(new ListaNotificacaoDTO(rmr, lida)));
	}
	
	@PostMapping("/api/notificacoes/criarnotificacao")
	public ResponseEntity<List<NotificacaoDTO>> criarNotificacao(@RequestBody(required = true) NotificacaoDTO notificacaoDto) {
		return ResponseEntity.ok(notificacaoService.criarNotificacao(notificacaoDto));
	}

}
