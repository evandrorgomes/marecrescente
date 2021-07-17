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
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Estratégia a ser executada quando o pedido de exame de CT de um paciente
 * é criado.
 * 
 */
@Component
public class PedidoExameCtCriadoObserver extends EntityObserver<PedidoExame> {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPacienteService pacienteService;

	
	@Override
	public boolean condition(PedidoExame pedidoExame) {
		
		return StatusPedidosExame.AGUARDANDO_AMOSTRA.getId().equals(pedidoExame.getStatusPedidoExame().getId())
				&& OwnerPedidoExame.PACIENTE.equals(pedidoExame.getOwner())
				&& pedidoExame.getDataColetaAmostra() == null && pedidoExame.getDataRecebimentoAmostra() == null
				&& pedidoExame.getExame() == null;
	}

	@Override
	public void process(PedidoExame pedidoExame) {
		final Paciente paciente = pacienteService.obterPacientePorSolicitacaoCT(pedidoExame.getSolicitacao().getId());
		final Usuario usuario = paciente.getMedicoResponsavel().getUsuario();
		String mensagem = null;
		
		if(pedidoExame.getTipoExame().getId().equals(TiposExame.TESTE_CONFIRMATORIO_SWAB.getId())) {
			mensagem = AppUtil.getMensagem(messageSource, "pedido.exame.fase3.swab.criado.para.paciente");
		}
		else {
			mensagem = AppUtil.getMensagem(messageSource, "pedido.exame.fase3.criado.para.paciente");
		}
		
		CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(mensagem)
			.apply();
	}

}
