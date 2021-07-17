package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.view.RegistroContatoView;
import br.org.cancer.modred.model.RegistroContato;
import br.org.cancer.modred.service.IRegistroContatoService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Endpoint para métodos REST de registro de contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/registrocontatos")
public class RegistroContatoController {
	
	@Autowired
	private IRegistroContatoService registroContatoService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Salva objeto de registro de contato de telefonema realizado pelo setor de 
	 * contato REDOME.
	 * @param registro objeto a ser salvo passado pelo body.
	 * @return Http result e mensagem de sucesso.
	 */
	@PostMapping
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<String> inserirContato(@RequestBody RegistroContato registro){
		registroContatoService.inserir(registro);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "registro.contato.mensagem.sucesso.gravacao"));
	}
	
	/**
	 * Lista os pedidos de contato de acordo com o a identificação do pedido de contato.
	 * @param pagina pagina a ser recuperada.
	 * @param quantidadeRegistros quantidade de registros a serem recuperados.
	 * @param idPedido identificação do pedido de contato a ser listado.
	 * @return lista paginada de registros.
	 */
	@GetMapping
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	@JsonView(RegistroContatoView.Consulta.class)
	public ResponseEntity<JsonPage> listarContatos(
			@RequestParam(required = true) Integer pagina,
			@RequestParam(required = true) Integer quantidadeRegistros,
			@RequestParam(required = true) Long idPedido){

		PageRequest pageRequest = new PageRequest(pagina, quantidadeRegistros);
		return ResponseEntity.ok().body(new JsonPage(RegistroContatoView.Consulta.class,registroContatoService.listarPor(pageRequest, idPedido)));
	}

}
