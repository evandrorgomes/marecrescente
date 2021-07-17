package br.org.cancer.modred.controller;

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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.model.AvaliacaoPedidoColeta;
import br.org.cancer.modred.service.impl.AvaliacaoPedidoColetaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe controladora para expor métodos rest de Avaliação de Pedido de Coleta.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoespedidocoleta", produces = "application/json;charset=UTF-8")
public class AvaliacaoPedidoColetaController {

	@Autowired
	private AvaliacaoPedidoColetaService avaliacaoPedidoColetaService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Obtém um avaliação do pedido de coleta por id.
	 * 
	 *@param idAvaliacaoPedidoColeta - id da avaliação.
	 *@return Avaliação do Pedido de Coleta populada.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('AVALIAR_PEDIDO_COLETA')")
	@JsonView(AvaliacaoPedidoColetaView.Detalhe.class)
	public ResponseEntity<AvaliacaoPedidoColeta> obterResultadoParaAvaliacao(
			@PathVariable(name = "id", required = true) Long idAvaliacaoPedidoColeta) {
		return new ResponseEntity<AvaliacaoPedidoColeta>(avaliacaoPedidoColetaService.obterAvaliacaoDePedidoDeColetaPorId(idAvaliacaoPedidoColeta), HttpStatus.OK);
	}
	
	
	/**
	 * Grava avaliação de pedido de coleta.
	 * 
	 * @param idAvaliacao - identificação
	 * @param avaliacaoPedidoColeta - avaliacao a ser gravada.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "{idAvaliacao}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('AVALIAR_PEDIDO_COLETA')")
	public ResponseEntity<CampoMensagem> negarAvaliacaoResultadoWorkup(@PathVariable(required = true) Long idAvaliacao,
			@RequestBody(required = false) AvaliacaoPedidoColeta avaliacaoPedidoColeta) {
		avaliacaoPedidoColetaService.salvarAvaliacao(idAvaliacao, avaliacaoPedidoColeta);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"avaliacao.pedido.coleta.sucesso_gravacao")),
				HttpStatus.OK);
	}

}
