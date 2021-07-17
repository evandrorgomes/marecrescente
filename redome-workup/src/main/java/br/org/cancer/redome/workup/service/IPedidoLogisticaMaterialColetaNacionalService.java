package br.org.cancer.redome.workup.service;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DetalheLogisticaMaterialDTO;
import br.org.cancer.redome.workup.dto.LogisticaMaterialTransporteDTO;
import br.org.cancer.redome.workup.dto.TarefaLogisticaMaterialDTO;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaNacional;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Logistica de material.
 * @author ergomes
 *
 */
public interface IPedidoLogisticaMaterialColetaNacionalService extends IService<PedidoLogisticaMaterialColetaNacional, Long> {

	/**
	 * Lista todoas as tarefas do tipo Logistica.
	 * 
	 * @param pagina - número da pagina.
	 * @param quantidadeRegistros - quantidade de registro por página.
	 * @return Page<TarefaDTO>  - Lista de objeto TarefaDTO paginado.
	 */
	Page<TarefaLogisticaMaterialDTO> listarTarefasPedidoLogisticaMaterialColetaNacional(int pagina, int quantidadeRegistros) throws JsonProcessingException;
	
	DetalheLogisticaMaterialDTO obterPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica);
	
	void salvarPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica, DetalheLogisticaMaterialDTO detalhe);
	
	void finalizarPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica, DetalheLogisticaMaterialDTO detalhe) throws JsonProcessingException;
	
	PedidoLogisticaMaterialColetaNacional criarPedidoLogisticaMaterialColetaNacional(PedidoColeta pedidoColeta);
	
	LogisticaMaterialTransporteDTO obterPedidoLogisticaMaterialParaTransportadora(Long id);

	DetalheLogisticaMaterialDTO obterPedidoLogisticaMaterialColetaAerea(Long idPedidoLogistica);
	
//	Page<TarefaAcompanhamentoLogisticaMaterialDTO> listarTarefasAcompanhamentoLogisticaMaterial(int pagina, int quantidadeRegistros) throws JsonProcessingException;

	/**
	 * Atualiza o pedido de logistica de material para coleta em aéreo.
	 *  
	 * @param id Identificador do pedido de logistica
	 */
	void atualizarLogisticaMaterialParaAereo(Long id);
	
	boolean pedidoLogisticaEstaFinalizadoSemJustificativa(Long idPedidoColeta);

}

