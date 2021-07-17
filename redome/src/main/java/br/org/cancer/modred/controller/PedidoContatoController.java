package br.org.cancer.modred.controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.PedidoContatoFinalizadoVo;

/**
 * Controlador para a entidade de busca.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/pedidoscontato", produces = "application/json;charset=UTF-8")
public class PedidoContatoController {

	@Autowired
	private IDoadorNacionalService doadorService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoContatoService pedidoContatoService;
	
	
	/**
	 * Obtém um doador por um pedido de contato.
	 * @param pedidoContatoId
	 * @return
	 */
	@GetMapping(value = "{id}/doadores")
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	@JsonView(DoadorView.ContatoFase2.class)
	public ResponseEntity<DoadorNacional> obterDoadorAssociadoAoPedido(
			@PathVariable(name = "id", required = true) Long pedidoContatoId) {
		return ResponseEntity.ok(doadorService.obterDoadorPorPedidoContato(pedidoContatoId));
	}
	
//	/**
//	 * Salva o formulário sem validar.
//	 * 
//	 * @param pedidoContatoId - Identificador do pedido de contato.
//	 * @param formulario - Formulário a ser salvo
//	 * @return mensagem de sucesso. 
//	 */
//	@PostMapping(value = "{id}/formulario")
//	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
//			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")	
//	public ResponseEntity<CampoMensagem> salvarFormulario(
//			@PathVariable(name = "id", required = true) Long pedidoContatoId,
//			@RequestBody(required = true) Formulario formulario) {
//		
//		pedidoContatoService.salvarFormularioContato(pedidoContatoId, formulario);
//		
//		return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "formulario.contato.salvo.sucesso")));
//		
//	}
	
//	/**
//	 * Obtém o questionário para o pedido de contato e pelo tipo de formulário.
//	 * 
//	 * @param id - identificador do pedido de contato
//	 * @param idTipoFormulario - id do formulario.
//	 * @return Formulário - formulário com dados.
//	 */
//	@GetMapping(value = "{id}/questionario")
//	@JsonView(DoadorView.Questionario.class)
//	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
//			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
//	public ResponseEntity<Formulario> obterQuestionario(
//			@PathVariable(name = "id", required = true) Long id,
//			@RequestParam(name = "tipo", required = true) Long idTipoFormulario) {
//
//		return ResponseEntity.ok().body(formularioService.obterFormulario(id, TiposFormulario.valueOf(idTipoFormulario)));
//	}
	
	@GetMapping(value="primeiro")
	@JsonView(DoadorView.ContatoFase2.class)
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<TarefaDTO> obterPrimeiraTarefaDeContato(			
			@RequestParam(name = "tipoTarefaId", required = false) Long tipoTarefaId) {
		
		return ResponseEntity.ok().body(pedidoContatoService.obterPrimeiroPedidoContatoDaFilaDeTarefas(tipoTarefaId) );		
	}
	
	@GetMapping(value="tarefa")
	@JsonView(DoadorView.ContatoFase2.class)
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<TarefaDTO> obterPedidoContatoPoridTarefa(			
			@RequestParam(name = "tarefaId", required = false) Long tarefaId,
			@RequestParam(name = "tentativaId", required = false) Long tentativaId) {
		return ResponseEntity.ok().body(pedidoContatoService.obterPedidoContatoPoridTarefa(tentativaId, tarefaId) );		
	}

	
	/**
	 * Finaliza o pedido de contato.
	 * 
	 * @param id - identificador do pedido de contato.
	 * @param pedidoContatoFinalizado - VO para facilitar a passagem de parametros.
	 * @return mensagem de sucesso.
	 */
	@PostMapping(value="{id}")
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<CampoMensagem> finalizarPedidoContato(
			@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required=true) PedidoContatoFinalizadoVo pedidoContatoFinalizado) {
		
		pedidoContatoService.finalizarPedidoContato(id, false, pedidoContatoFinalizado);
		
		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.contato.finalizado.mensagem.sucesso")));		
	}
	
	
}