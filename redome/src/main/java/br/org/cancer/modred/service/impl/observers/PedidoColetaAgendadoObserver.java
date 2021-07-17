/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusPedidosColeta;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Estratégia a ser executada quando a busca é liberada.
 * 
 */
@Component
public class PedidoColetaAgendadoObserver extends EntityObserver<PedidoColeta> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean condition(PedidoColeta pedidoColeta) {
		return StatusPedidosColeta.AGENDADO.getId().equals(pedidoColeta.getStatusPedidoColeta().getId());
	}

	@Override
	public void process(PedidoColeta pedidoColeta) {
		final Match match = pedidoColeta.getSolicitacao().getMatch();
		final Paciente paciente = match.getBusca().getPaciente();
		IDoador<?> doador = (IDoador<?>) match.getDoador();
		
		CategoriasNotificacao.PEDIDO_COLETA.getConfiguracao()
			.criar()
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(match.getBusca().getCentroTransplante().getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(
				AppUtil.getMensagem(messageSource, "pedido.coleta.agendado.centro_transplantador.notificacao", 
						doador.getIdentificacao().toString(), paciente.getRmr())
			)
			.apply();
	}

}
