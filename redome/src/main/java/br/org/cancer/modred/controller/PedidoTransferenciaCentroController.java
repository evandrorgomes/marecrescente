package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.PedidoTransferenciaCentroDTO;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPedidoTransferenciaCentroService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe REST envolvendo a entidade pedido de transferencia centro.
 * 
 * @author brunosousa
 *
 */
@RestController
@RequestMapping(value = "/api/pedidostransferenciacentro", produces = "application/json;charset=UTF-8")
public class PedidoTransferenciaCentroController {

	@Autowired
	private IPedidoTransferenciaCentroService pedidoTransferenciaCentroService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Lista todos os pedidos de transferencias de centro.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR + "')")
	public JsonViewPage<TarefaDTO> listarTarefasTransportadoraParaUsuarioLogado(
			@RequestParam(required = true) Boolean atribuidosAMin,
			@RequestParam(required = true) Long idParceiro,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
					
		return pedidoTransferenciaCentroService.listarTarefas(atribuidosAMin, idParceiro, new PageRequest(pagina, quantidadeRegistros));
	}
	
	/**
	 * Método para obter o pedido de transferencia de centro.
	 * 
	 * @param idPedidoTransferenciaCentro  Identificador do pedido de transferencia de centro
	 * @return DTO contendo o pedido de transferencia de centro e a ultima evolução do paciente.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR + "')")
	@JsonView(PedidoTransferenciaCentroView.Detalhe.class)
	public ResponseEntity<PedidoTransferenciaCentroDTO> obterPedidoTransferenciaCentro(
			@PathVariable(name="id", required=true) Long idPedidoTransferenciaCentro) {
		return new ResponseEntity<PedidoTransferenciaCentroDTO>(pedidoTransferenciaCentroService.obterPedidoTransferenciaCentroDTO(idPedidoTransferenciaCentro), HttpStatus.OK);
	}
	
	
	/**
	 * Método para recusar o pedido de transferência de centro de um paciente.
	 * 
	 * @param idPedidoTransferenciaCentro - Identificador do pedido de transferencia do centro.
	 * @param justificativa - Justificativa da recusa.
	 * @return mesangem de sucesso.
	 */
	@RequestMapping(value = "{id}/recusar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR + "')")	
	public ResponseEntity<CampoMensagem> recusarPedidoTransferenciaCentro(
			@PathVariable(name="id", required=true) Long idPedidoTransferenciaCentro,
			@RequestBody(required = true) String justificativa) {
		
		pedidoTransferenciaCentroService.reprovarTransferencia(idPedidoTransferenciaCentro, justificativa);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "pedido.transferencia.centroavaliador.recusado"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para aceitar o pedido de transferência de centro de um paciente.
	 * 
	 * @param idPedidoTransferenciaCentro - Identificador do pedido de transferencia do centro.
	 * @return mesangem de sucesso.
	 */
	@RequestMapping(value = "{id}/aceitar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR + "')")	
	public ResponseEntity<CampoMensagem> recusarPedidoTransferenciaCentro(
			@PathVariable(name="id", required=true) Long idPedidoTransferenciaCentro) {
		
		pedidoTransferenciaCentroService.aceitarTransferencia(idPedidoTransferenciaCentro);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "pedido.transferencia.centroavaliador.aceito"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	
	
	
}
