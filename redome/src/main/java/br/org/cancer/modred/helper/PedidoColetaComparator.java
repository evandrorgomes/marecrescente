/**
 * 
 */
package br.org.cancer.modred.helper;

import java.time.LocalDate;
import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * @author Usuario
 *
 */
public class PedidoColetaComparator implements Comparator<TarefaDTO> {
	
	/**
	 * Regra ordenação: MO Nacional ou internacional utilizará a data de disponibilidade do doador que está no pedido de workup. 
	 * Cordão Nacionnal e Internacional será utilizada a menor data informada na prescrição.
	 * 
	 */
	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		if (tarefa1.getObjetoRelacaoEntidade() instanceof PedidoColeta) {
			PedidoColeta pedidoColetaTarefa1 = (PedidoColeta) tarefa1.getObjetoRelacaoEntidade();
			PedidoColeta pedidoColetaTarefa2 = (PedidoColeta) tarefa2.getObjetoRelacaoEntidade();
			if (pedidoColetaTarefa2 == null) {
				return -1;
			}

			LocalDate primeiraData = null;
			LocalDate segundaData = null;
			
			if(ehCordao(pedidoColetaTarefa1)) {
				return 0;
			}
			
			if (ehMedula(pedidoColetaTarefa1)) {
				primeiraData = pedidoColetaTarefa1.getPedidoWorkup().getDataPrevistaDisponibilidadeDoador();
			}
			else {
				primeiraData = retornaMenorDataColetaPrescricao(pedidoColetaTarefa1);
			}
			
			if (ehMedula(pedidoColetaTarefa2)) {
				segundaData = pedidoColetaTarefa2.getPedidoWorkup() != null ? 
						pedidoColetaTarefa2.getPedidoWorkup().getDataPrevistaDisponibilidadeDoador() : null;
			}
			else {
				segundaData = retornaMenorDataColetaPrescricao(pedidoColetaTarefa2);
			}
			
			if(primeiraData != null && segundaData != null) {
				return primeiraData.compareTo(segundaData);
			}
		}
		return 0;
	}
	
	/**
	 * Verifica se o tipo do doador é medula.
	 * 
	 * @param pedidoColeta
	 * @return true - se o doador dor medula
	 */
	private boolean ehMedula(PedidoColeta pedidoColeta) {
		return TiposDoador.NACIONAL.getId().equals(pedidoColeta.getSolicitacao().getMatch().getDoador().getTipoDoador().getId()) ||
				TiposDoador.INTERNACIONAL.getId().equals(pedidoColeta.getSolicitacao().getMatch().getDoador().getTipoDoador().getId());
	}

	private boolean ehCordao(PedidoColeta pedidoColeta) {
		return TiposDoador.CORDAO_NACIONAL.getId().equals(pedidoColeta.getSolicitacao().getMatch().getDoador().getTipoDoador().getId()) ||
				TiposDoador.CORDAO_INTERNACIONAL.getId().equals(pedidoColeta.getSolicitacao().getMatch().getDoador().getTipoDoador().getId());
	}
	
	/**
	 * Retorna a menor data dentre o par de datas de resultado da prescrição.
	 * 
	 * @param pedidoWorkupTarefa
	 * @return
	 */
	private LocalDate retornaMenorDataColetaPrescricao(PedidoColeta pedidoColeta) {
		Prescricao prescricao = pedidoColeta.getSolicitacao().getPrescricao(); 
		if (prescricao.getDataColeta1().compareTo(prescricao.getDataColeta2()) < 0) {
			return prescricao.getDataColeta1();
		}
		else {
			return prescricao.getDataColeta2();
		}
	}

}
