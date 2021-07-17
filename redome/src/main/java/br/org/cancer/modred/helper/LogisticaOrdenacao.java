package br.org.cancer.modred.helper;

import java.time.LocalDate;
import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.domain.TiposTarefa;

/**
 * Define como deve funcionar a ordenação da lista de tarefas de logística.
 * Esta lógica será aplicada ao tipo de tarefa LOGISTICA que deverá aplicá-la a todos os
 * outros tipos agrupados. 
 * A ordenação deverá ser por: 
 * 		1. Aging
 * 		2. Processo (Ordem de prioridade: material, coleta e workup)
 * 
 * @author Pizão
 *
 */
public class LogisticaOrdenacao implements Comparator<TarefaDTO> {

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		TiposTarefa tipoTarefa1 = TiposTarefa.valueOf(tarefa1.getTipo().getId());
		TiposTarefa tipoTarefa2 = TiposTarefa.valueOf(tarefa2.getTipo().getId());
		
		final LocalDate dataAging1 = obterDataAging(tipoTarefa1, (PedidoLogistica) tarefa1.getObjetoRelacaoEntidade());
		final LocalDate dataAging2 = obterDataAging(tipoTarefa2, (PedidoLogistica) tarefa2.getObjetoRelacaoEntidade());
		
		if(dataAging1.isBefore(dataAging2)){
			return -1;
		}
		else if(dataAging1.isAfter(dataAging2)){
			return 1;
		}
		else {
			Integer prioridadeTarefa1 = obterOrdemPrioridade(tipoTarefa1);
			Integer prioridadeTarefa2 = obterOrdemPrioridade(tipoTarefa2);
			return prioridadeTarefa1.compareTo(prioridadeTarefa2);
		}
	}
	
	/**
	 * Obtém a data considerada no aging da lista de tarefa de acordo com o tipo de tarefa (processo).
	 * 
	 * @param tipoTarefa tipo de tarefa a ser utilizado como filtro.
	 * @param pedidoLogistica pedido de logística que contém a entidade que definirá de onde virá a data de aging.
	 * @return data de acordo com o processo (aging).
	 */
	private LocalDate obterDataAging(TiposTarefa tipoTarefa, PedidoLogistica pedidoLogistica){
		switch(tipoTarefa){
			case INFORMAR_DOCUMENTACAO_MATERIAL_AEREO:
			case PEDIDO_LOGISTICA_DOADOR_COLETA:
			case PEDIDO_LOGISTICA_CORDAO_NACIONAL:
				LocalDate dataDisponibilidadeDoador = pedidoLogistica.getPedidoColeta().getDataDisponibilidadeDoador();
				if(dataDisponibilidadeDoador != null){
					return dataDisponibilidadeDoador;
				}
				return pedidoLogistica.getPedidoColeta().getDataColeta();
			case INFORMAR_LOGISTICA_DOADOR_WORKUP:
				return pedidoLogistica.getPedidoWorkup().getDataInicioWorkup();
			default:
				throw new IllegalArgumentException("Tipo de tarefa não não pertence a lista de tarefas de logística: " + tipoTarefa);
		}
	}
	
	/**
	 * De acordo com o tipo passado, retorna a ordem de prioridade que ele deve ter na lista de tarefas.
	 * 
	 * @param tipoTarefa tipo de tarefa a ser verificado.
	 * @return ordem da prioridade na lista.
	 */
	private Integer obterOrdemPrioridade(TiposTarefa tipoTarefa){
		switch(tipoTarefa){
			case INFORMAR_DOCUMENTACAO_MATERIAL_AEREO:
			case PEDIDO_LOGISTICA_CORDAO_NACIONAL:
				return 1;
			case PEDIDO_LOGISTICA_DOADOR_COLETA:
				return 2;
			case INFORMAR_LOGISTICA_DOADOR_WORKUP:
				return 3;
			default:
				throw new IllegalArgumentException("Tipo de tarefa não não pertence a lista de tarefas de logística: " + tipoTarefa);
		}
	}

}
