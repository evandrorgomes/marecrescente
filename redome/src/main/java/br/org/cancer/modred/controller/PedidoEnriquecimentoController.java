package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.service.IPedidoEnriquecimentoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Controlador para a entidade de busca.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/pedidosenriquecimento", produces = "application/json;charset=UTF-8")
public class PedidoEnriquecimentoController {

	@Autowired
	private IPedidoEnriquecimentoService pedidoEnriquecimentoService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Método para fechar o pedido de enriquecimento.
	 * 
	 * @param idPedidoEnriquecimento - identificado do pedido
	 * @return CampoMensagem - objeto com a mensagem de retorno
	 */
	@PutMapping(value = "{id}")
	@PreAuthorize("hasPermission('ENRIQUECER_DOADOR')")
	public ResponseEntity<CampoMensagem> fecharPedidoEnriquecimento(@PathVariable(name = "id") Long idPedidoEnriquecimento) {

		pedidoEnriquecimentoService.fecharPedidoEnriquecimento(idPedidoEnriquecimento);
		return ResponseEntity.ok(new CampoMensagem("", AppUtil.getMensagem(messageSource, "pedido.fechado.sucesso")));
	}
	
	/**
	 * Obtém a primeira tarefa disponível de pedido de enriquecimento.
	 * 
	 * @return tarefa já atribuída para o usuário logado.
	 */
	@GetMapping(value="primeiro")
	@JsonView(BuscaView.Enriquecimento.class)
	@PreAuthorize("hasPermission('ENRIQUECER_DOADOR')")
	public ResponseEntity<TarefaDTO> obterPrimeiraTarefaDeContato() {
		
		return ResponseEntity.ok().body(pedidoEnriquecimentoService.obterPrimeiroPedidoEnriquecimentoDaFilaDeTarefas() );		
	}

	/**
	 * Método para fechar o pedido de enriquecimento e retorna os dados do doador.
	 * 
	 * @param idPedidoEnriquecimento - identificado do pedido
	 * @param idDoador - identificador do doador do pedido de contato
	 * @return ConsultaDoadorNacionalVo - retorna o objeto VO com as informações do doador
	 */
	@PutMapping(value = "{id}/doador/{idDoador}")
	@PreAuthorize("hasPermission('ENRIQUECER_DOADOR')")
	public ResponseEntity<ConsultaDoadorNacionalVo> fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(
			@PathVariable(name = "id") Long idPedidoEnriquecimento,
			@PathVariable(name = "idDoador") Long idDoador){
		return ResponseEntity.ok().body(pedidoEnriquecimentoService.fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(idPedidoEnriquecimento,idDoador));
	}
}