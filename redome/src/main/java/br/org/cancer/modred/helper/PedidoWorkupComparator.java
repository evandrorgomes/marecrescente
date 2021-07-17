package br.org.cancer.modred.helper;

import java.time.LocalDate;
import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;
import br.org.cancer.modred.model.domain.StatusTarefa;

/**
 * @author Usuario
 *
 */
public class PedidoWorkupComparator implements Comparator<TarefaDTO> {

	/**
	 * Regra ordenação: Quando status da tarefa estiver atribuida, deve aplicar o seguinte criterio: 1º critério de ordenação. 
	 * EM_ANALISE, AGUARDANDO_CT, OUTROS STATUS, dataCriação (pedido_workup) Independente do status da tarefa deverá aplicar o
	 * segundo critério: 2º critério de ordenação: Se dataResultadoWorkup 1 > dataResultadoWorkup 2 então ordena pela
	 * dataResultadoWorkup2 senao ordena pela dataResultadoWorkup1
	 */
	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		if (tarefa1.getObjetoRelacaoEntidade() instanceof PedidoWorkup) {
			PedidoWorkup pedidoWorkupTarefa1 = (PedidoWorkup) tarefa1.getObjetoRelacaoEntidade();
			PedidoWorkup pedidoWorkupTarefa2 = (PedidoWorkup) tarefa2.getObjetoRelacaoEntidade();
			if (pedidoWorkupTarefa2 == null) {
				return -1;
			}
			
			if (StatusTarefa.ATRIBUIDA.equals(tarefa1.getStatus())) {

				if (StatusPedidosWorkup.REALIZADO.getId().equals(pedidoWorkupTarefa1.getStatusPedidoWorkup().getId())) {
					if (pedidoWorkupTarefa1.getStatusPedidoWorkup().equals(pedidoWorkupTarefa2.getStatusPedidoWorkup())) {
						return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
					}
					return 1;
				}
				if (StatusPedidosWorkup.AGUARDANDO_CT.getId().equals(pedidoWorkupTarefa1.getStatusPedidoWorkup().getId())) {
					if (pedidoWorkupTarefa1.getStatusPedidoWorkup().equals(pedidoWorkupTarefa2.getStatusPedidoWorkup())) {
						return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
					}
					return -1;
				}
				if (StatusPedidosWorkup.AGENDADO.getId().equals(pedidoWorkupTarefa1.getStatusPedidoWorkup().getId())){
					if (pedidoWorkupTarefa1.getStatusPedidoWorkup().equals(pedidoWorkupTarefa2.getStatusPedidoWorkup())) {
						return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
					}
					return -1;
				}
				if (StatusPedidosWorkup.EM_ANALISE.getId().equals(pedidoWorkupTarefa1.getStatusPedidoWorkup().getId())) {
					if (pedidoWorkupTarefa1.getStatusPedidoWorkup().equals(pedidoWorkupTarefa2.getStatusPedidoWorkup())) {
						return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
					}
					return -1;
				}
				if (StatusPedidosWorkup.CONFIRMADO_CT.getId().equals(pedidoWorkupTarefa1.getStatusPedidoWorkup().getId())) {
					if (pedidoWorkupTarefa1.getStatusPedidoWorkup().equals(pedidoWorkupTarefa2.getStatusPedidoWorkup())) {
						return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
					}
					return -1;
				}

			}
			else {
				return segundoCriterioOrdenacao(pedidoWorkupTarefa1, pedidoWorkupTarefa2);
			}

		}
		return 0;
	}

	/**
	 * Critério de data de criação do pedido de workup.
	 * 
	 * @param pedidoWorkupTarefa1
	 * @param pedidoWorkupTarefa2
	 * @return
	 */
	private int segundoCriterioOrdenacao(PedidoWorkup pedidoWorkupTarefa1, PedidoWorkup pedidoWorkupTarefa2) {
		if (pedidoWorkupTarefa1.getDataCriacao().compareTo(pedidoWorkupTarefa2.getDataCriacao()) < 0) {
			return -1;
		}

		if (pedidoWorkupTarefa1.getDataCriacao().compareTo(pedidoWorkupTarefa2.getDataCriacao()) > 0) {
			return 1;
		}
		return criterioMenorDataResultadoWorkup(pedidoWorkupTarefa1, pedidoWorkupTarefa2);

	}

	private int criterioMenorDataResultadoWorkup(PedidoWorkup pedidoWorkupTarefa1, PedidoWorkup pedidoWorkupTarefa2) {
		LocalDate dataPedidoWorkup1 = getDataComparacaoPedidoWorkup(pedidoWorkupTarefa1);
		LocalDate dataPedidoWorkup2 = getDataComparacaoPedidoWorkup(pedidoWorkupTarefa2);

		return dataPedidoWorkup1.compareTo(dataPedidoWorkup2);
	}

	/**
	 * Retorna a menor data dentre o par de datas de resultado da prescrição.
	 * 
	 * @param pedidoWorkupTarefa
	 * @return
	 */
	private LocalDate getDataComparacaoPedidoWorkup(PedidoWorkup pedidoWorkupTarefa) {
		LocalDate dataPedidoWorkup = null;
		if (pedidoWorkupTarefa.getSolicitacao().getPrescricao().getDataResultadoWorkup1().compareTo(pedidoWorkupTarefa
				.getSolicitacao().getPrescricao().getDataResultadoWorkup2()) > 0) {
			dataPedidoWorkup = pedidoWorkupTarefa.getSolicitacao().getPrescricao().getDataResultadoWorkup2();
		}
		else {
			dataPedidoWorkup = pedidoWorkupTarefa.getSolicitacao().getPrescricao().getDataResultadoWorkup1();
		}
		return dataPedidoWorkup;
	}

}
