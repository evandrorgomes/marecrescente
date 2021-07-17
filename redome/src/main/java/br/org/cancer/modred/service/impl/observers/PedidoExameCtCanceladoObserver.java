/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.OwnerPedidoExame;
import br.org.cancer.modred.model.domain.StatusPedidosExame;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Estratégia a ser executada quando o pedido de exame de CT de um paciente
 * é cancelado.
 * 
 */
@Component
public class PedidoExameCtCanceladoObserver extends EntityObserver<PedidoExame> {

	@Autowired
	private MessageSource messageSource;

	
	@Override
	public boolean condition(PedidoExame pedidoExame) {
		return StatusPedidosExame.CANCELADO.getId().equals(pedidoExame.getStatusPedidoExame().getId())
				&& OwnerPedidoExame.PACIENTE.equals(pedidoExame.getOwner());
	}

	@Override
	public void process(PedidoExame pedidoExame) {
		final Paciente paciente = pedidoExame.getSolicitacao().getBusca().getPaciente();
		final Usuario usuario = paciente.getMedicoResponsavel().getUsuario();
		
		CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(AppUtil.getMensagem(messageSource, "notificacao.pedido.exame.ct.cancelado"))
			.apply();
	}

}
