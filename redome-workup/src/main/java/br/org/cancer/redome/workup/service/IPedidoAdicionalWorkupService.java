package br.org.cancer.redome.workup.service;

import java.util.List;

import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface para métodos de negócios de Pedido adicional de Resultado Workup.
 * 
 * @author ergomes
 *
 */
public interface IPedidoAdicionalWorkupService extends IService<PedidoAdicionalWorkup, Long>{

	/**
	 * Salvar um pedido adicional para o analista de workup - Doador Nacional.
	 * 
	 * @param descrição - relação dos exames do medico ao analista workup
	 * @param idPedidoWorkup - identificador do pedido de Workup
	 */
	String criarPedidoAdicionalDoadorNacional(Long idPedidoWorkup, String descricao);

	/**
	 * Salvar um pedido adicional para o analista de workup - Doador Internacional.
	 * 
	 * @param descrição - relação dos exames do medico ao analista workup
	 * @param idPedidoWorkup - identificador do pedido de workup
	 */
	String criarPedidoAdicionalDoadorInternacional(Long idPedidoWorkup, String descricao);

	/**
	 * Listar pedidos adicionais de workup por id do pedido de workup.
	 * 
	 * @param idPedidoWorkup - identificador do pedido de workup
	 * @return List<PedidoAdicionalWorkup> - Lista de pedido adicional de workup populado 
	 */
	List<PedidoAdicionalWorkup> listarPedidosAdicionaisWorkupPorIdPedidoWorkup(Long idPedidoWorkup);

	/**
	 * obter pedido adicional de workup por id do pedido adicional.
	 * 
	 * @param idPedidoAdicional - identificador do pedido adicional de workup
	 * @return PedidoAdicionalWorkup - objeto pedido adicional de workup populado 
	 */
	PedidoAdicionalWorkup obterPedidoAdicionalWorkup(Long idPedidoAdicional);

	/**
	 * finalizar pedido adicional de workup.
	 * 
	 * @param idPedidoAdicionalWorkup - identificador do pedido adicional de workup
	 * @param List<ArquivoPedidoAdicionalWorkup> - lista de arquivos de pedido adicional de workup.
	 * 
	 * @return String - mensagem de retorno.
	 */
	String finalizarPedidoAdicionalWorkup(PedidoAdicionalWorkup pedidoAdicionalWorkup);
	
}
