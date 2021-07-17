package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPedidoEnvioEmdisService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;


/**
 * Classe para expor métodos REST de pedido de envio de paciente emdis.
 * @author Filipe paes
 *
 */
@RestController
@RequestMapping(value = "/api/pedidosenvioemdis", produces = "application/json;charset=UTF-8")
public class PedidoEnvioEmdisController {
	
	@Autowired
	private IPedidoEnvioEmdisService pedidoEnvioEmdisService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Cria novos pedidos de envio de dados para emdis.
	 * @param idBusca identificação da busca do paciente.
	 * @return mensagem de sucesso. 
	 */
	@PostMapping("/")
	@PreAuthorize("hasPermission('" + Recurso.CRIAR_PEDIDO_ENVIO_PACIENTE_EMDIS + "')")
	public ResponseEntity<CampoMensagem> criarPedidoEnvioEmdis(@RequestParam("idbusca") Long idBusca){
		pedidoEnvioEmdisService.criarPedido(idBusca);
		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.envio.emdis.enviado.sucesso")));
	}
	

}
